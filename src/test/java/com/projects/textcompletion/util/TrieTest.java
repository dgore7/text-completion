package com.projects.textcompletion.util;

import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TrieTest {

    @Test
    public void testTrie() throws Exception {
        Trie trie = new Trie();
        trie.addEntry("abcd");
        trie.addEntry("abcc");
        trie.addEntry("abdd");
        trie.addEntry("hello");
        Set<String> result = trie.collectLeaves("ab")
                .stream()
                .map(Trie.Leaf::getToken).collect(Collectors.toSet());
        assertTrue(result.contains("abcd"));
        assertTrue(result.contains("abcc"));
        assertTrue(result.contains("abdd"));
        assertFalse(result.contains("hello"));
    }
}
