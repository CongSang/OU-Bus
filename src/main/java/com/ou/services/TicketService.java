package com.ou.services;

import com.ou.pojo.Seat;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
/**
 *
 * @author CÔNG SANG
 */
public class TicketService {
    
    // Create when app run
    public static int createNewTicket() throws SQLException {
        final int MAX = 1000000;
        String status = "FREE";
        Random rand = new Random();
        try (Connection conn = Jdbc.getConn()) {
            
            List<Seat> seats = SeatService.getAllSeatNoTicket();
            System.out.println("Create Ticket:" + seats.size());
            if (seats != null) {
                for(Seat s : seats) {
                    PreparedStatement stm = conn.prepareStatement("INSERT INTO ticket (id, trip_id"
                            + ", seat_id, status)"
                                + "VALUES (?,?,?,?)");    
                    stm.setInt(1, rand.nextInt(MAX));
                    stm.setInt(2, s.getTripId());
                    stm.setInt(3, s.getId());
                    stm.setString(4, status);           

                    stm.executeUpdate(); 
                }   
            } 
        }
        return 0;
    }
    
    // Khach hang dat ve va chuyen trang thai BOOKED
    public static int createTicketBooking(int tripId, int seatId
            , int customerId, String dateBook) throws SQLException {
        String status = "BOOKING";
               
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE ticket"
                    +" SET customer_id = ?, status = ?, date_book = ? "
                    + " WHERE trip_id = ? AND seat_id = ?");
            stm.setInt(1, customerId);
            stm.setString(2, status);
            stm.setString(3, dateBook);
            stm.setInt(4, tripId);
            stm.setInt(5, seatId);           
            
            return stm.executeUpdate(); 
        }     
    }
}
