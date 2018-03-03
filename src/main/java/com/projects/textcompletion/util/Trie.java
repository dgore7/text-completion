package com.projects.textcompletion.util;

import java.util.*;

public class Trie {
    private TrieNode root = new TrieNode();

    static class TrieNode {
        boolean isLeaf;
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

    public Set<String> getSubTrie(String prefix) throws Exception {
        StringBuilder sb = new StringBuilder();
        Set<String> result = new HashSet<>();
        if (prefix == null)
            throw new IllegalArgumentException("prefix cannot be null");
        TrieNode current = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            current = current.get(c);
            sb.append(c);
            if (current == null)
                throw  new Exception("prefix does not exist");
        }
//        sb.append(current.data);
        dfs(current, sb, result);
        return result;
    }

    private void dfs(TrieNode node, StringBuilder sb, Set<String> result) {
        if (node == null)
            return;
        if (node.isLeaf)
            result.add(sb.toString());
        for (Character key : node.nextSet()) {
            sb.append(key);
            dfs(node.get(key), sb, result);
            sb.deleteCharAt(sb.length()-1);
        }
    }

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trie)) return false;
        Trie trie = (Trie) o;
        return Objects.equals(root, trie.root);
    }

    @Override
    public int hashCode() {

        return Objects.hash(root);
    }


}
