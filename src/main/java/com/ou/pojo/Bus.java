/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

/**
 *
 * @author Admin
 */
public class Bus {
    private int id;
    private String busSerial;

    public Bus() {
    }

    public Bus(int id, String busSerial) {
        this.id = id;
        this.busSerial = busSerial;
    } 
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the busSerial
     */
    public String getBusSerial() {
        return busSerial;
    }

    /**
     * @param busSerial the busSerial to set
     */
    public void setBusSerial(String busSerial) {
        this.busSerial = busSerial;
    }
    
}
