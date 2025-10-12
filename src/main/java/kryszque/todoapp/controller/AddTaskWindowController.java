package kryszque.todoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kryszque.todoapp.model.db.TaskDAO;
import kryszque.todoapp.model.tasks.Task;

import java.time.format.DateTimeFormatter;

public class AddTaskWindowController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField categoryField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Slider prioritySlider;

    private final TaskDAO taskDAO = new TaskDAO();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }


    @FXML
    private void handleAddTask() {
        Task newTask = new Task();
        newTask.setTitle(titleField.getText());
        newTask.setCategory(categoryField.getText());
        if (datePicker.getValue() != null) {
            newTask.setDate(datePicker.getValue().format(formatter));
        } else if (newTask.getTitle() != null && newTask.getCategory() != null) {
            // Exception - date wasn't picked
            showAlert("No date picked!", "Please pick a date.");
            return;
        } else {
            showAlert("Fields not filled!", "Please fill all fields.");
            return;

        }
        newTask.setDescription(descriptionArea.getText());
        newTask.setPriority((int) prioritySlider.getValue());

        if (newTask.getTitle() != null && newTask.getCategory() != null && newTask.getDate() != null) {
            taskDAO.addTask(newTask);
            clearFields();
            mainWindowController.loadTasks();
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();
        } else if (newTask.getTitle() == null && newTask.getCategory() == null) {
            showAlert("Fields not filled!", "Please fill all fields.");
        } else if (newTask.getDate() == null) {
            showAlert("Invalid date!", "Please provide a valid date.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        titleField.clear();
        categoryField.clear();
        datePicker.setValue(null);
        descriptionArea.clear();
        prioritySlider.setValue(5);
    }
}