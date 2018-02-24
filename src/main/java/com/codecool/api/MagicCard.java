package com.codecool.api;

import com.codecool.api.exceptions.EntityIsDeadException;
import com.codecool.api.exceptions.NoMoreRoomOnDeskException;
import com.codecool.api.exceptions.WrongTargetException;

public class MagicCard extends Card implements Magic {

    public MagicCard(String name, int health, String description, int manaCost) {
        super(name, health, description, manaCost);
    }

    public void drawCard(Player player, int amount) {
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

    public void doDamage(Entity target, int amount) {
        target.takeDamage(amount);
    }

    public void setHealthAndDamage(Minion target, int amount) {
        target.increaseHealthCap(amount);
        target.setHealth(target.getHealth() + amount);
        target.setAttack(target.getAttack() + amount);
    }

    @Override
    public void summon(Player target, int amount, Minion minion) throws NoMoreRoomOnDeskException {
        for (int i = 0; i < amount; i++) {
            target.placeWithoutMana(minion);
        }
    }

    public void doMagic(Entity entity) throws EntityIsDeadException, WrongTargetException, NoMoreRoomOnDeskException {
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
            case "Summon":
                if (!(entity instanceof Player)) {
                    throw new WrongTargetException();
                }
                summon(((Player) entity), amount, new Minion(args[2], Integer.parseInt(args[4]), "Summoned", 0, Integer.parseInt(args[7])));
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