package com.codecool.api;

import com.codecool.api.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player extends Entity {

    private int currentMana = 0;
    private int maxMana = 0;
    private final int MANACAP = 10;

    private List<Card> hand = new ArrayList<>();
    private List<Card> desk = new ArrayList<>();
    private List<Card> deck = new ArrayList<>();

    // Constructor(s)
    public Player(String name, int health) {
        super(name, health);
    }

    // Getter(s)
    public int getMana() {
        return currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getDesk() {
        return desk;
    }

    public List<Card> getDeck() {
        return deck;
    }

    // Method(s)
    public void manageMana() throws EntityIsDeadException {
        checkIfAlive();
        if (maxMana < MANACAP) {
            maxMana++;
        }
        currentMana = maxMana;
    }

    public void addToDeck(Card card) {
        deck.add(card);
    }

    public void cardDraw() throws EntityIsDeadException {
        if (deck.size() > 0) {
            Card draw = deck.get(0);
            deck.remove(draw);
            if (hand.size() < 5) {
                hand.add(draw);
            }
        } else {
            takeDamage(1);
        }
    }

    public void startRound() throws EntityIsDeadException {
        manageMana();
        cardDraw();
    }

    public void placeCard(int index) throws NoMoreRoomOnDeskException, NotEnoughManaException {
        Card card = hand.get(index);
        if (card.getManaCost() > currentMana) {
            throw new NotEnoughManaException();
        }

        if (desk.size() < 5) {
            hand.remove(card);
            desk.add(card);
            currentMana -= card.getManaCost();
        } else {
            throw new NoMoreRoomOnDeskException();
        }
    }

    @Override
    public String toString() {
        return "name= " + getName() +
                ", health= " + getHealth() +
                ", currentMana= " + currentMana +
                ", maxMana= " + maxMana +
                "\nAmount of cards in deck: " + deck.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(getName(), player.getName());
    }
}
