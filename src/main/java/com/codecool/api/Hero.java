package com.codecool.api;

import com.codecool.api.datamanager.CardParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hero {
    private final String name;
    private final String imagePath;
    private final Random random = new Random();

    private final List<Card> deck;

    public Hero(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
        deck = new ArrayList<>();
    }

    public void importAndShuffle() {
        List<Card> unshuffled = new CardParser("src/main/resources/decks/" + getName() + ".xml").getCards();
        while (unshuffled.size() != 0) {
            int rand = random.nextInt(unshuffled.size());
            Card card = unshuffled.get(rand);
            deck.add(card);
            unshuffled.remove(card);
        }
    }

    public String getName() {
        return name;
    }

    public List<Card> getDeck() {
        return deck;
    }
}
