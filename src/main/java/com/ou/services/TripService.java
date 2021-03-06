package com.ou.services;

import com.ou.utils.DateTimeCalc;
import com.ou.pojo.Trip;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TripService {
    static String id = "id";
    static String from = "from";
    static String to = "to";
    static String date = "date_start";
    static String busId = "bus_id";
    static String complete = "complete";
    static String busSerial = "bus_serial";
    
    public static List<Trip> getTrips(String kw) throws SQLException, ParseException {
        List<Trip> trips = new ArrayList<>();
            try (Connection conn = Jdbc.getConn()) {
                PreparedStatement stm = conn.prepareStatement("SELECT * FROM "
                            + "trip WHERE id like concat('%', ?, '%') OR trip.from like "
                            + "concat('%', ?, '%') OR trip.to like concat('%', ?, '%')");

                if (kw == null)
                    kw = "";

                stm.setString(1, kw);
                stm.setString(2, kw);
                stm.setString(3, kw);              
                ResultSet rs = stm.executeQuery();

                while(rs.next()) {
                    Date d = new Date(rs.getTimestamp("date_start").getTime());           
                    String strDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(d);
                    
                    String dateStart = strDate.split(" ")[0];
                    String timeStart = strDate.split(" ")[1];
                    
                    Trip t = new Trip(rs.getInt(id), rs.getString(from)
                            , rs.getString(to), dateStart, timeStart
                            , rs.getInt(busId), rs.getBoolean(complete));

                    trips.add(t);
                }
            }
        return trips;
    }
    
    // for table view customer
    public static List<Trip> getTripForCustomerSearch(String fromSearch
            , String toSearch) throws SQLException, ParseException {
        List<Trip> trips = new ArrayList<>();
            try (Connection conn = Jdbc.getConn()) {
                PreparedStatement stm = conn.prepareStatement("SELECT t.*, b.seats as number"
                        + " FROM trip t, bus b"
                        + " WHERE t.bus_id = b.id AND t.from like concat('%', ?, '%')"
                        + " AND t.to like concat('%', ?, '%') AND complete = ?");

                if (fromSearch == null)
                    fromSearch = "";
                if (toSearch == null)
                    toSearch = "";
             
                stm.setString(1, fromSearch);
                stm.setString(2, toSearch);
                stm.setInt(3, 0); // Chua hoan thanh
                ResultSet rs = stm.executeQuery();

                while(rs.next()) {
                    Date d = new Date(rs.getTimestamp("date_start").getTime());           
                    String strDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(d);
                    
                    String dateStart = strDate.split(" ")[0];
                    String timeStart = strDate.split(" ")[1];

                    Trip t = new Trip(rs.getInt(id), rs.getString(from)
                            , rs.getString(to), dateStart, timeStart, rs.getInt("number")
                            , rs.getInt(busId), rs.getBoolean(complete));

                    trips.add(t);
                }
            }
        return trips;
    }
    
    public static Trip getTripById(int tripId) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM trip"
                    + " where id = ?");
            stm.setInt(1, tripId);
            
            ResultSet rs = stm.executeQuery();
            
            Trip t = null;
            if(rs.next()) {
                Date d = new Date(rs.getTimestamp("date_start").getTime());           
                String strDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(d);
                    
                String dateStart = strDate.split(" ")[0];
                String timeStart = strDate.split(" ")[1];
                    
                t = new Trip(tripId, rs.getString(from)
                        , rs.getString(to), dateStart, timeStart
                        , rs.getInt(busId), rs.getBoolean(complete));
            }      
            return t;
        }     
    }
     
    public static int addTrip(Trip t) throws SQLException {
        try(Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO "
                    + "trip(trip.from, trip.to, date_start, bus_id, complete)"
                    + "VALUES(?,?,?,?,?)");
            
            stm.setString(1, t.getFrom().trim());
            stm.setString(2, t.getTo().trim());
            stm.setString(3, (t.getDate() + " " + t.getTime()).trim());
            stm.setInt(4, t.getBusId());
            stm.setBoolean(5, t.isComplete());
            
            if (stm.executeUpdate() > 0) {
                // Tu tao ve cho moi chuyen di moi duoc them
                TicketService.createNewTicket();
                return 1;
            }
            return 0;

        }
        
    }
    
    public static int deleteTrip(int id) throws SQLException {
        try(Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM trip WHERE id = ?");
            
            stm.setInt(1, id);
            
            return stm.executeUpdate();
        }
    }
    
    public static int updateTrip(Trip t) throws SQLException, ParseException {
        try(Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE trip SET trip.from = ?"
                    + ", trip.to = ?, date_start = ?, bus_id = ?, complete = ? WHERE id = ?");
            
            String strDate = DateTimeCalc.formatyyyyMMdd(t.getDate());
            
            stm.setString(1, t.getFrom().trim());
            stm.setString(2, t.getTo().trim());
            stm.setString(3, (strDate + " " + t.getTime()).trim());
            stm.setInt(4, t.getBusId());
            stm.setBoolean(5, t.isComplete());
            stm.setInt(6, t.getId());
            if (stm.executeUpdate() > 0) {
                // Tu tao ve cho moi chuyen di moi duoc them
                TicketService.createNewTicket();
                return 1;
            }
            return 0;
        }
    }
    
    public static boolean setCompleteTrip() throws SQLException, ParseException {
        Date currentTime = Date.from(Instant.now());
        
        List<Trip> trips = getTrips(null);
        if(!trips.isEmpty()) {
            for(Trip t : trips) {
                String date1 = t.getDate();
                String time = t.getTime();
                Date date2 = DateTimeCalc.formatToDate(date1, time);
                if(DateTimeCalc.timeBetween(date2, currentTime) >= 0) {
                    try (Connection conn = Jdbc.getConn()) {
                        PreparedStatement stm = conn.prepareStatement("UPDATE trip"
                            +" SET complete = ? WHERE id = ?");
                        stm.setInt(1, 1); // Hoan thanh
                        stm.setInt(2, t.getId());

                        stm.executeUpdate(); 
                    }

                }
            }
            return true;
        }
        return false;
    }
}
