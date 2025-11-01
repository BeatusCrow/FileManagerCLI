package org.rzsp.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Класс для работы с файлами и директориями.
 * Позволяет выполнять операцию копирования файла/директории и возвращает размер файлов в директории.
 */
public class FileManager {

    private static final int BUFFER_SIZE = 8192; // Размер буфера для копирования файла

    private final File sourceFileOrDirectory;
    private final File destinationDirectory;

    /**
     * Конструктор класса FileManager с указанием пути копируемого файла и директории назначения.
     *
     * @param pathToSourceFile путь к копируемогу файлу/директории
     * @param pathToDestinationDirectory путь к директории назначения
     * @throws IOException если произошла ошибка ввода
     * @throws IllegalArgumentException если копируемая директория совпадает с директорией назначения
     */
    public FileManager(String pathToSourceFile, String pathToDestinationDirectory) throws IOException {
        this.sourceFileOrDirectory = FileValidator.validateSourceFileAndGetFile(pathToSourceFile);
        this.destinationDirectory = FileValidator.validateDestinationAndGetFile(pathToDestinationDirectory);

        if (sourceFileOrDirectory.getCanonicalFile().equals(destinationDirectory.getCanonicalFile())) {
            throw new IllegalArgumentException("Source and destination file can not be the same");
        }
    }

    /**
     * Копирует файл либо директорию в указанную директорию назначения.
     * Определяет тип копируемого объекта и вызывает соотвествующий метод копирования.
     *
     * @throws IOException если копируемый объект не является файлом или директорией
     * @see #copyFile(File, File)
     * @see #copyDirectory(File, File)
     */
    public void copy() throws IOException {

        if (sourceFileOrDirectory.isFile()) {
            copyFile(sourceFileOrDirectory, destinationDirectory);
        } else if (sourceFileOrDirectory.isDirectory()) {
            copyDirectory(sourceFileOrDirectory, destinationDirectory);
        } else {
            throw new IOException("Unsupported file type: " + sourceFileOrDirectory);
        }

    }

    /**
     * Возвращает размер всех файлов в директории.
     * Если директория содержит другие директории, то рекурсирвно возвращается их размер и суммируется.
     *
     * @param directory директория, размер которой хотим вернуть
     * @return размер всех файлов в директории
     */
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

    /**
     * Копирует файл
     *
     * @param copiedFileOrDirectory копируемый файл/директория
     * @param destinationToCopy директория назначения, куда копируется copiedFileOrDirectory
     * @throws IOException если происходит ошибка ввода и вывода
     */
    private void copyFile(File copiedFileOrDirectory, File destinationToCopy) throws IOException {
        /*
        * Создает файл назначения, куда будет копироваться исходный файл
        * Например: мы копируем /home/user/example/text.txt в /home/user/test
        *           Код создаст /home/user/test/text.txt, где destinationToCopy -> /home/user/test, а copiedFileOrDirectory.getName() -> text.txt
         */
        destinationToCopy = new File(destinationToCopy, copiedFileOrDirectory.getName());

        try (
                FileInputStream in = new FileInputStream(copiedFileOrDirectory);
                FileOutputStream out = new FileOutputStream(destinationToCopy)
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

    }

    /**
     * Копирует директорию,
     *
     * @param copiedDirectory копируемая директория
     * @param destinationToCopy директория назначения, куда копируется copiedDirectory
     * @throws IOException
     */
    private void copyDirectory(File copiedDirectory, File destinationToCopy) throws IOException {
        /*
         * Создает файл назначения, куда будет копироваться исходная директория
         * Например: мы копируем /home/user/example/someDirectory в /home/user/test
         *           Код создаст /home/user/test/someDirectory, где destinationToCopy -> /home/user/test, а copiedFileOrDirectory.getName() -> someDirectory
         */
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