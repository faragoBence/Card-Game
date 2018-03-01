package com.codecool.cmdprog;

import com.codecool.api.*;
import com.codecool.api.Iterators.CardIterator;
import com.codecool.api.Iterators.HeroIterator;
import com.codecool.api.Iterators.MinionIterator;
import com.codecool.api.datamanager.HeroParser;
import com.codecool.api.exceptions.*;

import java.util.List;
import java.util.Scanner;

class CmdProg {
    private final Scanner scanner = new Scanner(System.in);
    private Board board;
    private Player currentPlayer;
    private Player enemy;

    public void run() {
        while (true) {
            clearscreen();
            printMenu();
            String choose = scanner.nextLine();
            switch (choose) {
                case "1":
                    clearscreen();
                    runGame();
                    break;
                case "2":
                    clearscreen();
                    cardListing();
                    break;
                case "3":
                    System.exit(0);
                default:
                    System.out.println("Wrong input entered!");
            }

        }
    }

    private void runGame() {
        System.out.println("Please enter your name!");
        currentPlayer = new Player(scanner.nextLine(), 20);
        System.out.println("Please enter the name of the another player");
        enemy = new Player(scanner.nextLine(), 20);
        board = new Board(currentPlayer, enemy);
        selectHero(currentPlayer);
        selectHero(enemy);
        board.start();
        List<Player> list = board.randomizeStart();
        currentPlayer = list.get(0);
        enemy = list.get(1);
        Player temp;
        while (currentPlayer.isAlive() && enemy.isAlive()) {
            boolean roundOver = true;
            try {
                currentPlayer.startRound();
            } catch (EntityIsDeadException ex) {
                break;
            }
            while (roundOver) {
                if (!currentPlayer.isAlive() || !enemy.isAlive()) {
                    break;
                }
                System.out.println("\n" + enemy);
                listCards(enemy.getDesk());
                System.out.println("\nYour desk:\n");
                listCards(currentPlayer.getDesk());
                System.out.println("Your hand:\n");
                listCards(currentPlayer.getHand());
                System.out.println("\n" + currentPlayer + "\n");
                printList();
                String line = scanner.nextLine();
                clearscreen();
                roundOver = commandReader(line.split(" "));
                try {
                    currentPlayer.clearField();
                    enemy.clearField();
                } catch (NoMoreRoomOnDeskException ex) {
                    System.out.println("The desk is full!");
                }
            }
            temp = currentPlayer;
            currentPlayer = enemy;
            enemy = temp;
        }
        String winner;
        if (currentPlayer.isAlive()) {
            winner = currentPlayer.getName();
        } else {
            winner = enemy.getName();
        }
        clearscreen();
        System.out.println("Congratulations! " + winner + " won the game!");
    }

    private boolean commandReader(String[] args) {
        switch (args[0]) {
            case ":place":
                handlePlace(args);
                return true;
            case ":attack":
                handleAttack(args);
                return true;
            case ":magic":
                handleMagic(args);
                return true;
            case ":end":
                board.changeAttackState(enemy);
                return false;
            default:
                System.out.println("Wrong input entered!");
                return true;
        }
    }

    private void printList() {
        System.out.println(":place [card number]");
        System.out.println(":magic [Magic card number target](me,enemy,me 1,enemy 1)");
        System.out.println(":attack [your minion number  enemy minion number]");
        System.out.println(":attack [your minion number] (To attack enemy player)");
        System.out.println(":end");
    }

    private void handleMagic(String[] args) {
        MagicCard magicCard;
        try {
            int cardIndex = Integer.parseInt(args[1]);
            if (currentPlayer.getHand().get(cardIndex - 1) instanceof MagicCard) {
                magicCard = (MagicCard) currentPlayer.getHand().get(cardIndex - 1);
                if (magicCard.getManaCost() <= currentPlayer.getMana()) {

                    currentPlayer.placeCard(cardIndex - 1);
                    switch (args[2]) {
                        case "me":
                            if (args.length < 4) {
                                magicCard.doMagic(currentPlayer);
                            } else {
                                int index = Integer.parseInt(args[3]);
                                magicCard.doMagic(currentPlayer.getDesk().get(index - 1));
                            }
                            break;
                        case "enemy":
                            if (args.length < 4) {
                                magicCard.doMagic(enemy);
                            } else {
                                int index = Integer.parseInt(args[3]);
                                magicCard.doMagic(enemy.getDesk().get(index - 1));
                            }
                            break;
                        default:
                            System.out.println("Wrong input entered");
                    }
                } else {
                    throw new NotEnoughManaException();
                }
            } else {
                System.out.println("It isn't a magic card!");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            System.out.println("Wrong input entered!");
        } catch (WrongTargetException ex) {
            System.out.println("You targeted wrong entity!");
        } catch (NoMoreRoomOnDeskException ex) {
            System.out.println("Desk is full!");
        } catch (NotEnoughManaException ex) {
            System.out.println("You don't have enough mana!");
        }
    }

    private void handlePlace(String[] args) {
        try {
            int index = Integer.parseInt(args[1]);
            if (currentPlayer.getHand().get(index - 1) instanceof Minion) {
                if (index <= currentPlayer.getHand().size()) {
                    currentPlayer.placeCard(index - 1);
                } else {
                    System.out.println("Wrong input entered!");
                }
            } else {
                System.out.println("It's a magic card, use the :magic command!");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Wrong input entered!");
        } catch (NoMoreRoomOnDeskException ex) {
            System.out.println("No more room on desk!");
        } catch (NotEnoughManaException ex) {
            System.out.println("Not enough mana to place that card!");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Wrong input entered");
        }
    }

    private void handleAttack(String[] args) {
        try {
            int index1 = Integer.parseInt(args[1]);
            if (args.length > 2) {
                int index2 = Integer.parseInt(args[2]);
                if (index1 <= currentPlayer.getDesk().size() && index2 <= enemy.getDesk().size()) {
                    ((Minion) currentPlayer.getDesk().get(index1 - 1)).attack(((Minion) enemy.getDesk().get(index2 - 1)));
                } else {
                    System.out.println("Wrong input entered!");
                }
            } else {
                if (index1 <= currentPlayer.getDesk().size()) {
                    ((Minion) currentPlayer.getDesk().get(index1 - 1)).attack(enemy);
                } else {
                    System.out.println("Wrong input entered!");
                }
            }
        } catch (CanNotAttackException ex) {
            System.out.println("You can not attack in this turn!");
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            System.out.println("Wrong input entered!");
        } catch (SelfTargetException ex) {
            System.out.println("Cannot attack yourself!");
        } catch (TargetisStealthException ex) {
            System.out.println("The target is Stealth!");
        }
    }

    private void cardListing() {
        CardLister cardLister = new CardLister();
        deckChoose(cardLister);
        clearscreen();
        while (true) {
            printListingOptions();
            String sorting = scanner.nextLine();
            switch (sorting) {
                case "1":
                    cardLister.manaCostAsc();
                    listCards(cardLister.getCardList());
                    break;
                case "2":
                    cardLister.manaCostDesc();
                    listCards(cardLister.getCardList());
                    break;
                case "3":
                    cardLister.NameAsc();
                    listCards(cardLister.getCardList());
                    break;
                case "4":
                    cardLister.NameDesc();
                    listCards(cardLister.getCardList());
                    break;
                case "5":
                    cardLister.attackAsc();
                    listMinionCards(cardLister.getMinionCardList());
                    break;
                case "6":
                    cardLister.attackDesc();
                    listMinionCards(cardLister.getMinionCardList());
                    break;
                case "7":
                    cardLister.healthAsc();
                    listMinionCards(cardLister.getMinionCardList());
                    break;
                case "8":
                    cardLister.healthDesc();
                    listMinionCards(cardLister.getMinionCardList());
                    break;
                case "0":
                    cardListing();
                    break;
                default:
                    System.out.println("Wrong input entered!");


            }
        }
    }

    private void deckChoose(CardLister cardLister) {
        while (true) {
            clearscreen();
            System.out.println("Please select a Hero deck from the list or enter 'all'");
            HeroParser heroParser = new HeroParser();
            listHeroes(heroParser.getHeroes());
            String choose = scanner.nextLine();
            try {
                if (!choose.equals("all")) {
                    cardLister.setCardParser(heroParser.getHeroes().get(Integer.parseInt(choose) - 1));
                    cardLister.importCards();
                    break;
                } else {
                    for (int i = 0; i < heroParser.getHeroes().size(); i++) {
                        cardLister.setCardParser(heroParser.getHeroes().get(i));
                        cardLister.importCards();
                    }
                    break;
                }
            } catch (Exception ex) {
                System.out.println("Wrong input entered!");
            }
        }
    }

    private void printListingOptions() {
        System.out.println("For all cards:");
        System.out.println("\t1) Mana cost asc.");
        System.out.println("\t2) Mana cost desc.");
        System.out.println("\t3) Name asc.");
        System.out.println("\t4) Name desc.");
        System.out.println("\nFor minion cards:");
        System.out.println("\t5) Attack asc.");
        System.out.println("\t6) Attack desc.");
        System.out.println("\t7) Health asc.");
        System.out.println("\t8) Health desc.");
        System.out.println("\t0) Go back to deck select");
    }

    private void listCards(List<Card> cards) {
        CardIterator cardIterator = new CardIterator(cards);
        int num = 1;
        while (cardIterator.hasNext()) {
            System.out.println(Integer.toString(num++) + ") " + cardIterator.next());
        }
        System.out.println("------------------------------------------------------------------------------------------");

    }

    public void listMinionCards(List<Minion> cards) {
        MinionIterator cardIterator = new MinionIterator(cards);
        int num = 1;
        while (cardIterator.hasNext()) {
            System.out.println(Integer.toString(num++) + ") " + cardIterator.next());
        }
        System.out.println("------------------------------------------------------------------------------------------");

    }

    private void listHeroes(List<Hero> heroes) {
        HeroIterator heroIterator = new HeroIterator(heroes);
        int num = 1;
        while (heroIterator.hasNext()) {
            System.out.println(Integer.toString(num++) + ") " + heroIterator.next().getName());
        }
        System.out.println("------------------------------------------------------------------------------------------");

    }

    private void printMenu() {
        System.out.println("1) Start game\n2) Inspect decks\n3) Exit game");
    }


    private void clearscreen() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }

    private void selectHero(Player player) {
        while (true) {
            listHeroes(board.getHeroes());
            String heroName = scanner.nextLine();
            try {
                player.setHero(board.getHeroes().get(Integer.parseInt(heroName) - 1));
                board.getHeroes().remove(Integer.parseInt(heroName) - 1);
                break;

            } catch (Exception ex) {
                System.out.println("Wrong input entered!");
            }
        }
    }
}

