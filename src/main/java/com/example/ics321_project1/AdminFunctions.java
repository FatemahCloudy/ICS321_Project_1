package com.example.ics321_project1;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

            String sql = "INSERT INTO Race (raceName, trackName, raceDate, raceTime) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

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

    // Delete an owner and their ownership records
    public VBox deleteOwner() {
        VBox vbox = new VBox(10);
        Label lblOwnerId = new Label("Owner ID:");
        TextField txtOwnerId = new TextField();

        Button btnDelete = new Button("Delete Owner");
        btnDelete.setOnAction(e -> {
            int ownerId = Integer.parseInt(txtOwnerId.getText());

            String deleteOwnsSQL = "DELETE FROM Owns WHERE ownerId = ?";
            String deleteOwnerSQL = "DELETE FROM Owner WHERE ownerId = ?";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmtOwns = conn.prepareStatement(deleteOwnsSQL);
                 PreparedStatement stmtOwner = conn.prepareStatement(deleteOwnerSQL)) {

                // Delete from Owns
                stmtOwns.setInt(1, ownerId);
                stmtOwns.executeUpdate();

                // Delete the owner
                stmtOwner.setInt(1, ownerId);
                int rowsAffected = stmtOwner.executeUpdate();

                if (rowsAffected > 0)
                    System.out.println("✅ Owner (ID: " + ownerId + ") deleted successfully.");
                else
                    System.out.println("⚠️ No owner found with that ID.");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        vbox.getChildren().addAll(lblOwnerId, txtOwnerId, btnDelete);
        return vbox;
    }

    // Move a horse to a new stable
    public VBox moveHorse() {
        VBox vbox = new VBox(10);
        Label lblHorseId = new Label("Horse ID:");
        TextField txtHorseId = new TextField();
        Label lblNewStableId = new Label("New Stable ID:");
        TextField txtNewStableId = new TextField();

        Button btnMove = new Button("Move Horse");
        btnMove.setOnAction(e -> {
            int horseId = Integer.parseInt(txtHorseId.getText());
            int newStableId = Integer.parseInt(txtNewStableId.getText());

            String sql = "UPDATE Horse SET stableId = ? WHERE horseId = ?";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, newStableId);
                stmt.setInt(2, horseId);
                int rows = stmt.executeUpdate();

                if (rows > 0)
                    System.out.println("✅ Horse " + horseId + " moved to stable " + newStableId + " successfully!");
                else
                    System.out.println("⚠️ Horse not found!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        vbox.getChildren().addAll(lblHorseId, txtHorseId, lblNewStableId, txtNewStableId, btnMove);
        return vbox;
    }

    // Approve a trainer
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
            int trainerId = Integer.parseInt(txtTrainerId.getText());
            String lname = txtLastName.getText();
            String fname = txtFirstName.getText();
            int stableId = Integer.parseInt(txtStableId.getText());

            String sql = "INSERT INTO Trainer (trainerId, lname, fname, stableId) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, trainerId);
                stmt.setString(2, lname);
                stmt.setString(3, fname);
                stmt.setInt(4, stableId);
                stmt.executeUpdate();

                System.out.println("✅ Trainer approved!");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        vbox.getChildren().addAll(lblTrainerId, txtTrainerId, lblLastName, txtLastName, lblFirstName, txtFirstName, lblStableId, txtStableId, btnApprove);
        return vbox;
    }
}