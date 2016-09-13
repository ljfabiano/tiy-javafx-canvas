package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    @FXML
    ListView todoList;

    @FXML
    TextField todoText;

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();
    ArrayList<ToDoItem> savableList = new ArrayList<ToDoItem>();
    String fileName = "todos.json";
    ToDoDatabase myDB;
    Connection conn;

    public String userFullName;
    public String username;
    public User myUser;

    public Controller()
    {

    }
    public Controller(String username, String userFullName)
    {
        this.username = username;
        this.userFullName = userFullName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //System.out.print("Please enter your name: ");
        Scanner inputScanner = new Scanner(System.in);
        //username = inputScanner.nextLine();

//        if (username != null && !username.isEmpty()) {
//            fileName = username + ".json";
//        }
        //Add a prompt to the app asking the user to enter their username (email address) to retrieve their ToDos
        //On that prompt, also give the user the option to create a new user:
        //If they choose to create a new user, ask them for user information (username, which is their email address, and full name)
        //Use that information to create a new user and start the app with that user
        //Retrieve the ToDos just for that user and display them in the app
        //Make sure that when a ToDo is toggled and when a ToDo is created, it's for the user who "signed in"
        System.out.println("Checking existing list ...");
        //ToDoItemList retrievedList = retrieveList();
        myDB = new ToDoDatabase();
        try {
            myDB.init();
            conn = DriverManager.getConnection(myDB.DB_URL);
            //if (userFullName != null)
            //{
                System.out.println("username = " + username);
                System.out.println("userfullname = " + userFullName);
                myUser = new User(username, userFullName);
                myDB.insertUser(conn, username, userFullName);
                //myUser.id = myDB.
            //}
//            else {
//                myUser = myDB.selectUser(conn, username);
//                System.out.println("username = " + username);
//                System.out.println("userfullname = " + userFullName);
//
//            }
            savableList = myDB.selectToDosForUser(conn, myUser.id);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

            for (ToDoItem item : savableList) {
                todoItems.add(item);
            }


        todoList.setItems(todoItems);
    }

    public void saveToDoList() {
        if (todoItems != null && todoItems.size() > 0) {
            System.out.println("Saving " + todoItems.size() + " items in the list");
            savableList = new ArrayList<ToDoItem>(todoItems);
            System.out.println("There are " + savableList.size() + " items in my savable list");
            saveList();
        } else {
            System.out.println("No items in the ToDo List");
        }
    }

    public void addItem() {
        System.out.println("Adding item ...");
        try {
            //myDB.insertToDo(conn, todoText.getText());
        }
        catch(Exception e)
        {

        }
        todoItems.add(new ToDoItem(todoText.getText()));
        todoText.setText("");
    }

    public void removeItem() {
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        System.out.println("Removing " + todoItem.text + " ...");
        todoItems.remove(todoItem);
    }

    public void toggleItem() {
        System.out.println("Toggling item ...");
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        try {
            myDB.toggleToDo(conn, todoItem.id);
        }
        catch(Exception e)
        {

        }
        if (todoItem != null) {
            todoItem.isDone = !todoItem.isDone;
            todoList.setItems(null);
            todoList.setItems(todoItems);
        }
    }

    public void saveList() {
        try {

            // write JSON
            JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
            String jsonString = jsonSerializer.serialize(new ToDoItemList(todoItems));

            System.out.println("JSON = ");
            System.out.println(jsonString);

            File sampleFile = new File(fileName);
            FileWriter jsonWriter = new FileWriter(sampleFile);
            jsonWriter.write(jsonString);
            jsonWriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public ToDoItemList retrieveList() {
        try {

            Scanner fileScanner = new Scanner(new File(fileName));
            fileScanner.useDelimiter("\\Z"); // read the input until the "end of the input" delimiter
            String fileContents = fileScanner.next();
            JsonParser ToDoItemParser = new JsonParser();

            ToDoItemList theListContainer = ToDoItemParser.parse(fileContents, ToDoItemList.class);
            System.out.println("==============================================");
            System.out.println("        Restored previous ToDoItem");
            System.out.println("==============================================");
            return theListContainer;
        } catch (IOException ioException) {
            // if we can't find the file or run into an issue restoring the object
            // from the file, just return null, so the caller knows to create an object from scratch
            return null;
        }
    }
    
}
