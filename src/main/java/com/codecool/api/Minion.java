package com.codecool.api;

import com.codecool.api.exceptions.CanNotAttackException;
import com.codecool.api.exceptions.EntityIsDeadException;
import com.codecool.api.exceptions.SelfTargetException;

public class Minion extends Card {

    private int attack;

    private boolean canAttack;

    public Minion(String name, int health, String description, int manaCost, int attack) {
        super(name, health, description, manaCost);
        this.attack = attack;
        this.canAttack = false;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }


    public boolean canAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }


    // Method(s)
    public void attack(Minion targetCard) throws SelfTargetException, CanNotAttackException {
        if (this == targetCard) {
            throw new SelfTargetException();
        }
        if (canAttack) {
            int penaltyDamage = targetCard.getAttack();
            targetCard.takeDamage(attack);
            takeDamage(penaltyDamage);
            setCanAttack(false);
        } else {
            throw new CanNotAttackException();
        }
    }
    public void attack(Player player) {
        player.takeDamage(attack);
        setCanAttack(false);
    }

    @Override
    public String toString() {
        return "name= " + getName() +
                ", manaCost= " + getManaCost() +
                ", attack= " + getAttack() +
                ", health= " + getHealth() +
                ", can attack= " + canAttack();
    }
}
