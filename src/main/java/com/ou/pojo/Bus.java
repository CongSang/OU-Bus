/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.util.List;

/**
 *
 * @author Admin
 */
public class Bus {
    private int id;
    private String busSerial;
    private int seatNumber;
    private List<Seat> seats;

    public Bus() {
    }

    public Bus(int id, String busSerial) {
        this.id = id;
        this.busSerial = busSerial;
    } 
    
    public Bus(int id, String busSerial, int seatNumber) {
        this.id = id;
        this.busSerial = busSerial;
        this.seatNumber = seatNumber;
    }

    // For Create Bus's seat if list<seat> < seats 
    public Bus(int id, String busSerial, int seatNumber, List<Seat> seats) {
        this.id = id;
        this.busSerial = busSerial;
        this.seatNumber = seatNumber;
        this.seats = seats;
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

    @Override
    public String toString() {
        return this.getBusSerial();
    }   

    /**
     * @return the seatNumber
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * @param seatNumber the seatNumber to set
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * @return the seats
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * @param seats the seats to set
     */
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

}
