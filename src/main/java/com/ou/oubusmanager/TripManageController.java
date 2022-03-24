/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.oubusmanager;

import com.ou.pojo.Bus;
import com.ou.pojo.Trip;
import com.ou.services.TripService;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

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
    private TableColumn<Trip, Integer> busIdColumn;
    
    @FXML
    private TableColumn<Trip, Boolean> completeColumn;
    
    @FXML
    private TableColumn<Trip, Void> btnColumn;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtTo;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        busIdColumn.setCellValueFactory(new PropertyValueFactory<>("busId"));
        completeColumn.setCellValueFactory(new PropertyValueFactory<>("complete"));
        
        try {
            cbBus.setItems(FXCollections.observableList(TripService.getBuses()));
        } catch (SQLException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {       
            this.loadData(null); 
        } catch (ParseException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtSearch.textProperty().addListener((event) -> {
            try {
                this.loadData(txtSearch.getText());
            } catch (ParseException ex) {
                Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void loadData(String kw) throws ParseException {       
        ObservableList<Trip> trips = FXCollections.observableArrayList();
        
        try {  
            trips.addAll(TripService.getTrips(kw));          
            tvTrip.setItems(trips);
            addButtonToTable();
        } catch (SQLException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addButtonToTable() {

        Callback<TableColumn<Trip, Void>, TableCell<Trip, Void>> cellFactory = new Callback<TableColumn<Trip, Void>, TableCell<Trip, Void>>() {
            @Override
            public TableCell<Trip, Void> call(final TableColumn<Trip, Void> param) {
                final TableCell<Trip, Void> cell = new TableCell<Trip, Void>() {
                    
                    Image img = new Image("com/images/trashbin.png");
                    ImageView view = new ImageView(img);
                    
                    
                    Button btn = new Button();
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Trip data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                        
                        view.setFitHeight(15);
                        view.setPreserveRatio(true);
                        btn.setPrefSize(5, 5);
                        btn.setGraphic(view);
                        btn.setCursor(Cursor.HAND);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        btnColumn.setCellFactory(cellFactory);

    }
    
    @FXML
    void btnAdd_click(ActionEvent event){
        LocalDate d = LocalDate.now();
        if (dpDate.getValue() == null || txtFrom.getText().isEmpty()||
                txtTo.getText().isEmpty() || txtTime.getText().isEmpty() ||
                cbBus.getValue() == null) {
             Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LOI");
            a.setContentText("Vul long nhap day du thong tin!!");

            a.showAndWait();
        }
        else {
        
            LocalDateTime dtCheck;

            if (txtTime.getText() != null) {
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    dtCheck = LocalDateTime.parse(d.toString() + " " + txtTime.getText(), dtf);

                    String datetime = d.toString() + " " + txtTime.getText();                
                    Trip t = new Trip (txtFrom.getText(), txtTo.getText(),
                    datetime, cbBus.getValue().getId(), false);
                    
                    if (TripService.addTrip(t) != -1) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("LOI");
                        a.setContentText("Them thanh cong!!");

                        a.showAndWait();
                        
                        loadData(null);
                        reset();
                    }
                    else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("LOI");
                        a.setContentText("Khong the them!!");

                        a.showAndWait();
                    }
                        
                } catch (Exception e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("LOI");
                    a.setContentText(e.getMessage());

                    a.showAndWait();
                }
            }
        }            
    }
    
    @FXML
    void btnUpdate_click(ActionEvent event) {

    }
    
    @FXML
    void btnReset_click(ActionEvent event) {
        reset();
    }
    
    @FXML
    void ivLogout_click(ActionEvent event) {
        
    }
    
    void reset() {
        txtFrom.setText("");
        txtTo.setText("");
        dpDate.setValue(null);
        txtTime.setText(""); 
        cbBus.setValue(null);;
    }
}
