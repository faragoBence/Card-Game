package com.codecool.api;

import com.codecool.api.exceptions.EntityIsDeadException;
import com.codecool.api.exceptions.NoMoreRoomOnDeskException;
import com.codecool.api.exceptions.NotEnoughManaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player extends Entity {

    private int currentMana = 0;
    private int maxMana = 0;
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> desk = new ArrayList<>();

    private int manacap = 10;
    private List<Card> deck = new ArrayList<>();
    private Hero hero;

    public Player(String name, int health) {
        super(name, health);
    }

    // Getters and Setters
    public int getMana() {
        return currentMana;
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

    public Hero getHero() {
        return hero;
    }


    public int getMaxMana() {
        return maxMana;
    }

    public int getManacap() {
        return manacap;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setMaxMana(int amount) {
        maxMana += amount;
    }

    public void setManacap(int amount) {
        manacap += 1;
    }


    // Method(s)
    public void manageMana() throws EntityIsDeadException {
        checkIfAlive();
        if (maxMana < manacap) {
            maxMana++;
        }
        currentMana = maxMana;
    }

    public void addToDeck(Card card) {
        deck.add(card);
    }

    public void cardDraw() {
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

    public void abillityDraw() {
        if (deck.size() > 0) {
            Card draw = deck.get(0);
            deck.remove(draw);
            if (hand.size() < 6) {
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

    public void deckCreating() {
        getHero().importAndShuffle();
        deck = getHero().getDeck();
    }

    public void placeCard(int index) throws NoMoreRoomOnDeskException, NotEnoughManaException {
        Card card = hand.get(index);
        if (card.getManaCost() > currentMana) {
            throw new NotEnoughManaException();
        }

        if (desk.size() < 5 || card instanceof MagicCard) {
            hand.remove(card);
            currentMana -= card.getManaCost();
            if (card instanceof Minion) {
                desk.add(card);

                if (card.getDescription().split(" ")[0].equals("Battlecry:")) {
                    ((Minion) card).useAbility(this);
                }
            }
        } else {
            throw new NoMoreRoomOnDeskException();
        }
    }

    public void placeWithoutMana(Card card) throws NoMoreRoomOnDeskException {
        if (desk.size() < 5) {
            desk.add(card);
        } else {
            throw new NoMoreRoomOnDeskException();
        }
    }

    public boolean attackable() {
        for (int i = 0; i < desk.size(); i++) {
            if (((Minion) desk.get(i)).getAbility().equals("Taunt")) {
                return false;
            }

        }
        return true;
    }

    public void clearField() throws NoMoreRoomOnDeskException {
        List<Card> desk = getDesk();
        for (int i = 0; i < desk.size(); i++) {
            if (!desk.get(i).isAlive()) {
                if (desk.get(i).getDescription().split(" ")[0].equals("Deathrattle:")) {
                    ((Minion) desk.get(i)).useAbility(this);
                }
                desk.remove(desk.get(i));
            }
        }
    }


    @Override
    public String toString() {
        return "name= " + getName() +
                ", health= " + getHealth() +
                ", currentMana= " + currentMana +
                ", maxMana= " + maxMana +
                ", Hero = " + hero.getName() +
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
