package com.ou.oubusmanager;

import com.ou.pojo.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CÔNG SANG
 */
public class BookTicketController implements Initializable {

    @FXML
    private Button btnBookTicket;

    @FXML
    private ImageView btnLogout;

    @FXML
    private TextField txtDateDeparture;

    @FXML
    private DatePicker dpSearchDate;

    @FXML
    private TextField dpTimeDeparture;

    @FXML
    private Label lblCustomerName;

    @FXML
    private TableView tvTrip;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtSearchFrom;

    @FXML
    private TextField txtSearchTo;

    @FXML
    private TextField txtTo;
    private Customer customer;

    @FXML
    void btnBookTicketClick(ActionEvent event) {

    }

    @FXML
    void btnLogoutClick(MouseEvent event) throws IOException {
        Stage stage = App.getPrimaryStage();
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("com/ou/oubusmanager/Enter.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        this.customer = null;
        stage.show();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.setNameUser(customer.getName());
    }
    
    public void setNameUser(String name) {
        this.lblCustomerName.setText(name);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
