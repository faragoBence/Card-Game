package com.codecool.api.comparators.minion;

import com.codecool.api.Minion;

import java.util.Comparator;

class NameAsc implements Comparator<Minion> {

    @Override
    public int compare(Minion o1, Minion o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
