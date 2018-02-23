package com.codecool.api;

import com.codecool.api.exceptions.EntityIsDeadException;
import com.codecool.api.exceptions.NoMoreRoomOnDeskException;
import com.codecool.api.exceptions.NotEnoughManaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setUp() {
        player = new Player("testName", 20);
    }

    @Test
    void takeDamage() {
        assertEquals(20, player.getHealth());
        assertTrue(player.isAlive());

        player.takeDamage(5);
        assertEquals(15, player.getHealth());
        assertTrue(player.isAlive());

        player.takeDamage(18);
        assertEquals(-3, player.getHealth());
        assertFalse(player.isAlive());

        player.takeDamage(3);
    }

    @Test
    void heal() throws EntityIsDeadException {
        assertEquals(20, player.getHealth());
        player.heal(5);
        assertEquals(20, player.getHealth());

        player.takeDamage(8);
        player.heal(2);
        assertEquals(14, player.getHealth());

        player.takeDamage(20);
        assertThrows(EntityIsDeadException.class, () -> player.heal(2));
    }

    @Test
    void manageMana() throws EntityIsDeadException {
        assertEquals(0, player.getMana());

        player.manageMana();
        assertEquals(1, player.getMana());

        player.manageMana();
        player.manageMana();
        assertEquals(3, player.getMana());

        for (int i = 0; i < 7; i++) {
            player.manageMana();
        }
        assertEquals(10, player.getMana());

        player.manageMana();
        assertEquals(10, player.getMana());

        player.takeDamage(20);
        assertThrows(EntityIsDeadException.class, () -> player.manageMana());
    }

    @Test
    void placeCard() throws EntityIsDeadException {
        Minion card1 = new Minion("testName1", 5, "testDescription1", 2, 3);
        Minion card2 = new Minion("testName2", 8, "testDescription2", 4, 4);

        player.addToDeck(card1);
        assertEquals(1, player.getDeck().size());

        player.cardDraw();
        assertEquals(20, player.getHealth());
        assertEquals(1, player.getHand().size());
        assertEquals(card1, player.getHand().get(0));

        player.cardDraw();
        assertEquals(19, player.getHealth());
        assertEquals(1, player.getHand().size());

        player.addToDeck(card2);
        player.cardDraw();
        assertEquals(19, player.getHealth());
        assertEquals(2, player.getHand().size());
        assertEquals(card2, player.getHand().get(1));
    }
}