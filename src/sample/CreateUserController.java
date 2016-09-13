package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jfabiano on 9/11/2016.
 */
public class CreateUserController implements Initializable{

    @FXML
    TextField newUsername;

    @FXML
    TextField newUserFullName;

    SampleFXMLApp myFXMLApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //System.out.print("Please enter your name: ");
        //Scanner inputScanner = new Scanner(System.in);
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
    public void createNewUserAndLogin()
    {
        //User myUser = new User(newUsername.getText(), newUserFullName.getText());
        myFXMLApp = new SampleFXMLApp();
        Stage primaryStage = new Stage();
        try {
            System.out.println(newUsername.getText());
            System.out.println(newUserFullName.getText());
            myFXMLApp.startMainStage(primaryStage, newUsername.getText(), newUserFullName.getText());
        }
        catch(Exception e)
        {

        }
    }
}
