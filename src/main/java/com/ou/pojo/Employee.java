package com.ou.pojo;

/**
 *
 * @author CÔNG SANG
 */
public class Employee extends Account{
    private UserRole role = UserRole.EMPLOYEE;

    public Employee(int id, String username, String password, String name, String age, String phone) {
        super(id, username, password, name, age, phone);
    }

    public Employee() {
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
