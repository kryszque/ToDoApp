package kryszque.todoapp.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import kryszque.todoapp.model.tasks.Task;

import java.io.IOException;
import java.net.URL;

public class TaskCell extends ListCell<Task> {

    @FXML
    private Label titleLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private VBox vbox;

    private FXMLLoader fxmlLoader;

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
                    System.err.println("FATAL: Could not find /TaskCell.fxml. Make sure the file is in src/main/resources");
                    // Display something basic if the FXML is not found
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
            dateLabel.setText(task.getDate());
            descriptionLabel.setText(task.getDescription());
            priorityLabel.setText(String.valueOf(task.getPriority()));

            setText(null);
            setGraphic(vbox);
        }
    }
}