package org.rzsp.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Класс для работы с файлами и директориями.
 * Позволяет выполнять операцию копирования файла/директории и возвращает размер файлов в директории.
 *
 */
public class FileManager {

    private static final int BUFFER_SIZE = 8192; // Размер буфера для копирования файла

    private final File sourceFileOrDirectory;
    private final File destinationDirectory;

    public FileManager(String pathToSourceFile, String pathToDestinationDirectory) throws IOException {
        this.sourceFileOrDirectory = FileValidator.validateSourceFileAndGetFile(pathToSourceFile);
        this.destinationDirectory = FileValidator.validateDestinationAndGetFile(pathToDestinationDirectory);

        if (sourceFileOrDirectory.getCanonicalFile().equals(destinationDirectory.getCanonicalFile())) {
            throw new IllegalArgumentException("Source and destination file can not be the same");
        }
    }

    public void copy() throws IOException {

        if (sourceFileOrDirectory.isFile()) {
            copyFile(sourceFileOrDirectory, destinationDirectory);
        } else if (sourceFileOrDirectory.isDirectory()) {
            copyDirectory(sourceFileOrDirectory, destinationDirectory);
        } else {
            throw new IOException("Unsupported file type: " + sourceFileOrDirectory);
        }

    }

    public long getDirectorySize(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return 0;
        }

        return Arrays.stream(files)
                .mapToLong(file -> {
                    if (file.isDirectory()) {
                        return getDirectorySize(file);
                    } else if (file.isFile()) {
                        return file.length();
                    } else {
                        return 0;
                    }
                })
                .sum();
    }


    private void copyFile(File copiedFile, File destinationToCopy) throws IOException {
        destinationToCopy = new File(destinationToCopy, copiedFile.getName());

        try (
                FileInputStream in = new FileInputStream(copiedFile);
                FileOutputStream out = new FileOutputStream(destinationToCopy)
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

    }

    private void copyDirectory(File copiedDirectory, File destinationToCopy) throws IOException {
        File newDirectoryToCopy = new File(destinationToCopy, copiedDirectory.getName());

        File[] directoryFiles = copiedDirectory.listFiles();
        if (directoryFiles != null) {
            for (File file : directoryFiles) {
                if (file.isFile()) {
                    copyFile(file, newDirectoryToCopy);
                } else if (file.isDirectory()) {
                    copyDirectory(file, newDirectoryToCopy);
                }
            }
        }

    }

    public File getDestinationDirectory() { return destinationDirectory; }

}