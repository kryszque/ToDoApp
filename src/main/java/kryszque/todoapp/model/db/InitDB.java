package kryszque.todoapp.model.db;

import java.sql.Connection;
import java.sql.Statement;

public class InitDB {
    public static void init(){
        String query = "CREATE TABLE IF NOT EXISTS tasks ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"
                + "category TEXT NOT NULL,"
                + "due_to_date TEXT NOT NULL,"
                + "add_date TEXT NOT NULL,"
                + "description TEXT,"
                + "priority INTEGER DEFAULT 3,"
                + "done INTEGER DEFAULT 0);";

        try (Connection connection = Database.setConnection()) {
            assert connection != null;
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(query);
                System.out.println("Tasks table created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
