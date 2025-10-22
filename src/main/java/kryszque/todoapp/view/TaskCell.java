package kryszque.todoapp.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kryszque.todoapp.controller.EditTaskWindowController;
import kryszque.todoapp.controller.MainWindowController;
import kryszque.todoapp.model.db.TaskDAO;
import kryszque.todoapp.model.tasks.Task;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class TaskCell extends ListCell<Task> {

    @FXML
    private Label titleLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label dueToDateLabel;
    @FXML
    private Label addDateLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private VBox vbox;

    // NOWE POLA FXML
    @FXML
    private Button completeButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private FXMLLoader fxmlLoader;

    private MainWindowController mainWindowController;
    private TaskDAO taskDAO = new TaskDAO();

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }


    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if(empty || task == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                URL fxmlUrl = getClass().getResource("/TaskCell.fxml");
                if (fxmlUrl == null) {
                    System.err.println("FATAL: Could not find /TaskCell.fxml.");
                    setText("Error: Could not load TaskCell.fxml");
                    setGraphic(null);
                    return;
                }
                fxmlLoader = new FXMLLoader(fxmlUrl);
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            titleLabel.setText(task.getTitle());
            categoryLabel.setText(task.getCategory());
            dueToDateLabel.setText(task.getDue_to_date());
            addDateLabel.setText(task.getAddDate());
            descriptionLabel.setText(task.getDescription());
            priorityLabel.setText(String.valueOf(task.getPriority()));


            completeButton.setText(task.isDone() ? "Undo" : "Complete");
            completeButton.setOnAction(event -> handleComplete(task));

            editButton.setOnAction(event -> handleEdit(task));
            editButton.setDisable(task.isDone());

            deleteButton.setOnAction(event -> handleDelete(task));

            vbox.getStyleClass().remove("task-cell-done");

            if (task.isDone()) {
                vbox.getStyleClass().add("task-cell-done");
            }

            setText(null);
            setGraphic(vbox);
        }
    }

    private void handleComplete(Task task) {
        task.setDone(!task.isDone());
        taskDAO.editTask(task);
        mainWindowController.loadTasks();
    }

    private void handleDelete(Task task) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Task");
        alert.setHeaderText("Are you sure you want to delete this task?");
        alert.setContentText("Task: " + task.getTitle());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            taskDAO.deleteTask(task.getId());
            mainWindowController.loadTasks();
        }
    }

    private void handleEdit(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/EditTaskWindow.fxml");
            if (xmlUrl == null) {
                System.err.println("FATAL: Could not find /EditTaskWindow.fxml.");
                return;
            }
            loader.setLocation(xmlUrl);
            Parent root = loader.load();

            EditTaskWindowController controller = loader.getController();
            controller.setMainWindowController(mainWindowController);
            controller.setTask(task);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit task");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}