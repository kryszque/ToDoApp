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

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT id, title, category, date, description, priority, done FROM tasks";

        try (Connection connection = Database.setConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = new Task();
                task.setTitle(rs.getString("title"));
                task.setCategory(rs.getString("category"));
                task.setDate(rs.getString("date"));
                task.setDescription(rs.getString("description"));
                task.setPriority(rs.getInt("priority"));
                task.setDone(rs.getBoolean("done"));

                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

}