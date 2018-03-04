package com.codecool.guiprog;

import com.codecool.api.Board;
import com.codecool.api.Player;
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
import java.io.IOException;
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
    Pane desk1, desk2, desk3, desk4, desk5;
    @FXML
    Pane hero, enemyHero;
    private List<Pane> hand;


    private List<Pane> desk;
    private List<Pane> eDesk;
    // Game data
    private Player currentPlayer;
    private Player enemy;
    private Board board;
    Stage thisStage;


    private void alert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(alertMessage);
        alert.show();
    }

    public void settleUp(Player current, Player enemy, Board board) {
        currentPlayer = current;
        this.enemy = enemy;
        this.board = board;
        System.out.println(currentPlayer.getHero().getImagePath());
        ((ImageView) hero.getChildren().get(0)).setImage((new Image(new File(currentPlayer.getHero().getImagePath()).toURI().toString())));
        ((Label) hero.getChildren().get(1)).setText(Integer.toString(currentPlayer.getHealth()));
        ((ImageView) enemyHero.getChildren().get(0)).setImage((new Image(new File(enemy.getHero().getImagePath()).toURI().toString())));
        ((Label) enemyHero.getChildren().get(1)).setText(Integer.toString(enemy.getHealth()));
    }

    private void initHand() throws IOException {
        hand = new ArrayList<>();
        hand.add(hand1);
        hand.add(hand2);
        hand.add(hand3);
        hand.add(hand4);
        hand.add(hand5);
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
        alert("Turn ended");
        Player temp = currentPlayer;
        currentPlayer = enemy;
        enemy = temp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setThis(Stage stage) {
        thisStage = stage;
    }
}
