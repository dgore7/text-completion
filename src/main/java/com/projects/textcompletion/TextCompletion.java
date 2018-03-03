package com.projects.textcompletion;

import java.util.List;

public interface TextCompletion {
    void loadEntries(List<String> entries);

    void recordHit(String target);

    List<String> complete(String prefix);

    int getMaxHits();

    void setMaxHits(int maxHits);
}
