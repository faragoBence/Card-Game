package com.codecool.api;

import com.codecool.api.exceptions.EntityIsDeadException;

public abstract class Entity {

    private String name;
    private int health;
    private int healthCap;
    private boolean isAlive = true;

    public Entity(String name, int health) {
        this.name = name;
        this.health = health;
        this.healthCap = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void increaseHealthCap(int amount) {
        healthCap += amount;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void takeDamage(int amount) {
        health -= amount;
        isAlive = health > 0;
    }

    public void heal(int amount) throws EntityIsDeadException {
        checkIfAlive();
        health += amount;
        if (health > healthCap) {
            health = healthCap;
        }
    }

    protected void checkIfAlive() throws EntityIsDeadException {
        if (!isAlive) {
            throw new EntityIsDeadException();
        }
    }
}
