package com.ou.oubusmanager;

import com.ou.services.BusService;
import com.ou.services.SeatService;
import com.ou.services.TicketService;
import com.ou.services.TripService;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
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
        systemAuto();
        
        launch(args);
    }
    
    public static void systemAuto () throws ParseException {
        int milisInASecond = 1000;
        long time = System.currentTimeMillis();

        Runnable update = new Runnable() {
            public void run() {
                try {
                    // Chuyen di da hoan thanh
                    TripService.setCompleteTrip();
                    // Chuyen ve trong(FREE) ve trang thai thu hoi(WITHDRAW)
                    TicketService.setTicketFreeBefore5min();
                    // Chuyen ve khach hang da dat ma khong lay truoc 30p xe chay
                    TicketService.setTicketBookedBefore30min();
                    // Tu tao ghe
                    SeatService.createSeatOfBus();
                    // Tu tao ve cho moi chuyen di moi duoc them
                    TicketService.createNewTicket();
                } catch (SQLException ex) {
                    Logger.getLogger(BookTicketController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                update.run();
            }
        }, time % milisInASecond, milisInASecond);

        // This will update for the current minute, it will be updated again in at most one minute.
        update.run();
    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }

}