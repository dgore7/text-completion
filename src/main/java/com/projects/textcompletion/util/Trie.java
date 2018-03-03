package com.projects.textcompletion.util;

import java.util.*;

public class Trie {
    private TrieNode root = new TrieNode();

    static class TrieNode {
        boolean isLeaf;
        long frequency;
        Character data;
        Map<Character, TrieNode> next = new HashMap<>();

        boolean contains(Character c) {
            return next.containsKey(c);
        }

        void add(Character key, TrieNode node) {
            next.put(key,node);
        }

        TrieNode get(Character c) {
            return next.get(c);
        }

        Set<Character> nextSet() {
            return next.keySet();
        }
    }

    public Trie() {
    }

    public void addEntry(String entry) {
        TrieNode current = root;
        for (int i = 0; i < entry.length(); i++) {
            char c = entry.charAt(i);
            if (current.contains(c)) {
                current = current.get(c);
            } else {
                TrieNode node = new TrieNode();
                node.data = c;
                current.add(c, node);
                current = node;
            }
        }
        current.isLeaf = true;
    }

    public TrieNode getSubTrie(String prefix) throws RuntimeException {
        if (prefix == null)
            throw new IllegalArgumentException("prefix cannot be null");
        return walk(root, prefix);
    }

    private TrieNode walk(TrieNode current, String prefix) throws RuntimeException {
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            current = current.get(c);
            if (current == null)
                throw  new RuntimeException("prefix does not exist");
        }
        return current;
    }

    public void updateFrequency(String string) throws RuntimeException {
        TrieNode current = root;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            current = current.get(c);
            if (current == null)
                throw  new RuntimeException("prefix does not exist");
        }
        if (!current.isLeaf)
            throw  new RuntimeException("Leaf does not exist");
        current.frequency += 1;
    }

    public static class Leaf {
        long frequency;
        String token;

        Leaf(long frequency, String prefix) {
            this.frequency = frequency;
            this.token = prefix;
        }

        public long getFrequency() {
            return frequency;
        }

        public String getToken() {
            return token;
        }
    }

    public List<Leaf> collectLeaves(String prefix) {
        TrieNode node = getSubTrie(prefix);
        if (node == null)
            return Collections.emptyList();
        List<Leaf> result = new ArrayList<>();
        collectLeaves(node, new StringBuilder(prefix), result);
        return result;
    }

    private void collectLeaves(TrieNode node, StringBuilder sb, List<Leaf> leaves) {
        if (node == null)
            return;
        if (node.isLeaf)
            leaves.add(new Leaf(node.frequency, sb.toString()));
        for (Character key : node.nextSet()) {
            sb.append(key);
            collectLeaves(node.get(key), sb, leaves);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
