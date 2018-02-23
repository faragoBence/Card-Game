package com.codecool.api.comparators.magicCard;

import com.codecool.api.MagicCard;

import java.util.Comparator;

public class ManaCostAsc implements Comparator<MagicCard> {

    @Override
    public int compare(MagicCard o1, MagicCard o2) {
        return o1.getManaCost() - o2.getManaCost();
    }
}
