package com.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentController {

    @FXML
    private TextField regField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> colReg;

    @FXML
    private TableColumn<Student, String> colFirst;

    @FXML
    private TableColumn<Student, String> colLast;

    @FXML
    private Label statusLabel;

    @FXML
    private Button saveButton;

    private final ObservableList<Student> studentList =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colReg.setCellValueFactory(
                cellData -> cellData.getValue().regNumberProperty()
        );

        colFirst.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty()
        );

        colLast.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty()
        );

        studentTable.setItems(studentList);

        // Disable Save until all fields contain text.
        saveButton.disableProperty().bind(
                regField.textProperty().isEmpty()
                        .or(firstNameField.textProperty().isEmpty())
                        .or(lastNameField.textProperty().isEmpty())
        );

        loadStudents();
    }

    @FXML
    private void handleSaveStudent() {

        String reg = regField.getText().trim();
        String fName = firstNameField.getText().trim();
        String lName = lastNameField.getText().trim();

        statusLabel.setText("Saving student...");

        Task<Void> saveTask = new Task<>() {

            @Override
            protected Void call() throws Exception {

                String sql =
                        "INSERT INTO students " +
                        "(registration_number, first_name, last_name, email) " +
                        "VALUES (?, ?, ?, ?)";

                try (
                    Connection conn = DatabaseUtil.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql)
                ) {

                    stmt.setString(1, reg);
                    stmt.setString(2, fName);
                    stmt.setString(3, lName);
                    stmt.setString(4, reg + "@university.edu");

                    stmt.executeUpdate();
                }

                return null;
            }
        };

        saveTask.setOnSucceeded(event -> {

            statusLabel.setText("Student saved successfully!");

            regField.clear();
            firstNameField.clear();
            lastNameField.clear();

            loadStudents();
        });

        saveTask.setOnFailed(event -> {

            statusLabel.setText(
                    "Error: " + saveTask.getException().getMessage()
            );

            saveTask.getException().printStackTrace();
        });

        Thread thread = new Thread(saveTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void loadStudents() {

        statusLabel.setText("Loading students...");

        LoadStudentsTask task = new LoadStudentsTask();

        task.setOnSucceeded(event -> {

            studentList.setAll(task.getValue());

            statusLabel.setText(
                    "Loaded " + studentList.size()
                            + " students from database."
            );
        });

        task.setOnFailed(event -> {

            statusLabel.setText(
                    "Failed to load students: "
                            + task.getException().getMessage()
            );

            task.getException().printStackTrace();
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}