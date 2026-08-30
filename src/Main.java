import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application entry point for the FileVault JavaFX application.
 * 
 * OOP Concept Demonstrated:
 * - Inheritance: Extends javafx.application.Application and overrides the start method.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainController controller = new MainController(primaryStage);

        Scene scene = new Scene(controller.createView(), 700, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("FileVault - File Encryption & Decryption");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(550);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
