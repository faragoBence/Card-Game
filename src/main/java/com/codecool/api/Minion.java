package com.codecool.api;

import com.codecool.api.exceptions.CanNotAttackException;
import com.codecool.api.exceptions.SelfTargetException;

public class Minion extends Card {

    private int attack;

    private boolean canAttack;

    private String ability = "Nothing";

    public Minion(String name, int health, String description, int manaCost, int attack) {
        super(name, health, description, manaCost);
        this.attack = attack;
        this.canAttack = false;
        createAbility();
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


    public String getAbility() {
        return ability;
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

    public void attack(Player player) throws CanNotAttackException {
        if (canAttack) {
            if (player.attackable()) {
                player.takeDamage(attack);
                setCanAttack(false);
            } else {
                throw new CanNotAttackException();
            }
        } else {
            throw new CanNotAttackException();
        }
    }

    @Override
    public String toString() {
        return "name= " + getName() +
                ", manaCost= " + getManaCost() +
                ", attack= " + getAttack() +
                ", health= " + getHealth() +
                ", ability= " + getDescription() +
                ", can attack= " + canAttack();
    }

    public void createAbility() {
        String[] desc = getDescription().split(" ");
        if (desc[0].equals("Battlecry:")) {
            ability = desc[1];
        }
    }
}
