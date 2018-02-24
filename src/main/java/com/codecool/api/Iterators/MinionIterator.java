package com.codecool.api.Iterators;

import com.codecool.api.Minion;

import java.util.Iterator;
import java.util.List;

public class MinionIterator implements Iterator<Minion> {

    private final List<Minion> cards;
    private final int size;
    private int index;

    public MinionIterator(List<Minion> cards) {
        this.cards = cards;
        index = 0;
        size = cards.size();
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public Minion next() {
        if (hasNext()) {
            return cards.get(index++);
        }
        return null;
    }
}