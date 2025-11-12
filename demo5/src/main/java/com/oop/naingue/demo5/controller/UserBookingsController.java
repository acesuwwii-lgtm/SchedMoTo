package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Booking;
import com.oop.naingue.demo5.models.Rooms;
import com.oop.naingue.demo5.repositories.BookingRepository;
import com.oop.naingue.demo5.repositories.RoomsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.Optional;

public class UserBookingsController extends BaseController {

    @FXML private javafx.scene.control.TextField txtFullName;
    @FXML private javafx.scene.control.TextField txtContactInfo;
    @FXML private javafx.scene.control.TextField txtRoomId;
    @FXML private javafx.scene.control.TextField txtRoomNo;
    @FXML private javafx.scene.control.TextField txtRoomType;
    @FXML private javafx.scene.control.DatePicker dpCheckIn;
    @FXML private javafx.scene.control.DatePicker dpCheckOut;

    @FXML private javafx.scene.control.Button btnSave;
    @FXML private javafx.scene.control.Button btnCancel;

    private final BookingRepository bookingRepository = new BookingRepository();
    private final RoomsRepository roomsRepository = new RoomsRepository();

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
            txtContactInfo.setText(currentUser.getPhone());
        }
    }

    @FXML
    private void onSaveBooking() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No user logged in.");
            return;
        }

        if (txtFullName.getText().isEmpty() || txtContactInfo.getText().isEmpty()
                || dpCheckIn.getValue() == null || dpCheckOut.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return;
        }

        Rooms room = roomsRepository.findByRoomId(Integer.parseInt(txtRoomId.getText()));
        if (room != null) {
            String status = Optional.ofNullable(room.getStatus()).orElse("").trim().toLowerCase();
            if (status.equals("booked") || status.equals("under-maintenance")) {
                showAlert(Alert.AlertType.WARNING, "Unavailable",
                        "This room cannot be booked because it is " + room.getStatus());
                return;
            }
        }

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

        btnSave.setDisable(true);

        showAlert(Alert.AlertType.INFORMATION, "Booking Saved", "Booking created successfully.");

        // Pass bookingId to payment controller
        PaymentController paymentController = (PaymentController) app.getController("payment-menu");
        if (paymentController != null) {
            paymentController.setBookingId(booking.getBookingId()); // pass int directly
        }
        app.switchScene("payment-menu");

    }
}
