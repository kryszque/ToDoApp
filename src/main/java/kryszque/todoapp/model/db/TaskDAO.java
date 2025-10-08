package kryszque.todoapp.model.db;

import kryszque.todoapp.model.tasks.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public void addTask(Task task){
        String query = "INSERT INTO tasks (title, category, date, description, priority) VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = Database.setConnection();
            PreparedStatement pstmt = connection.prepareStatement(query)){

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getCategory());
            pstmt.setString(3, task.getDate());
            pstmt.setString(4, task.getDescription());
            pstmt.setInt(5, task.getPriority());

            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<String> getTasks() {
        List<String> tasks = new ArrayList<>();
        String sql = "SELECT id, title, category, date, description, priority, done FROM tasks";

        try (Connection connection = Database.setConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String category = rs.getString("category");
                String date = rs.getString("date");
                String description = rs.getString("description");
                int priority = rs.getInt("priority");
                boolean done = rs.getBoolean("done");

                tasks.add(String.format(
                        "id: %d. [%s] title: %s, category: %s, priority %d, add date: %s, description: %s",
                        id, done ? "X" : " ", title, category, priority, date, description
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

}
