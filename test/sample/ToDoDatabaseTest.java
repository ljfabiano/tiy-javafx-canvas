package sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static sample.ToDoDatabase.DB_URL;

/**
 * Created by jfabiano on 9/8/2016.
 */
public class ToDoDatabaseTest {
    static ToDoDatabase todoDatabase;

    @Before
    public void setUp() throws Exception {
        if (todoDatabase == null) {
            todoDatabase = new ToDoDatabase();
            todoDatabase.init();
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInit() throws Exception {
        // test to make sure we can access the new database
        Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement todoQuery = conn.prepareStatement("SELECT * FROM todos");
        ResultSet results = todoQuery.executeQuery();
        assertNotNull(results);
    }
    @Test
    public void testInsertToDo() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        String todoText = "UnitTest-ToDo";

        // adding a call to insertUser, so we have a user to add todos for
        String username = "unittester@tiy.com";
        String fullName = "Unit Tester";
        int userID = todoDatabase.insertUser(conn, username, fullName);

        todoDatabase.insertToDo(conn, todoText, userID);

        // make sure we can retrieve the todo we just created
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos where text = ?");
        stmt.setString(1, todoText);
        ResultSet results = stmt.executeQuery();
        assertNotNull(results);
        // count the records in results to make sure we get what we expected
        int numResults = 0;
        while (results.next()) {
            numResults++;
        }

        assertEquals(1, numResults);

        todoDatabase.deleteToDo(conn, todoText);
        // make sure we remove the test user we added earlier
        todoDatabase.deleteUser(conn, username);

        // make sure there are no more records for our test todo
        results = stmt.executeQuery();
        numResults = 0;
        while (results.next()) {
            numResults++;
        }
        assertEquals(0, numResults);
    }


    @Test
    public void testSelectAllToDos() throws Exception {
        Connection conn = DriverManager.getConnection(DB_URL);
        String firstToDoText = "UnitTest-ToDo1";
        String secondToDoText = "UnitTest-ToDo2";

        ArrayList<ToDoItem> todos = todoDatabase.selectToDos(conn);
        int todosBefore = todos.size();

        // adding a call to insertUser, so we have a user to add todos for
        String username = "unittester@tiy.com";
        String fullName = "Unit Tester";
        int userID = todoDatabase.insertUser(conn, username, fullName);

        todoDatabase.insertToDo(conn, firstToDoText, userID);
        todoDatabase.insertToDo(conn, secondToDoText, userID);

        todos = todoDatabase.selectToDos((conn));
        System.out.println("Found " + todos.size() + " todos in the database");

        assertTrue("There should be at least 2 todos in the database (there are " +
                todos.size() + ")", todos.size() >= todosBefore + 2);

        todoDatabase.deleteToDo(conn, firstToDoText);
        todoDatabase.deleteToDo(conn, secondToDoText);
        todoDatabase.deleteUser(conn, username);
    }

    @Test
    public void testToggleToDo() throws Exception {

        Connection conn = DriverManager.getConnection(DB_URL);
        int id = 0;
        String text = "";
        Boolean isDone;

        String firstToDoText = "UnitTest-ToggleToDo1";
        //todoDatabase.insertToDo(conn, firstToDoText);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos WHERE text = ?");
        stmt.setString(1, firstToDoText);
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            id = results.getInt("id");
            text = results.getString("text");
            isDone = results.getBoolean("is_Done");
            assertFalse(isDone);
            System.out.println(id + " - " + text + " - " + isDone);
        }
        ToDoDatabase.toggleToDo(conn, id);
        results = stmt.executeQuery();
        while (results.next()) {
            id = results.getInt("id");
            text = results.getString("text");
            isDone = results.getBoolean("is_Done");
            assertTrue(isDone);
            System.out.println(id + " - " + text + " - " + isDone);
        }
        ToDoDatabase.toggleToDo(conn, id);
        results = stmt.executeQuery();
        while (results.next()) {
            id = results.getInt("id");
            text = results.getString("text");
            isDone = results.getBoolean("is_Done");
            assertFalse(isDone);
            System.out.println(id + " - " + text + " - " + isDone);
        }
        todoDatabase.deleteToDo(conn, text);
//        PreparedStatement stmtTwo = conn.prepareStatement("UPDATE todos SET is_done = NOT is_done WHERE id = ?");
//        stmtTwo.setInt(1, id);
//        stmtTwo.execute();

    }
//    public int testInsertUser(Connection conn, String username, String fullname) throws SQLException {
//        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?)");
//        stmt.setString(1, username);
//        stmt.setString(2, fullname);
//        stmt.execute();
//
//        stmt = conn.prepareStatement("SELECT * FROM users where username = ?");
//        stmt.setString(1, username);
//        ResultSet results = stmt.executeQuery();
//        results.next();
//        return results.getInt("id");
//    }
@Test
public void testInsertUser() throws Exception {
    Connection conn = DriverManager.getConnection(DB_URL);
    String username = "UnitTest-Insert-User";
    String fullName = "Joe Fabiano";
    //todoDatabase.insertUser(conn, username, fullName);

    // make sure we can retrieve the todo we just created
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND fullname = ?");
    stmt.setString(1, username);
    stmt.setString(2, fullName);
    ResultSet results = stmt.executeQuery();
    assertNotNull(results);
    // count the records in results to make sure we get what we expected
    int numResults = 0;
    while (results.next()) {
        numResults++;
    }

    //assertEquals(1, numResults);
    todoDatabase.insertUser(conn, username, fullName);

    stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND fullname = ?");
    stmt.setString(1, username);
    stmt.setString(2, fullName);
    results = stmt.executeQuery();
    assertNotNull(results);
    // count the records in results to make sure we get what we expected
    int numResultsTwo = 0;
    while (results.next()) {
        numResultsTwo++;
    }

    assertEquals(1, numResultsTwo - numResults);

    todoDatabase.deleteUser(conn, username);

    // make sure there are no more records for our test todo
    results = stmt.executeQuery();
    numResults = 0;
    while (results.next()) {
        numResults++;
    }
    assertEquals(0, numResults);
}
    @Test
    public void testInsertToDoForUser() throws Exception {
        Connection conn = DriverManager.getConnection(DB_URL);
        String todoText = "UnitTest-ToDo";
        String todoText2 = "UnitTest-ToDo2";

        // adding a call to insertUser, so we have a user to add todos for
        String username = "unittester@tiy.com";
        String fullName = "Unit Tester";
        int userID = todoDatabase.insertUser(conn, username, fullName);

        String username2 = "unitester2@tiy.com";
        String fullName2 = "Unit Tester 2";
        int userID2 = todoDatabase.insertUser(conn, username2, fullName2);

        todoDatabase.insertToDo(conn, todoText, userID);
        todoDatabase.insertToDo(conn, todoText2, userID2);

        // make sure each user only has one todo item
        ArrayList<ToDoItem> todosUser1 = todoDatabase.selectToDosForUser(conn, userID);
        ArrayList<ToDoItem> todosUser2 = todoDatabase.selectToDosForUser(conn, userID2);

        assertEquals(1, todosUser1.size());
        assertEquals(1, todosUser2.size());

        // make sure each todo item matches
        ToDoItem todoUser1 = todosUser1.get(0);
        assertEquals(todoText, todoUser1.text);
        ToDoItem todoUser2 = todosUser2.get(0);
        assertEquals(todoText2, todoUser2.text);

        todoDatabase.deleteToDo(conn, todoText);
        todoDatabase.deleteToDo(conn, todoText2);
        // make sure we remove the test user we added earlier
        todoDatabase.deleteUser(conn, username);
        todoDatabase.deleteUser(conn, username2);

    }

}