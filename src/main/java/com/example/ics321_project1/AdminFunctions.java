package com.example.ics321_project1;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.Connection;

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
            String name = txtRaceName.getText();
            String track = txtTrackName.getText();
            String date = txtRaceDate.getText();
            String time = txtRaceTime.getText();

            String sql = "INSERT INTO Race (race_name, track_name, race_date, race_time) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.connect();
                 java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, name);
                stmt.setString(2, track);
                stmt.setString(3, date);
                stmt.setString(4, time);
                stmt.executeUpdate();

                System.out.println("✅ Race added successfully!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
            //ToDo: Logic to delete an owner
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
            // TODo:Logic to move the horse to a new stable
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
            // ToDo:Logic to add the trainer to the database
        });

        vbox.getChildren().addAll(lblTrainerId, txtTrainerId, lblLastName, txtLastName, lblFirstName, txtFirstName, lblStableId, txtStableId, btnApprove);
        return vbox;
    }
}