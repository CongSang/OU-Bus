/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

/**
 *
 * @author CÔNG SANG
 */
public class Customer extends Account{
    private Account.UserRole role = Account.UserRole.CUSTOMER;

    public Customer(int id, String name, String phone) {
        super(id, name, phone);
    }

    public Customer() {
    }

    /**
     * @return the role
     */
    public Account.UserRole getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Account.UserRole role) {
        this.role = role;
    }
}
