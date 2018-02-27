package com.codecool.api;

import com.codecool.api.exceptions.CanNotAttackException;
import com.codecool.api.exceptions.NoMoreRoomOnDeskException;
import com.codecool.api.exceptions.SelfTargetException;
import com.codecool.api.exceptions.TargetisStealthException;

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
    //Getters and Setters

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
    public void attack(Minion targetCard) throws SelfTargetException, CanNotAttackException, TargetisStealthException {
        if (this == targetCard) {
            throw new SelfTargetException();
        }
        if (canAttack) {
            if (getAbility().equals("Stealth")) {
                ability = "Nothing";
            }
            int penaltyDamage = targetCard.getAttack();
            if (targetCard.getAbility() != "Stealth") {
                targetCard.takeDamage(attack);
                takeDamage(penaltyDamage);
                setCanAttack(false);
            } else {
                throw new TargetisStealthException();
            }
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

    public void createAbility() {
        String[] desc = getDescription().split(" ");
        if (desc[0].equals("Battlecry:") || desc[0].equals("Deathrattle:")) {
            ability = desc[1];
        }
    }

    public void useAbility(Player player) throws NoMoreRoomOnDeskException {
        String[] args = getDescription().split(" ");
        switch (ability) {
            case "Restore":
                heal(player, Integer.parseInt(args[5]));
                break;
            case "Draw":
                drawCard(player, Integer.parseInt(args[2]));
                break;
            case "Summon":
                for (int i = 0; i < Integer.parseInt(args[2]); i++) {
                    summon(player, new Minion(args[3], Integer.parseInt(args[5]), "Summoned", 0, Integer.parseInt(args[8])));
                }
                break;
            case "Increase":
                increaseMana(player);
                break;
            case "Charge":
                setCanAttack(true);
                break;
            case "Heal":
                healAll(player, Integer.parseInt(args[7]));
                break;

        }
    }


    @Override
    public String toString() {
        String str = "name= " + getName() +
                ", manaCost= " + getManaCost() +
                ", attack= " + getAttack() +
                ", health= " + getHealth();
        if (!(getAbility().equals("Nothing"))) {
            str += ", ability= " + getDescription();
        }
        if (canAttack()) {
            str += ", can attack";
        }
        return str;
    }
}
