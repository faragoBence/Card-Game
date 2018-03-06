package com.codecool.guiprog;

import com.codecool.api.Board;
import com.codecool.api.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NameEnterController implements Initializable {
    Stage thisStage;

    @FXML
    TextField player1Field = new TextField();
    @FXML
    TextField player2Field = new TextField();
    @FXML
    Label nextLabel = new Label();
    @FXML
    ImageView nextPic = new ImageView();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void heroSelect() throws IOException {
        Player player1 = new Player(player1Field.getText(), 20);
        Player player2 = new Player(player2Field.getText(), 20);
        Board board = new Board(player1, player2);
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/HeroSelect.fxml"));
        Loader.load();
        HeroSelect heroSelect = Loader.getController();
        List<Player> list = board.randomizeStart();
        player1 = list.get(0);
        player2 = list.get(1);
        heroSelect.start(player1, player2, board);
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        heroSelect.setThis(stage);
        stage.setTitle("Stoned Hearth");
        stage.setResizable(false);
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
        thisStage.close();

    }

    public void setThis(Stage stage) {
        thisStage = stage;
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
