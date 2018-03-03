package com.projects.textcompletion;

import com.projects.textcompletion.util.Trie;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

class TextCompletionImpl implements TextCompletion {
    private Trie trie;
    private int maxHits = 10;

    TextCompletionImpl() {
        trie = new Trie();
    }

    TextCompletionImpl(List<String> entries) {
        trie = new Trie();
        for (String token : entries)
            trie.addEntry(token);
    }

    @Override
    public void loadEntries(List<String> entries) {
        for (String token : entries)
            trie.addEntry(token);
    }

    @Override
    public void recordHit(String target) {
        trie.updateFrequency(target);
    }

    @Override
    public List<String> complete(String prefix) {
        try {
            return trie.collectLeaves(prefix)
                    .stream()
                    .sorted(Comparator
                            .comparingLong(Trie.Leaf::getFrequency)
                            .reversed())
                    .limit(maxHits)
                    .map(Trie.Leaf::getToken).collect(toList());
        } catch (RuntimeException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public int getMaxHits() {
        return maxHits;
    }

    @Override
    public void setMaxHits(int maxHits) {
        this.maxHits = maxHits;
    }
}
