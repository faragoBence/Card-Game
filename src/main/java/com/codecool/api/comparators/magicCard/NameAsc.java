package com.codecool.api.comparators.magicCard;

import com.codecool.api.MagicCard;

import java.util.Comparator;

public class NameAsc implements Comparator<MagicCard> {

    @Override
    public int compare(MagicCard o1, MagicCard o2) {
        return o1.compareTo(o2);
    }
}
