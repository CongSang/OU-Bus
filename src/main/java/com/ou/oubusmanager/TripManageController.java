package com.ou.oubusmanager;

import com.ou.pojo.Admin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author CÔNG SANG
 */
public class TripManageController implements Initializable {
    
    private Admin admin;

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
