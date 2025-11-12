package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Booking;
import com.oop.naingue.demo5.models.Rooms;
import com.oop.naingue.demo5.repositories.BookingRepository;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

import java.util.List;

public class UserBookingsController extends BaseController {

    @FXML private TextField txtFullName;
    @FXML private TextField txtContactInfo;
    @FXML private TextField txtRoomId;
    @FXML private TextField txtRoomNo;
    @FXML private TextField txtRoomType;
    @FXML private DatePicker dpCheckIn;
    @FXML private DatePicker dpCheckOut;

    private final BookingRepository bookingRepository = new BookingRepository();

    @FXML
    private void onCancel() {
        app.switchScene("user-booking-list");
    }

    @FXML
    public void setRoomForBooking(Rooms room) {
        if (room != null) {
            txtRoomId.setText(String.valueOf(room.getRoomId()));
            txtRoomNo.setText(String.valueOf(room.getRoomNumber()));
            txtRoomType.setText(room.getRoomType());
        }
        if (currentUser != null) {
            txtContactInfo.setText(currentUser.getPhone()); // or currentUser.getEmail()
        }

    }


    @FXML
    private void onSaveBooking() {
        if (currentUser == null) {
            showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Error", "No user logged in.");
            return;
        }

        // Simple validation
        if (txtFullName.getText().isEmpty()) {
            showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Validation Error", "Full Name is required.");
            return;
        }

        if (txtContactInfo.getText().isEmpty()) {
            showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Validation Error", "Contact Info is required.");
            return;
        }

        if (dpCheckIn.getValue() == null) {
            showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Validation Error", "Check-in date is required.");
            return;
        }

        if (dpCheckOut.getValue() == null) {
            showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Validation Error", "Check-out date is required.");
            return;
        }

        // Create booking
        Booking booking = new Booking();
        booking.setUserId(currentUser.getId());
        booking.setFullName(txtFullName.getText());
        booking.setContactInfo(txtContactInfo.getText());
        booking.setRoomId(Integer.parseInt(txtRoomId.getText()));
        booking.setRoomNumber(txtRoomNo.getText());
        booking.setRoomType(txtRoomType.getText());
        booking.setCheckedInAt(java.sql.Date.valueOf(dpCheckIn.getValue()));
        booking.setCheckedOutAt(java.sql.Date.valueOf(dpCheckOut.getValue()));
        booking.setBookingStatus(Booking.BookingStatus.PENDING);

        bookingRepository.insert(booking);

        showAlert(javafx.scene.control.Alert.AlertType.INFORMATION, "Success", "Booking saved successfully.");
        app.switchScene("user-booking-list", currentUser);


    }

}
