package com.ou.services;

import com.ou.pojo.Bus;
import com.ou.pojo.Seat;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author CÔNG SANG
 */
public class SeatService {
    
    // Lay ghe chua co ve cua moi chuyen xe
    public static List<Seat> getAllSeatNoTicket() throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT s.id, s.bus_id, t.id as trip_id FROM seat s, trip t"
                        + " WHERE s.bus_id= t.bus_id AND t.complete = ? "
                    + "AND (s.id, t.id) NOT IN (SELECT seat_id, trip_id FROM ticket)");
            stm.setInt(1, 0);
            
            ResultSet rs = stm.executeQuery();
            
            List<Seat> seat = new ArrayList<>();
            while(rs.next()) {
                int id = rs.getInt("id");
                int busId = rs.getInt("bus_id");
                int tripId = rs.getInt("trip_id");
                seat.add(new Seat(id, busId, tripId));
            }
        return seat;
        }
    }
    
    // Lay ghe con trong de khach hang dat ve(FREE)
    public static List<Seat> getSeatEmpty(int busId, int tripId) throws SQLException {
        String empty = "FREE";
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT s.id, s.bus_id"
                    + " FROM seat s, ticket t"
                    + " WHERE s.id = t.seat_id AND s.bus_id = ?"
                    + " AND t.status = ? AND t.trip_id = ?");
            stm.setInt(1, busId);
            stm.setString(2, empty);
            stm.setInt(3, tripId);
            
            ResultSet rs = stm.executeQuery();
            
            List<Seat> s = new ArrayList<>();
            while(rs.next()) {
                int id = rs.getInt("id");
                s.add(new Seat(id, busId));
            }
        return s;
        }
    }
    
//    public static List<Seat> getSeatByBus(int busId) throws SQLException {
//        try (Connection conn = Jdbc.getConn()) {
//            PreparedStatement stm = conn.prepareStatement("SELECT *"
//                    + " FROM seat WHERE bus_id = ?");
//            stm.setInt(1, busId);
//            
//            ResultSet rs = stm.executeQuery();
//            
//            List<Seat> s = new ArrayList<>();
//            while(rs.next()) {
//                int id = rs.getInt("id");
//                s.add(new Seat(id, busId));
//            }
//        return s;
//        }
//    }
    
    // Create when App run
    public static void createSeatOfBus() throws SQLException {
        Random rand = new Random();
        int MAX = 10000;
        try (Connection conn = Jdbc.getConn()) {
            List<Bus> busNeedAddSeat = BusService.getBusNotEnoughtSeats();
            System.out.println("Create seat bus: " + busNeedAddSeat.size());
            for(Bus b : busNeedAddSeat) { 
                System.out.println(b.getBusSerial() + "   " + b.getSeatNumber());
                for(int i = 0; i < b.getSeatNumber(); i++) {
                    int id = b.getId()*100000 + rand.nextInt(MAX);
                    PreparedStatement stm = conn.prepareStatement("INSERT INTO seat "
                    + "(id, bus_id) VALUES(?,?)");
                    stm.setInt(1, id);
                    stm.setInt(2, b.getId());

                    stm.executeUpdate();
                }
            }
        }
    }
    
}
