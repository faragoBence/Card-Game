package com.codecool.api.comparators.magicCard;

import com.codecool.api.MagicCard;

import java.util.Comparator;

public class NameDesc implements Comparator<MagicCard> {

    @Override
    public int compare(MagicCard o1, MagicCard o2) {
        return o2.compareTo(o1);
    }
}
