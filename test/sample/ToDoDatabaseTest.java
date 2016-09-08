package sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by jfabiano on 9/8/2016.
 */
public class ToDoDatabaseTest {
    ToDoDatabase todoDatabase;

    @Before
    public void setUp() throws Exception {
        todoDatabase = new ToDoDatabase();
        todoDatabase.init();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInit() throws Exception {
        // test to make sure we can access the new database
        Connection conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
        PreparedStatement todoQuery = conn.prepareStatement("SELECT * FROM todos");
        ResultSet results = todoQuery.executeQuery();
        assertNotNull(results);
    }
    @Test
    public void testInsertToDo() throws Exception {
        Connection conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
        String todoText = "UnitTest-ToDo";

        todoDatabase.insertToDo(conn, todoText);

        // make sure we can retrieve the todo we just created
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos WHERE text = ?");
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
        Connection conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
        String firstToDoText = "UnitTest-ToDo1";
        String secondToDoText = "UnitTest-ToDo2";

        ArrayList<ToDoItem> todos = todoDatabase.selectToDos(conn);
        int todosBefore = todos.size();

        todoDatabase.insertToDo(conn, firstToDoText);
        todoDatabase.insertToDo(conn, secondToDoText);

        todos = todoDatabase.selectToDos((conn));
        System.out.println("Found " + todos.size() + " todos in the database");

        assertTrue("There should be at least 2 todos in the database (there are " +
                todos.size() + ")", todos.size() >= todosBefore + 2);

        todoDatabase.deleteToDo(conn, firstToDoText);
        todoDatabase.deleteToDo(conn, secondToDoText);
    }

    @Test
    public void testToggleToDo() throws Exception {

        Connection conn = DriverManager.getConnection(ToDoDatabase.DB_URL);
        int id = 0;
        String text = "";
        Boolean isDone;

        String firstToDoText = "UnitTest-ToggleToDo1";
        todoDatabase.insertToDo(conn, firstToDoText);
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
}