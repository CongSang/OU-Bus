/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.oubusmanager;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author CÔNG SANG
 */
public class DateTimeCalc {
    public static long timeBetween(Date date1, Date date2) {
        try {

            String format = "dd-MM-yyyy hh:mm";

            SimpleDateFormat sdf = new SimpleDateFormat(format);

            sdf.format(date1);
            sdf.format(date2);

            // getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
            long diff = date2.getTime() - date1.getTime();

//            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
//
//            int diffhours = (int) (diff / (60 * 60 * 1000));
//
//            int diffmin = (int) (diff / (60 * 1000));
//
//            int diffsec = (int) (diff / (1000));
            return diff;

        } catch (Exception e) {
                e.printStackTrace();
        }
        return -1;
    }
    
    public static Date formatToDate(String date, String time) throws ParseException {
        String format = "dd-MM-yyyy HH:mm";

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dateFormat = (Date) sdf.parse(date + " " + time);
        return dateFormat;
    }
    
    public static String formatddMMyyyy(String date) throws ParseException {
        String format = "MM-dd-yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dateFormat = (Date) sdf.parse(date);
    
        return new SimpleDateFormat("dd-MM-yyyy").format(dateFormat);
    }
    
    public static String formatyyyyMMdd(String date) throws ParseException {
        String format = "dd-MM-yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dateFormat = (Date) sdf.parse(date);
    
        return new SimpleDateFormat("yyyy-MM-dd").format(dateFormat);
    }
    
}
