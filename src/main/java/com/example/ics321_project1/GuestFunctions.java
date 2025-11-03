package com.example.ics321_project1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class GuestFunctions {
    // 1) Horses (name, age) + trainer name, filtered by Owner last name
    public VBox horsesByOwnerLastNamePane() {
        VBox root = new VBox(8); root.setPadding(new Insets(10));
        TextField lastName = new TextField(); lastName.setPromptText("Owner last name (e.g., Mohammed)");
        Button search = new Button("Search");

        TableView<Row1> table = new TableView<>();
        TableColumn<Row1, String> c1 = new TableColumn<>("Horse");
        c1.setCellValueFactory(d -> d.getValue().horseName);
        TableColumn<Row1, String> c2 = new TableColumn<>("Age");
        c2.setCellValueFactory(d -> d.getValue().age);
        TableColumn<Row1, String> c3 = new TableColumn<>("Trainer First");
        c3.setCellValueFactory(d -> d.getValue().trainerF);
        TableColumn<Row1, String> c4 = new TableColumn<>("Trainer Last");
        c4.setCellValueFactory(d -> d.getValue().trainerL);
        table.getColumns().addAll(c1,c2,c3,c4);

        search.setOnAction(e -> {
            ObservableList<Row1> rows = FXCollections.observableArrayList();
            String sql = """
                SELECT h.horseName, h.age,
                       t.fname AS trainerF, t.lname AS trainerL
                FROM Owner o
                JOIN Owns ow ON ow.ownerId = o.ownerId
                JOIN Horse h ON h.horseId = ow.horseId
                LEFT JOIN Trainer t ON t.stableId = h.stableId
                WHERE o.lname = ?
                ORDER BY h.horseName
            """;
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, lastName.getText().trim());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        rows.add(new Row1(
                                rs.getString("horseName"),
                                String.valueOf(rs.getInt("age")),
                                rs.getString("trainerF"),
                                rs.getString("trainerL")
                        ));
                    }
                }
                table.setItems(rows);
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        HBox top = new HBox(8, new Label("Owner Last Name:"), lastName, search);
        HBox.setHgrow(lastName, Priority.ALWAYS);
        root.getChildren().addAll(top, table);
        return root;
    }

    // 2) Trainers who trained winners (results='first') + winning horse + race details
    public VBox winningTrainersPane() {
        VBox root = new VBox(8); root.setPadding(new Insets(10));
        Button refresh = new Button("Refresh");
        TableView<Row2> table = new TableView<>();
        table.getColumns().addAll(
                col(Row2::trainerF, "Trainer First"),
                col(Row2::trainerL, "Trainer Last"),
                col(Row2::horseName, "Winning Horse"),
                col(Row2::raceName, "Race"),
                col(Row2::trackName, "Track"),
                col(Row2::raceDate, "Date"),
                col(Row2::raceTime, "Time")
        );

        refresh.setOnAction(e -> {
            ObservableList<Row2> rows = FXCollections.observableArrayList();
            String sql =  """
    SELECT DISTINCT
           tr.fname AS trainerF, tr.lname AS trainerL,
           h.horseName,
           r.raceName, r.trackName,
           DATE_FORMAT(r.raceDate, '%Y-%m-%d') AS raceDate,
           DATE_FORMAT(r.raceTime, '%H:%i:%s') AS raceTime
    FROM RaceResults rr
    JOIN Horse h    ON h.horseId = rr.horseId
    JOIN Trainer tr ON tr.stableId = h.stableId
    JOIN Race r     ON r.raceId = rr.raceId
    WHERE rr.results = 'first'
    ORDER BY raceDate DESC, raceTime DESC, trainerL, trainerF
""";
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Row2(
                            rs.getString("trainerF"),
                            rs.getString("trainerL"),
                            rs.getString("horseName"),
                            rs.getString("raceName"),
                            rs.getString("trackName"),
                            rs.getString("raceDate"),
                            rs.getString("raceTime")
                    ));
                }
                table.setItems(rows);
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        root.getChildren().addAll(new HBox(8, refresh), table);
        return root;
    }

    // 3) Trainer total winnings (sum of prize), sorted by winnings desc
    public VBox trainerWinningsPane() {
        VBox root = new VBox(8); root.setPadding(new Insets(10));
        Button refresh = new Button("Refresh");
        TableView<Row3> table = new TableView<>();
        table.getColumns().addAll(
                col(Row3::trainerF, "Trainer First"),
                col(Row3::trainerL, "Trainer Last"),
                col(Row3::totalWinnings, "Total Winnings")
        );

        refresh.setOnAction(e -> {
            ObservableList<Row3> rows = FXCollections.observableArrayList();
            String sql = """
                SELECT tr.fname AS trainerF, tr.lname AS trainerL,
                       COALESCE(SUM(rr.prize), 0) AS totalWinnings
                FROM Trainer tr
                LEFT JOIN Horse h      ON h.stableId = tr.stableId
                LEFT JOIN RaceResults rr ON rr.horseId = h.horseId
                GROUP BY tr.trainerId, tr.fname, tr.lname
                ORDER BY totalWinnings DESC, tr.lname, tr.fname
            """;
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Row3(
                            rs.getString("trainerF"),
                            rs.getString("trainerL"),
                            String.format("%.2f", rs.getDouble("totalWinnings"))
                    ));
                }
                table.setItems(rows);
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        root.getChildren().addAll(new HBox(8, refresh), table);
        return root;
    }

    // 4) Tracks + count of races + total number of horses participating on that track
    public VBox trackStatsPane() {
        VBox root = new VBox(8); root.setPadding(new Insets(10));
        Button refresh = new Button("Refresh");
        TableView<Row4> table = new TableView<>();
        table.getColumns().addAll(
                col(Row4::trackName, "Track"),
                col(Row4::raceCount, "Race Count"),
                col(Row4::totalEntries, "Total Horse Entries")
        );

        refresh.setOnAction(e -> {
            ObservableList<Row4> rows = FXCollections.observableArrayList();
            String sql = """
                SELECT t.trackName,
                       COUNT(DISTINCT r.raceId) AS raceCount,
                       COUNT(rr.horseId)        AS totalEntries
                FROM Track t
                LEFT JOIN Race r        ON r.trackName = t.trackName
                LEFT JOIN RaceResults rr ON rr.raceId = r.raceId
                GROUP BY t.trackName
                ORDER BY raceCount DESC, totalEntries DESC, t.trackName
            """;
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Row4(
                            rs.getString("trackName"),
                            String.valueOf(rs.getLong("raceCount")),
                            String.valueOf(rs.getLong("totalEntries"))
                    ));
                }
                table.setItems(rows);
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        root.getChildren().addAll(new HBox(8, refresh), table);
        return root;
    }

    // ---------- tiny row models ----------

    record Row1(javafx.beans.property.SimpleStringProperty horseName,
                javafx.beans.property.SimpleStringProperty age,
                javafx.beans.property.SimpleStringProperty trainerF,
                javafx.beans.property.SimpleStringProperty trainerL) {
        Row1(String a, String b, String c, String d){ this(new javafx.beans.property.SimpleStringProperty(a),
                new javafx.beans.property.SimpleStringProperty(b),
                new javafx.beans.property.SimpleStringProperty(c),
                new javafx.beans.property.SimpleStringProperty(d)); }
    }

    record Row2(javafx.beans.property.SimpleStringProperty trainerF,
                javafx.beans.property.SimpleStringProperty trainerL,
                javafx.beans.property.SimpleStringProperty horseName,
                javafx.beans.property.SimpleStringProperty raceName,
                javafx.beans.property.SimpleStringProperty trackName,
                javafx.beans.property.SimpleStringProperty raceDate,
                javafx.beans.property.SimpleStringProperty raceTime) {
        Row2(String a,String b,String c,String d,String e,String f,String g){
            this(new javafx.beans.property.SimpleStringProperty(a),
                    new javafx.beans.property.SimpleStringProperty(b),
                    new javafx.beans.property.SimpleStringProperty(c),
                    new javafx.beans.property.SimpleStringProperty(d),
                    new javafx.beans.property.SimpleStringProperty(e),
                    new javafx.beans.property.SimpleStringProperty(f),
                    new javafx.beans.property.SimpleStringProperty(g));
        }
    }

    record Row3(javafx.beans.property.SimpleStringProperty trainerF,
                javafx.beans.property.SimpleStringProperty trainerL,
                javafx.beans.property.SimpleStringProperty totalWinnings) {
        Row3(String a,String b,String c){
            this(new javafx.beans.property.SimpleStringProperty(a),
                    new javafx.beans.property.SimpleStringProperty(b),
                    new javafx.beans.property.SimpleStringProperty(c));
        }
    }

    record Row4(javafx.beans.property.SimpleStringProperty trackName,
                javafx.beans.property.SimpleStringProperty raceCount,
                javafx.beans.property.SimpleStringProperty totalEntries) {
        Row4(String a,String b,String c){
            this(new javafx.beans.property.SimpleStringProperty(a),
                    new javafx.beans.property.SimpleStringProperty(b),
                    new javafx.beans.property.SimpleStringProperty(c));
        }
    }

    // helper to make string columns fast
    private <T> TableColumn<T, String> col(java.util.function.Function<T, javafx.beans.property.SimpleStringProperty> getter, String title){
        TableColumn<T, String> c = new TableColumn<>(title);
        c.setCellValueFactory(data -> getter.apply(data.getValue()));
        c.setPrefWidth(160);
        return c;
    }
}
