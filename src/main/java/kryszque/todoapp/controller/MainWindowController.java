package kryszque.todoapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kryszque.todoapp.model.db.TaskDAO;
import kryszque.todoapp.model.tasks.Task;
import kryszque.todoapp.view.TaskCell;

import java.io.IOException;
import java.net.URL;

public class MainWindowController {

    @FXML
    private ListView<Task> taskListView;


    private final TaskDAO taskDAO = new TaskDAO();


    @FXML
    public void initialize() {
        taskListView.setCellFactory(param -> new TaskCell());
        loadTasks();
    }

    public void loadTasks() {
        taskListView.setItems(FXCollections.observableArrayList(taskDAO.getTasks()));
    }

    @FXML
    private void handleAddTaskButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/AddTaskWindow.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            AddTaskWindowController controller = loader.getController();
            controller.setMainWindowController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Dodaj nowe zadanie");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}