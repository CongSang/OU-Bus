package com.ou.oubusmanager;

import com.ou.pojo.Customer;
import com.ou.pojo.Seat;
import com.ou.pojo.Trip;
import com.ou.services.BusService;
import com.ou.services.SeatService;
import com.ou.services.TicketService;
import com.ou.services.TripService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private ComboBox<Seat> cbSeatEmpty;
    private Customer customer;

    @FXML
    void btnBookTicketClick(ActionEvent event) {
        
        
        Trip selected = (Trip) this.tvTrip.getSelectionModel().getSelectedItem();
        
        if(selected != null) {
            String message = String.format("Đặt vé chuyến %s - %s\nNgày %s\nLúc %s"
                    , selected.getFrom(), selected.getTo()
                    , selected.getDate(), selected.getTime());
        
            Optional<ButtonType> confirm = EnterController.showConfirmDialog(message);
            if(confirm.get() == ButtonType.OK) {
            
                Seat seat = cbSeatEmpty.getSelectionModel().getSelectedItem();
                if (seat != null) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    System.out.println(dtf.format(now));   

                    try {
                        TicketService.createTicketBooked(selected.getId(), seat.getId(), this.customer.getId(), dtf.format(now));
                        EnterController.showSuccessDialog("Đặt vé thành công");
                    } catch (SQLException ex) {
                        Logger.getLogger(BookTicketController.class.getName()).log(Level.SEVERE, null, ex);
                        EnterController.showErrorDialog("Đặt vé thất bại");
                    }
                }
                else
                    EnterController.showErrorDialog("Vui lòng chọn ghế trước khi đặt.");
            }
            else
                EnterController.showErrorDialog("Hủy đặt vé"); 
        }
        else
            EnterController.showErrorDialog("Chọn chuyến đi trước khi đặt vé.");
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
    
    
    @FXML
    void btnCancelSearchClick(ActionEvent event) {
        txtSearchFrom.setText("");
        txtSearchTo.setText("");
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.setNameUser(this.customer.getName());
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
