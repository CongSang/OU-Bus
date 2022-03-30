/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.testJdbc;

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
import org.junit.jupiter.params.provider.ValueSource;
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
    @DisplayName ("Kiem tra ghe da co ve")
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
    @CsvSource({"4,3", "7,3", "8,3", "11,1"})
    @DisplayName ("Kiem tra ghe chua duoc dat ve")
    public void testGetSeatEmpty(int tripId, int busId) {
        try {
            int expected = 3; // So ghe chua duoc dat hoac mua 
            int actual = s.getSeatEmpty(busId, tripId).size();
            Assertions.assertEquals(expected, actual
                    , String.format("Dang ktr tripId = %d, busId = %d", tripId, busId));
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
