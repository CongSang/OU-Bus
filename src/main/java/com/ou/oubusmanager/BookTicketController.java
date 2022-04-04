package com.ou.oubusmanager;

import com.ou.utils.DateTimeCalc;
import com.ou.pojo.Customer;
import com.ou.pojo.Employee;
import com.ou.pojo.Seat;
import com.ou.pojo.Trip;
import com.ou.services.BusService;
import com.ou.services.SeatService;
import com.ou.services.TicketService;
import com.ou.services.TripService;
import com.ou.services.UserService;
import com.ou.utils.MyAlert;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TextField txtTimeDeparture;

    @FXML
    private Label lblCustomerName;

    @FXML
    private TableView tvTrip;
    
    @FXML
    private TableColumn<Trip, String> dateColumn;
    
    @FXML
    private TableColumn<Trip, String> fromColumn;
    
    @FXML
    private TableColumn<Trip, String> timeColumn;

    @FXML
    private TableColumn<Trip, String> toColumn;
    
    @FXML
    private TableColumn<Trip, Integer> seatsColumn;
    
    @FXML
    private TableColumn<Trip, String> id_hiddenColumn;
    
    @FXML
    private TableColumn<Trip, String> busId_hiddenColumn;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtSearchFrom;

    @FXML
    private TextField txtSearchTo;
    
    @FXML
    private Button btnCancelSearch;

    @FXML
    private TextField txtTo;
    
    @FXML
    private TextField txtBusSeri;
    
    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPhone;
    
    @FXML
    private ComboBox<Seat> cbSeatEmpty;

    @FXML
    private Button btnSaleTicket;
    private Employee employee;
    public TicketManageController ticketManageController;

    @FXML
    void btnBookTicketClick(ActionEvent event) throws ParseException, SQLException {
        // 60p doi ra mili giay
        long milis60min = 60 * 60 * 1000;
        
        // Thoi gian hien tai
        Date currentTime = Date.from(Instant.now());
        Trip selected = (Trip) this.tvTrip.getSelectionModel().getSelectedItem();
        String date = selected.getDate();
        String time = selected.getTime();
        Date date1 = DateTimeCalc.formatToDate(date, time);
        
        if(selected != null) {
            String message = String.format("Đặt vé chuyến %s - %s\nNgày %s\nLúc %s"
                    , selected.getFrom(), selected.getTo()
                    , selected.getDate(), selected.getTime());
        
            Optional<ButtonType> confirm = MyAlert.showConfirmDialog(message);
            
            // Kiem tra thoi gian dat ve chi duoc thuc hien truoc khi xe chay 60p 
            if (DateTimeCalc.timeBetween(currentTime, date1) >= milis60min) {
                if(confirm.get() == ButtonType.OK) {
                    String name = txtFullName.getText();
                    String phone = txtPhone.getText();
                    Customer customer = (Customer) UserService.getCustomer(phone);
                    Seat seat = cbSeatEmpty.getSelectionModel().getSelectedItem();
                    // Kiem tra khong chon ghe
                    if (seat != null) {
                        if(customer != null) {
                            bookTicket(selected, seat, customer);
                        }
                        else {
                            UserService.addUser(name, phone, null, null, null, "CUSTOMER");
                            Customer customer1 = (Customer) UserService.getCustomer(phone);
                            bookTicket(selected, seat, customer1);
                        }
                    }
                    else
                        MyAlert.showErrorDialog("Vui lòng chọn ghế trước khi đặt.");
                }
                else
                    MyAlert.showErrorDialog("Hủy đặt vé"); 
            }
            else
                MyAlert.showErrorDialog("Sắp đến thời gian xe chạy không được đặt vé nữa.");
        }
        else
            MyAlert.showErrorDialog("Chọn chuyến đi trước khi đặt vé.");
    }
    
    public void bookTicket(Trip trip, Seat seat, Customer customer) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));   

        try {
            TicketService.createTicketBooked(trip.getId(), seat.getId()
                    , customer.getId(), this.employee.getId(), dtf.format(now));
            MyAlert.showSuccessDialog("Đặt vé thành công");
        } catch (SQLException ex) {
            Logger.getLogger(BookTicketController.class.getName()).log(Level.SEVERE, null, ex);
            MyAlert.showErrorDialog(ex.getMessage());
        }
    }
    
    @FXML
    void btnSaleTicketClick(ActionEvent event) {

    }

    @FXML
    void btnLogoutClick(MouseEvent event) throws IOException {
        Stage stage = App.getPrimaryStage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("com/ou/oubusmanager/TicketManage.fxml"));
        Parent root = fxmlLoader.load();
        ticketManageController = fxmlLoader.<TicketManageController>getController();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
        
        ticketManageController.setEmployee(this.employee);
        this.employee = null;
    }
    
    
    @FXML
    void btnCancelSearchClick(ActionEvent event) {
        txtSearchFrom.setText("");
        txtSearchTo.setText("");
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.setNameUser(this.employee.getName());
    }
    
    public void setNameUser(String name) {
        this.lblCustomerName.setText(name);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        id_hiddenColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        busId_hiddenColumn.setCellValueFactory(new PropertyValueFactory<>("busId"));
        
        try {
            this.loadData(null, null);
        } catch (ParseException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Tim kiem diem di
        txtSearchFrom.textProperty().addListener((event) -> {
            try {
                this.loadData(txtSearchFrom.getText(), txtSearchTo.getText());
            } catch (ParseException ex) {
                Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // Tim kiem diem den
        txtSearchTo.textProperty().addListener((event) -> {
            try {
                this.loadData(txtSearchFrom.getText(), txtSearchTo.getText());
            } catch (ParseException ex) {
                Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // Su kien click 1 dong trong bang
        tvTrip.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    selectRowTable();
                } catch (SQLException ex) {
                    Logger.getLogger(BookTicketController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    } 
    
    private void loadData(String from, String to) throws ParseException {       
        ObservableList<Trip> trips = FXCollections.observableArrayList();
        
        try {
            trips.addAll(TripService.getTripForCustomerSearch(from, to));
            
            tvTrip.setItems(trips);
        } catch (SQLException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void selectRowTable() throws SQLException {
        Trip selected = (Trip) this.tvTrip.getSelectionModel().getSelectedItem();
        txtFrom.setText(selected.getFrom());
        txtTo.setText(selected.getTo());
        txtDateDeparture.setText(selected.getDate());
        txtTimeDeparture.setText(selected.getTime());
        txtBusSeri.setText(BusService.getBusById(selected.getBusId()).getBusSerial());
        this.cbSeatEmpty.setItems(FXCollections.observableList(SeatService
                .getSeatEmpty(selected.getBusId(), selected.getId())));
    }
}
