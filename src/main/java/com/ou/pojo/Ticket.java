package com.ou.pojo;

/**
 *
 * @author CÔNG SANG
 */
public class Ticket {
    private int id;
    private int tripId;
    private int seatId;
    private int customerId;
    private int employeeId;
    private Status status = Status.FREE;
    private String dateBook;
    private String datePrint;

    public Ticket() {
    }
    
    // Khoi tao ve truoc in ve sau (khach hang dat ve)
    public Ticket(int id, int tripId, int seatId, int customerId
            , int employeeId, String dateBook) {
        this.id = id;
        this.tripId = tripId;
        this.seatId = seatId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.dateBook = dateBook;
    }
    
    // Khoi tao ve dong thoi in ve (nhan vien ban ve)
    public Ticket(int id, int tripId, int seatId, int customerId
            , int employeeId, String dateBook, String datePrint) {
        this.id = id;
        this.tripId = tripId;
        this.seatId = seatId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.dateBook = dateBook;
        this.datePrint = datePrint;
    }
    
    // set status
    public Ticket(int id, int tripId, int seatId, int customerId
            , int employeeId, Status status, String dateBook, String datePrint) {
        this.id = id;
        this.tripId = tripId;
        this.seatId = seatId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.status = status;
        this.dateBook = dateBook;
        this.datePrint = datePrint;
    }
            
    
    
    public enum Status {
        FREE, BOOKING, BOOKED, BOUGHT, WITHDRAW
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

    /**
     * @return the seatId
     */
    public int getSeatId() {
        return seatId;
    }

    /**
     * @param seatId the seatId to set
     */
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the dateBook
     */
    public String getDateBook() {
        return dateBook;
    }

    /**
     * @param dateBook the dateBook to set
     */
    public void setDateBook(String dateBook) {
        this.dateBook = dateBook;
    }

    /**
     * @return the datePrint
     */
    public String getDatePrint() {
        return datePrint;
    }

    /**
     * @param datePrint the datePrint to set
     */
    public void setDatePrint(String datePrint) {
        this.datePrint = datePrint;
    }
}
