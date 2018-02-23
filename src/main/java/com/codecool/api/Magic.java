package com.codecool.api;

import com.codecool.api.exceptions.EntityIsDeadException;

public interface Magic {
    void drawCard(Player player, int amount)throws EntityIsDeadException;
    void heal(Entity target, int amount)throws EntityIsDeadException;
    void setHealth(Minion target, int amount);
    void doDamage(Entity target,int amount)throws EntityIsDeadException;
    void setHealthAndDamage(Minion target,int amount);
}
