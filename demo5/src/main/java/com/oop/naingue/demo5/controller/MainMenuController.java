package com.oop.naingue.demo5.controller;

import javafx.fxml.FXML;

public class MainMenuController extends BaseController {
    @FXML
    private void onLogoutSubmit() {
        app.switchScene("login");
    }

    @FXML
    private void onRoomSubmit() {
        System.out.println("Switching to Button");
    }

    @FXML
    private void onBookingSubmit() {
        System.out.println("Switching to Button");
    }

    @FXML
    private void onPaymentSubmit() {
        System.out.println("Switching to Button");
    }
}
