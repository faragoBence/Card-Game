package com.codecool.guiprog;

import com.codecool.api.Board;
import com.codecool.api.Entity;
import com.codecool.api.Minion;
import com.codecool.api.Player;
import com.codecool.api.exceptions.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameWindow implements Initializable {
    @FXML
    Pane eDesk1, eDesk2, eDesk3, eDesk4, eDesk5;
    @FXML
    Button EndTurn = new Button();
    @FXML
    Pane hand1, hand2, hand3, hand4, hand5;
    @FXML
    Pane desk1, desk2, desk3, desk4, desk5, hero, enemyHero;
    @FXML
    Label deckSize, eDeckSize, Name;
    private List<Pane> hand;
    @FXML
    Label curMaxMana, curCurMana, curManaCap, enemyCurMana, enemyMaxMana;

    private List<Pane> desk;
    private List<Pane> eDesk;
    // Game data
    private Player currentP;
    private Player enemyP;
    private Board board;
    Stage thisStage;
    Entity attacker = null;


    private void alert(String title, String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(alertMessage);
        alert.show();
    }

    public void settleUp(Player current, Player enemyP, Board board) {
        currentP = current;
        this.enemyP = enemyP;
        this.board = board;
        hero.setOnMouseEntered(event -> ((Pane) event.getSource()).setEffect(glow(Color.GREEN)));
        enemyHero.setOnMouseEntered(event -> ((Pane) event.getSource()).setEffect(glow(Color.DARKRED)));
        hero.setOnMouseExited(event -> ((Pane) event.getSource()).setEffect(null));
        enemyHero.setOnMouseExited(event -> ((Pane) event.getSource()).setEffect(null));

        try {
            currentP.startRound();
        } catch (EntityIsDeadException e) {
            alert("Entity is dead", "Sorry, but u can't do this, because the entity is dead!");
        }
        initHand();
        initDesk();
        initEDesk();
        refresh();
    }

    public void refreshHand() {
        for (int i = 0; i < hand.size(); i++) {
            if (i < currentP.getHand().size()) {
                hand.get(i).setMouseTransparent(false);
                ((ImageView) hand.get(i).getChildren().get(0)).setImage(new Image(new File(currentP.getHand().get(i).getImagePath()).toURI().toString()));
                if (currentP.getHand().get(i) instanceof Minion) {
                    ((Label) hand.get(i).getChildren().get(1)).setText(Integer.toString(((Minion) currentP.getHand().get(i)).getAttack()));
                    ((Label) hand.get(i).getChildren().get(2)).setText(Integer.toString((currentP.getHand().get(i)).getHealth()));
                } else {
                    ((Label) hand.get(i).getChildren().get(1)).setText(null);
                    ((Label) hand.get(i).getChildren().get(2)).setText(null);
                }
            } else {
                hand.get(i).setMouseTransparent(true);
                ((ImageView) hand.get(i).getChildren().get(0)).setImage(null);
                ((Label) hand.get(i).getChildren().get(1)).setText(null);
                ((Label) hand.get(i).getChildren().get(2)).setText(null);
            }
        }
    }

    public void refreshDesk() {
        for (int i = 0; i < desk.size(); i++) {
            if (i < currentP.getDesk().size()) {
                ((ImageView) desk.get(i).getChildren().get(0)).setImage(new Image(new File(currentP.getDesk().get(i).getImagePath()).toURI().toString()));
                if (currentP.getDesk().get(i) instanceof Minion) {
                    ((Label) desk.get(i).getChildren().get(1)).setText(Integer.toString(((Minion) currentP.getDesk().get(i)).getAttack()));
                    ((Label) desk.get(i).getChildren().get(2)).setText(Integer.toString((currentP.getDesk().get(i)).getHealth()));
                    desk.get(i).toFront();
                    if (((Minion) currentP.getDesk().get(i)).canAttack()) {
                        desk.get(i).setEffect(glow(Color.RED));
                    } else {
                        desk.get(i).setEffect(null);
                    }
                    if (((Minion) currentP.getDesk().get(i)).getAbility().equals("Taunt")) {
                        desk.get(i).getChildren().get(0).setEffect(tauntEffect());
                    } else if (((Minion) currentP.getDesk().get(i)).getAbility().equals("Stealth")) {
                        desk.get(i).getChildren().get(0).setEffect(stealthEffect());
                    } else {
                        desk.get(i).getChildren().get(0).setEffect(null);
                    }
                } else {
                    ((Label) desk.get(i).getChildren().get(1)).setText(null);
                    ((Label) desk.get(i).getChildren().get(2)).setText(null);
                }
            } else {
                ((ImageView) desk.get(i).getChildren().get(0)).setImage(null);
                ((Label) desk.get(i).getChildren().get(1)).setText(null);
                ((Label) desk.get(i).getChildren().get(2)).setText(null);
                desk.get(i).toBack();
            }
        }
    }

    public void refreshEDesk() {
        for (int i = 0; i < eDesk.size(); i++) {
            if (i < enemyP.getDesk().size()) {
                ((ImageView) eDesk.get(i).getChildren().get(0)).setImage(new Image(new File(enemyP.getDesk().get(i).getImagePath()).toURI().toString()));
                if (enemyP.getDesk().get(i) instanceof Minion) {
                    ((Label) eDesk.get(i).getChildren().get(1)).setText(Integer.toString(((Minion) enemyP.getDesk().get(i)).getAttack()));
                    ((Label) eDesk.get(i).getChildren().get(2)).setText(Integer.toString((enemyP.getDesk().get(i)).getHealth()));
                    eDesk.get(i).toFront();
                    if (((Minion) enemyP.getDesk().get(i)).getAbility().equals("Taunt")) {
                        eDesk.get(i).getChildren().get(0).setEffect(tauntEffect());
                    } else if (((Minion) enemyP.getDesk().get(i)).getAbility().equals("Stealth")) {
                        eDesk.get(i).getChildren().get(0).setEffect(stealthEffect());
                    } else {
                        eDesk.get(i).getChildren().get(0).setEffect(null);
                    }
                } else {
                    ((Label) eDesk.get(i).getChildren().get(1)).setText(null);
                    ((Label) eDesk.get(i).getChildren().get(2)).setText(null);
                    eDesk.get(i).toBack();
                }
            } else {
                ((ImageView) eDesk.get(i).getChildren().get(0)).setImage(null);
                ((Label) eDesk.get(i).getChildren().get(1)).setText(null);
                ((Label) eDesk.get(i).getChildren().get(2)).setText(null);
            }
        }
    }

    public void refreshHeroes() {
        ((ImageView) enemyHero.getChildren().get(0)).setImage((new Image(new File(enemyP.getHero().getFullImagePath()).toURI().toString())));
        ((Label) enemyHero.getChildren().get(1)).setText(Integer.toString(enemyP.getHealth()));
        ((ImageView) hero.getChildren().get(0)).setImage((new Image(new File(currentP.getHero().getFullImagePath()).toURI().toString())));
        ((Label) hero.getChildren().get(1)).setText(Integer.toString(currentP.getHealth()));
        enemyHero.setOnMouseClicked(event -> {
            try {
                handleAttack((Pane) event.getSource());
            } catch (SelfTargetException e) {
                alert("You attacked yourself!", "You can't attack him!");
                attacker = null;
            } catch (CanNotAttackException e) {
                alert("You can't attack!", "This card can't attack more in this round!");
                attacker = null;
            } catch (TargetisStealthException e) {
                alert("You can't attack Stealth!", "The target you selected is in Stealth mode!");
                attacker = null;
            } catch (TauntOnBoardException e) {
                alert("Taunt on Board!", "You can't attack him, because a taunt card is on the enemy board.");
                attacker = null;
            }
            refresh();
        });

        hero.setOnMouseClicked(event -> {
            try {
                handleAttack((Pane) event.getSource());
            } catch (SelfTargetException e) {
                alert("You attacked yourself!", "You can't attack your hero!");
                attacker = null;
            } catch (CanNotAttackException e) {
                alert("You can't attack!", "This card can't attack more in this round!");
                attacker = null;
            } catch (TargetisStealthException e) {
                alert("You can't attack Stealth!", "The target you selected is in Stealth mode!");
                attacker = null;
            } catch (TauntOnBoardException e) {
                alert("Taunt on Board!", "You can't attack him, because a taunt card is on the enemy board.");
                attacker = null;
            }
            refresh();
        });
        deckSize.setText(Integer.toString(currentP.getDeck().size()));
        eDeckSize.setText(Integer.toString(enemyP.getDeck().size()));
        curMaxMana.setText(Integer.toString(currentP.getMaxMana()));
        curCurMana.setText(Integer.toString(currentP.getMana()));
        curManaCap.setText(Integer.toString(currentP.getManacap()));
        enemyCurMana.setText(Integer.toString(enemyP.getMana()));
        enemyMaxMana.setText(Integer.toString(enemyP.getMaxMana()));
        if (attacker != null) {
            Name.setText(attacker.getName());
            Name.setVisible(true);
        } else {
            Name.setVisible(false);
        }

    }

    public void refresh() {
        try {
            currentP.clearField();
            enemyP.clearField();
        } catch (NoMoreRoomOnDeskException e) {
            alert("Your desk is full!", "You can't summon more minions!");
        }
        refreshHand();
        refreshDesk();
        refreshEDesk();
        refreshHeroes();
    }


    private void initHand() {
        hand = new ArrayList<>();
        hand.add(hand1);
        hand.add(hand2);
        hand.add(hand3);
        hand.add(hand4);
        hand.add(hand5);
        for (Pane pane : hand) {
            pane.setOnMouseClicked(event -> {
                try {
                    currentP.placeCard(Integer.parseInt(((Pane) event.getSource()).getChildren().get(0).getId()) - 1);
                } catch (NoMoreRoomOnDeskException e) {
                    alert("No more room in the desk!", "You can't place more card!");
                } catch (NotEnoughManaException e) {
                    alert("Not enough mana!", "You haven't got enough mana to place this card!");
                }
                refresh();
            });
            pane.setOnMouseEntered(event -> increasePane((Pane) event.getSource()));
            pane.setOnMouseExited(event -> decreasePane((Pane) event.getSource()));
        }
    }

    private void initDesk() {
        desk = new ArrayList<>();
        desk.add(desk1);
        desk.add(desk2);
        desk.add(desk3);
        desk.add(desk4);
        desk.add(desk5);
        for (Pane pane : desk) {
            pane.setOnMouseEntered(event -> ((Pane) event.getSource()).setEffect(glow(Color.YELLOWGREEN)));
            pane.setOnMouseExited(event -> {
                Pane temp = ((Pane) event.getSource());
                String id = temp.getChildren().get(0).getId().split("")[1];
                if (((Minion) currentP.getDesk().get(Integer.parseInt(id) - 1)).canAttack()) {
                    temp.setEffect(glow(Color.RED));
                } else {
                    ((Pane) event.getSource()).setEffect(null);
                    refresh();
                }
                ;
            });
            pane.setOnMouseClicked(event -> {
                try {
                    handleAttack((Pane) event.getSource());
                } catch (SelfTargetException e) {
                    alert("You attacked yourself!", "You can't attack your cards!");
                    attacker = null;
                } catch (CanNotAttackException e) {
                    alert("You can't attack!", "This card can't attack more in this round!");
                    attacker = null;
                } catch (TargetisStealthException e) {
                    alert("You can't attack Stealth!", "The target you selected is in Stealth mode!");
                    attacker = null;
                } catch (TauntOnBoardException e) {
                    alert("Taunt on Board!", "You can't attack him, because a taunt card is on the enemy board.");
                    attacker = null;
                }
                refresh();
            });
        }
    }

    private void initEDesk() {
        eDesk = new ArrayList<>();
        eDesk.add(eDesk1);
        eDesk.add(eDesk2);
        eDesk.add(eDesk3);
        eDesk.add(eDesk4);
        eDesk.add(eDesk5);
        for (Pane pane : eDesk) {
            pane.setOnMouseEntered(event -> ((Pane) event.getSource()).setEffect(glow(Color.RED)));
            pane.setOnMouseExited(event -> ((Pane) event.getSource()).setEffect(null));
            pane.setOnMouseClicked(event -> {
                try {
                    handleAttack((Pane) event.getSource());
                } catch (SelfTargetException e) {
                    alert("You attacked yourself!", "You can't attack that entity!");
                    attacker = null;
                } catch (CanNotAttackException e) {
                    alert("You can't attack!", "This card can't attack more in this round!");
                    attacker = null;
                } catch (TargetisStealthException e) {
                    alert("You can't attack Stealth!", "The target you selected is in Stealth mode!");
                    attacker = null;
                } catch (TauntOnBoardException e) {
                    alert("Taunt on Board!", "You can't attack him, because a taunt card is on the enemy board.");
                    attacker = null;
                }
                refresh();
            });
        }
    }


    // Game Screen - Method(s)
    public void endTurn() {
        Player temp = currentP;
        currentP = enemyP;
        enemyP = temp;
        board.changeAttackState(currentP);
        try {
            currentP.startRound();
        } catch (EntityIsDeadException ex) {
            alert("Entity is dead", "Sorry, but u can't do this, because the entity is dead!");
        }
        attacker = null;
        refresh();
    }

    public void increasePane(Pane pane) {
        pane.setScaleX(1.1);
        pane.setScaleY(1.1);
        pane.setLayoutY(pane.getLayoutY() - 50);
        pane.getChildren().get(0).setScaleX(1.1);
        pane.getChildren().get(0).setScaleY(1.1);
        pane.getChildren().get(0).setLayoutY(pane.getChildren().get(0).getLayoutY() - 50);
        pane.getChildren().get(1).setScaleX(1.1);
        pane.getChildren().get(1).setScaleY(1.1);
        pane.getChildren().get(1).setLayoutY(pane.getChildren().get(1).getLayoutY() - 42);
        pane.getChildren().get(1).setLayoutX(pane.getChildren().get(1).getLayoutX() - 3);
        pane.getChildren().get(2).setScaleX(1.1);
        pane.getChildren().get(2).setScaleY(1.1);
        pane.getChildren().get(2).setLayoutY(pane.getChildren().get(2).getLayoutY() - 42);
        pane.getChildren().get(2).setLayoutX(pane.getChildren().get(2).getLayoutX() + 5);
        pane.toFront();

    }

    public void decreasePane(Pane pane) {
        pane.setScaleX(1);
        pane.setScaleY(1);
        pane.setLayoutY(pane.getLayoutY() + 50);
        pane.getChildren().get(0).setScaleX(1);
        pane.getChildren().get(0).setScaleY(1);
        pane.getChildren().get(0).setLayoutY(pane.getChildren().get(0).getLayoutY() + 50);
        pane.getChildren().get(1).setScaleX(1);
        pane.getChildren().get(1).setScaleY(1);
        pane.getChildren().get(1).setLayoutY(pane.getChildren().get(1).getLayoutY() + 42);
        pane.getChildren().get(1).setLayoutX(pane.getChildren().get(1).getLayoutX() + 3);
        pane.getChildren().get(2).setScaleX(1);
        pane.getChildren().get(2).setScaleY(1);
        pane.getChildren().get(2).setLayoutY(pane.getChildren().get(2).getLayoutY() + 42);
        pane.getChildren().get(2).setLayoutX(pane.getChildren().get(2).getLayoutX() - 5);
    }

    private DropShadow glow(Color color) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(color);
        shadow.setRadius(33);
        shadow.setWidth(65.5);
        shadow.setHeight(68.5);
        return shadow;
    }

    private InnerShadow innerShadow(Color color) {
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setBlurType(BlurType.THREE_PASS_BOX);
        innerShadow.setChoke(0.71);
        innerShadow.setColor(color);
        return innerShadow;
    }

    private InnerShadow tauntEffect() {
        return innerShadow(Color.BLACK);
    }

    private InnerShadow stealthEffect() {
        return innerShadow(Color.WHITE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleAttack(Pane pane) throws SelfTargetException, CanNotAttackException, TargetisStealthException, TauntOnBoardException {
        String id = pane.getChildren().get(0).getId();
        if (attacker == null) {
            if (!id.equals("hero1") && !id.equals("hero2")) {
                String[] alphabets = id.split("");
                if (alphabets[0].equals("p")) {
                    if (((Minion) currentP.getDesk().get(Integer.parseInt(alphabets[1]) - 1)).canAttack()) {
                        attacker = currentP.getDesk().get(Integer.parseInt(alphabets[1]) - 1);
                    } else {
                        throw new CanNotAttackException();
                    }
                }
            }
        } else {
            if (id.equals("hero1")) {
                throw new SelfTargetException();
            } else if (id.equals("hero2")) {
                ((Minion) attacker).attack(enemyP);
                attacker = null;
            } else {
                String[] alphabets = id.split("");
                if (alphabets[0].equals("p")) {
                    throw new SelfTargetException();
                } else {
                    Minion target = (Minion) enemyP.getDesk().get(Integer.parseInt(alphabets[1]) - 1);
                    ((Minion) attacker).attack(target, enemyP);
                    attacker = null;
                }
            }
        }
        refresh();

    }

    public void setThis(Stage stage) {
        thisStage = stage;
    }
}
