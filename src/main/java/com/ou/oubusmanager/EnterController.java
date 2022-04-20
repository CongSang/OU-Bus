package com.ou.oubusmanager;

import com.ou.utils.Security;
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
import com.ou.pojo.User;
import com.ou.pojo.Admin;
import com.ou.pojo.Employee;
import com.ou.services.UserService;
import com.ou.utils.MyAlert;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    public static BookTicketController bookticket;
    public static TripManageController tripmanage;
    public static TicketManageController ticketmanage;
    public static TicketFormController ticketform;
    
    @FXML
    void btnBackClick(MouseEvent event) {
        pnSignIn.toFront();
        new FadeInLeft(pnSignIn).play();
        
        reset();
    }

    @FXML
    void btnChangeSignUpClick(ActionEvent event) {
        pnSignUp.toFront();
        new FadeInRight(pnSignUp).play();
        
        txtUsernameSignIn.setText("");
        txtPasswordSignIn.setText("");
    }

    @FXML
    void btnCreateAccountClick(ActionEvent event) throws SQLException {
       checkSignUp();
    }

    @FXML
    void btnLoginClick(ActionEvent event) throws SQLException, IOException {
        checkLogin();
    }
    
    @FXML
    public void enterLogin(KeyEvent e) throws SQLException, IOException {
        if(e.getCode().equals(KeyCode.ENTER)) {
            checkLogin();
        }
    }
    
    public void checkLogin() throws SQLException, IOException {
        String username = txtUsernameSignIn.getText();
        String password = txtPasswordSignIn.getText();
        
        if (username.equals("")) {
            MyAlert.showErrorDialog("Vui lòng nhập Tài khoản.");
        }
        else if (password.equals("")) {
            MyAlert.showErrorDialog("Vui lòng nhập Mật khẩu.");
        }
        else {
            User a = UserService.getUserLogin(username, password);
            if(a instanceof Admin) {
                changeAdminScene();
                tripmanage.setAdmin((Admin) a);
            }
            else if (a instanceof Employee) {
                changeEmployeeScene();
                ticketmanage.setEmployee((Employee) a);
            }
            else
                MyAlert.showErrorDialog("Sai Tài khoản hoặc Mật khẩu.");
        }
    }
    
    public void checkSignUp () {
        String username = txtUsernameSignUp.getText();
        String password = txtPasswordSignUp.getText();
        String fullName = txtFullName.getText();
        String phone = txtPhone.getText();
        String age = txtAge.getText();
        String role = "EMPLOYEE";
        
        if(fullName.equals("") || phone.equals("") || age.equals("") 
                || username.equals("") || password.equals("")) {
            MyAlert.showErrorDialog("Vui lòng điền hết các thông tin.");
        }
        else {
            password = Security.encryptMD5(password);
            UserService.addUser(fullName, phone, age, username, password, role);
            MyAlert.showSuccessDialog("Tạo tài khoản thành công");
            reset();
            pnSignIn.toFront();
            new FadeInLeft(pnSignIn).play();
        }
    }
    
    public void changeAdminScene() throws IOException {
        Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("com/ou/oubusmanager/TripManage.fxml"));
        Parent root = fxmlLoader.load();
        tripmanage = fxmlLoader.<TripManageController>getController();
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    } 
    
    public void changeEmployeeScene() throws IOException {
        Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("com/ou/oubusmanager/TicketManage.fxml"));
        Parent root = fxmlLoader.load();
        ticketmanage = fxmlLoader.<TicketManageController>getController();
//        ticketform = fxmlLoader.<TicketFormController>getController();
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    } 
    
    public void reset() {
        txtFullName.setText("");
        txtPhone.setText("");
        txtAge.setText("");
        txtUsernameSignUp.setText("");
        txtPasswordSignUp.setText("");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
