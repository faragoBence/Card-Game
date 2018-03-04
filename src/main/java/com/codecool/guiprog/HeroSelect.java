package com.codecool.guiprog;

import com.codecool.api.Board;
import com.codecool.api.Hero;
import com.codecool.api.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
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

public class HeroSelect implements Initializable {
    @FXML
    Pane Hero1, Hero2, Hero3, Hero4, Hero5;
    @FXML
    Label myLab;

    Stage thisStage;
    Board board;
    Player player1;
    Player player2;
    List<Hero> myHeroes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void start(Player player1, Player player2, Board board) throws IOException {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        createField();
    }

    public void setThis(Stage stage) {
        thisStage = stage;
    }

    public void createField() {
        List<Pane> heroes = new ArrayList<>();
        heroes.add(Hero1);
        heroes.add(Hero2);
        heroes.add(Hero3);
        heroes.add(Hero4);
        heroes.add(Hero5);
        for (int i = 0; i < board.getHeroes().size(); i++) {
            ((ImageView) heroes.get(i).getChildren().get(0)).setImage(new Image(new File(board.getHeroes().get(i).getImagePath()).toURI().toString()));
        }
        myLab.setText(player1.getName() + ", please select a Hero!");
    }

    public void onClick(Pane pane) {
        int index = Integer.parseInt(pane.getChildren().get(0).getId()) - 1;
        if (!myHeroes.contains(board.getHeroes().get(index))) {
            myHeroes.add(board.getHeroes().get(index));
            pane.getChildren().get(0).setEffect(makeBlackWhite());
            myLab.setText(player2.getName() + ", please select a Hero!");
        } else {
            myLab.setText(player1.getName() + " already selected this Hero");
        }
        if (myHeroes.size() >= 2) {
            startGame();
        }


    }

    public void increasePane(Pane pane) {
        pane.setScaleX(1.2);
        pane.setScaleY(1.2);
        pane.getChildren().get(0).setScaleX(1.1);
        pane.getChildren().get(0).setScaleY(1.1);
        pane.getChildren().get(1).setScaleX(1.1);
        pane.getChildren().get(1).setScaleY(1.1);
        pane.getChildren().get(1).setLayoutX(pane.getChildren().get(1).getLayoutX() + 10);
        pane.getChildren().get(1).setLayoutY(pane.getChildren().get(1).getLayoutY() + 10);
    }

    public void decreasePane(Pane pane) {
        pane.setScaleX(1);
        pane.setScaleY(1);
        pane.getChildren().get(0).setScaleX(1);
        pane.getChildren().get(0).setScaleY(1);
        pane.getChildren().get(1).setScaleX(1);
        pane.getChildren().get(1).setScaleY(1);
        pane.getChildren().get(1).setLayoutX(pane.getChildren().get(1).getLayoutX() - 10);
        pane.getChildren().get(1).setLayoutY(pane.getChildren().get(1).getLayoutY() - 10);
    }

    public void increaseHero1() {
        increasePane(Hero1);
    }

    public void decreaseHero1() {
        decreasePane(Hero1);
    }

    public void clickHero1() {
        onClick(Hero1);
    }

    public void increaseHero2() {
        increasePane(Hero2);
    }

    public void decreaseHero2() {
        decreasePane(Hero2);
    }

    public void clickHero2() {
        onClick(Hero2);
    }

    public void increaseHero3() {
        increasePane(Hero3);
    }

    public void decreaseHero3() {
        decreasePane(Hero3);
    }

    public void clickHero3() {
        onClick(Hero3);
    }

    public void increaseHero4() {
        increasePane(Hero4);
    }

    public void decreaseHero4() {
        decreasePane(Hero4);
    }

    public void clickHero4() {
        onClick(Hero4);
    }

    public void increaseHero5() {
        increasePane(Hero5);
    }

    public void decreaseHero5() {
        decreasePane(Hero5);
    }

    public void clickHero5() {
        onClick(Hero5);
    }

    public ColorAdjust makeBlackWhite() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        return colorAdjust;
    }

    public void startGame() {
        player1.setHero(myHeroes.get(0));
        player2.setHero(myHeroes.get(1));


        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/GameWindow.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            //Valamit majd.
        }
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        GameWindow gameWindow = Loader.getController();
        gameWindow.setThis(stage);
        stage.show();
        thisStage.close();
    }
}
