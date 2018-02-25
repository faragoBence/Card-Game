package com.codecool.api;

import com.codecool.api.datamanager.HeroParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private final Player player1;
    private final Player player2;
    private final Random random = new Random();

    private List<Hero> heroes;

    public Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        createHeroList();
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    private void makeHand(Player player) {
        for (int i = 0; i < 2; i++) {
            player.cardDraw();
        }
    }

    public void start() {
        player1.deckCreating();
        player2.deckCreating();
        makeHand(player1);
        makeHand(player2);

    }

    private void createHeroList() {
        heroes = new HeroParser().getHeroes();
    }


    public void changeAttackState(Player player) {
        List<Card> desk = player.getDesk();
        for (Card aDesk : desk) {
            if (aDesk instanceof Minion) {
                if (!((Minion) aDesk).canAttack()) {
                    ((Minion) aDesk).setCanAttack(true);
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
