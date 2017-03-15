package com.structure.app;

import java.util.ArrayList;

class Combination {
    private ArrayList<String> combArray;

    ArrayList<String> getExampleArray() {
        return exampleList;
    }

    private ArrayList<String> exampleList;
    private int occurrences;

    int getOccurrences() {
        return occurrences;
    }
    ArrayList<String> getCombArray() {
        return combArray;
    }

    Combination(ArrayList<String> grammInfo, ArrayList<String> example) {
        this.combArray = (ArrayList<String>) grammInfo.clone();
        this.exampleList = (ArrayList<String>) example.clone();
        this.occurrences = 1;
    }

    void increment() {
        this.occurrences += 1;
    }
}
