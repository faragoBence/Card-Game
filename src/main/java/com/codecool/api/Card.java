package com.codecool.api;
import java.util.Objects;

public abstract class Card extends Entity implements Comparable<Card> {

    private final String description;
    private final int manaCost;


    // Constructor(s)
    Card(String name, int health, String description, int manaCost) {
        super(name, health);
        this.description = description;
        this.manaCost = manaCost;
    }

    // Getter(s)
    public String getDescription() {
        return description;
    }

    public int getManaCost() {
        return manaCost;
    }


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
