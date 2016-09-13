package sample;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jfabiano on 9/8/2016.
 */
public class ToDoDatabase {
    public final static String DB_URL = "jdbc:h2:./main";

    public void init() throws SQLException {
        // we'll add some implementation code here once we have a unit test method for it
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS todos (id IDENTITY, text VARCHAR, is_done BOOLEAN, user_id INT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, username VARCHAR, fullname VARCHAR)");

        //stmt.execute("CREATE TABLE IF NOT EXISTS todos (id IDENTITY, text VARCHAR, is_done BOOLEAN)");
    }
    public void insertToDo(Connection conn, String text, int userID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO todos VALUES (NULL, ?, false, ?)");
        stmt.setString(1, text);
        stmt.setInt(2, userID);
        stmt.execute();
    }
    public void deleteToDo(Connection conn, String text) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM todos WHERE text = ?");
        stmt.setString(1, text);
        stmt.execute();
    }

    public static ArrayList<ToDoItem> selectToDos(Connection conn) throws SQLException {
        ArrayList<ToDoItem> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM todos");
        while (results.next()) {
            int id = results.getInt("id");
            String text = results.getString("text");
            boolean isDone = results.getBoolean("is_done");
            items.add(new ToDoItem(id, text, isDone));
        }
        return items;
    }

    public ArrayList<ToDoItem> selectToDosForUser(Connection conn, int userID) throws SQLException {
        ArrayList<ToDoItem> items = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos " +
                "INNER JOIN users ON todos.user_id = users.id " +
                "WHERE users.id = ?");
        stmt.setInt(1, userID);
        ResultSet results = stmt.executeQuery();

        while (results.next()) {
            int id = results.getInt("id");
            String text = results.getString("text");
            boolean isDone = results.getBoolean("is_done");
            items.add(new ToDoItem(id, text, isDone));
        }
        return items;
    }

    public static void toggleToDo(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE todos SET is_done = NOT is_done WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }
    public int insertUser(Connection conn, String username, String fullname) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, fullname);
        stmt.execute();

        stmt = conn.prepareStatement("SELECT * FROM users where username = ?");
        stmt.setString(1, username);
        ResultSet results = stmt.executeQuery();
        results.next();
        return results.getInt("id");
    }
    public void deleteUser(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM users where username = ?");
        stmt.setString(1, username);
        stmt.execute();
    }
    public User selectUser(Connection conn, String username)throws SQLException
{
    //retrieve a user from the database based on the username and return the corresponding user object DONE
    //The selectUser() method should take the username (email address) as the parameter to use to find the user DONE
    //if the user does not exist, the selectUser() method should return null DONE
    User user;
    int id = 0;
    String username1 = "";
    String fullname = "";
    PreparedStatement stmt = conn.prepareStatement("SELECT FROM users where username = ?");
    stmt.setString(1, username);
    //stmt.execute();
    ResultSet results = stmt.executeQuery();
    if (results.next()) {
        id = results.getInt("id");
        username1 = results.getString("username");
        fullname = results.getString("fullname");
        user = new User(id, username1, fullname);
        return user;
    }
    else
    {
        return null;
    }
//    stmt = conn.prepareStatement("SELECT * FROM users where username = ?");
//    stmt.setString(1, username);
//    ResultSet results = stmt.executeQuery();
//    results.next();
//    return results.getInt("id");

}
}
