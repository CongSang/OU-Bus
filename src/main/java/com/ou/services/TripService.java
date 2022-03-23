/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.services;

import com.ou.pojo.Trip;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
    public static List<Trip> getTrip(String kw) throws SQLException, ParseException {
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

                    Trip t = new Trip(rs.getInt(id), rs.getString(from)
                            , rs.getString(to), strDate
                            , rs.getInt(busId), rs.getBoolean(complete));

                    trips.add(t);
                }
            }
   
        
        return trips;
    }
}
