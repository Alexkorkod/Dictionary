package com.structure.app;

import java.util.ArrayList;

class Combination {
    private ArrayList<String> combArray;
    private ArrayList<String> exampleList;
    private int occurrences;
    private ArrayList<Position> positionsList = new ArrayList<>();

    int getOccurrences() {
        return occurrences;
    }
    ArrayList<String> getCombArray() {
        return combArray;
    }
    ArrayList<String> getExampleArray() {
        return exampleList;
    }
    ArrayList<Position> getPositionsArray() {
        return positionsList;
    }

    Combination(ArrayList<String> grammInfo, ArrayList<String> example, Position.PositionEnum position) {
        this.combArray = new ArrayList<>(grammInfo);
        this.exampleList = new ArrayList<>(example);
        this.occurrences = 1;
        this.positionsList.add(new Position(position));
    }

    void increment() {
        this.occurrences += 1;
    }

    int findPositionPosition(Position.PositionEnum positionEnum) {
        int key = 0;
        for (Position position : positionsList) {
            if (position.getPosition().equals(positionEnum)) {
                return key;
            }
            key++;
        }
        return -1;
    }
}
