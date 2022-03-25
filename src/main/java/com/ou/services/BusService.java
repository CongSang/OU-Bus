package com.ou.services;

import com.ou.pojo.Bus;
import com.ou.utils.Jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
