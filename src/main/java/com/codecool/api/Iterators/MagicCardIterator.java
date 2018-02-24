package com.codecool.api.Iterators;

import com.codecool.api.MagicCard;

import java.util.Iterator;
import java.util.List;

public class MagicCardIterator implements Iterator<MagicCard> {

    private final List<MagicCard> cards;
    private final int size;
    private int index;

    public MagicCardIterator(List<MagicCard> cards) {
        this.cards = cards;
        index = 0;
        size = cards.size();
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public MagicCard next() {
        if (hasNext()) {
            return cards.get(index++);
        }
        return null;
    }
}
