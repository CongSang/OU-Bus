/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.oubusmanager;

import com.ou.pojo.Trip;
import com.ou.services.TripService;
import com.ou.pojo.Admin;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

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
    private ComboBox<?> cbBus;

    @FXML
    private DatePicker dpDate;

    @FXML
    private ImageView ivLogout;

    @FXML
    private TableView<Trip> tvTrip;
    
    @FXML
    private TableColumn<Trip, Integer> idColumn;
    
    @FXML
    private TableColumn<Trip, String> fromColumn = new TableColumn<Trip, String>("Tu");
    
    @FXML
    private TableColumn<Trip, String> toColumn = new TableColumn<Trip, String>("Den");
    
    @FXML
    private TableColumn<Trip, String> dateColumn = new TableColumn<Trip, String>("Ngay khoi hanh");
    
    @FXML
    private TableColumn<Trip, Integer> busIdColumn = new TableColumn<Trip, Integer>("Ma xe");
    
    @FXML
    private TableColumn<Trip, Boolean> completeColumn = new TableColumn<Trip, Boolean>("Da hoan thanh");

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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        busIdColumn.setCellValueFactory(new PropertyValueFactory<>("busId"));
        completeColumn.setCellValueFactory(new PropertyValueFactory<>("complete"));
        
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
            trips.addAll(TripService.getTrip(kw));
            for(Trip t : trips) {
                System.out.println(t.getDate());
                System.out.println(t);
            }
            
            tvTrip.setItems(trips);
        } catch (SQLException ex) {
            Logger.getLogger(TripManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void btnAdd_click(ActionEvent event) {

    }
    
    @FXML
    void btnUpdate_click(ActionEvent event) {

    }
    
    @FXML
    void btnReset_click(ActionEvent event) {

    }
    
    @FXML
    void ivLogout_click(ActionEvent event) {
        
    }
}
