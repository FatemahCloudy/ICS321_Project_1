package com.example.ics321_project1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;


public class MainPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            DatabaseConnection.initialize(); // connect once
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database");
            return; // stop app if no DB
        }

        VBox vbox = new VBox(15);
        HBox header = new HBox(20);
        Scene scene = new Scene(vbox, 300, 250);
        PageNavigator navigator = new PageNavigator(primaryStage);
        Label title = new Label("Horse Racing System");

        // Create two buttons
        Button button1 = new Button("Admin");
        Button button2 = new Button("User");


        // Set button actions
        button1.setOnAction(e -> navigator.goToAdminPage());
        button2.setOnAction(e -> navigator.goToUserPage());

        //add header
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15));
        header.getStyleClass().add("header");

        title.getStyleClass().add("title");
        header.getChildren().add(title);

        vbox.getStyleClass().add("vbox");

        // add the components to the vbox
        vbox.getChildren().addAll(header, button1, button2);


        // load the CSS file
        URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("CSS file not found");
        }

        // set the scene on the stage
        primaryStage.setTitle("Horse Racing");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
