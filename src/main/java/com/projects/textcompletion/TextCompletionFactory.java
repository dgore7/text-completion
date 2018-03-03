package com.projects.textcompletion;

import com.projects.textcompletion.TextCompletionImpl;

import java.util.List;

public class TextCompletionFactory {
    public static TextCompletion getInstance() {
        return new TextCompletionImpl();
    }
    public static TextCompletion getInstance(List<String> entries) {
        return new TextCompletionImpl(entries);
    }
}
