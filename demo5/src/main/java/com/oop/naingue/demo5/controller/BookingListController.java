package com.oop.naingue.demo5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class BookingListController {

    @FXML private TextField bookingIdField;
    @FXML private TextField customerIdField;
    @FXML private TextField roomIdField;
    @FXML private DatePicker checkInDate;
    @FXML private DatePicker checkOutDate;

    @FXML
    private void onSaveBooking() {
        String bookingId = bookingIdField.getText().trim();
        String customerId = customerIdField.getText().trim();
        String roomId = roomIdField.getText().trim();
        LocalDate checkIn = checkInDate.getValue();
        LocalDate checkOut = checkOutDate.getValue();

        // Validate inputs
        StringBuilder errors = new StringBuilder();

        if (bookingId.isEmpty()) {
            errors.append("Booking ID is required.\n");
        }

        if (customerId.isEmpty()) {
            errors.append("Customer ID is required.\n");
        }

        if (roomId.isEmpty()) {
            errors.append("Room ID is required.\n");
        }

        if (checkIn == null) {
            errors.append("Check-in date is required.\n");
        }

        if (checkOut == null) {
            errors.append("Check-out date is required.\n");
        }

        if (checkIn != null && checkOut != null && checkOut.isBefore(checkIn)) {
            errors.append("Check-out date cannot be before check-in date.\n");
        }

        if (errors.length() > 0) {
            // Show validation errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please fix the following errors:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return; // Keep the form open for corrections
        }

        // If all valid, proceed to save booking
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Booking Saved");
        success.setHeaderText(null);
        success.setContentText("Booking successfully saved:\nBooking ID: " + bookingId);
        success.showAndWait();

        // Optional: clear fields after successful save
        bookingIdField.clear();
        customerIdField.clear();
        roomIdField.clear();
        checkInDate.setValue(null);
        checkOutDate.setValue(null);
    }
}
