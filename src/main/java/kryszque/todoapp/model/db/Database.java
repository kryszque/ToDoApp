package kryszque.todoapp.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:sqlite:tasks:db";

    @org.jetbrains.annotations.Nullable
    public static Connection setConnection(){
        try{
            return DriverManager.getConnection(URL);
        } catch (SQLException e){
            System.out.println("Database connection failed");
            e.printStackTrace();
            return null;
        }
    }
}
