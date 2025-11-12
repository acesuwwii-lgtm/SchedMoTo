package com.oop.naingue.demo5.controller;

import javafx.fxml.FXML;

public class AdminController extends BaseController {

    @FXML
    private void onLogoutSubmit() {
        app.switchScene("login");
    }

    @FXML
    private void onUserSubmit() {
        app.switchScene("admin-user");
    }

    @FXML
    private void onBookingSubmit() {
        app.switchScene("booking");
    }

    @FXML
    private void onRoomSubmit() {
        System.out.println("Room submitted");
    }

    @FXML
    private void onPaymentSubmit() {
        System.out.println("Payment Submitted");
    }

}
