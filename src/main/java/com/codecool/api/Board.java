package com.codecool.api;

import com.codecool.api.datamanager.CardParser;
import com.codecool.api.exceptions.EntityIsDeadException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    List<Card> deck;
    Player player1;
    Player player2;
    Random random = new Random();

    public Board(Player player1, Player player2) {
        deck = new CardParser("src/main/resources/cardcollection.xml").getCards();
        this.player1 = player1;
        this.player2 = player2;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void makeDecks(Player player) {
        int rand = random.nextInt(deck.size());
        Card card = deck.get(rand);
        card.setOwner(player);
        player.addToDeck(card);
        deck.remove(card);
    }

    public void makeHand(Player player) throws EntityIsDeadException {
        for (int i = 0; i < 2; i++) {
            player.cardDraw();
        }
    }

    public void start() throws EntityIsDeadException {
        Player player = player1;
        while (deck.size() != 0) {
            makeDecks(player);
            if (player.equals(player1)) {
                player = player2;
            } else {
                player = player1;
            }
        }
        makeHand(player1);
        makeHand(player2);
    }

    public void clearField(Player player) {
        List<Card> desk = player.getDesk();
        for (int i = 0; i < desk.size(); i++) {
            if (!desk.get(i).isAlive()) {
                desk.remove(desk.get(i));
            }
        }
    }

    public void changeAttackState(Player player) {
        List<Card> desk = player.getDesk();
        for (int i = 0; i < desk.size(); i++) {
            if (desk.get(i) instanceof Minion) {
                if (!((Minion) desk.get(i)).canAttack()) {
                    ((Minion) desk.get(i)).setCanAttack(true);
                }
            }
        }
    }

    public List<Player> randomizeStart() {
        List<Player> players = new ArrayList<>();
        int num = random.nextInt(2);
        if (num == 0) {
            players.add(player1);
            players.add(player2);
        } else {
            players.add(player2);
            players.add(player1);
        }
        return players;
    }

}
