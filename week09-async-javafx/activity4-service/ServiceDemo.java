import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServiceDemo extends Application {

    private final Service<Void> syncService = new Service<>() {

        @Override
        protected Task<Void> createTask() {

            return new Task<>() {

                @Override
                protected Void call() throws Exception {

                    for (int i = 1; i <= 10; i++) {

                        if (isCancelled()) {
                            break;
                        }

                        Thread.sleep(500);

                        updateMessage(
                                "Syncing data... "
                                + (i * 10) + "%"
                        );
                    }

                    return null;
                }
            };
        }
    };

    @Override
    public void start(Stage primaryStage) {

        Label statusLabel =
                new Label("Ready to sync.");

        ProgressIndicator progressIndicator =
                new ProgressIndicator();

        progressIndicator.setVisible(false);

        Button syncButton =
                new Button("Sync Data");

        Button cancelButton =
                new Button("Cancel");

        cancelButton.setDisable(true);

        syncButton.setOnAction(e -> {

            if (syncService.isRunning()) {
                return;
            }

            statusLabel.textProperty().unbind();

            statusLabel.setText(
                    "Starting synchronization..."
            );

            progressIndicator.setVisible(true);

            syncButton.setDisable(true);
            cancelButton.setDisable(false);

            syncService.reset();

            syncService.messageProperty().addListener(
                    (observable, oldValue, newValue) -> {

                        statusLabel.setText(newValue);
                    }
            );

            syncService.setOnSucceeded(event -> {

                statusLabel.textProperty().unbind();

                statusLabel.setText(
                        "Synchronization Complete!"
                );

                progressIndicator.setVisible(false);

                syncButton.setDisable(false);
                cancelButton.setDisable(true);
            });

            syncService.setOnCancelled(event -> {

                statusLabel.textProperty().unbind();

                statusLabel.setText(
                        "Synchronization Cancelled."
                );

                progressIndicator.setVisible(false);

                syncButton.setDisable(false);
                cancelButton.setDisable(true);
            });

            syncService.setOnFailed(event -> {

                statusLabel.textProperty().unbind();

                statusLabel.setText(
                        "Synchronization Failed."
                );

                progressIndicator.setVisible(false);

                syncButton.setDisable(false);
                cancelButton.setDisable(true);
            });

            syncService.start();
        });

        cancelButton.setOnAction(e -> {

            if (syncService.isRunning()) {

                syncService.cancel();
            }
        });

        VBox root = new VBox(
                20,
                syncButton,
                progressIndicator,
                statusLabel,
                cancelButton
        );

        root.setPadding(new Insets(30));

        root.setStyle(
                "-fx-alignment: center;"
        );

        Scene scene =
                new Scene(root, 450, 300);

        primaryStage.setTitle(
                "JavaFX Service Demo"
        );

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}