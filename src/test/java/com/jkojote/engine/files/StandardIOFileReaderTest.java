package com.jkojote.engine.files;

import com.jkojote.linear.engine.utils.FileReader;
import com.jkojote.linear.engine.utils.StandardIOFileReader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StandardIOFileReaderTest {

    private FileReader fileReader = new StandardIOFileReader();

    @Test
    public void asString() throws IOException {
        String expected = "Text text Text\nText";
        String path = "src/test/java/com/jkojote/engine/files/file.txt";
        File file = new File(path);
        assertTrue(file.exists());
        String actualFromString = fileReader.asString(path);
        String actualFromFile = fileReader.asString(file);
        assertEquals(actualFromFile, actualFromString);
        assertEquals(expected, actualFromFile);
    }
}
