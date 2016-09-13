package sample;/**
 * Created by Dominique on 4/21/2016.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Scanner;

public class SampleFXMLApp extends Application {

    String userName;
    String userFullName;

    public static void main(String[] args) {

        System.out.println("Welcome to the TIY TODO App");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("todolist.fxml"));

//        String fileName = getParameters().getRaw().get(0);

        //Add a prompt to the app asking the user to enter their username (email address) to retrieve their ToDos
        //On that prompt, also give the user the option to create a new user:
        //If they choose to create a new user, ask them for user information (username, which is their email address, and full name)
        //Use that information to create a new user and start the app with that user
        //Retrieve the ToDos just for that user and display them in the app
        //Make sure that when a ToDo is toggled and when a ToDo is created, it's for the user who "signed in"

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("loginscreen.fxml").openStream());
        LoginController controller = (LoginController) fxmlLoader.getController();

        primaryStage.setTitle("TIY ToDo App");
        primaryStage.setScene(new Scene(root, 800, 600));

//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we) {
//                System.out.println("Stage is closing -> saving the todo list! ");
//                //controller.saveList();
//            }
//        });

        //primaryStage.

        primaryStage.show();
    }

    public void startMainStage(Stage primaryStage, String userName, String userFullName) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("todolist.fxml"));
        this.userName = userName;
        this.userFullName = userFullName;
//        String fileName = getParameters().getRaw().get(0);

        //Add a prompt to the app asking the user to enter their username (email address) to retrieve their ToDos
        //On that prompt, also give the user the option to create a new user:
        //If they choose to create a new user, ask them for user information (username, which is their email address, and full name)
        //Use that information to create a new user and start the app with that user
        //Retrieve the ToDos just for that user and display them in the app
        //Make sure that when a ToDo is toggled and when a ToDo is created, it's for the user who "signed in"
        //Controller controller = new Controller(userName, userFullName);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("todolist.fxml").openStream());
        //Controller controller = new Controller(userName, userFullName);
        Controller controller = (Controller) fxmlLoader.getController();
        controller.username = userName;
        controller.userFullName = userFullName;
        primaryStage.setTitle("TIY ToDo App");
        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing -> saving the todo list! ");
                //controller.saveList();
            }
        });

        primaryStage.show();

    }
    public void startCreateNewUserStage(Stage primaryStage) throws Exception {//set values needed,
//        Parent root = FXMLLoader.load(getClass().getResource("todolist.fxml"));

//        String fileName = getParameters().getRaw().get(0);

        //Add a prompt to the app asking the user to enter their username (email address) to retrieve their ToDos
        //On that prompt, also give the user the option to create a new user:
        //If they choose to create a new user, ask them for user information (username, which is their email address, and full name)
        //Use that information to create a new user and start the app with that user
        //Retrieve the ToDos just for that user and display them in the app
        //Make sure that when a ToDo is toggled and when a ToDo is created, it's for the user who "signed in"

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("createnewuserscreen.fxml").openStream());
        CreateUserController controller = (CreateUserController) fxmlLoader.getController();
        //controller.newUsername. = userName;
        primaryStage.setTitle("TIY ToDo App");
        primaryStage.setScene(new Scene(root, 800, 600));

//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we) {
//                System.out.println("Stage is closing -> saving the todo list! ");
//                //controller.saveList();
//            }
//        });

        //primaryStage.

        primaryStage.show();
    }

}
