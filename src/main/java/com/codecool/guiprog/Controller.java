package com.codecool.guiprog;

import com.codecool.api.exceptions.EntityIsDeadException;
import javafx.application.Application;
import com.codecool.api.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

import java.io.IOException;


public class Controller extends Application {
    Player player1;
    Player player2;
    Board board;
    Stage primaryStage;
    Scene scene;
    @FXML
    TextField player1Field;
    @FXML
    TextField player2Field;
    @FXML
    Label nextLabel;
    @FXML
    ImageView nextPic;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        change(primaryStage, "/NameEnter");


    }

    public void change(Stage primaryStage, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("HearthStone");
        primaryStage.setResizable(false);
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    public void next() throws IOException {
        player1 = new Player(player1Field.getText(), 20);
        player2 = new Player(player2Field.getText(), 20);
        board = new Board(player1, player2);
        try {
            board.start();
            alert(player1.getHand().get(0).toString());

        } catch (EntityIsDeadException ex) {
            alert("Entity is dead!");
        }



    }

    public void alert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(alertMessage);
        alert.show();
    }

    public DropShadow glow() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.YELLOW);
        shadow.setRadius(33);
        shadow.setWidth(65.5);
        shadow.setHeight(68.5);
        return shadow;
    }

    public void player1Field1Glow() {
        player1Field.setEffect(glow());
    }

    public void player2Field1Glow() {
        player2Field.setEffect(glow());
    }

    public void setPlayer1FieldDefault() {
        player1Field.setEffect(null);
    }

    public void setPlayer2FieldDefault() {
        player2Field.setEffect(null);
    }

    public void increaseNextPic() {
        nextPic.setScaleX(1.2);
        nextPic.setScaleY(1.2);
        nextLabel.setScaleX(1.2);
        nextLabel.setScaleY(1.2);
    }

    public void decreaseNextPic() {
        nextPic.setScaleX(1);
        nextPic.setScaleY(1);
        nextLabel.setScaleX(1);
        nextLabel.setScaleY(1);
    }

    public void quit() {
        System.exit(0);
    }

    public void startGame() throws IOException {
        change(primaryStage, "/Menu.fxml");
    }

    public void startMenu() {
    }

}
