package com.codecool.guiprog;

import com.codecool.api.Board;
import com.codecool.api.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    // FXML properties
    // Login Screen

    // Game Window
    private static List<Pane> hand;
    @FXML
    Button EndTurn = new Button();
    @FXML
    private final Pane eDesk5 = new Pane();
    @FXML
    static Pane hand1, hand2, hand3, hand4, hand5 = new Pane();
    @FXML static ImageView Image1 = new ImageView();
    @FXML
    Pane eDesk1;
    @FXML
    Pane desk1, desk2, desk3, desk4, desk5 = new Pane();
    @FXML
    Pane eDesk2;
    @FXML
    Pane eDesk3;
    @FXML
    Pane eDesk4;
    private Parent root;
    private List<Pane> desk;
    private List<Pane> eDesk;
    // Game data
    private Player player1;
    private Player player2;
    private Board board;
    Stage thisStage;


    private void alert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(alertMessage);
        alert.show();
    }
    // Login Screen - Method(s)

    private void initHand() throws IOException {
        hand = new ArrayList<>();
        hand.add(hand1);
        hand.add(hand2);
        hand.add(hand3);
        hand.add(hand4);
        hand.add(hand5);
        System.out.println(player1.getHand().get(0).getName());
        Image cardImg = new Image(new File("/cardimgs/default.png").toURI().toString());
        Image1.setVisible(true);
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
        Player temp = player1;
        player2 = player1;
        player1 = temp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setThis(Stage stage) {
        thisStage = stage;
    }
}
