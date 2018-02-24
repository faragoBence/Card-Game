package com.codecool.api.Iterators;

import com.codecool.api.Hero;

import java.util.Iterator;
import java.util.List;

public class HeroIterator implements Iterator<Hero> {

    private final List<Hero> heroes;
    private final int size;
    private int index;

    public HeroIterator(List<Hero> heroes) {
        this.heroes = heroes;
        index = 0;
        size = heroes.size();
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public Hero next() {
        if (hasNext()) {
            return heroes.get(index++);
        }
        return null;
    }
}

