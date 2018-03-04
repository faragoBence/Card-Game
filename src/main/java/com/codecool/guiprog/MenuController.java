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

public class MenuController implements Initializable {
    Stage thisStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToNameEnter(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/NameEnter.fxml"));
        Loader.load();
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        NameEnterController nameEnterController = Loader.getController();
        nameEnterController.setThis(stage);
        stage.show();
        thisStage.close();

    }

    public void setThis(Stage stage) {
        thisStage = stage;
    }
}
