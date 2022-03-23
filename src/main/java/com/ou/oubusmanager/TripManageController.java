package com.ou.oubusmanager;

import com.ou.pojo.Admin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author CÔNG SANG
 */
public class TripManageController implements Initializable {
    
    @FXML
    private Button btnBookTicket;

    @FXML
    private ImageView btnLogout;

    @FXML
    private DatePicker dpDateDeparture;

    @FXML
    private DatePicker dpSearchDate;

    @FXML
    private TextField dpTimeDeparture;

    @FXML
    private Label lblCustomerName;

    @FXML
    private ListView<?> lvTrip;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtSearchFrom;

    @FXML
    private TextField txtSearchTo;

    @FXML
    private TextField txtTo;
    private Admin admin;

    @FXML
    void btnBookTicketClick(ActionEvent event) {

    }

    @FXML
    void btnLogoutClick(MouseEvent event) {

    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
