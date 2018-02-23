package com.codecool.api;

import com.codecool.api.exceptions.EntityIsDeadException;
import com.codecool.api.exceptions.WrongTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicCardTest {

    private MagicCard magicCard;
    private Player player;
    private Minion minion;

    @BeforeEach
    void setUp() {
        player = new Player("testName", 20);
        minion = new Minion("Rightous Protector", 2, "xy", 1, 2);
    }

    @Test
    void drawCard() throws EntityIsDeadException, WrongTargetException {
        magicCard = new MagicCard("Divine Favor", 1, "Draw 3 cards", 3);
        for (int i = 0; i < 4; i++) {
            player.addToDeck(minion);
        }
        assertEquals(4, player.getDeck().size());
        assertEquals(0, player.getHand().size());

        assertThrows(WrongTargetException.class, () -> magicCard.doMagic(minion));
        assertThrows(WrongTargetException.class, () -> magicCard.doMagic(magicCard));

        magicCard.doMagic(player);
        assertEquals(1, player.getDeck().size());
        assertEquals(3, player.getHand().size());
    }

    @Test
    void heal() throws EntityIsDeadException, WrongTargetException {
        magicCard = new MagicCard("Divine Favor", 1, "Restore 4 amount of health to the target", 3);

        player.takeDamage(10);
        assertEquals(10, player.getHealth());

        magicCard.doMagic(player);
        assertEquals(14, player.getHealth());

        minion.takeDamage(1);
        assertEquals(1, minion.getHealth());

        magicCard = new MagicCard("Divine Favor", 1, "Restore 1 amount of health to the target", 3);
        magicCard.doMagic(minion);
        assertEquals(2, minion.getHealth());
    }

    @Test
    void setHealth() throws EntityIsDeadException, WrongTargetException {
        magicCard = new MagicCard("Divine Favor", 1, "Make 1 a minion's health", 3);

        assertThrows(WrongTargetException.class, () -> magicCard.doMagic(player));
        assertThrows(WrongTargetException.class, () -> magicCard.doMagic(magicCard));

        assertEquals(2, minion.getHealth());
        magicCard.doMagic(minion);
        assertEquals(1, minion.getHealth());

        magicCard = new MagicCard("Divine Favor", 1, "Make 43 a minion's health", 3);
        magicCard.doMagic(minion);
        assertEquals(43, minion.getHealth());
    }

    @Test
    void doDamage() throws EntityIsDeadException, WrongTargetException {
        magicCard = new MagicCard("Divine Favor", 1, "Do 6 damage to a minion", 3);

        assertThrows(WrongTargetException.class, () -> magicCard.doMagic(player));
        assertThrows(WrongTargetException.class, () -> magicCard.doMagic(magicCard));

        magicCard.doMagic(minion);
        assertEquals(-4, minion.getHealth());
        assertFalse(minion.isAlive());
    }

    @Test
    void setHealthAndDamage() throws EntityIsDeadException, WrongTargetException {
        magicCard = new MagicCard("Divine Favor", 1, "Give 2 health and attack to a minion", 3);

        assertThrows(WrongTargetException.class, () -> magicCard.doMagic(player));
        assertThrows(WrongTargetException.class, () -> magicCard.doMagic(magicCard));

        magicCard.doMagic(minion);
        assertEquals(4, minion.getHealth());
        assertEquals(4, minion.getAttack());
    }
}