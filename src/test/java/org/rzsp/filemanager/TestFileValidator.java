package org.rzsp.filemanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class TestFileValidator {

    /// TEST validateSourceFileAndGetFile ///
    /// ********************************* ///
    /// ********************************* ///

    @Test
    void pathToSourceFileOrDirectoryIsCorrect() throws IOException {
        File tempFile = File.createTempFile("text", ".txt");
        tempFile.deleteOnExit();

        File result = FileValidator.validateSourceFileAndGetFile(tempFile.getAbsolutePath());
        assertNotNull(result);
        assertTrue(result.exists());
        assertEquals(tempFile.getAbsoluteFile(), result.getAbsoluteFile());
    }

    @Test
    void pathToSourceFileOrDirectoryIsNull() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileValidator.validateSourceFileAndGetFile(null)
        );

        assertEquals("Field \"Source file\" is null or blank", exception.getMessage());
    }

    @Test
    void pathToSourceFileOrDirectoryIsBlank() {
        String blankPath = "";

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileValidator.validateSourceFileAndGetFile(blankPath)
        );

        assertEquals("Field \"Source file\" is null or blank", exception.getMessage());
    }

    @Test
    void pathToSourceFileOrDirectoryIsSpaces() {
        String spacePath = "    ";

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileValidator.validateSourceFileAndGetFile(spacePath)
        );

        assertEquals("Field \"Source file\" is null or blank", exception.getMessage());
    }

    @Test
    void sourceFileIsNotExist() throws IOException {
        String pathToNotExistFile = "/home/user/file/not/exist.txt";

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileValidator.validateSourceFileAndGetFile(pathToNotExistFile)
        );

        assertEquals("File \""+ pathToNotExistFile + "\" is not found", exception.getMessage());
    }

    /// TEST validateDestinationAndGetFile ///
    /// ********************************* ///
    /// ********************************* ///

    @Test
    void pathToDestinationDirectoryIsCorrect() throws IOException {
        File tempFile = Files.createTempDirectory("dir").toFile();
        tempFile.deleteOnExit();

        File result = FileValidator.validateDestinationAndGetFile(tempFile.getAbsolutePath());
        assertNotNull(result);
        assertTrue(result.exists());
        assertEquals(tempFile.getAbsoluteFile(), result.getAbsoluteFile());
    }

    @Test
    void pathToDestinationDirectoryIsNull() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileValidator.validateDestinationAndGetFile(null)
        );

        assertEquals("Field \"Destination\" is null or blank", exception.getMessage());
    }

    @Test
    void pathToDestinationDirectoryIsBlank() {
        String blankPath = "";

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileValidator.validateDestinationAndGetFile(blankPath)
        );

        assertEquals("Field \"Destination\" is null or blank", exception.getMessage());
    }

    @Test
    void pathToDestinationDirectoryIsSpaces() {
        String spacesPath = "   ";

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileValidator.validateDestinationAndGetFile(spacesPath)
        );

        assertEquals("Field \"Destination\" is null or blank", exception.getMessage());
    }

}
