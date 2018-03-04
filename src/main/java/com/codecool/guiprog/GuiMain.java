package com.codecool.guiprog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiMain extends Application {
    Scene menu;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Start.fxml"));
        Loader.load();
        Intro intro = Loader.getController();
        Parent root = Loader.getRoot();
        Scene scene = new Scene(root);
        stage.setTitle("Stoned Hearth");
        stage.setResizable(false);
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        stage.setScene(scene);
        intro.setThis(stage);
        stage.show();
    }


}
