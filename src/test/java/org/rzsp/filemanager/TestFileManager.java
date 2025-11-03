package org.rzsp.filemanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;


public class TestFileManager {

    @Test
    void testFileManagerConstructorWithSourceAndDestinationDirectoryEquals() throws IOException {
        File tempFile = Files.createTempDirectory("tempDir").toFile();

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new FileManager(tempFile.getAbsolutePath(), tempFile.getAbsolutePath())
        );

        assertEquals("Source directory and destination directory can not be the same", exception.getMessage());
    }

}
