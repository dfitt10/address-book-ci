package com.example.addressbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    // HelloApplication class extends Application class, which is the main entrypoint for JavaFX applications
    public static final String TITLE = "Address Book";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    @Override
    public void start(Stage stage) throws IOException {
        // start method is called when the application is launched, sets up the main window for the application,
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // FXMLLoader loads FXML files, hello-view.fxml and sets contents of window.
        // FXML files describe the layout of the GUI
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        // scene is the content of the window, set to FXML files, size of window as 320x240 pixels
        stage.setTitle(TITLE);
        // stage is the main window of the application, setTitle to set title of window
        stage.setScene(scene);
        // method to set the content of the window
        stage.show();
        //stage is shown using the show metho
    }

    public static void main(String[] args){
        launch();
    }
}
