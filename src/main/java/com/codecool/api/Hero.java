package com.codecool.api;

import com.codecool.api.datamanager.CardParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hero {
    private final String name;
    private final String imagePath;
    private final String fullImagePath;
    private final Random random = new Random();
    private final List<Card> deck;

    public Hero(String name, String imagePath, String fullImagePath) {
        this.fullImagePath = fullImagePath;
        this.name = name;
        this.imagePath = imagePath;
        deck = new ArrayList<>();
    }

    //Getters
    public String getName() {
        return name;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getFullImagePath() {
        return fullImagePath;
    }

    //Method
    public void importAndShuffle() {
        List<Card> unshuffled = new CardParser("src/main/resources/decks/" + getName() + ".xml").getCards();
        while (unshuffled.size() != 0) {
            int rand = random.nextInt(unshuffled.size());
            Card card = unshuffled.get(rand);
            deck.add(card);
            unshuffled.remove(card);
        }
    }
}
