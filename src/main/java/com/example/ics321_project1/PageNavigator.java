package com.example.ics321_project1;

import javafx.stage.Stage;

public class PageNavigator {
    private Stage stage;

    public PageNavigator(Stage stage) {
        this.stage = stage;
    }

    // Handles the moving backwards button in both admin and user pages
    public void goToMainPage() {
        try {
            new MainPage().start(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Handles the admin button
    public void goToAdminPage() {
        try {
            new AdminPage().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handles the user button
    public void goToUserPage() {
        try {
            new UserPage().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
