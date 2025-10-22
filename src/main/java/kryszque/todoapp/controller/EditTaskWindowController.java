package kryszque.todoapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kryszque.todoapp.model.db.TaskDAO;
import kryszque.todoapp.model.tasks.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditTaskWindowController {

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
    private Task currentTask;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setTask(Task task) {
        this.currentTask = task;

        titleField.setText(task.getTitle());
        categoryField.setText(task.getCategory());
        descriptionArea.setText(task.getDescription());
        prioritySlider.setValue(task.getPriority());

        if (task.getDue_to_date() != null && !task.getDue_to_date().isEmpty()) {
            datePicker.setValue(LocalDate.parse(task.getDue_to_date(), formatter));
        }
    }

    @FXML
    private void handleEditTask() {
        if (currentTask == null) {
            showAlert("Error", "No task selected for editing.");
            return;
        }

        // Walidacja (podobna do AddTaskWindowController)
        if (datePicker.getValue() == null) {
            showAlert("No date picked!", "Please pick a date.");
            return;
        }
        if (titleField.getText() == null || titleField.getText().isEmpty() ||
                categoryField.getText() == null || categoryField.getText().isEmpty()) {
            showAlert("Fields not filled!", "Please fill all fields.");
            return;
        }

        // Zaktualizuj obiekt currentTask
        currentTask.setTitle(titleField.getText());
        currentTask.setCategory(categoryField.getText());
        currentTask.setDue_to_date(datePicker.getValue().format(formatter));
        currentTask.setPriority((int) prioritySlider.getValue());

        if (descriptionArea.getText() == null || descriptionArea.getText().isEmpty()) {
            currentTask.setDescription("none");
        } else {
            currentTask.setDescription(descriptionArea.getText());
        }

        taskDAO.editTask(currentTask);

        mainWindowController.loadTasks();

        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}