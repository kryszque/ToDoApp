package kryszque.todoapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kryszque.todoapp.model.db.TaskDAO;
import kryszque.todoapp.model.tasks.Task;

import java.time.format.DateTimeFormatter;

public class MainWindowController {

    @FXML
    private ListView<String> taskListView;
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


    @FXML
    public void initialize() {
        loadTasks();
    }

    private void loadTasks() {
        taskListView.setItems(FXCollections.observableArrayList(taskDAO.getTasks()));
    }

    @FXML
    private void handleAddTask() {
        Task newTask = new Task();
        newTask.setTitle(titleField.getText());
        newTask.setCategory(categoryField.getText());
        if (datePicker.getValue() != null) {
            newTask.setDate(datePicker.getValue().format(formatter));
        } else {
            // Obsługa błędu - data nie została wybrana
            showAlert("Błąd", "Data nie została wybrana.");
            return;
        }
        newTask.setDescription(descriptionArea.getText());
        newTask.setPriority((int) prioritySlider.getValue());

        if (newTask.getTitle() != null && newTask.getCategory() != null && newTask.getDate() != null) {
            taskDAO.addTask(newTask);
            clearFields();
            loadTasks();
        } else {
            showAlert("Błąd", "Proszę wypełnić wszystkie pola poprawnie.");
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