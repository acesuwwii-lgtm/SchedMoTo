package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.mainmenu.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.UUID;

public class BookingListController {

    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtContactInfo;
    @FXML
    private DatePicker dpCheckIn;
    @FXML
    private DatePicker dpCheckOut;
    @FXML
    private TextField txtRoomType;
    @FXML
    private TextField txtRoomId;
    @FXML
    private TextField txtRoomNo;

    private Room selectedRoom;
    private final BookingDAO bookingDAO = new BookingDAO();

    @FXML
    public void initialize() {
        // Automatically set checkout 1 day after check-in
        dpCheckIn.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && (dpCheckOut.getValue() == null || !dpCheckOut.getValue().isAfter(newVal))) {
                dpCheckOut.setValue(newVal.plusDays(1));
            }
        });
    }

    public void setSelectedRoom(Room room) {
        this.selectedRoom = room;
        if (room != null) {
            txtRoomId.setText(room.getRoomId());
            txtRoomNo.setText(room.getRoomNo());
            txtRoomType.setText(room.getRoomType());
        }
    }

    public void setUserInfo(String fullName, String contactInfo) {
        txtFullName.setText(fullName);
        txtContactInfo.setText(contactInfo);
    }

    @FXML
    private void onSaveBooking() {
        try {
            if (txtFullName.getText().isEmpty() ||
                    txtContactInfo.getText().isEmpty() ||
                    dpCheckIn.getValue() == null ||
                    dpCheckOut.getValue() == null ||
                    txtRoomType.getText().isEmpty()) {

                showAlert(Alert.AlertType.WARNING, "⚠️ Please fill in all required fields before saving.");
                return;
            }

            // Generate booking ID
            String bookingId = "BKG-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            Booking booking = new Booking();
            booking.setBookingId(bookingId);

            if (CurrentUser.getInstance().isLoggedIn()) {
                booking.setCustomerId(CurrentUser.getInstance().getCustomerId().toString());
            }

            booking.setFullName(txtFullName.getText());
            booking.setContactInfo(txtContactInfo.getText());
            booking.setRoomNo(selectedRoom != null ? selectedRoom.getRoomNumber() : "N/A");
            booking.setRoomType(txtRoomType.getText());
            booking.setCheckInDate(dpCheckIn.getValue());
            booking.setCheckOutDate(dpCheckOut.getValue());

            // Save to DB
            bookingDAO.addBooking(booking);

            showAlert(Alert.AlertType.INFORMATION,
                    "✅ Booking successfully saved!\nBooking ID: " + bookingId);

            // Load Payment.fxml and pass data
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/Payment.fxml"));
            Parent paymentRoot = loader.load();

            PaymentController paymentController = loader.getController();
            paymentController.setPaymentDetails(
                    bookingId,
                    txtRoomId.getText(),
                    selectedRoom != null ? selectedRoom.getPrice() : 0.0
            );

            Stage stage = (Stage) txtFullName.getScene().getWindow();
            stage.setScene(new Scene(paymentRoot));
            stage.setTitle("Payment - SchedMoTo");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "❌ Error saving booking: " + e.getMessage());
        }
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) txtFullName.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        txtFullName.clear();
        txtContactInfo.clear();
        dpCheckIn.setValue(null);
        dpCheckOut.setValue(null);
    }
}
