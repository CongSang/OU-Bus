package com.ou.oubusmanager;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInRight;
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
import com.ou.utils.DateTimeCalc;
import com.ou.utils.MyAlert;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TicketFormController implements Initializable{

    @FXML
    private Button btnCancelSearch;
    
    @FXML
    private Button btnCancelSearchTrip;
    
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
    private Button btnCancel;

    @FXML
    private ComboBox<Seat> cbSeat;
    
    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerPhone;

    @FXML
    private TableColumn<Ticket, Integer> customerColumn;

    @FXML
    private TableColumn<Ticket, Integer> idColumn;

    @FXML
    private TableColumn<Ticket, Integer> seatColumn;

    @FXML
    private TableColumn<Ticket, Integer> tripColumn;
    
    @FXML
    private TableColumn<Trip, String> fromColumn;
    
    @FXML
    private TableColumn<Trip, String> toColumn;
    
    @FXML
    private TableColumn<Trip, String> dateColumn;

    @FXML
    private TableColumn<Trip, String> timeColumn;
    
    @FXML
    private TableColumn<Trip, Integer> busId_hiddenColumn;
    
    @FXML
    private TableColumn<Trip, Integer> id_hiddenColumn;
    
    @FXML
    private TableColumn<Trip, String> btnColumn;

    @FXML
    private TableView<Ticket> tvTicket;
    
     @FXML
    private TableView<Trip> tvTrip;

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
    
    @FXML
    private TextField txtSearchFrom;

    @FXML
    private TextField txtSearchTo;
    
    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;
    
    @FXML
    private ImageView btnBack;
    
    private Employee employee;   
    private Ticket selectedTicket;
    private Trip selectedTrip = new Trip();
    public TicketManageController ticketManageController;
    
    public void setEmployee(Employee em) {
        this.employee = em;
    }

    @FXML
    void btnCancelSearchClick(ActionEvent event) {
        txtSearch.setText("");
        btnCancelSearch.setVisible(false);
    }
    
    @FXML
    void btnCancelSearchTripClick(ActionEvent event) {
        txtSearchFrom.setText("");
        txtSearchTo.setText("");
        btnCancelSearchTrip.setVisible(false);
    }
    
    @FXML
    void btnCancelTicket_click(ActionEvent event) throws SQLException {
        Optional<ButtonType> confirm = MyAlert.showConfirmDialog("Có chắc chắn hủy vé không?");
        
        if(confirm.get() == ButtonType.OK) {
            if(TicketService.setTicketFree(selectedTicket.getTripId(), selectedTicket.getSeatId()) > 0) {
                MyAlert.showSuccessDialog("Hủy vé thành công");
                loadTickets(null);
                clearSelection();      
            }
            else
                MyAlert.showErrorDialog("Hủy vé thất bại");
        }
        
    }

    @FXML
    void btnChangeTrip_click(ActionEvent event) {
        new FadeInRight(pane2).play();
        pane2.toFront();
        loadTrips(null, null);
        btnChangeTrip.setVisible(false);
    }

    @FXML
    void btnExport_click(ActionEvent event) {

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
    void btnSave_click(ActionEvent event) throws SQLException {
        if(TicketService.setTicketFree(selectedTicket.getTripId(),
                selectedTicket.getSeatId()) > 0) {
            Ticket newTicket = new Ticket(selectedTrip.getId()
                    , cbSeat.getSelectionModel().getSelectedItem().getId(),
                    selectedTicket.getCustomerId()
                    , this.employee.getId(), Ticket.Status.BOOKED,
                    DateTimeCalc.getNow());
            
            if(TicketService.createTicketBooked(newTicket) > 0) {
                MyAlert.showSuccessDialog("Lưu thành công");
                selectedTicket = newTicket;
                loadTickets(null);
            }
        }
    }
    
    @FXML
    void btnCancel_click(ActionEvent event) {
        clearSelection();
    }
    
    @FXML
    void btnBack_clicked(MouseEvent event) {
        new FadeInRight(pane1).play();
        pane1.toFront();
    }
    
    private void loadTickets(String kw) {
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
    
    private void loadTrips(String from, String to) {
        ObservableList<Trip> trips = FXCollections.observableArrayList();
        try {
            
            trips.addAll(TripService.getTripForCustomerSearch(from, to));
            tvTrip.setItems(trips);
            
            if(!txtSearchFrom.getText().isBlank() && !txtSearchTo.getText().isBlank())
                btnCancelSearchTrip.setVisible(true);
            
            addButtonToTable();
        } catch (SQLException ex) {
            Logger.getLogger(TicketFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TicketFormController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void addButtonToTable() {

        Callback<TableColumn<Trip, String>, TableCell<Trip, String>> cellFactory = (TableColumn<Trip, String> param) -> {
            
            final TableCell<Trip, String> cell = new TableCell<Trip, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView chooseIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT);
                        
                        chooseIcon.setStyleClass("select-icon");
                        
                        //event click on icon
                        chooseIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                Trip data = getTableView().getItems().get(getIndex());
                                selectedTrip = data;
                                
                                txtFrom.setText(data.getFrom());
                                txtTo.setText(data.getTo());
                                txtDateDeparture.setText(data.getDate());
                                txtTimeDeparture.setText(data.getTime());
                                txtBusSeri.setText(BusService.getBusById(data.getBusId()).getBusSerial());
                                ObservableList<Seat> freeSeats = FXCollections.observableArrayList();
                                freeSeats.addAll(SeatService.getSeatEmpty(data.getBusId(), data.getId()));
                                
                                cbSeat.setItems(freeSeats);
                                
                                new FadeInRight(pane1).play();
                                pane1.toFront();
                                btnChangeTrip.setVisible(true);
                            } catch (SQLException ex) {
                                Logger.getLogger(TicketFormController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        
                        HBox managebtn = new HBox(chooseIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(chooseIcon, new Insets(2, 2, 2, 1));
                        setGraphic(managebtn);
                        setText(null);
                        
                    }
                }
            };
            return cell;
        };
        btnColumn.setCellFactory(cellFactory);
        btnColumn.setResizable(false);
    }
    
    private void selectRowTable() throws SQLException, ParseException {
        selectedTicket = (Ticket) this.tvTicket.getSelectionModel().getSelectedItem();
        selectedTrip.setId(selectedTicket.getTripId());
        
        Bus b = BusService.getBusByTripId(selectedTicket.getTripId());
        Trip t = TripService.getTripById(selectedTicket.getTripId());     
        Customer c = UserService.getCustomerById(selectedTicket.getCustomerId());
        ObservableList<Seat> freeSeats = FXCollections.observableArrayList();
        freeSeats.addAll(SeatService.getSeatEmpty(b.getId(), t.getId()));
        
        cbSeat.setItems(freeSeats);
        txtFrom.setText(t.getFrom());
        txtTo.setText(t.getTo());
        txtDateDeparture.setText(t.getDate());
        txtTimeDeparture.setText(t.getTime());
        txtBusSeri.setText(b.getBusSerial());
        
        lblCustomerName.setText(c.getName());
        lblCustomerPhone.setText(c.getPhone());
        
        Seat s = SeatService.getSeatById(selectedTicket.getSeatId());
        if (s != null) {
            freeSeats.add(s);
            cbSeat.setValue(s);
        }
    }

    private void clearSelection() {
        tvTicket.getSelectionModel().clearSelection();
        
        btnCancelTicket.setVisible(false);
        btnChangeTrip.setVisible(false);
        btnExport.setVisible(false);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        
        lblCustomerName.setText("");
        lblCustomerPhone.setText("");
        
        txtFrom.setText("");
        txtTo.setText("");
        txtDateDeparture.setText("");
        txtTimeDeparture.setText("");
        txtBusSeri.setText("");
        cbSeat.setValue(null);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Ticket view
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tripColumn.setCellValueFactory(new PropertyValueFactory<>("tripId"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seatId"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        //Trip view
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        id_hiddenColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        busId_hiddenColumn.setCellValueFactory(new PropertyValueFactory<>("busId"));
        
        loadTickets(null);
        
        txtSearch.textProperty().addListener(event -> {
            this.loadTickets(txtSearch.getText());
        });
        
        // Tim kiem diem di
        txtSearchFrom.textProperty().addListener((event) -> {
            this.loadTrips(txtSearchFrom.getText(), txtSearchTo.getText());
        });
        
        // Tim kiem diem den
        txtSearchTo.textProperty().addListener((event) -> {
            this.loadTrips(txtSearchFrom.getText(), txtSearchTo.getText());
        });
        
        tvTicket.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        selectRowTable();
                        btnCancelTicket.setVisible(true);
                        btnChangeTrip.setVisible(true);
                        btnExport.setVisible(true);
                        btnSave.setVisible(true);
                        btnCancel.setVisible(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
    }
}
