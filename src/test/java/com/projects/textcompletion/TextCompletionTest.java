package com.projects.textcompletion;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

public class TextCompletionTest {
    private static TextCompletion textCompletion;

    @BeforeClass
    public static void setup() throws IOException {
        textCompletion = TextCompletionFactory.getInstance();
        textCompletion.loadEntries(Files.lines(new File("words.txt").toPath()).collect(Collectors.toList()));
    }

    @Test
    public void testTextComplete() {
        for (int i = 0; i < 5; i++)
            textCompletion.recordHit("hello");
        for (int i = 0; i < 3; i++)
            textCompletion.recordHit("headache");
        textCompletion.recordHit("heat");

        List<String> result = textCompletion.complete("he");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        for (String tok : result)
            System.out.println(tok);
    }
}
