package kryszque.todoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kryszque.todoapp.model.db.InitDB;

import java.io.IOException;
import java.net.URL;

public class ToDoApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        InitDB.init(); // Inicjalizacja bazy danych

        // Debugging classpath
        try {
            System.out.println("Attempting to load resources from classpath root: " + ToDoApp.class.getResource("/"));
        } catch (Exception e) {
            System.err.println("Error getting classpath root: " + e.getMessage());
        }


        // Ładowanie FXML
        URL fxmlUrl = getClass().getResource("/MainWindow.fxml");
        if (fxmlUrl == null) {
            System.err.println("FATAL: Nie mozna znalezc pliku /MainWindow.fxml.");
            return;
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

        // Ładowanie CSS
        URL cssUrl = getClass().getResource("/styles.css");
        if (cssUrl == null) {
            System.err.println("WARNING: Nie mozna znalezc pliku /styles.css.");
        } else {
            scene.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("Successfully loaded styles.css");
        }


        primaryStage.setTitle("ToDo App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}