package com.codecool.api;

import com.codecool.api.exceptions.CanNotAttackException;
import com.codecool.api.exceptions.EntityIsDeadException;
import com.codecool.api.exceptions.SelfTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinionTest {

    private Minion card1;
    private Minion card2;

    @BeforeEach
    void setUp() {
        card1 = new Minion("testName1", 5, "testDescription1", 4, 3);
        card2 = new Minion("testName2", 11, "testDescription2", 45, 6);
    }

    @Test
    void testEquals() {
        assertNotEquals(card1, card2);

        card2 = new Minion("testName1", 11, "testDescription2", 45, 6);
        assertEquals(card1, card2);
    }

    @Test
    void testAttack() throws SelfTargetException, CanNotAttackException {
        assertThrows(SelfTargetException.class, () -> card1.attack(card1));
        assertFalse(card1.canAttack());
        assertThrows(CanNotAttackException.class, () -> card1.attack(card2));

        card1.setCanAttack(true);
        assertTrue(card1.canAttack());
        card1.attack(card2);
        assertFalse(card1.isAlive());
        assertEquals(8, card2.getHealth());
        assertTrue(card2.isAlive());

        assertThrows(SelfTargetException.class, () -> card2.attack(card2));
        card2.setCanAttack(true);
        card2.attack(card1);
    }
}