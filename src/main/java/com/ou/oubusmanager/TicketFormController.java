package com.ou.oubusmanager;

import com.ou.pojo.Bus;
import com.ou.pojo.Customer;
import com.ou.pojo.Employee;
import com.ou.pojo.Seat;
import com.ou.pojo.Ticket;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TicketFormController implements Initializable{

    @FXML
    private Button btnCancelSearch;

    @FXML
    private Button btnCancelTicket;

    @FXML
    private Button btnChangeTrip;

    @FXML
    private Button btnExport;

    @FXML
    private ImageView btnLogout;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<Seat> cbSeat;

    @FXML
    private TableColumn<Ticket, Integer> customerColumn;

    @FXML
    private TableColumn<Ticket, Integer> idColumn;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerPhone;

    @FXML
    private TableColumn<Ticket, Integer> seatColumn;

    @FXML
    private TableColumn<Ticket, Integer> tripColumn;

    @FXML
    private TableView<Ticket> tvTicket;

    @FXML
    private TextField txtBusSeri;

    @FXML
    private TextField txtDateDeparture;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtTimeDeparture;

    @FXML
    private TextField txtTo;
    
    private Employee employee;   
    private Ticket selected;
    public static TicketManageController ticketManageController;
    public static TicketExportController ticketExport;
    
    public void setEmployee(Employee em) {
        this.employee = em;
    }

    @FXML
    void btnCancelSearchClick(ActionEvent event) {
        txtSearch.setText("");
        btnCancelSearch.setVisible(false);
    }

    @FXML
    void btnCancelTicket_click(ActionEvent event) throws SQLException {
        if(TicketService.setTicketFree(selected.getId()) > 0) {
            MyAlert.showSuccessDialog("Hủy vé thành công");
            loadTickets(null);
            clearSelection();      
        }
        else
            MyAlert.showErrorDialog("Hủy vé thất bại");
        
    }

    @FXML
    void btnChangeTrip_click(ActionEvent event) {

    }

    @FXML
    void btnExport_click(ActionEvent event) throws SQLException, IOException {
        Ticket ticket = TicketService.getTicketByTripSeat(selected.getTripId()
                , selected.getSeatId());
        printTicket();
        ticketExport.setTicket(ticket);
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
    void btnSave_click(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tripColumn.setCellValueFactory(new PropertyValueFactory<>("tripId"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seatId"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        loadTickets(null);
        
        txtSearch.textProperty().addListener(event -> {
            this.loadTickets(txtSearch.getText());
        });
        
        
        tvTicket.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        selectRowTable();
                        btnCancelTicket.setVisible(true);
                        btnChangeTrip.setVisible(true);
                        btnExport.setVisible(true);
                        btnSave.setVisible(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
    }
    
    public void printTicket() throws IOException {
        Stage primaryStage = new Stage();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("com/ou/oubusmanager/TicketExport.fxml"));
        Parent root = fxmlLoader.load();
        ticketExport = fxmlLoader.<TicketExportController>getController();
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public void loadTickets(String kw) {
        ObservableList<Ticket> tks = FXCollections.observableArrayList();
        if (kw == null)
            txtSearch.setText("");
        try {
            tks.addAll(TicketService.getBooketTickets(kw));
            tvTicket.setItems(tks);
            
            changeCellFactory();
            if(!txtSearch.getText().isBlank())
                btnCancelSearch.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Thay doi cach hien thi cua cac column
    public void changeCellFactory() {
        customerColumn.setCellFactory(col -> new TableCell<Ticket, Integer>() {
            @Override
            protected void updateItem(Integer t, boolean empty) {
                super.updateItem(t, empty);
                try {
                    if (!empty) {
                        Customer c = UserService.getCustomerById(t);
                        setText(c != null ? c.getName() : null);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TicketFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        tripColumn.setCellFactory(col -> new TableCell<Ticket, Integer>() {
            @Override
            protected void updateItem(Integer t, boolean empty) {
                super.updateItem(t, empty);
                try {
                    if (!empty){
                        Trip trip = TripService.getTripById(t);
                        setText(trip != null ? trip.getFrom() + " - " + trip.getTo() : null);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TicketFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }
    
    private void selectRowTable() throws SQLException, ParseException {
        selected = (Ticket) this.tvTicket.getSelectionModel().getSelectedItem();
        Bus b = BusService.getBusByTripId(selected.getTripId());
        Trip t = TripService.getTripById(selected.getTripId());
        Seat s = SeatService.getSeatById(selected.getSeatId());
        Customer c = UserService.getCustomerById(selected.getCustomerId());
        
        txtFrom.setText(t.getFrom());
        txtTo.setText(t.getTo());
        txtDateDeparture.setText(t.getDate());
        txtTimeDeparture.setText(t.getTime());
        txtBusSeri.setText(b.getBusSerial());
        
        lblCustomerName.setText(c.getName());
        lblCustomerPhone.setText(c.getPhone());
        
        if (s!= null)
            cbSeat.setValue(s);
    }

    private void clearSelection() {
        tvTicket.getSelectionModel().clearSelection();
        
        btnCancelTicket.setVisible(false);
        btnChangeTrip.setVisible(false);
        btnExport.setVisible(false);
        btnSave.setVisible(false);
        
        lblCustomerName.setText("");
        lblCustomerPhone.setText("");
        
        txtFrom.setText("");
        txtTo.setText("");
        txtDateDeparture.setText("");
        txtTimeDeparture.setText("");
        txtBusSeri.setText("");
        cbSeat.setValue(null);
    }
}
