package com.codecool.api;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Objects;

public abstract class Card extends Entity implements Comparable<Card> {

    private String description;
    private int manaCost;
    private Player owner;

    private Image cardImg;


    // Constructor(s)
    public Card(String name, int health , String description, int manaCost) {
        super(name, health);
        this.description = description;
        this.manaCost = manaCost;
        searchCardImg();
    }

    // Getter(s)
    public String getDescription() {
        return description;
    }

    public int getManaCost() {
        return manaCost;
    }

    public Player getOwner() {
        return owner;
    }

    public Image getCardImg() {
        return cardImg;
    }

    // Setter(s)
    public void setOwner(Player newOwner) {
        owner = newOwner;
    }


    // Method(s)
    private void searchCardImg() {
        try {
            for (File file : new File(getClass().getResource("/cardimgs").getFile()).listFiles()) {
                if (file.getName().equals(getName())) {
                    cardImg = new Image(file.toURI().toString());
                }
            }
            if (cardImg == null) {
                cardImg = new Image(new File("/cardimgs/default.png").toURI().toString());
            }
        } catch (Exception e) {
        }
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
