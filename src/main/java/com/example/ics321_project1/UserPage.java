package com.example.ics321_project1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;

public class UserPage extends Application {
    @Override
    public void start(Stage primaryStage) {
        GuestFunctions guest = new GuestFunctions();
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        // ---- header ----
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15));
        header.getStyleClass().add("header");

        Label title = new Label("Horse Racing Management System (Guest)");
        title.getStyleClass().add("title");
        header.getChildren().add(title);
        root.setTop(header);

        // ---- content grid (2x2) ----
        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(16);
        grid.setPadding(new Insets(0, 0, 0, 0));

        // Make two equal-width columns
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c1.setPercentWidth(50);
        c2.setPercentWidth(50);
        c1.setHgrow(Priority.ALWAYS);
        c2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(c1, c2);

        // Make two equal-height rows
        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        r1.setPercentHeight(50);
        r2.setPercentHeight(50);
        r1.setVgrow(Priority.ALWAYS);
        r2.setVgrow(Priority.ALWAYS);
        grid.getRowConstraints().addAll(r1, r2);

        // Build guest panes
        VBox ownerPane    = guest.horsesByOwnerLastNamePane();
        VBox winnersPane  = guest.winningTrainersPane();
        VBox winningsPane = guest.trainerWinningsPane();
        VBox tracksPane   = guest.trackStatsPane();

        // Let panes stretch inside the grid
        for (VBox box : new VBox[]{ownerPane, winnersPane, winningsPane, tracksPane}) {
            box.setFillWidth(true);
            GridPane.setHgrow(box, Priority.ALWAYS);
            GridPane.setVgrow(box, Priority.ALWAYS);
        }

        // Place panes in a 2x2 layout
        grid.add(ownerPane,   0, 0);
        grid.add(winnersPane, 1, 0);
        grid.add(winningsPane,0, 1);
        grid.add(tracksPane,  1, 1);

        root.setCenter(grid);
         Scene scene = new Scene(root, 800, 400);


        //load the CSS file
        URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("CSS file not found");
        }

        primaryStage.setScene(scene);
        primaryStage.setTitle("Horse Racing Guest");
        primaryStage.show();
    }
}
