package com.example.ics321_project1;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AdminFunctions {
    public VBox addRace() {
        VBox vbox = new VBox(10); // Spacing between elements
        Label lblRaceName = new Label("Race Name:");
        TextField txtRaceName = new TextField();
        Label lblTrackName = new Label("Track Name:");
        TextField txtTrackName = new TextField();
        Label lblRaceDate = new Label("Race Date (YYYY-MM-DD):");
        TextField txtRaceDate = new TextField();
        Label lblRaceTime = new Label("Race Time (HH:MM):");
        TextField txtRaceTime = new TextField();

        Button btnAddRace = new Button("Add Race");
        btnAddRace.setOnAction(e -> {
            // Logic to add the race to the database
        });

        vbox.getChildren().addAll(lblRaceName, txtRaceName, lblTrackName, txtTrackName, lblRaceDate, txtRaceDate, lblRaceTime, txtRaceTime, btnAddRace);
        return vbox;
    }

    public VBox deleteOwner() {
        VBox vbox = new VBox(10);
        Label lblOwnerId = new Label("Owner ID:");
        TextField txtOwnerId = new TextField();

        Button btnDelete = new Button("Delete Owner");
        btnDelete.setOnAction(e -> {
            // Logic to delete the owner from the database
        });

        vbox.getChildren().addAll(lblOwnerId, txtOwnerId, btnDelete);
        return vbox;
    }

    public VBox moveHorse() {
        VBox vbox = new VBox(10);
        Label lblHorseId = new Label("Horse ID:");
        TextField txtHorseId = new TextField();
        Label lblNewStableId = new Label("New Stable ID:");
        TextField txtNewStableId = new TextField();

        Button btnMove = new Button("Move Horse");
        btnMove.setOnAction(e -> {
            // Logic to move the horse to a new stable
        });

        vbox.getChildren().addAll(lblHorseId, txtHorseId, lblNewStableId, txtNewStableId, btnMove);
        return vbox;
    }

    public VBox approveTrainer() {
        VBox vbox = new VBox(10);
        Label lblTrainerId = new Label("Trainer ID:");
        TextField txtTrainerId = new TextField();
        Label lblLastName = new Label("Last Name:");
        TextField txtLastName = new TextField();
        Label lblFirstName = new Label("First Name:");
        TextField txtFirstName = new TextField();
        Label lblStableId = new Label("Stable ID:");
        TextField txtStableId = new TextField();

        Button btnApprove = new Button("Approve Trainer");
        btnApprove.setOnAction(e -> {
            // Logic to add the trainer to the database
        });

        vbox.getChildren().addAll(lblTrainerId, txtTrainerId, lblLastName, txtLastName, lblFirstName, txtFirstName, lblStableId, txtStableId, btnApprove);
        return vbox;
    }
}