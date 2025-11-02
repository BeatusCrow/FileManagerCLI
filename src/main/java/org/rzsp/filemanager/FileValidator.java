package org.rzsp.filemanager;

import java.io.File;
import java.io.IOException;


/**
 * Класс валидатор пути к копируемому файлу/директории и пути назначения.
 * Имеет статические методы для проверки существования и корректности пути
 */
public class FileValidator {

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
        if (pathToSourceFileOrDirectory == null || pathToSourceFileOrDirectory.isBlank()) {
            throw new IllegalArgumentException("Field \"Source file\" is empty");
        }

        File sourceFileOrDirectory = new File(pathToSourceFileOrDirectory);

        if (!sourceFileOrDirectory.exists()) {
            throw new IllegalArgumentException("File \""+ sourceFileOrDirectory + "\" is not found");
        }

        if (!sourceFileOrDirectory.canRead()) {
            throw new IOException("File \"" + sourceFileOrDirectory + "\" is not readable");
        }

        return sourceFileOrDirectory;
    }

    /**
     * Преобразует строковый путь к директории назначения в объект {@link File} и выполняет его валидацию.
     * Проверяет существование пути и является ли объект {@link File} директорией.
     *
     * @param pathToDestinationDirectory путь к директории назначения
     * @return директория назначения в виде объекта {@link File}, прошедшая валидацию
     * @throws IllegalArgumentException если путь к директории назначения пуст
     * @throws IOException если не удается создать структуру директорий назначения
     */
    public static File validateDestinationAndGetFile(String pathToDestinationDirectory) throws IOException {
        if (pathToDestinationDirectory == null || pathToDestinationDirectory.isBlank()) {
            throw new IllegalArgumentException("Field \"Destination\" is empty");
        }

        File destination = new File(pathToDestinationDirectory);

        if (!destination.isDirectory() && !destination.mkdirs()) { // Создает директорию назначения, а так же создает родительские директории если они не существуют
            throw new IOException("Failed to create directories");
        }

        if (!destination.isDirectory()) {
            throw new IllegalArgumentException("Destination must be a directory: " + destination);
        }

        return destination;
    }

}
