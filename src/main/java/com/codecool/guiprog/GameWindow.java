package com.codecool.guiprog;

import com.codecool.api.Board;
import com.codecool.api.Minion;
import com.codecool.api.Player;
import com.codecool.api.exceptions.EntityIsDeadException;
import com.codecool.api.exceptions.NoMoreRoomOnDeskException;
import com.codecool.api.exceptions.NotEnoughManaException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private List<Pane> hand;

    private List<Pane> desk;
    private List<Pane> eDesk;
    // Game data
    private Player currentP;
    private Player enemyP;
    private Board board;
    Stage thisStage;


    private void alert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(alertMessage);
        alert.show();
    }

    public void settleUp(Player current, Player enemyP, Board board) {
        currentP = current;
        this.enemyP = enemyP;
        this.board = board;
        initHand();
        initDesk();
        initEDesk();
        refreshHand();
        refreshDesk();
        refreshHeroes();
    }

    public void refreshHand() {
        for (int i = 0; i < currentP.getHand().size(); i++) {
            ((ImageView) hand.get(i).getChildren().get(0)).setImage(new Image(new File(currentP.getHand().get(i).getImagePath()).toURI().toString()));
            if (currentP.getHand().get(i) instanceof Minion) {
                ((Label) hand.get(i).getChildren().get(1)).setText(Integer.toString(((Minion) currentP.getHand().get(i)).getAttack()));
                ((Label) hand.get(i).getChildren().get(2)).setText(Integer.toString((currentP.getHand().get(i)).getHealth()));
            }
        }
    }

    public void refreshDesk() {
        for (int i = 0; i < currentP.getDesk().size(); i++) {
            ((ImageView) desk.get(i).getChildren().get(0)).setImage(new Image(new File(currentP.getDesk().get(i).getImagePath()).toURI().toString()));
            if (currentP.getDesk().get(i) instanceof Minion) {
                ((Label) desk.get(i).getChildren().get(1)).setText(Integer.toString(((Minion) currentP.getDesk().get(i)).getAttack()));
                ((Label) desk.get(i).getChildren().get(2)).setText(Integer.toString((currentP.getDesk().get(i)).getHealth()));
            }
        }
    }

    public void refreshHeroes() {
        ((ImageView) enemyHero.getChildren().get(0)).setImage((new Image(new File(enemyP.getHero().getImagePath()).toURI().toString())));
        ((Label) enemyHero.getChildren().get(1)).setText(Integer.toString(enemyP.getHealth()));
        ((ImageView) hero.getChildren().get(0)).setImage((new Image(new File(currentP.getHero().getImagePath()).toURI().toString())));
        ((Label) hero.getChildren().get(1)).setText(Integer.toString(currentP.getHealth()));
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
                    alert("No more room in the desk!");
                } catch (NotEnoughManaException e) {
                    alert("Not enough mana!");
                }
            });
        }
    }

    private void initDesk() {
        desk = new ArrayList<>();
        desk.add(desk1);
        desk.add(desk2);
        desk.add(desk3);
        desk.add(desk4);
        desk.add(desk5);
    }

    private void initEDesk() {
        eDesk = new ArrayList<>();
        eDesk.add(eDesk1);
        eDesk.add(eDesk2);
        eDesk.add(eDesk3);
        eDesk.add(eDesk4);
        eDesk.add(eDesk5);
    }


    // Game Screen - Method(s)
    public void endTurn() {
        Player temp = currentP;
        currentP = enemyP;
        enemyP = temp;
        try {
            currentP.startRound();
        } catch (EntityIsDeadException ex) {
            alert("Entity is dead!");
        }
        refreshHand();
        refreshDesk();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setThis(Stage stage) {
        thisStage = stage;
    }
}
