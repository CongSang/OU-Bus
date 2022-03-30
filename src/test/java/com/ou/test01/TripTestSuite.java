/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.testJdbc;

import com.ou.pojo.Bus;
import com.ou.pojo.Trip;
import com.ou.services.BusService;
import com.ou.services.TripService;
import com.ou.utils.DateTimeCalc;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
/**
 *
 * @author Admin
 */
public class TripTestSuite {
    private static Connection conn;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = Jdbc.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(TripTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TripTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvSource({"Test01,Test02,2022-03-22,16:00,1,false"})
    @DisplayName("Test add trip vào db")
    public void testAddTrip(String from, String to, String date, String time,
            int busId, boolean isComplete) {
        Trip t = new Trip(from, to, date, time, busId, isComplete);
        try {
            int actual = TripService.addTrip(t);
            Assertions.assertEquals(1, actual);
            
        } catch (SQLException ex) {
            Logger.getLogger(TripTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvSource({"Test01"})
    @DisplayName("Test Delete trip")
    public void testDeleteTrip(String from) throws ParseException {  
        try {
            List<Trip> trips = TripService.getTrips(from) ;
            int actual = TripService.deleteTrip(trips.get(0).getId());
            Assertions.assertEquals(1, actual);            
        } catch (SQLException ex) {
            Logger.getLogger(TripTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvSource({"4,1", "TPHCM,5", "HN,1", "Bình Thuận,1"})
    @DisplayName("Test Get trips chua keyword")
    public void testGetTrips(String kw, int expected) throws ParseException {  
        try {
            int actual = TripService.getTrips(kw).size();
            Assertions.assertEquals(expected, actual, "Test gettrips contains: " + kw);            
        } catch (SQLException ex) {
            Logger.getLogger(TripTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvSource({"TPHCM,HN,0", "Bình Thuận,TPHCM,1", "TPHCM,Cà Mau,0", "TPHCM,Cần Thơ,1"})
    @DisplayName("Test GettripsForCustomer")
    public void testGetTripsForCustomer(String from, String to, int expected) throws ParseException {  
        try {
            int actual = TripService.getTripForCustomerSearch(from, to).size();
            Assertions.assertEquals(expected, actual, "Test gettrips from " + from + " to " + to);            
        } catch (SQLException ex) {
            Logger.getLogger(TripTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvSource({"3,true", "4,false", "1, false"})
    @DisplayName("Test setComplete")
    public void testSetCompleteTrips(int soComplete, boolean expected) throws SQLException, ParseException {
        TripService.setCompleteTrip();
        List<Trip> trips = new ArrayList<>();
        trips.addAll(TripService.getTrips(null));
        //set complete if now() > t.datetime
        for (Trip t: trips) {
            Date d = DateTimeCalc.formatToDate(t.getDate(), t.getTime());
            if (DateTimeCalc.timeBetween(d, new Date()) >= 0) {
                t.setComplete(true);
            }     
        }
        List<Trip> completedTrips = trips.stream().filter(t -> t.isComplete()).collect(Collectors.toList());
        System.out.println(completedTrips.size());
        boolean actual = false;
        if ((soComplete + 1) == completedTrips.size())
            actual = true;
        Assertions.assertEquals(expected, actual, "So complete expected: "
                                +(soComplete)+ "\n So complete thuc su: " + (completedTrips.size() - 1));
    }
    
    @ParameterizedTest
    @CsvSource({"4,TPHCM,HN,2022-03-12,15:00,3,false", "8,Bình Thuận,TPHCM,2022-06-11,14:00,3,false"})
    @DisplayName("Test GettripsForCustomer")
    public void testUpdateTrip(int id, String from, String to, String date, String time
                                , int busId, boolean complete) throws ParseException {  
        try {
            //Expected rows affected
            int expected = 1;
            Trip t = new Trip(id, from, to, date, time, busId, complete);
            int actual = TripService.updateTrip(t);
            //Rows affected
            Assertions.assertEquals(expected, actual, "Test update trip id: " + id);            
        } catch (SQLException ex) {
            Logger.getLogger(TripTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
