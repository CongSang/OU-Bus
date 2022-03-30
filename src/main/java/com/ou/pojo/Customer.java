/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

/**
 *
 * @author CÔNG SANG
 */
public class Customer extends User{
    private User.UserRole role = User.UserRole.CUSTOMER;

    public Customer(int id, String name, String phone) {
        super(id, name, phone);
    }

    public Customer() {
    }

    /**
     * @return the role
     */
    public User.UserRole getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(User.UserRole role) {
        this.role = role;
    }
}
