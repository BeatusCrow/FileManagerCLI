package org.rzsp.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Класс валидатор пути к копируемому файлу/директории и пути назначения.
 * Имеет статические методы для проверки существования и корректности пути
 */
public class FileValidator {
    private static final Logger logger = LogManager.getLogger(FileValidator.class);

    /**
     * Преобразует строковый путь к копируемому файлу/директории в объект {@link File} и выполняет его валидацию.
     * Проверяет существование файла/директории и возможность чтения.
     *
     * @param pathToSourceFileOrDirectory путь к файлу или директории
     * @return копируемый файл/директория в виде объекта {@link File}, прошедший валидацию
     * @throws IllegalArgumentException если путь null, пустой или не существует
     * @throws IOException если файл недоступен для чтения
     */
    public static File validateSourceFileAndGetFile(String pathToSourceFileOrDirectory) throws IOException {
        logger.debug("Validating source path: {}", pathToSourceFileOrDirectory);

        if (pathToSourceFileOrDirectory == null || pathToSourceFileOrDirectory.isBlank()) {
            throw new IllegalArgumentException("Field \"Source file\" is null or blank");
        }

        File sourceFileOrDirectory = new File(pathToSourceFileOrDirectory);

        if (!sourceFileOrDirectory.exists()) {
            throw new IllegalArgumentException("File \""+ sourceFileOrDirectory + "\" is not found");
        }

        if (!sourceFileOrDirectory.canRead()) {
            throw new IOException("File \"" + sourceFileOrDirectory + "\" is not readable");
        }

        logger.debug("Validation for source path is successful");

        return sourceFileOrDirectory;
    }

    /**
     * Преобразует строковый путь к директории назначения в объект {@link File} и выполняет его валидацию.
     * Проверяет существование пути и является ли объект {@link File} директорией.
     *
     * @param pathToDestinationDirectory путь к директории назначения
     * @return директория назначения в виде объекта {@link File}, прошедшая валидацию
     * @throws IllegalArgumentException если путь к директории назначения пуст или ведет не к директории
     * @throws IOException если не удается создать структуру директорий назначения
     */
    public static File validateDestinationAndGetFile(String pathToDestinationDirectory) throws IOException {
        logger.debug("Validation destination path: {}", pathToDestinationDirectory);

        if (pathToDestinationDirectory == null || pathToDestinationDirectory.isBlank()) {
            throw new IllegalArgumentException("Field \"Destination\" is null or blank");
        }

        File destination = new File(pathToDestinationDirectory);

        if (destination.exists() && !destination.isDirectory()) {
            throw new IllegalArgumentException(destination + " must be a directory");
        }

        if (!destination.exists() && !destination.mkdirs()) { // Создает директорию назначения, а так же создает родительские директории если они не существуют
            throw new IOException("Failed to create parent directories or destination directory");
        }

        logger.debug("Validation for destination path is successful");

        return destination;
    }

}
