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
import com.ou.pojo.Account;
import com.ou.pojo.Admin;
import com.ou.pojo.Customer;
import com.ou.pojo.Employee;
import com.ou.services.UserService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    
    public void checkLogin() throws SQLException, IOException {
        String username = txtUsernameSignIn.getText();
        String password = txtPasswordSignIn.getText();
        
        if (username.equals("")) {
            this.showErrorDialog("Vui lòng nhập Tài khoản.");
        }
        else if (password.equals("")) {
            this.showErrorDialog("Vui lòng nhập Mật khẩu.");
        }
        else {
            Account a = UserService.getUserLogin(username, password);
            if(a instanceof Admin) {
                changeAdminScene();
                tripmanage.setAdmin((Admin) a);
            }
            else if (a instanceof Customer) {
                changeCustomerScene();
                bookticket.setCustomer((Customer) a);
            }
            else if (a instanceof Employee) {
                changeEmployeeScene();
            }
            else
                showErrorDialog("Sai Tài khoản hoặc Mật khẩu.");
        }
    }
    
    public void checkSignUp () {
        String username = txtUsernameSignUp.getText();
        String password = txtPasswordSignUp.getText();
        String fullName = txtFullName.getText();
        String phone = txtPhone.getText();
        String age = txtAge.getText();
        String role = "CUSTOMER";
        
        if(fullName.equals("") || phone.equals("") || age.equals("") 
                || username.equals("") || password.equals("")) {
            this.showErrorDialog("Vui lòng điền hết các thông tin.");
        }
        else {
            try {
                UserService.addUserSignUp(fullName, phone, age, username, password, role);
                this.showSuccessDialog("Tạo tài khoản thành công");
                reset();
                
                pnSignIn.toFront();
                new FadeInLeft(pnSignIn).play();
            } catch (SQLException ex) {
                Logger.getLogger(EnterController.class.getName()).log(Level.SEVERE, null, ex);
                this.showErrorDialog("Tạo tài khoản thất bại.\nTên tài khoản của bạn có thể bị trùng.");
            }
        }
    }
    
    public void changeCustomerScene() throws IOException {
        Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("com/ou/oubusmanager/BookTicket.fxml"));
        Parent root = fxmlLoader.load();
        bookticket = fxmlLoader.<BookTicketController>getController();
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
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
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    } 
    
    public void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(message);
        alert.setContentText("LỖI");
        alert.showAndWait();
    }
    
    public void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông tin");
        alert.setHeaderText(message);
        alert.setContentText("THÀNH CÔNG");
        alert.showAndWait();
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
