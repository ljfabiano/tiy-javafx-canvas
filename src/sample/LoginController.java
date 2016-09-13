package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.net.URL;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Created by jfabiano on 9/11/2016.
 */
public class LoginController implements Initializable {

    @FXML
    TextField usernameText;

    SampleFXMLApp myFXMLApp;

    String fullName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //System.out.print("Please enter your name: ");
        //Scanner inputScanner = new Scanner(System.in);
        //username = inputScanner.nextLine();

//        if (username != null && !username.isEmpty()) {
//            fileName = username + ".json";
//        }
        //Add a prompt to the app asking the user to enter their username (email address) to retrieve their ToDos DONE
        //On that prompt, also give the user the option to create a new user: DONE
        //If they choose to create a new user, ask them for user information (username, which is their email address, and full name) DONE
        //Use that information to create a new user and start the app with that user
        //Retrieve the ToDos just for that user and display them in the app
        //Make sure that when a ToDo is toggled and when a ToDo is created, it's for the user who "signed in"
        //System.out.println("Checking existing list ...");
        //ToDoItemList retrievedList = retrieveList();
//        myDB = new ToDoDatabase();
//        try {
//            myDB.init();
//            conn = DriverManager.getConnection(myDB.DB_URL);
//            savableList = myDB.selectToDos(conn);
//        }catch(Exception e)
//        {
//
//        }
//
//        for (ToDoItem item : savableList) {
//            todoItems.add(item);
//        }
//
//
//        todoList.setItems(todoItems);
    }
    public void login()
    {
        myFXMLApp = new SampleFXMLApp();
        Stage primaryStage = new Stage();
        try {
            System.out.println(usernameText.getText());
            myFXMLApp.startMainStage(primaryStage, usernameText.getText(), fullName);
        }
        catch(Exception e)
        {

        }
    }
    public void goToNewUserScreen()
    {
        myFXMLApp = new SampleFXMLApp();//either pass in a reference to this object when calling the startCreateNewUserStage method, or find a way for this login controller to be in scope for the new samplefxml app
        Stage primaryStage = new Stage();
        try {
            myFXMLApp.startCreateNewUserStage(primaryStage);
        }
        catch(Exception e)
        {

        }
    }
}
