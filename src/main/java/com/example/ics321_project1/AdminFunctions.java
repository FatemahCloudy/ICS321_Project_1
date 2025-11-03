package com.example.ics321_project1;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.*;

public class AdminFunctions {

    // Add a new Race
    public VBox addRace() {
        VBox vbox = new VBox(10);

        Label lblRaceId = new Label("Race ID:");
        TextField txtRaceId = new TextField();

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
            String raceId = txtRaceId.getText().trim();
            String name = txtRaceName.getText().trim();
            String track = txtTrackName.getText().trim();
            String date = txtRaceDate.getText().trim();
            String time = txtRaceTime.getText().trim();

            if (raceId.isEmpty() || name.isEmpty() || track.isEmpty() || date.isEmpty() || time.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "All fields are required!");
                return;
            }

            String sql = "INSERT INTO Race (raceId, raceName, trackName, raceDate, raceTime) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, raceId);
                stmt.setString(2, name);
                stmt.setString(3, track);
                stmt.setString(4, date);
                stmt.setString(5, time);

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "✅ Race added successfully!");

            } catch (SQLIntegrityConstraintViolationException ex) {
                showAlert(Alert.AlertType.ERROR, "❌ Invalid track name or duplicate race ID.");
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "❌ Error adding race: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(lblRaceId, txtRaceId, lblRaceName, txtRaceName, lblTrackName, txtTrackName,
                lblRaceDate, txtRaceDate, lblRaceTime, txtRaceTime, btnAddRace);
        return vbox;
    }

    // Delete an owner using stored procedure
    public VBox deleteOwner() {
        VBox vbox = new VBox(10);

        Label lblOwnerId = new Label("Owner ID:");
        TextField txtOwnerId = new TextField();

        Button btnDelete = new Button("Delete Owner");
        btnDelete.setOnAction(e -> {
            String ownerId = txtOwnerId.getText().trim();

            if (ownerId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please enter an Owner ID.");
                return;
            }

            String callSQL = "{CALL DeleteOwnerAndRelated(?)}";

            try (Connection conn = DatabaseConnection.connect();
                 CallableStatement stmt = conn.prepareCall(callSQL)) {

                stmt.setString(1, ownerId);
                stmt.execute();

                showAlert(Alert.AlertType.INFORMATION, "✅ Owner (ID: " + ownerId + ") deleted successfully.");

            } catch (SQLException ex) {
                if (ex.getMessage().contains("doesn't exist")) {
                    showAlert(Alert.AlertType.ERROR, "⚠️ Stored procedure not found. Please create it in MySQL.");
                } else {
                    ex.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "❌ Error deleting owner: " + ex.getMessage());
                }
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
            String horseId = txtHorseId.getText().trim();
            String newStableId = txtNewStableId.getText().trim();

            if (horseId.isEmpty() || newStableId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please fill both fields.");
                return;
            }

            String sql = "UPDATE Horse SET stableId = ? WHERE horseId = ?";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, newStableId);
                stmt.setString(2, horseId);
                int rows = stmt.executeUpdate();

                if (rows > 0)
                    showAlert(Alert.AlertType.INFORMATION, "✅ Horse " + horseId + " moved successfully!");
                else
                    showAlert(Alert.AlertType.WARNING, "⚠️ Horse not found!");

            } catch (SQLIntegrityConstraintViolationException ex) {
                showAlert(Alert.AlertType.ERROR, "❌ Invalid Stable ID! It does not exist.");
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "❌ Error moving horse: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(lblHorseId, txtHorseId, lblNewStableId, txtNewStableId, btnMove);
        return vbox;
    }

    // Approve a new trainer
    public VBox approveTrainer() {
        VBox vbox = new VBox(10);

        Label lblTrainerId = new Label("Trainer ID:");
        TextField txtTrainerId = new TextField();

        Label lblLName = new Label("Last Name:");
        TextField txtLName = new TextField();

        Label lblFName = new Label("First Name:");
        TextField txtFName = new TextField();

        Label lblStableId = new Label("Stable ID:");
        TextField txtStableId = new TextField();

        Button btnApprove = new Button("Approve Trainer");
        btnApprove.setOnAction(e -> {
            String trainerId = txtTrainerId.getText().trim();
            String lname = txtLName.getText().trim();
            String fname = txtFName.getText().trim();
            String stableId = txtStableId.getText().trim();

            if (trainerId.isEmpty() || lname.isEmpty() || fname.isEmpty() || stableId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "All fields are required!");
                return;
            }

            String sql = "INSERT INTO Trainer (trainerId, lname, fname, stableId) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, trainerId);
                stmt.setString(2, lname);
                stmt.setString(3, fname);
                stmt.setString(4, stableId);

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "✅ Trainer approved successfully!");

            } catch (SQLIntegrityConstraintViolationException ex) {
                showAlert(Alert.AlertType.ERROR, "❌ Invalid Stable ID or duplicate Trainer ID.");
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "❌ Error approving trainer: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(lblTrainerId, txtTrainerId, lblLName, txtLName,
                lblFName, txtFName, lblStableId, txtStableId, btnApprove);
        return vbox;
    }

    // Helper for alerts
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}