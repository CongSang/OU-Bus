/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.test01;

import com.ou.pojo.Ticket;
import com.ou.pojo.Trip;
import com.ou.services.TicketService;
import com.ou.services.TripService;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author CÔNG SANG
 */
public class TicketTestSuite {
    private static Connection conn;
    private static TicketService t;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = Jdbc.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        t = new TicketService();
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    @DisplayName("Kiem tra tao ve cho chuyen di moi")
    public void testCreateTicketForNewTrip() throws SQLException {
        int expexted = 3;
        Trip trip = new Trip("HCM", "HN", "2022-08-15", "15:00", 2, false);
        TripService.addTrip(trip);
        int actual = t.createNewTicket(); // Neu co chuyen xe can tao thi tra ve 1
        Assertions.assertEquals(expexted, actual);
    }
    
    @ParameterizedTest
    @CsvSource({"4,0", "7,3", "8,0"})
    public void testGetTicketByTrip(int tripId, int expected) {
        try {
            int actual = t.getTicketByTrip(tripId).size(); // Tra ve so ve cua chuyen
            Assertions.assertEquals(expected, actual
                    , String.format("Dang ktra tripId = %d", tripId));
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
//    @ParameterizedTest
//    @CsvSource({"7,0", "11,0"})
//    public void testSetTicketFree(int tripId, int expected) {
//        try {
//            int actual = t.createTicketFree(tripId); // Chuyen cac ve BOOKED thanh FREE
//            Assertions.assertEquals(expected, actual
//                    , String.format("Dang ktra tripId = %d", tripId));
//        } catch (SQLException ex) {
//            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @ParameterizedTest
//    @CsvSource({"7,3", "11,3"})
//    public void testSetTicketWithDraw(int tripId, int expected) {
//        try {
//            int actual = t.createTicketWithDraw(tripId); // Chuyen cac ve FREE thanh WITHDRAW
//            Assertions.assertEquals(expected, actual
//                    , String.format("Dang ktra tripId = %d", tripId));
//        } catch (SQLException ex) {
//            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    @ParameterizedTest
    @CsvSource({"7, 1, 4, 3, 2022-03-29, 1"})
    public void testSetTicketBooked(int tripId, int seatId, int customerID
            , int employeeId, String dateBook, int expected) {
        try {
            Ticket ticket = new Ticket(tripId, seatId, customerID, employeeId, Ticket.Status.FREE, dateBook);
            
            int actual = t.createTicketBooked(ticket); // Chuyen 1 ve FREE thanh BOOKED
            Assertions.assertEquals(expected, actual
                    , String.format("Dang ktra tripId = %d", tripId));
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
