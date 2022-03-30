/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.test01;

import com.ou.services.SeatService;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author CÔNG SANG
 */
public class SeatTestSuite {
    private static Connection conn;
    private static SeatService s;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = Jdbc.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        s = new SeatService();
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
    public void testSeatNoTicket() {
        try {
            int expected = 0; // So ghe chua co ve
            int actual = s.getAllSeatNoTicket().size();
            Assertions.assertEquals(expected, actual);
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvSource({"4,3,0", "7,3,2", "8,2,0"})
    @DisplayName ("Kiem tra ghe chua duoc dat ve cua 1 chuyen")
    public void testGetSeatEmpty(int tripId, int busId, int expected) {
        try { 
            int actual = s.getSeatEmpty(busId, tripId).size();
            Assertions.assertEquals(expected, actual
                    , String.format("Dang ktr tripId = %d, busId = %d", tripId, busId));
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void testCreateSeatOfBus() throws SQLException {
        boolean actual = s.createSeatOfBus();
        Assertions.assertTrue(actual);
    }
}
