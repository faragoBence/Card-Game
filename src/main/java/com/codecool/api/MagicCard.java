package com.codecool.api;

import com.codecool.api.exceptions.*;

public class MagicCard extends Card implements Magic {

    public MagicCard(String name, int health, String description, int manaCost) {
        super(name, health, description, manaCost);
    }

    public void drawCard(Player player, int amount) throws EntityIsDeadException {
        for (int i = 0; i < amount; i++) {
            player.cardDraw();
        }
    }

    public void heal(Entity target, int amount) throws EntityIsDeadException {
        target.heal(amount);
    }

    public void setHealth(Minion target, int amount) {
        target.setHealth(amount);
    }

    public void doDamage(Entity target, int amount) throws EntityIsDeadException {
        target.takeDamage(amount);
    }

    public void setHealthAndDamage(Minion target, int amount) {
        target.increaseHealthCap(amount);
        target.setHealth(target.getHealth() + amount);
        target.setAttack(target.getAttack() + amount);
    }

    public void doMagic(Entity entity) throws EntityIsDeadException, WrongTargetException {
        String[] args = getDescription().split(" ");
        int amount = Integer.parseInt(args[1]);
        switch (args[0]) {
            case "Draw":
                if (!(entity instanceof Player)) {
                    throw new WrongTargetException();
                }
                drawCard(((Player) entity), amount);
                break;
            case "Give":
                if (!(entity instanceof Minion)) {
                    throw new WrongTargetException();
                }
                setHealthAndDamage(((Minion) entity), amount);
                break;
            case "Do":
                if (!(entity instanceof Minion)) {
                    throw new WrongTargetException();
                }
                doDamage(entity, amount);
                break;
            case "Make":
                if (!(entity instanceof Minion)) {
                    throw new WrongTargetException();
                }
                setHealth(((Minion) entity), amount);
                break;
            case "Restore":
                heal(entity, amount);
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