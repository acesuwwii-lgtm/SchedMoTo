package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Booking;
import com.oop.naingue.demo5.models.Rooms;
import com.oop.naingue.demo5.repositories.BookingRepository;
import com.oop.naingue.demo5.repositories.RoomsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class ReceiptController extends BaseController {

    @FXML private Label lblReceipt;

    private final BookingRepository bookingRepository = new BookingRepository();
    private final RoomsRepository roomsRepository = new RoomsRepository();

    private int bookingId;

    // Called by PaymentController when switching scene
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
        printReceipt();
    }

    private void printReceipt() {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        if (booking == null) return;

        Rooms room = roomsRepository.findByRoomId(booking.getRoomId());

        StringBuilder receiptText = new StringBuilder();
        receiptText.append("----- RECEIPT -----\n")
                .append("Booking ID: ").append(booking.getBookingId()).append("\n")
                .append("Guest Name: ").append(booking.getFullName()).append("\n")
                .append("Room Number: ").append(booking.getRoomNumber()).append("\n")
                .append("Room Type: ").append(booking.getRoomType()).append("\n")
                .append("Check-In: ").append(booking.getCheckedInAt()).append("\n")
                .append("Check-Out: ").append(booking.getCheckedOutAt()).append("\n");

        if (room != null) {
            receiptText.append("Amount Paid: ").append(room.getRoomPrice()).append("\n");
        }

        receiptText.append("Payment Date: ").append(LocalDate.now()).append("\n")
                .append("Payment Status: ").append(booking.getBookingStatus()).append("\n")
                .append("-------------------");

        System.out.println(receiptText);

        if (lblReceipt != null) {
            lblReceipt.setText(receiptText.toString());
        }
    }
}
