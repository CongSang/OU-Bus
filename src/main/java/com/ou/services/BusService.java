package com.ou.services;

import com.ou.pojo.Bus;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CÔNG SANG
 */
public class BusService {
    
    public static Bus getBusById(int id) throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM bus where id = ?");
            stm.setInt(1, id);
            
            ResultSet rs = stm.executeQuery();
            
            Bus b = null;
            while(rs.next()) {
                String busSeri = rs.getString("bus_serial");
                b = new Bus(id, busSeri);
            }
        return b;
        }
    }
    
    // Kiem tra bus co du seat khong
    public static List<Bus> getBusNotEnoughtSeats() throws SQLException {
        try (Connection conn = Jdbc.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT distinct b.*,"
                    + "(SELECT count(*) FROM seat s WHERE s.bus_id = b.id) as num "
                    + "FROM bus b");
            
            ResultSet rs = stm.executeQuery();
            
            List<Bus> b = new ArrayList<>();
            while(rs.next()) {
                int id = rs.getInt("id");
                String busSeri = rs.getString("bus_serial");
                int seats = rs.getInt("seats");
                int countSeat = rs.getInt("num");
                
                if(countSeat < seats ) {
                    int seatNeedCreate = seats - countSeat;
                    b.add(new Bus(id, busSeri, seatNeedCreate));
                }
            }
        return b;
        }
    }
}
