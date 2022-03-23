package com.ou.oubusmanager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import animatefx.animation.FadeInRight;
import animatefx.animation.FadeInLeft;
import com.ou.pojo.Admin;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author CÔNG SANG
 */
public class EnterController implements Initializable {

    @FXML
    private Pane pnSignIn;

    @FXML
    private Pane pnSignUp;
    
    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnChangeSignUp;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtFullName;

    @FXML
    private PasswordField txtPasswordSignIn;

    @FXML
    private PasswordField txtPasswordSignUp;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUsernameSignIn;

    @FXML
    private TextField txtUsernameSignUp;

    @FXML
    void btnBackClick(MouseEvent event) {
        pnSignIn.toFront();
        new FadeInLeft(pnSignIn).play();
        
        txtFullName.setText("");
        txtPhone.setText("");
        txtAge.setText("");
        txtUsernameSignUp.setText("");
        txtPasswordSignUp.setText("");
    }

    @FXML
    void btnChangeSignUpClick(ActionEvent event) {
        pnSignUp.toFront();
        new FadeInRight(pnSignUp).play();
        
        txtUsernameSignIn.setText("");
        txtPasswordSignIn.setText("");
    }

    @FXML
    void btnCreateAccountClick(ActionEvent event) {
        
        
    }

    @FXML
    void btnLoginClick(ActionEvent event) {

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
