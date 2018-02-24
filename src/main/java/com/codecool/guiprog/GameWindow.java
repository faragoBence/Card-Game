package com.codecool.guiprog;

import com.codecool.api.Board;
import com.codecool.api.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends Application {

    // FXML properties
    // Login Screen
    @FXML
    TextField player1Field = new TextField();
    @FXML
    TextField player2Field = new TextField();
    @FXML
    Label nextLabel = new Label();
    @FXML
    ImageView nextPic = new ImageView();

    // Game Window
    private static Stage primaryStage;
    private static Scene scene;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        GameWindow.primaryStage = primaryStage;
        root = FXMLLoader.load(getClass().getResource("/NameEnter.fxml"));
        GameWindow.scene = new Scene(root, 1280, 720);
        GameWindow.primaryStage.setScene(scene);
        GameWindow.primaryStage.setTitle("CodeStone");
        GameWindow.primaryStage.show();
    }

    private void change(String path) throws IOException {
        root = FXMLLoader.load(getClass().getResource(path));
        scene.setRoot(root);
    }

    private void alert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(alertMessage);
        alert.show();
    }

    // Intro
    public void goToMenu() throws IOException {
        change("/Menu.fxml");
    }


    // Menu Screen
    public void getPlayerNames() throws IOException {
        change("/NameEnter.fxml");
    }

    // Login Screen - Method(s)
    public void startGame() throws IOException {
        player1 = new Player(player1Field.getText(), 20);
        player2 = new Player(player2Field.getText(), 20);
        board = new Board(player1, player2);
            board.start();
            change("/GameWindow.fxml");
            initHand();
            initDesk();
            initEDesk();
    }

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
        change("/GameWindow.fxml");
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


    // FXML Effects
    private DropShadow glow() {
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
}
