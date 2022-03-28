/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.services;

import com.ou.pojo.Account;
import com.ou.pojo.Admin;
import com.ou.pojo.Customer;
import com.ou.pojo.Employee;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author CÔNG SANG
 */
public class UserService {
    public static Account getUserLogin(String username, String password) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user where username = ? AND password = ?");
            stm.setString(1, username);
            stm.setString(2, password);
            
            ResultSet rs = stm.executeQuery();
            
            Account u = null;
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String age = rs.getString("age");
                String user_role = rs.getString("user_role");
                
                switch (user_role) {
                    case "ADMIN":
                        u = new Admin(id, username, password, name, age, phone);
                        break;
                    case "EMPLOYEE":
                        u = new Employee(id, username, password, name, age, phone);
                        break;
                }
            }      
            return u;
        }     
    }
    
    public static Account getCustomer(String name, String phone) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user where name = ? AND phone = ?");
            stm.setString(1, name);
            stm.setString(2, phone);
            
            ResultSet rs = stm.executeQuery();
            
            Account u = null;
            while(rs.next()) {
                int id = rs.getInt("id");
                
                u = new Customer(id, name, phone);
            }      
            return u;
        }     
    }
    
    public static int addUser(String fullName, String phone, String age
            , String username, String password, String user_role) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO user (name, phone, age, username, password, user_role)"
                        + "VALUES (?,?,?,?,?,?)");    
            stm.setString(1, fullName);
            stm.setString(2, phone);
            stm.setString(3, age);
            stm.setString(4, username);
            stm.setString(5, password);
            stm.setString(6, user_role);           
            
            return stm.executeUpdate(); 
        }     
    }
}
