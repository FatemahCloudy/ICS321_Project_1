package com.example.ics321_project1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        AdminFunctions adminFunctions = new AdminFunctions();
        GridPane pane = new GridPane();
        HBox hbox = new HBox(20);
        HBox header = new HBox(20);
        Scene scene = new Scene(pane, 800, 400);
        Label title = new Label("Horse Racing Management System (Admin)");


        //add header
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15));
        header.getStyleClass().add("header");

        title.getStyleClass().add("title");
        header.getChildren().add(title);

        //add the rest of the contents
        hbox.getChildren().addAll(adminFunctions.addRace(), adminFunctions.deleteOwner(), adminFunctions.moveHorse(), adminFunctions.approveTrainer());

        //add everything to the pane
        pane.add(header, 0, 0);
        pane.add(hbox, 0, 2);

        //add a margin between the header and the contents
        GridPane.setMargin(hbox, new Insets(25, 0, 0, 0));


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