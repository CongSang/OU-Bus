package com.ou.oubusmanager;

import com.ou.services.TicketService;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            // Tao ve cho ghe chua co ve
            TicketService.createNewTicket();
        } catch (SQLException ex) {
            Logger.getLogger(BookTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }

}