package kryszque.todoapp.model.db;

import kryszque.todoapp.model.tasks.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        String query = "SELECT id, title, category, due_to_date, add_date, description, priority, done FROM tasks";

        try (Connection connection = Database.setConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Task task = new Task();
                task = setParameters(task, rs);
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public Task getTask(int id) {
        Task task = null;
        String query = "SELECT * FROM tasks WHERE id = ?";
        try(Connection connection = Database.setConnection();
            PreparedStatement pstmt = connection.prepareStatement(query)){

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                task = new Task();
                task = setParameters(task, rs);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return task;
    }

    public void editTask(Task updatedTask){
        Task oldTask = getTask(updatedTask.getId());

        if (oldTask == null) {
            System.err.println("Not possible to update. Task id= " + updatedTask.getId() + " doesn't exist.");
            return;
        }

        StringBuilder query = new StringBuilder("UPDATE tasks SET ");
        List<Object> params = new ArrayList<>();

        if (!Objects.equals(updatedTask.getTitle(), oldTask.getTitle())) {
            query.append("title = ?, ");
            params.add(updatedTask.getTitle());
        }
        if (!Objects.equals(updatedTask.getCategory(), oldTask.getCategory())) {
            query.append("category = ?, ");
            params.add(updatedTask.getCategory());
        }
        if (!Objects.equals(updatedTask.getDue_to_date(), oldTask.getDue_to_date())) {
            query.append("due_to_date = ?, ");
            params.add(updatedTask.getDue_to_date());
        }
        if(!Objects.equals(updatedTask.getAddDate(), oldTask.getAddDate())){
            query.append("add_date = ?, ");
            params.add(updatedTask.getAddDate());
        }
        if(!Objects.equals(updatedTask.getDescription(), oldTask.getDescription())){
            query.append("description = ?, ");
            params.add(updatedTask.getDescription());
        }
        if(!Objects.equals(updatedTask.getPriority(), oldTask.getPriority())){
            query.append("priority = ?, ");
            params.add(updatedTask.getPriority());
        }
        if (updatedTask.isDone() != oldTask.isDone()) {
            query.append("done = ?, ");
            params.add(updatedTask.isDone());
        }

        if (params.isEmpty()) {
            throw new IllegalArgumentException("No new fields were given.");
        }

        query.setLength(query.length() - 2);

        query.append(" WHERE id = ?");
        params.add(updatedTask.getId());

        try (Connection connection = Database.setConnection();
             PreparedStatement pstmt = connection.prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            pstmt.executeUpdate();
            System.out.println("Task id="+updatedTask.getId()+" successfully updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {
        String query = "DELETE FROM tasks WHERE id = ?";
        try(Connection connection = Database.setConnection();
            PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Task setParameters(Task task, ResultSet rs) throws SQLException {
            task.setTitle(rs.getString("title"));
            task.setCategory(rs.getString("category"));
            task.setDue_to_date(rs.getString("due_to_date"));
            task.setAdd_date(rs.getString("add_date"));
            task.setDescription(rs.getString("description"));
            task.setPriority(rs.getInt("priority"));
            task.setDone(rs.getBoolean("done"));
            return task;
    }

}