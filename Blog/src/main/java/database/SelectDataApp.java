package database;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pl.polsl.lab.model.Document;
import pl.polsl.lab.model.User;

/**
 * @author Jakub Hoś
 * @version 1.5 
 */
public class SelectDataApp {
    
    public ArrayList<Document> getDatabaseDocuments() {

        ArrayList<Document> result = new ArrayList();
        
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Documents");
            while (rs.next()) {
                int id = rs.getInt("AuthorID");
                String content = rs.getString("Content");
                LocalDateTime time = rs.getTimestamp("Added").toLocalDateTime();
                
                result.add(new Document(content, id, time));
            }
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return result;
    }
    
    public ArrayList<User> getDatabaseUsers() {

        ArrayList<User> result = new ArrayList();
        
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                int id = rs.getInt("UserID");
                String login = rs.getString("Login");
                String password = rs.getString("Password");
                
                result.add(new User(login,password,id));
            }
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return result;
    }

}
