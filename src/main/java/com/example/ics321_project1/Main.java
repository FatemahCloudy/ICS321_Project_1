package com.example.ics321_project1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        HBox hbox = new HBox(20);

        AdminFunctions adminFunctions = new AdminFunctions();

        hbox.getChildren().addAll(adminFunctions.addRace(), adminFunctions.deleteOwner(), adminFunctions.moveHorse(), adminFunctions.approveTrainer());

        pane.add(hbox, 0, 0);

        Scene scene = new Scene(pane, 800, 400);

        // Load the CSS file
        URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("CSS file not found");
        }

        primaryStage.setScene(scene);
        primaryStage.setTitle("Horse Racing Database Admin");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}