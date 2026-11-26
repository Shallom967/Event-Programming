import javafx.application.Application;
import javafx.stage.Stage;

public class ListenerDemo extends Application {

    @Override
    public void start(Stage primaryStage) {

        Person person = new Person();

        // =====================================
        // INVALIDATION LISTENER
        // =====================================

        person.nameProperty().addListener(
                observable -> {

                    System.out.println(
                            "1. [InvalidationListener] "
                            + "Name was changed!"
                    );
                }
        );

        // =====================================
        // CHANGE LISTENER
        // =====================================

        person.nameProperty().addListener(
                (observable, oldValue, newValue) -> {

                    System.out.println(
                            "2. [ChangeListener] "
                            + "Name changed from '"
                            + oldValue
                            + "' to '"
                            + newValue
                            + "'"
                    );
                }
        );

        // =====================================
        // TRIGGER CHANGES
        // =====================================

        System.out.println(
                "--- Setting name to Alice ---"
        );

        person.setName("Alice");

        System.out.println(
                "\n--- Setting name to Bob ---"
        );

        person.setName("Bob");

        System.out.println(
                "\n--- Setting name to Bob (same value) ---"
        );

        person.setName("Bob");
    }

    public static void main(String[] args) {

        launch(args);
    }
}