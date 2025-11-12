package com.oop.naingue.demo5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class ReceiptController extends BaseController {

    private String bookingId;
    private String amount;
    private LocalDate paymentDate;
    private String method;
    private String status;

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblReceipt;

    public void setPaymentDetails(String bookingId, String amount, LocalDate paymentDate, String method, String status) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
        this.status = status;

        printReceipt();
    }

    private void printReceipt() {
        System.out.println("----- RECEIPT -----");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + paymentDate);
        System.out.println("Method: " + method);
        System.out.println("Status: " + status);
        System.out.println("-------------------");

        // Optionally, show in UI label
        if (lblReceipt != null) {
            lblReceipt.setText(
                    "Booking ID: " + bookingId + "\n" +
                            "Amount: " + amount + "\n" +
                            "Date: " + paymentDate + "\n" +
                            "Method: " + method + "\n" +
                            "Status: " + status
            );
        }
    }
}
