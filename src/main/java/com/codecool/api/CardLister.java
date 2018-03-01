package com.codecool.api;

import com.codecool.api.comparators.card.ManaCostAsc;
import com.codecool.api.comparators.card.ManaCostDesc;
import com.codecool.api.comparators.card.NameAsc;
import com.codecool.api.comparators.card.NameDesc;
import com.codecool.api.comparators.minion.AttackAsc;
import com.codecool.api.comparators.minion.AttackDesc;
import com.codecool.api.comparators.minion.HealtDesc;
import com.codecool.api.comparators.minion.HealthAsc;
import com.codecool.api.datamanager.CardParser;

import java.util.ArrayList;
import java.util.List;

public class CardLister {

    private CardParser cardParser;

    private List<Card> CardList;

    private List<Minion> minionCardList;

    public CardLister() {
        CardList = new ArrayList<>();
        minionCardList = new ArrayList<>();
    }


    //Getters and Setters

    public List<Minion> getMinionCardList() {
        return minionCardList;
    }

    public List<Card> getCardList() {
        return CardList;
    }

    public void setCardParser(Hero hero) {
        cardParser = new CardParser("src/main/resources/decks/" + hero.getName() + ".xml");
    }

    //Methods

    public void importCards() {
        for (Card card : cardParser.getCards()) {
            CardList.add(card);
        }
        for (Minion minion : cardParser.getMinions()) {
            minionCardList.add(minion);
        }
    }

    //Sorting

    public void attackAsc() {
        minionCardList.sort(new AttackAsc());
    }

    public void attackDesc() {
        minionCardList.sort(new AttackDesc());
    }

    public void healthAsc() {
        minionCardList.sort(new HealthAsc());
    }

    public void healthDesc() {
        minionCardList.sort(new HealtDesc());
    }

    public void manaCostAsc() {
        minionCardList.sort(new ManaCostAsc());
        CardList.sort(new ManaCostAsc());
    }

    public void manaCostDesc() {
        minionCardList.sort(new ManaCostDesc());
        CardList.sort(new ManaCostDesc());
    }

    public void NameAsc() {
        minionCardList.sort(new NameAsc());
        CardList.sort(new NameAsc());
    }

    public void NameDesc() {
        minionCardList.sort(new NameDesc());
        CardList.sort(new NameDesc());
    }


}


