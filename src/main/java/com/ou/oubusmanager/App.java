package com.ou.oubusmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage primaryStageObj;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStageObj = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("com/ou/oubusmanager/Enter.fxml"));
        primaryStage.setTitle("OU Bus");
        primaryStage.getIcons()
                .add(new Image(getClass().getClassLoader().getResource("com/images/logo.png").toString()));

        Scene scene = new Scene(root);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }

}