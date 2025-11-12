package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Booking;
import com.oop.naingue.demo5.models.Rooms;
import com.oop.naingue.demo5.repositories.BookingRepository;
import com.oop.naingue.demo5.repositories.RoomsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentController extends BaseController {

    @FXML private TextField txtBookingId;
    @FXML private TextField txtAmount;
    @FXML private DatePicker dpPaymentDate;
    @FXML private ComboBox<String> cmbMethod;
    @FXML private ComboBox<String> cmbStatus;

    private int bookingId; // store as int internally

    private final BookingRepository bookingRepository = new BookingRepository();
    private final RoomsRepository roomsRepository = new RoomsRepository();

    // This is now called from UserBookingsController before switching scene
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;

        // Populate fields if FXML is already loaded
        if (txtBookingId != null) {
            txtBookingId.setText(String.valueOf(bookingId));
            populateRoomAndAmount();
        }
    }

    // Load booking and room details to populate amount and booking info
    private void populateRoomAndAmount() {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        if (booking != null) {
            txtBookingId.setText(String.valueOf(booking.getBookingId()));

            Rooms room = roomsRepository.findByRoomId(booking.getRoomId());
            if (room != null) {
                txtAmount.setText(String.valueOf(room.getRoomPrice()));
            }

            dpPaymentDate.setValue(java.time.LocalDate.now());
            cmbMethod.getSelectionModel().selectFirst();
            cmbStatus.getSelectionModel().select("Pending");
        }
    }

    @FXML
    private void onConfirmPayment() {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        if (booking == null) return;

        // Update booking and room status
        booking.setBookingStatus(Booking.BookingStatus.PAID);
        bookingRepository.update(booking.getId(), booking);

        Rooms room = roomsRepository.findByRoomId(booking.getRoomId());
        if (room != null) {
            room.setStatus("Booked");
            roomsRepository.update(room.getRoomId(), room);
        }

        // Show receipt
        Object controller = app.getController("receipt");
        if (controller instanceof ReceiptController receiptController) {
            receiptController.setBookingId(bookingId); // Pass booking ID to receipt
        }
        app.switchScene("receipt");

    }

    @FXML
    private void onCancel() {
        txtBookingId.clear();
        txtAmount.clear();
        dpPaymentDate.setValue(null);
        cmbMethod.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();
        app.switchScene("user-menu");
    }

    @FXML
    public void initialize() {
        cmbMethod.getItems().addAll("Credit Card", "Debit Card", "Cash", "Online");
        cmbStatus.getItems().addAll("Pending", "Completed", "Failed");
    }
}
