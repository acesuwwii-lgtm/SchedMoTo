package oop.calihat.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class PaymentController {
    @FXML private TextField amountField;
    @FXML private DatePicker paymentDate;
    @FXML private ComboBox<String> methodBox;
    @FXML private ComboBox<String> statusBox;
    @FXML private TextField receiptField;

    @FXML
    public void initialize() {
        methodBox.getItems().addAll("Cash", "Credit Card", "Gcash", "Paymaya");
        statusBox.getItems().addAll("Pending", "Completed", "Failed");
    }

    @FXML
    private void onProcessPayment() {
        String amount = amountField.getText();
        LocalDate date = paymentDate.getValue();
        String method = methodBox.getValue();
        String status = statusBox.getValue();
        String receipt = receiptField.getText();

        System.out.println("Payment => Amount: " + amount
                + ", Date: " + date
                + ", Method: " + method
                + ", Status: " + status
                + ", Receipt#: " + receipt);
        // add your logic here (process payment, persist, etc.)
    }
}
