package com.codecool.api;

import com.codecool.api.Iterators.CardIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardIteratorTest {

    private Card card1;
    private Card card2;
    private CardIterator iterator;

    @BeforeEach
    void setUp() {
        List<Card> cards = new ArrayList<>();
        card1 = new Minion("testName1", 5, "testDescription1", 4, 3);
        card2 = new Minion("testName2", 11, "testDescription2", 45, 76);
        cards.add(card1);
        cards.add(card2);
        iterator = new CardIterator(cards);
    }

    @Test
    void testIterator() {
        assertTrue(iterator.hasNext());

        Card returnedCard = iterator.next();
        assertEquals(card1, returnedCard);

        returnedCard = iterator.next();
        assertEquals(card2, returnedCard);

        assertFalse(iterator.hasNext());

        returnedCard = iterator.next();
        assertEquals(null, returnedCard);
    }

}