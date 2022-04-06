package com.ou.oubusmanager;

import com.ou.pojo.Employee;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CÔNG SANG
 */
public class TicketManageController implements Initializable {
    
    @FXML
    private Button btnTicket;
    
    @FXML
    private Button btnBookTicket;
    
    @FXML
    private AnchorPane ticketform;
    
    @FXML
    private Button btnExit;
    
    public static TicketFormController ticketFormController;
    public static BookTicketController bookticket;
    
    private Employee employee;
    
    public void setEmployee(Employee em) {
        this.employee = em;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    void btnTicket_click(ActionEvent event) throws IOException {
        Stage primaryStage = App.getPrimaryStage();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("com/ou/oubusmanager/TicketForm.fxml"));
        Parent root = fxmlLoader.load();
        ticketFormController = fxmlLoader.<TicketFormController>getController();
//        ticketmanage = fxmlLoader.<TicketManageController>getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
        
        ticketFormController.setEmployee(employee);
    }
    
    @FXML
    void btnBookTicket_click(ActionEvent event) throws IOException {
        Stage primaryStage = App.getPrimaryStage();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("com/ou/oubusmanager/BookTicket.fxml"));
        Parent root = fxmlLoader.load();
        bookticket = fxmlLoader.<BookTicketController>getController();
//        ticketmanage = fxmlLoader.<TicketManageController>getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
        
        bookticket.setEmployee(this.employee);
    }
    
    @FXML
    void btnExit_click(ActionEvent event) throws IOException {
        Stage stage = App.getPrimaryStage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("com/ou/oubusmanager/Enter.fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
        
        this.employee = null;
    }
}
