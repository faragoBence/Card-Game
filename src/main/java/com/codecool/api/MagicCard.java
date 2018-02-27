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


    public void doMagic(Entity entity) throws WrongTargetException, NoMoreRoomOnDeskException {
        String[] args = getDescription().split(" ");
        switch (args[0]) {
            case "Draw":
                if (!(entity instanceof Player)) {
                    throw new WrongTargetException();
                }
                drawCard(((Player) entity), Integer.parseInt(args[1]));
                break;
            case "Give":
                if (!(entity instanceof Minion)) {
                    throw new WrongTargetException();
                }
                setHealthAndDamage(((Minion) entity), Integer.parseInt(args[1]));
                break;
            case "Do":
                if (!(entity instanceof Minion)) {
                    throw new WrongTargetException();
                }
                doDamage(entity, Integer.parseInt(args[1]));
                break;
            case "Make":
                if (!(entity instanceof Minion)) {
                    throw new WrongTargetException();
                }
                setHealth(((Minion) entity), Integer.parseInt(args[1]));
                break;
            case "Restore":
                heal(entity, Integer.parseInt(args[1]));
                break;
            case "Summon":
                if (!(entity instanceof Player)) {
                    throw new WrongTargetException();
                }
                for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                    summon(((Player) entity), new Minion(args[2], Integer.parseInt(args[4]), "Summoned", 0, Integer.parseInt(args[7])));
                }
                break;
            case "Increase":
                increaseMana((Player) entity);
                break;
            case "Decrease":
                if (!(entity instanceof Player)) {
                    throw new WrongTargetException();
                }
                decreaseMana((Player) entity);
                break;
            case "Deal":
                if (!(entity instanceof Player)) {
                    throw new WrongTargetException();
                }
                damageAll((Player) entity, Integer.parseInt(args[1]));
                break;
            case "Heal":
                if (!(entity instanceof Player)) {
                    throw new WrongTargetException();
                }
                healAll((Player) entity, Integer.parseInt(args[3]));
                break;
            default:
                heal(1);
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