/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Admin
 */
public class MyAlert {

    public static Optional<ButtonType> showConfirmDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("X\u00e1c nh\u1eadn");
        alert.setHeaderText(message);
        alert.setContentText("Nh\u1ea5n OK \u0111\u1ec3 ho\u00e0n th\u00e0nh");
        Optional<ButtonType> option = alert.showAndWait();
        return option;
    }

    public static void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Th\u00f4ng tin");
        alert.setHeaderText(message);
        alert.setContentText("TH\u00c0NH C\u00d4NG");
        alert.showAndWait();
    }

    public static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("C\u1ea3nh b\u00e1o");
        alert.setHeaderText(message);
        alert.setContentText("L\u1ed6I");
        alert.showAndWait();
    }
    
}
