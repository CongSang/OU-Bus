/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.test01;

import com.ou.utils.DateTimeCalc;
import java.text.ParseException;
import java.util.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author CÔNG SANG
 */
public class CalcDateTimeTestSuite {
    @Test
    public void testDateTimeCalc() {
        Date date1 = new Date(2022, 3, 30, 11, 00);
        Date date2 = new Date(2022, 3, 30, 11, 30);
        long expexted = 30 * 60 * 1000;
        
        long actual = DateTimeCalc.timeBetween(date1, date2);
        
        Assertions.assertEquals(expexted, actual);
    }
    
    @ParameterizedTest
    @CsvSource({"30-3-2022, 15:00", "31-3-2021, 16:00"})
    public void testFormatToDate(String date, String time) throws ParseException {
        Date d = DateTimeCalc.formatToDate(date, time);
        
        Assertions.assertInstanceOf(Date.class, d);
    }
    
    @ParameterizedTest
    @CsvSource({"03-15-2022"})
    public void testFormatddMMyyyy(String date) throws ParseException {
        String expexted = "15-03-2022";
        String actual = DateTimeCalc.formatddMMyyyy(date);
        Assertions.assertEquals(expexted, actual);
    }
    
    @ParameterizedTest
    @CsvSource({"15-03-2022"})
    public void testFormatyyyyMMdd(String date) throws ParseException {
        String expexted = "2022-03-15";
        String actual = DateTimeCalc.formatyyyyMMdd(date);
        Assertions.assertEquals(expexted, actual);
    }
}
