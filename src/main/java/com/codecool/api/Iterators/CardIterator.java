package com.codecool.api.Iterators;

import com.codecool.api.Card;

import java.util.Iterator;
import java.util.List;

public class CardIterator implements Iterator<Card>{

    private final List<Card> cards;
    private int index;
    private final int size;

    public CardIterator(List<Card> cards){
        this.cards = cards;
        index = 0;
        size = cards.size();
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public Card next() {
        if (hasNext()) {
            return cards.get(index++);
        }
        return null;
    }
}
