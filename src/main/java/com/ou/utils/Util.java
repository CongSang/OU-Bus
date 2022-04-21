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
    static String splChrs = "-/@#$%^&_+=()" ;
    public static boolean checkInfoCustomer(TextField name, TextField phone) {
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
    
    public static boolean checkUsername(TextField name) {
        if(!(name.getText().toCharArray().length >= 8 && name.getText().toCharArray().length <= 15)) {
            MyAlert.showErrorDialog("Tài khoản không hợp lệ. Độ dài từ 8-15 kí tự");
            name.setText("");
            return false;
        }
        return true;
    }
    
    public static boolean checkPass(TextField pass) {
        if(!(pass.getText().toCharArray().length >= 8 && pass.getText().toCharArray().length <= 15)) {
            MyAlert.showErrorDialog("Mật khẩu không hợp lệ. Độ dài từ 8-15 kí tự");
            pass.setText("");
            return false;
        }
        return true;
    }
    
    public static boolean checkAge(TextField age) {
        if(!age.getText().matches("\\d{1}|\\d{2}")) {
            MyAlert.showErrorDialog("Tuổi không hợp lệ.");
            age.setText("");
            return false;
        }
        return true;
    }
    
    
}
