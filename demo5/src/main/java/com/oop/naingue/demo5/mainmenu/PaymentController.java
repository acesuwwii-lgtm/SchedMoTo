package com.oop.naingue.demo5.mainmenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class PaymentController {

    @FXML private TextField txtBookingId;
    @FXML private TextField txtRoomId;
    @FXML private TextField txtAmount;
    @FXML private DatePicker dpPaymentDate;
    @FXML private ComboBox<String> cmbMethod;
    @FXML private Button confirmButton;
    @FXML private Button btnCancel;

    private double totalAmount = 0;

    @FXML
    public void initialize() {
        cmbMethod.getItems().addAll("GCash", "PayMaya", "Credit Card", "Debit Card");
        dpPaymentDate.setValue(LocalDate.now());
        txtBookingId.setEditable(false);
        txtRoomId.setEditable(false);
        txtAmount.setEditable(false);
    }

    public void setPaymentDetails(String bookingId, String roomId, double price) {
        txtBookingId.setText(bookingId);
        txtRoomId.setText(roomId);
        txtAmount.setText(String.valueOf(price));
        dpPaymentDate.setValue(java.time.LocalDate.now());
    }

    @FXML
    private void onConfirmPayment(ActionEvent event) {
        String bookingId = txtBookingId.getText();
        String roomId = txtRoomId.getText();
        String method = cmbMethod.getValue();
        LocalDate date = dpPaymentDate.getValue();

        if (bookingId.isEmpty() || roomId.isEmpty() || method.isEmpty() || date == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Information",
                    "Please complete all payment fields before confirming.");
            return;
        }

        String message = String.format(
                "Payment Recorded Successfully!\n\nBooking ID: %s\nRoom ID: %s\nMethod: %s\nAmount: ₱%s\nDate: %s",
                bookingId, roomId, method, txtAmount.getText(), date
        );

        showAlert(Alert.AlertType.INFORMATION, "Payment Successful", message);
    }

    @FXML
    private void onCancel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/Mainmenu.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Room Management");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error",
                    "Unable to return to the Room page:\n" + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}