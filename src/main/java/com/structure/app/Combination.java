package com.structure.app;

import java.util.ArrayList;

public class Combination {
    public ArrayList<String> combArray = new ArrayList<>();
    public int occurrences;

    public Combination(String grammInfo) {
        combArray.add(grammInfo);
        occurrences = 1;
    }

    public Combination() {
        occurrences = 0;
    }
}
