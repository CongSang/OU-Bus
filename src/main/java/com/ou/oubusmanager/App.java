package com.ou.oubusmanager;

import com.ou.services.SeatService;
import com.ou.services.TicketService;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
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

    public static void main(String[] args) throws ParseException {
        Date d1 = Date.from(Instant.now());
        String date2 = "26-03-2022";
        String time2 = "23:00";

        String format = "dd-MM-yyyy hh:mm";

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        sdf.format(d1);
        Date d2 = DateTimeCalc.formatDateAndTime(date2, time2);
        
        DateTimeCalc.timeBetween(d1,d2);
        
        
        //Tu dong tao seats va ticket's seats neu chua co
        try {
            // Tao ghe
            SeatService.createSeatOfBus();
            
            // Tao ve
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