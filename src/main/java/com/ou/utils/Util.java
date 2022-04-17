/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.utils;

import javafx.scene.control.TextField;

/**
 *
 * @author CÔNG SANG
 */
public class Util {
    public static boolean checkInfoCustomer(TextField name, TextField phone) {
        String splChrs = "-/@#$%^&_+=()" ;
        if(name.getText().isBlank() || phone.getText().isBlank()) {
            MyAlert.showErrorDialog("Vui lòng nhập đầy đủ thông tin khách hàng.");
            return false;
        } else if(name.getText().matches(".*\\d.*") || name.getText().matches(".*[" + splChrs + "]+.*")) {
            MyAlert.showErrorDialog("Tên không hợp lệ.");
            name.setText("");
            return false;
        } else if(!phone.getText().matches("\\d{10}|\\d{11}")) {
            MyAlert.showErrorDialog("Số điện thoại không hợp lệ.");
            phone.setText("");
            return false;
        }
        return true;
    }
}
