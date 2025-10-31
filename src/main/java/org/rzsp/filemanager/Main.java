package org.rzsp.filemanager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        try {
            if (args.length != 2) {
                System.err.println("Usage: java Main <source file>, <destination directory>");
                return;
            }

            String pathToSourceFile = args[0];
            String pathToDestinationFile = args[1];

            FileManager fileManager = new FileManager(pathToSourceFile, pathToDestinationFile);
            fileManager.copy();
            System.out.println("Copied directory size: " + fileManager.getDirectorySize(fileManager.getDestinationDirectory()) + " bytes");

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }

}
