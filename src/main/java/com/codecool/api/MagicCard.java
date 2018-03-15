package com.codecool.api;

import com.codecool.api.exceptions.NoMoreRoomOnDeskException;
import com.codecool.api.exceptions.WrongTargetException;

public class MagicCard extends Card {

    public MagicCard(String name, int health, String description, int manaCost) {
        super(name, health, description, manaCost);
    }

    //Magic
    public void setHealthAndDamage(Minion target, int amount) {
        target.increaseHealthCap(amount);
        target.setHealth(target.getHealth() + amount);
        target.setAttack(target.getAttack() + amount);
    }

    public void setHealth(Minion target, int amount) {
        target.setHealth(amount);
    }

    public void decreaseMana(Player enemy) {
        enemy.setMaxMana(-1);
    }

    public void damageAll(Player enemy, int amount) {
        for (Card card : enemy.getDesk()) {
            card.takeDamage(amount);
        }
    }

    @Override
    public void drawCard(Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            player.abillityDraw();
        }
    }


    public void doMagic(Entity target) throws WrongTargetException, NoMoreRoomOnDeskException {
        String[] args = getDescription().split(" ");
        switch (args[0]) {
            case "Draw":
                if (!(target instanceof Player)) {
                    throw new WrongTargetException();
                }
                drawCard(((Player) target), Integer.parseInt(args[1]));
                break;
            case "Give":
                if (!(target instanceof Minion)) {
                    throw new WrongTargetException();
                }
                setHealthAndDamage(((Minion) target), Integer.parseInt(args[1]));
                break;
            case "Do":
                if (!(target instanceof Minion)) {
                    throw new WrongTargetException();
                }
                doDamage(target, Integer.parseInt(args[1]));
                break;
            case "Make":
                if (!(target instanceof Minion)) {
                    throw new WrongTargetException();
                }
                setHealth(((Minion) target), Integer.parseInt(args[1]));
                break;
            case "Restore":
                heal(target, Integer.parseInt(args[1]));
                break;
            case "Summon":
                if (!(target instanceof Player)) {
                    throw new WrongTargetException();
                }
                for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                    summon(((Player) target), new Minion(args[2], Integer.parseInt(args[4]), "Summoned", 0, Integer.parseInt(args[7])));
                }
                break;
            case "Increase":
                increaseMana((Player) target);
                break;
            case "Decrease":
                if (!(target instanceof Player)) {
                    throw new WrongTargetException();
                }
                decreaseMana((Player) target);
                break;
            case "Deal":
                if (!(target instanceof Player)) {
                    throw new WrongTargetException();
                }
                damageAll((Player) target, Integer.parseInt(args[1]));
                break;
            case "Heal":
                if (!(target instanceof Player)) {
                    throw new WrongTargetException();
                }
                healAll((Player) target, Integer.parseInt(args[7]));
                break;
        }
        takeDamage(1);
    }

    @Override
    public String toString() {
        return "name= " + getName() +
                ", manaCost= " + getManaCost() +
                ", description= " + getDescription();
    }
}