package com.codecool.guiprog;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Intro implements Initializable {

    Stage thisStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToMenu(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Menu.fxml"));
        Loader.load();
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        MenuController menu = Loader.getController();
        menu.setThis(stage);
        stage.show();
        thisStage.close();

    }

    public void setThis(Stage stage) {
        thisStage = stage;
    }
}
