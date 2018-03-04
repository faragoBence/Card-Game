package com.codecool.api;

import com.codecool.api.exceptions.NoMoreRoomOnDeskException;

import java.util.Objects;

public abstract class Card extends Entity implements Comparable<Card> {

    private final String description;
    private final int manaCost;

    String imagePath;

    Card(String name, int health, String description, int manaCost) {
        super(name, health);
        this.description = description;
        this.manaCost = manaCost;
        imagePath = "src/main/resources/cardimgs/" + name + ".png";
    }

    // Getters
    public String getDescription() {
        return description;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getImagePath() {
        return imagePath;
    }

    //Abilities
    public void drawCard(Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            player.cardDraw();
        }
    }

    public void heal(Entity target, int amount) {
        target.heal(amount);
    }

    public void doDamage(Entity target, int amount) {
        target.takeDamage(amount);
    }

    public void summon(Player target, Minion minion) throws NoMoreRoomOnDeskException {
        target.placeWithoutMana(minion);
    }

    public void increaseMana(Player target) {
        target.setManacap(1);
        target.setMaxMana(1);
    }

    public void healAll(Player target, int amount) {
        for (Card card : target.getDesk()) {
            card.heal(amount);
        }
    }


    //Methods
    @Override
    public String toString() {
        return "name= " + getName() +
                ", description= " + getDescription() +
                ", manaCost= " + getManaCost() +
                ", health= " + getHealth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(getName(), card.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getManaCost(), getHealth());
    }

    @Override
    public int compareTo(Card o) {
        return getName().compareTo(o.getName());
    }

}
