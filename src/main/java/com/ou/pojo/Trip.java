package com.ou.pojo;


/**
 *
 * @author Admin
 */
public class Trip {
    private int id;
    private String from;
    private String to;
    private String date;
    private String time;
    private int number;
    private int busId;
    private boolean complete;

    public Trip() {
    }
    
    public Trip(int id, String from, String to, String date
            , String time, int busId, boolean complete) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.busId = busId;
        this.complete = complete;
    }
    
    //    Create for customer
    public Trip(int id, String from, String to, String date
            , String time, int number, int busId, boolean complete) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.number = number;
        this.busId = busId;
        this.complete = complete;
    }
    
    public Trip(String from, String to, String date, String time, int busId, boolean complete) {
        this.from = from;
        this.to = to;
        this.busId = busId;
        this.complete = complete;
        this.date = date;
        this.time = time;
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

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }
    
    
    
}
