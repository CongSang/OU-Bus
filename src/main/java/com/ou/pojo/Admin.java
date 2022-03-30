package com.ou.pojo;

/**
 *
 * @author CÔNG SANG
 */
public class Admin extends User{
    private UserRole role = UserRole.ADMIN;

    public Admin(int id, String username, String password, String name, String age, String phone) {
        super(id, username, password, name, age, phone);
    }

    public Admin() {
    }

    /**
     * @return the role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(UserRole role) {
        this.role = role;
    }
    
}
