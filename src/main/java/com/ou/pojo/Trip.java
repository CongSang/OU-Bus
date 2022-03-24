/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.util.Date;
import javafx.beans.property.IntegerProperty;

/**
 *
 * @author Admin
 */
public class Trip {
    private int id;
    private String from;
    private String to;
    private String date;
    private int busId;
    private boolean complete;

    public Trip() {
    }

    public Trip(int id, String from, String to, String date, int busId, boolean complete) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.busId = busId;
        this.complete = complete;
        this.date = date;
    }
    
    public Trip(String from, String to, String date, int busId, boolean complete) {
        this.from = from;
        this.to = to;
        this.busId = busId;
        this.complete = complete;
        this.date = date;
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
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * @param complete the complete to set
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return this.getFrom()+ " - " + this.getTo(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    /**
     * @return the busId
     */
    public int getBusId() {
        return busId;
    }

    /**
     * @param busId the busId to set
     */
    public void setBusId(int busId) {
        this.busId = busId;
    }
    
    
    
}
