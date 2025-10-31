package org.rzsp.filemanager;

import java.io.File;
import java.io.IOException;

public class FileValidator {

    public static File validateSourceFile(String pathToSourceFile) throws IOException {
        if (pathToSourceFile == null || pathToSourceFile.isBlank()) {
            throw new IllegalArgumentException("Field \"Source file\" is empty");
        }

        File sourceFile = new File(pathToSourceFile);

        if (!sourceFile.exists()) {
            throw new IllegalArgumentException("File \""+ sourceFile + "\" is not found");
        }

        if (!sourceFile.canRead()) {
            throw new IOException("File \"" + sourceFile + "\" is not readable");
        }

        return sourceFile;
    }

    public static File validateDestination(String pathToDestinationDirectory) throws IOException {
        if (pathToDestinationDirectory == null || pathToDestinationDirectory.isBlank()) {
            throw new IllegalArgumentException("Field \"Destination\" is empty");
        }

        File destination = new File(pathToDestinationDirectory);

        if (destination.exists() && !destination.isDirectory()) {
            throw new IllegalArgumentException("Destination must be a directory: " + destination.getPath());
        }

        if (!destination.mkdirs()) {
            throw new IOException("Failed to create destination directory: " + destination.getPath());
        }

        return destination;
    }

}
