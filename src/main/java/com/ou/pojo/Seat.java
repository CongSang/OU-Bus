package com.ou.pojo;

/**
 *
 * @author CÔNG SANG
 */
public class Seat {
    private int id;
    private int busId;
    private int tripId;

    public Seat() {
    }

    public Seat(int id, int busId) {
        this.id = id;
        this.busId = busId;
    }
    
    // Phuong thuc dung de khoi tao nhung ghe chua co ve cua trip
    public Seat(int id, int busId, int tripId) {
        this.id = id;
        this.busId = busId;
        this.tripId = tripId;
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

    @Override
    public String toString() {
        return String.format("%s", this.getId());
    }

    /**
     * @return the tripId
     */
    public int getTripId() {
        return tripId;
    }

    /**
     * @param tripId the tripId to set
     */
    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
}
