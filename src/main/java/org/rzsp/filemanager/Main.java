package org.rzsp.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * Главный класс для выполнения функции копирования файла/директории и вывода размера директории, куда копируется файл/директория.
     *
     * @param args:
     *            args[0] - путь к копируемому файлу/директории;
     *            args[1] - путь директории, куда копируется файл или файлы директории.
     * @throws IOException ошибка ввода вывода
     */
    public static void main(String[] args) throws IOException {
        logger.info("Application is started");

        try {
            if (args.length != 2) {
                logger.error("Usage: java Main <source file>, <destination directory>");
                return;
            }

            String pathToSourceFile = args[0];
            String pathToDestinationFile = args[1];

            FileManager fileManager = new FileManager(pathToSourceFile, pathToDestinationFile);
            fileManager.copy();
            logger.info("Copied directory size: {} bytes", fileManager.getDirectorySize(fileManager.getDestinationDirectory()));

        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
        } finally {
            logger.info("Application is finished");
        }

    }

}
