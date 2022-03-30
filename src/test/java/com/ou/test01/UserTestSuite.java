/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.testJdbc;

import java.lang.String;
import com.ou.pojo.Trip;
import com.ou.pojo.User;
import com.ou.services.TripService;
import com.ou.services.UserService;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Admin
 */
public class UserTestSuite {
    private static Connection conn;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = Jdbc.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvSource({"ad,123,true", "em,123,true", "cus,123,false"})
    @DisplayName("Test get user login")
    public void testGetUserLogin(String username, String password, boolean expected) throws SQLException {
        User u = UserService.getUserLogin(username, password);
        if (expected)
            Assertions.assertNotNull(u);
        else
            Assertions.assertNull(u);
    }
    
    @ParameterizedTest
    @CsvSource({"et,091988283,,,,EMPLOYEE"})
    @DisplayName("Test add user")
    public void testAddUser(String fullName, String phone, String age
            , String username, String password, String user_role) throws SQLException {
        System.out.println(fullName + phone + age + username + password + user_role);
        UserService.addUser(fullName, phone, age, username, password, user_role);
        
        User u = UserService.getCustomer(phone);
        Assertions.assertNotNull(u);
    }
}
