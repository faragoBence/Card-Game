package com.codecool.guiprog;

import com.codecool.api.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EndGame implements Initializable {
    Stage thisStage;
    Player winner;

    @FXML
    Label nextLabel, winnerLabel = new Label();
    @FXML
    ImageView nextPic = new ImageView();

    public void settleUp(Player winner) {
        this.winner = winner;
        winnerLabel.setText("Congratulations, " + winner.getName() + " you won!");
    }

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setThis(Stage stage) {
        thisStage = stage;
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

    public void goToNameEnter() throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/NameEnter.fxml"));
        Loader.load();
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        NameEnterController nameEnterController = Loader.getController();
        nameEnterController.setThis(stage);
        stage.setTitle("Stoned Hearth");
        stage.setResizable(false);
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
        thisStage.close();

    }
}
