package com.ou.services;

import com.ou.utils.DateTimeCalc;
import com.ou.pojo.Seat;
import com.ou.pojo.Ticket;
import com.ou.pojo.Trip;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
/**
 *
 * @author CÔNG SANG
 */
public class TicketService {
    static long millis30min = 30 * 60 * 1000;
    static long millis5min = 5 * 60 * 1000;
    
    // Create when app run
    public static int createNewTicket() throws SQLException {
        final int MAX = 1000000;
        int rows = 0;
        String status = "FREE";
        Random rand = new Random();
        try (Connection conn = Jdbc.getConn()) {
            
            List<Seat> seats = SeatService.getAllSeatNoTicket();
            System.out.println("Create Ticket:" + seats.size());
            if (!seats.isEmpty()) {
                for(Seat s : seats) {
                    PreparedStatement stm = conn.prepareStatement("INSERT INTO ticket (id, trip_id"
                            + ", seat_id, status)"
                                + "VALUES (?,?,?,?)");    
                    stm.setInt(1, rand.nextInt(MAX));
                    stm.setInt(2, s.getTripId());
                    stm.setInt(3, s.getId());
                    stm.setString(4, status); 
                    rows++;

                    stm.executeUpdate(); 
                } 
                return rows;
            } 
        }
        return rows;
    }
    
    // Khach hang dat ve va chuyen trang thai BOOKED
    public static int createTicketBooked(int tripId, int seatId
            , int customerId, int employeeId, String dateBook) throws SQLException {
        String status = "BOOKED";
               
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE ticket"
                    +" SET customer_id = ?, employee_id = ?, status = ?, date_book = ? "
                    + " WHERE trip_id = ? AND seat_id = ?");
            stm.setInt(1, customerId);
            stm.setInt(2, employeeId);
            stm.setString(3, status);
            stm.setString(4, dateBook);
            stm.setInt(5, tripId);
            stm.setInt(6, seatId);           
            
            return stm.executeUpdate(); 
        }     
    }
    
    // Ve trong neu con 30p xe chay nhung khach hang khong lay hoac huy ve
    public static int createTicketFree(int tripId) throws SQLException {
        String status = "FREE";
               
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE ticket"
                    +" SET customer_id = ?, status = ?, date_book = ? "
                    + " WHERE trip_id = ? AND status = ?");
            stm.setString(1, null);
            stm.setString(2, status);
            stm.setString(3, null);
            stm.setInt(4, tripId);
            stm.setString(5, "BOOKED");
            
            return stm.executeUpdate(); 
        }     
    }
    
    // Trong vong 5p truoc khi xe khoi hanh ve FREE se chuyen ve trang thai WITHDRAW 
    public static int createTicketWithDraw(int tripId) throws SQLException {
        String status = "WITHDRAW";
               
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE ticket"
                    + " SET status = ?"
                    + " WHERE trip_id = ? AND status = ?");
            stm.setString(1, status);
            stm.setInt(2, tripId);
            stm.setString(3, "FREE");
            
            return stm.executeUpdate(); 
        }     
    }
    
    //Lay tat ca ve cua 1 chuyen
    public static List<Ticket> getTicketByTrip(int tripId) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT *"
                    + " FROM ticket"
                    + " WHERE trip_id = ?");
            stm.setInt(1, tripId);
            
            ResultSet rs = stm.executeQuery();
            
            List<Ticket> tickets = new ArrayList<>();
            while(rs.next()) {
                int id = rs.getInt("id");
                int seatId = rs.getInt("seat_id");
                int customerId = rs.getInt("customer_id");
                int employeeId = rs.getInt("employee_id");
                String dateBook = rs.getString("date_book");
                String datePrint = rs.getString("date_print");
                tickets.add(new Ticket(id, tripId, seatId, customerId, employeeId, dateBook, datePrint));
            }
        return tickets;
        }
    }
    
    // Chuyen ve khach hang da dat ma khong lay truoc 30p xe chay
    public static boolean setTicketFree30minLeft () throws SQLException, ParseException {
        Date currentTime = Date.from(Instant.now());
        
        List<Trip> trips = TripService.getTripForCustomerSearch(null, null);
        
        if(!trips.isEmpty()) {
            for (Trip t : trips) {
                String date = t.getDate();
                String time = t.getTime();
                Date date1 = DateTimeCalc.formatToDate(date, time);
                long db = DateTimeCalc.timeBetween(currentTime, date1);
                if(db <= millis30min && db > millis5min) {
                    createTicketFree(t.getId());
                }
            }
            return true;
        }
        return false;
    }
    
    // Chuyen ve trong (FREE) ve trang thai thu hoi (WITHDRAW)
    public static boolean setTicketWithDraw5minLeft () throws SQLException, ParseException {
        Date currentTime = Date.from(Instant.now());
        
        List<Trip> trips = TripService.getTripForCustomerSearch(null, null);
        
        if(!trips.isEmpty()) {
            for (Trip t : trips) {
                String date = t.getDate();
                String time = t.getTime();
                Date date1 = DateTimeCalc.formatToDate(date, time);
                long db = DateTimeCalc.timeBetween(currentTime, date1);
                if(db <= millis5min && db > 0) {
                    createTicketWithDraw(t.getId());
                }
            }
            return true;
        }
        return false;
    }
    
    public static List<Ticket> getBooketTickets(String kw) throws SQLException {
        List<Ticket> tks = new ArrayList<>();
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT ticket.id, trip_id, seat_id, customer_id"
                    + " FROM ticket join user on ticket.customer_id = user.id "
                    + " WHERE status = 'BOOKED' AND (name like concat('%', ?, '%')"
                    + " OR phone like concat('%', ?, '%'));");
            
            if(kw == null)
                kw = "";
            
            stm.setString(1, kw);
            stm.setString(2, kw);
            
            ResultSet rs = stm.executeQuery();
            
            while(rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("ticket.id"));
                t.setTripId(rs.getInt("trip_id"));
                t.setSeatId(rs.getInt("seat_id"));
                t.setCustomerId(rs.getInt("customer_id"));
                
                tks.add(t);
            }
        }
        
        return tks;
    }
    
    public static int setTicketFree(int ticketId) throws SQLException {
        try(Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE ticket"
                    + " SET customer_id=null, employee_id=null"
                    + ", status='FREE', date_book=null WHERE id=?");
            stm.setInt(1, ticketId);
            return stm.executeUpdate();
        }
    }
}
