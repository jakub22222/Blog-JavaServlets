package database;

import java.sql.*;

/**
 * @author Jakub Hoś
 * @version 1.5 
 */

public class ExecuteQueryApp {
    
    public void ExecuteQuery(String query) {
        
        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            System.out.println("Query executed");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        ExecuteQueryApp app = new ExecuteQueryApp();
        for(String query : args)
        app.ExecuteQuery(query);
    }
}
