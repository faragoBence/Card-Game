package com.codecool.api;

import com.codecool.api.exceptions.EntityIsDeadException;
import com.codecool.api.exceptions.NoMoreRoomOnDeskException;

interface Magic {
    void drawCard(Player player, int amount);
    void heal(Entity target, int amount)throws EntityIsDeadException;
    void setHealth(Minion target, int amount);

    void doDamage(Entity target, int amount);
    void setHealthAndDamage(Minion target,int amount);

    void summon(Player target, Minion minion) throws NoMoreRoomOnDeskException;
}
