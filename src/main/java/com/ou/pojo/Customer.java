package com.ou.pojo;

/**
 *
 * @author CÔNG SANG
 */
public class Customer extends Account{
    private UserRole role = Account.UserRole.CUSTOMER;

    public Customer(int id, String username, String password, String name, String age, String phone) {
        super(id, username, password, name, age, phone);
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
