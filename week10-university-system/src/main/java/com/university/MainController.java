package com.university;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane centerPane;

    @FXML
    public void initialize() {
        showDashboard();
    }

    @FXML
    private void showDashboard() {

        Label welcomeLabel =
                new Label("Welcome to the University Dashboard!");

        welcomeLabel.setStyle(
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold;"
        );

        centerPane.getChildren().setAll(welcomeLabel);
    }

    @FXML
    private void showStudents() {

        try {

            Parent studentView = FXMLLoader.load(
                    getClass().getResource("StudentView.fxml")
            );

            centerPane.getChildren().setAll(studentView);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}