package com.structure.app;

import java.util.ArrayList;

class Combination {
    private ArrayList<String> combArray;
    private int occurrences;

    int getOccurrences() {
        return occurrences;
    }
    public ArrayList<String> getCombArray() {
        return combArray;
    }

    Combination(ArrayList<String> grammInfo) {
        combArray = grammInfo;
        occurrences = 1;
    }

    void increment() {
        occurrences++;
    }
}
