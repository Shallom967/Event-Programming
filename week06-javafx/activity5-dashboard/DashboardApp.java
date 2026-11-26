import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        // =========================
        // TOP BAR
        // =========================
        Label title = new Label("My SaaS Dashboard");
        title.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 22px;" +
                "-fx-font-weight: bold;"
        );

        HBox topBar = new HBox(title);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #1e3a5f;");

        // =========================
        // SIDEBAR
        // =========================
        Button overviewButton = new Button("Overview");
        Button settingsButton = new Button("Settings");
        Button logoutButton = new Button("Logout");

        overviewButton.setMaxWidth(Double.MAX_VALUE);
        settingsButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        VBox sidebar = new VBox(
                15,
                overviewButton,
                settingsButton,
                logoutButton
        );

        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(160);
        sidebar.setStyle("-fx-background-color: #eeeeee;");

        // =========================
        // WELCOME MESSAGE
        // =========================
        Label welcomeLabel = new Label("Welcome back, User!");
        welcomeLabel.setStyle(
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;"
        );

        // =========================
        // DASHBOARD CARDS
        // =========================
        Label revenueTitle = new Label("Revenue");
        Label revenueValue = new Label("$12,450");

        Label usersTitle = new Label("Users");
        Label usersValue = new Label("1,204");

        Label ordersTitle = new Label("Orders");
        Label ordersValue = new Label("342");

        styleCardTitle(revenueTitle);
        styleCardTitle(usersTitle);
        styleCardTitle(ordersTitle);

        styleCardValue(revenueValue);
        styleCardValue(usersValue);
        styleCardValue(ordersValue);

        VBox revenueCard = createCard(revenueTitle, revenueValue);
        VBox usersCard = createCard(usersTitle, usersValue);
        VBox ordersCard = createCard(ordersTitle, ordersValue);

        GridPane cards = new GridPane();
        cards.setHgap(20);
        cards.setVgap(20);

        cards.add(revenueCard, 0, 0);
        cards.add(usersCard, 1, 0);
        cards.add(ordersCard, 2, 0);

        // =========================
        // CENTER CONTENT
        // =========================
        VBox centerContent = new VBox(
                30,
                welcomeLabel,
                cards
        );

        centerContent.setPadding(new Insets(30));

        // =========================
        // BORDERPANE
        // =========================
        BorderPane root = new BorderPane();

        root.setTop(topBar);
        root.setLeft(sidebar);
        root.setCenter(centerContent);

        // =========================
        // SCENE
        // =========================
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("SaaS Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createCard(Label title, Label value) {

        VBox card = new VBox(
                10,
                title,
                value
        );

        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        card.setPrefSize(180, 120);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #cccccc;" +
                "-fx-border-radius: 8px;" +
                "-fx-background-radius: 8px;"
        );

        return card;
    }

    private void styleCardTitle(Label label) {

        label.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-text-fill: #555555;"
        );
    }

    private void styleCardValue(Label label) {

        label.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );
    }

    public static void main(String[] args) {

        launch(args);
    }
}