package com.codecool.api.comparators.card;

import com.codecool.api.Card;

import java.util.Comparator;

public class ManaCostAsc implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return o1.getManaCost() - o2.getManaCost();
    }
}
