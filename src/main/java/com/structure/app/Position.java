package com.structure.app;


class Position {
    private PositionEnum position;
    private int occurrences;
    public enum PositionEnum {FIRST, LAST, ELSE}

    Position(PositionEnum position) {
        this.position = position;
        this.occurrences = 1;
    }

    int getOccurrences() {
        return occurrences;
    }

    PositionEnum getPosition() {
        return position;
    }

    void increment() {
        this.occurrences += 1;
    }
}
