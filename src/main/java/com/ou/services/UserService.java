/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.services;

import com.ou.pojo.User;
import com.ou.pojo.Admin;
import com.ou.pojo.Customer;
import com.ou.pojo.Employee;
import com.ou.utils.Jdbc;
import com.ou.utils.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author CÔNG SANG
 */
public class UserService {
    public static User getUserLogin(String username, String password) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            password = Security.encryptMD5(password);
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user where username = ? AND password = ?");
            stm.setString(1, username);
            stm.setString(2, password);
            
            ResultSet rs = stm.executeQuery();
            
            User u = null;
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
    
    public static Customer getCustomerById(int id) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user"
                    + " where id = ?");
            stm.setInt(1, id);
            
            ResultSet rs = stm.executeQuery();
            
            Customer u = null;
            while(rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                u = new Customer(id, name, phone);
            }      
            return u;
        }     
    }
    
    public static User getEmployeeById(int id) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user"
                    + " where id = ?");
            stm.setInt(1, id);
            
            ResultSet rs = stm.executeQuery();
            
            User u = null;
            while(rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                u = new Employee(id, name, phone);
            }      
            return u;
        }     
    }
    
    public static User getCustomer(String phone) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user where phone = ?");
            stm.setString(1, phone);
            
            ResultSet rs = stm.executeQuery();
            
            User u = null;
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                u = new Customer(id, name, phone);
            }      
            return u;
        }     
    }
    
    public static int addUser(String fullName, String phone, String age
            , String username, String password, String user_role) {
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
        } catch (SQLException e) {
            System.out.println("ERROR CODE: " + e.getErrorCode());
            return -1;
        }
    }
}
