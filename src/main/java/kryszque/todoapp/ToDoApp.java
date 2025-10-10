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

        // Bardziej niezawodny sposób ładowania FXML
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/MainWindow.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setTitle("ToDo App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}