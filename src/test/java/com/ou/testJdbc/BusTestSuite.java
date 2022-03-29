/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.testJdbc;

import com.ou.services.BusService;
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


/**
 *
 * @author CÔNG SANG
 */
public class BusTestSuite {
    private static Connection conn;
    private static BusService b;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = Jdbc.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        b = new BusService();
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
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("Kiem tra rong")
    public void testBusNotNull(int id) {
        try {
            String actual = b.getBusById(id).getBusSerial();
            Assertions.assertNotNull(actual);
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @DisplayName("Kiem tra so xe chua co cho")
    public void testBusNotEnoughSeat() {
        try {
            int expected = 2; // So xe can tao ghe
            int actual = b.getBusNotEnoughtSeats().size();
            Assertions.assertEquals(expected, actual);
        } catch (SQLException ex) {
            Logger.getLogger(BusTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
