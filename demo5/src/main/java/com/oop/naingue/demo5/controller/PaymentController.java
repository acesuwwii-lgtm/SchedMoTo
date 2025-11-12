package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PaymentController extends BaseController {

    @FXML
    private TextField txtBookingId;

    @FXML
    private TextField txtAmount;

    @FXML
    private DatePicker dpPaymentDate;

    @FXML
    private ComboBox<String> cmbMethod;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private void onConfirmPayment() {
        System.out.println("Confirm Payment clicked!");
        System.out.println("Booking ID: " + txtBookingId.getText());
        System.out.println("Amount: " + txtAmount.getText());
        System.out.println("Payment Date: " + dpPaymentDate.getValue());
        System.out.println("Payment Method: " + cmbMethod.getValue());
        System.out.println("Payment Status: " + cmbStatus.getValue());

        // Load receipt scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/fxml/Receipt.fxml"));
            AnchorPane receiptPane = loader.load();

            // Pass payment details to ReceiptController
            Object controller = loader.getController();
            if (controller instanceof ReceiptController receiptController) {
                receiptController.setPaymentDetails(
                        txtBookingId.getText(),
                        txtAmount.getText(),
                        dpPaymentDate.getValue(),
                        cmbMethod.getValue(),
                        cmbStatus.getValue()
                );
            }

            Stage stage = new Stage();
            stage.setTitle("Receipt");
            stage.setScene(new Scene(receiptPane));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCancel() {
        System.out.println("Cancel clicked!");
        txtBookingId.clear();
        txtAmount.clear();
        dpPaymentDate.setValue(null);
        cmbMethod.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();
    }

    @FXML
    public void initialize() {
        // Populate combo boxes with dummy data
        cmbMethod.getItems().addAll("Credit Card", "Debit Card", "Cash", "Online");
        cmbStatus.getItems().addAll("Pending", "Completed", "Failed");
    }

    @Override
    public void onSceneShown() {
        System.out.println("Payment scene is now shown.");
    }
}
