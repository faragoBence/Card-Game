package com.codecool.api.comparators.minion;

import com.codecool.api.Minion;

import java.util.Comparator;

class ManaCostDesc implements Comparator<Minion> {

    @Override
    public int compare(Minion o1, Minion o2) {
        return o2.getManaCost() - o1.getManaCost();
    }
}
