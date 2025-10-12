package kryszque.todoapp.model.db;

import kryszque.todoapp.model.tasks.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public void addTask(Task task){
        String query = "INSERT INTO tasks (title, category, due_to_date,add_date, description, priority) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = Database.setConnection();
            PreparedStatement pstmt = connection.prepareStatement(query)){

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getCategory());
            pstmt.setString(3, task.getDue_to_date());
            pstmt.setString(4, task.getAddDate());
            pstmt.setString(5, task.getDescription());
            pstmt.setInt(6, task.getPriority());

            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT id, title, category, due_to_date, add_date, description, priority, done FROM tasks";

        try (Connection connection = Database.setConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = new Task();
                task.setTitle(rs.getString("title"));
                task.setCategory(rs.getString("category"));
                task.setDue_to_date(rs.getString("due_to_date"));
                task.setAdd_date(rs.getString("add_date"));
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