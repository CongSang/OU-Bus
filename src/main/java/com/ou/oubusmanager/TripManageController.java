package com.ou.oubusmanager;

import com.ou.utils.DateTimeCalc;
import com.ou.pojo.Bus;
import com.ou.pojo.Trip;
import com.ou.services.TripService;
import com.ou.pojo.Admin;
import com.ou.services.BusService;
import com.ou.services.SeatService;
import com.ou.utils.MyAlert;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;

import java.time.LocalDate;
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
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
/**
 * FXML Controller class
 *
 * @author CÔNG SANG
 */
public class TripManageController implements Initializable {
    
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private ComboBox<Bus> cbBus;

    @FXML
    private DatePicker dpDate;

    @FXML
    private ImageView ivLogout;

    @FXML
    private TableView<Trip> tvTrip;
    
    @FXML
    private TableColumn<Trip, Integer> idColumn;
    
    @FXML
    private TableColumn<Trip, String> fromColumn;
    
    @FXML
    private TableColumn<Trip, String> toColumn;
    
    @FXML
    private TableColumn<Trip, String> dateColumn;
    
    @FXML
    private TableColumn<Trip, String> timeColumn;
    
    @FXML
    private TableColumn<Trip, Integer> busIdColumn;
    
    @FXML
    private TableColumn<Trip, Boolean> completeColumn;
    
    @FXML
    private TableColumn<Trip, String> btnColumn;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtTo;
    
    
    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
            toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            busIdColumn.setCellValueFactory(new PropertyValueFactory<>("busId"));
            completeColumn.setCellValueFactory(new PropertyValueFactory<>("complete"));
            completeColumn.resizableProperty().set(false);
            
            //format dd/MM/yyyy dpDate
            dpDate.setConverter(new StringConverter<LocalDate>()
            {
                private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
                
                @Override
                public String toString(LocalDate localDate)
                {
                    if(localDate==null)
                        return "";
                    return dateTimeFormatter.format(localDate);
                }
                
                @Override
                public LocalDate fromString(String dateString)
                {
                    if(dateString==null || dateString.trim().isEmpty())
                    {
                        return null;
                    }
                    return LocalDate.parse(dateString,dateTimeFormatter);
                }
            });
            
            //thay doi cach hien thi true false
            completeColumn.setCellFactory(col -> new TableCell<Trip, Boolean>() {
                @Override
                protected void updateItem(Boolean t, boolean empty) {
                    super.updateItem(t, empty); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                    setText(empty ? null : t ? "Hoàn thành" : "Chưa");
                }
                
            });
            
            //thay doi cach hien thi id
            busIdColumn.setCellFactory(cell -> new TableCell<Trip, Integer>() {
                @Override
                protected void updateItem(Integer t, boolean empty) {
                    super.updateItem(t, empty); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                    
                    if (t != null) {
                        try {
                            Bus b = BusService.getBusById(t);
                            setText(b != null ? b.getBusSerial() : null);
                        } catch (SQLException ex) {
                            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
               
                    
                }
                
            });
            
            cbBus.setItems(FXCollections.observableList(BusService.getBuses()));
            this.loadData(null);
            txtSearch.textProperty().addListener((event) -> {
                try {
                    this.loadData(txtSearch.getText());
                } catch (ParseException ex) {
                    Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            // Su kien click 1 dong trong bang
            tvTrip.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        selectRowTable();
                        btnAdd.setDisable(true);
                        btnAdd.setVisible(false);
                        
                        btnUpdate.setDisable(false);
                        btnUpdate.setVisible(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (ParseException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void loadData(String kw) throws ParseException {       
        ObservableList<Trip> trips = FXCollections.observableArrayList();
        if (kw == null)
            txtSearch.setText("");
        try {
            trips.addAll(TripService.getTrips(kw));
            for (Trip t : trips) {
                Date dateStart = DateTimeCalc.formatToDate(t.getDate(), t.getTime());
                if (DateTimeCalc.timeBetween(new Date(), dateStart) <= 0) {
                    t.setComplete(true);
                    TripService.updateTrip(t);
                }
                    
            }
            
            tvTrip.setItems(trips);
            addButtonToTable();
        } catch (SQLException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        
                        deleteIcon.setStyleClass("delete-icon");
                        
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Optional<ButtonType> confirm = MyAlert.showConfirmDialog("Có chắc chắn xóa không?");
                            Trip data = getTableView().getItems().get(getIndex());
                            
                            if(confirm.get() == ButtonType.OK) {
                                try {
                                    if(TripService.deleteTrip(data.getId()) != -1) {
                                        MyAlert.showSuccessDialog("Xóa chuyến xe thành công.");

                                        clearSelection();
                                        reset();
                                        loadData(null);
                                    }
                                    else
                                        MyAlert.showErrorDialog("Xóa chuyến xe thất bại.");
                                } catch (SQLException | ParseException ex) {
                                    Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        
                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 2, 1));
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
    
    @FXML
    void btnAdd_click(ActionEvent event){
//        LocalDate d = LocalDate.now();
        if (dpDate.getValue() == null || txtFrom.getText().isEmpty()||
                txtTo.getText().isEmpty() || txtTime.getText().isEmpty() ||
                cbBus.getValue() == null) {
            MyAlert.showErrorDialog("Vui lòng nhập đầy đủ thông tin");
        }
        else {
        
            LocalDateTime dtCheck;

            if (txtTime.getText() != null) {
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    dtCheck = LocalDateTime.parse(dpDate.getValue() + " " + txtTime.getText(), dtf);

//                    String datetime = d.toString() + " " + txtTime.getText();                
                    Trip t = new Trip (txtFrom.getText(), txtTo.getText(),
                        dtf.format(dtCheck).split(" ")[0],
                        dtf.format(dtCheck).split(" ")[1], cbBus.getValue().getId(), false);
                    
                    if (TripService.addTrip(t) != -1) {
                        MyAlert.showSuccessDialog("Thêm chuyến xe thành công.");

                        loadData(null);
                        reset();
                    }
                    else {
                        MyAlert.showErrorDialog("Có lỗi xảy ra. Không thể thêm.");
                    }
                        
                } catch (Exception e) {
                    MyAlert.showErrorDialog(e.getMessage());
                }
            }
        }            
    }
    
    @FXML
    void btnUpdate_click(ActionEvent event) {
        Trip selected = (Trip) this.tvTrip.getSelectionModel().getSelectedItem();
        
        if (dpDate.getValue() == null || txtFrom.getText().isEmpty()||
                txtTo.getText().isEmpty() || txtTime.getText().isEmpty() ||
                cbBus.getValue() == null) {
            MyAlert.showErrorDialog("Vui lòng nhập đầy đủ thông tin");
        }
        else {
        
            LocalDateTime dtCheck;

            if (txtTime.getText() != null) {
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    
                    dtCheck = LocalDateTime.parse(dpDate.getValue().format(dateFormat) + " " + txtTime.getText(), dtf);
           
                    Trip t = new Trip (selected.getId(), txtFrom.getText(), txtTo.getText(),
                        dtf.format(dtCheck).split(" ")[0],
                        dtf.format(dtCheck).split(" ")[1], cbBus.getValue().getId(), false);
                    
                    if (TripService.updateTrip(t) != -1) {
                        MyAlert.showSuccessDialog("Cập nhật chuyến xe thành công.");
                        
                        loadData(null);
                        reset();
                        clearSelection();
                    }
                    else {
                        MyAlert.showErrorDialog("Có lỗi xảy ra. Không thể cập nhật.");
                    }
                        
                } catch (Exception e) {
                    MyAlert.showErrorDialog(e.getMessage());
                }
            }
        }
    }
    
    @FXML
    void btnReset_click(ActionEvent event) {
        reset();
    }
    
    @FXML
    void btnCancel_click(ActionEvent event) {
        reset();
        
        clearSelection();
    }
    
    @FXML
    void ivLogout_click(MouseEvent event) throws IOException {
        Stage stage = App.getPrimaryStage();
        Parent root = FXMLLoader.load(this.getClass().getClassLoader()
                .getResource("com/ou/oubusmanager/Enter.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        this.admin = null;
        stage.show();
    }
    
    void reset() {
        
        txtFrom.setText("");
        txtTo.setText("");
        dpDate.setValue(null);
        txtTime.setText(""); 
        cbBus.setValue(null);;
    }
    
    private void selectRowTable() throws SQLException, ParseException {
        Trip selected = (Trip) this.tvTrip.getSelectionModel().getSelectedItem();
        Bus b = BusService.getBusById(selected.getBusId());
        txtFrom.setText(selected.getFrom());
        txtTo.setText(selected.getTo());
        
        LocalDate parsedDate = LocalDate.parse(DateTimeCalc.formatyyyyMMdd(selected.getDate()));
        dpDate.setValue(parsedDate);
        txtTime.setText(selected.getTime());
        if (b != null)
            cbBus.setValue(b);
        
        btnCancel.setVisible(true);
    }
    
    void clearSelection() {
        this.tvTrip.getSelectionModel().clearSelection();
        
        btnAdd.setDisable(false);
        btnAdd.setVisible(true);

        btnUpdate.setDisable(true);
        btnUpdate.setVisible(false);
        
        btnCancel.setVisible(false);
    }
}
