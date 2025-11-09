package com.oop.naingue.demo5.mainmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainmenuController {

    @FXML private StackPane contentArea;
    @FXML private Button btnDashboard;
    @FXML private Button btnRooms;
    @FXML private Button btnBooking;
    @FXML private Button btnPayment;
    @FXML private Button btnLogout;

    @FXML
    private void handleRoom() {
        System.out.println("🏨 Loading Rooms...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/Room.fxml"));
            Parent roomView = loader.load();
            contentArea.getChildren().setAll(roomView);
            System.out.println("✅ Room view loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Failed to load Room.fxml: " + e.getMessage());
            showErrorPlaceholder("Room.fxml", e.getMessage());
        }
    }

    @FXML
    private void handleBooking() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/BookingList.fxml"));
            Parent bookingView = loader.load();
            contentArea.getChildren().setAll(bookingView);
            System.out.println("✅ Booking view loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load BookingList.fxml: " + e.getMessage());
            showErrorPlaceholder("BookingList.fxml", e.getMessage());
        }
    }

    @FXML
    private void handlePayment() {
        System.out.println("💰 Loading Payments...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/Payment.fxml"));
            Parent paymentView = loader.load();
            contentArea.getChildren().setAll(paymentView);
            System.out.println("✅ Payment view loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Failed to load Payment.fxml: " + e.getMessage());
            showErrorPlaceholder("Payment.fxml", e.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        System.out.println("🚪 Logout button clicked");

        // Show confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Logout Confirmation");
        confirmAlert.setHeaderText("Are you sure you want to logout?");
        confirmAlert.setContentText("You will be redirected to the login screen.");

        // Customize button text
        ButtonType yesButton = new ButtonType("Yes, Logout");
        ButtonType cancelButton = new ButtonType("Cancel");
        confirmAlert.getButtonTypes().setAll(yesButton, cancelButton);

        // Show dialog and wait for response
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            performLogout();
        } else {
            System.out.println("❌ Logout cancelled by user");
        }
    }

    private void performLogout() {
        try {
            System.out.println("🔄 Logging out...");

            // Load login view (✅ FIXED: added leading slash)
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/oop/naingue/demo5/login-view.fxml")
            );
            Parent loginRoot = loader.load();

            // Get current stage
            Stage stage = (Stage) contentArea.getScene().getWindow();

            // Create new scene with login view
            Scene scene = new Scene(loginRoot, 500, 600);
            stage.setScene(scene);
            stage.setTitle("Login - SchedMoTo");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

            System.out.println("✅ Logged out successfully!");

            // Show success message (optional)
            showSuccessNotification("Logged out successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Failed to load login view: " + e.getMessage());
        }
    }

    private void showErrorPlaceholder(String viewName, String errorMessage) {
        try {
            javafx.scene.layout.VBox placeholder = new javafx.scene.layout.VBox(20);
            placeholder.setAlignment(javafx.geometry.Pos.CENTER);
            placeholder.setStyle("-fx-padding: 50; -fx-background-color: #ffebee;");

            javafx.scene.control.Label icon = new javafx.scene.control.Label("❌");
            icon.setStyle("-fx-font-size: 64px;");

            javafx.scene.control.Label titleLabel = new javafx.scene.control.Label(
                    "Error Loading " + viewName
            );
            titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #d32f2f;");

            javafx.scene.control.Label messageLabel = new javafx.scene.control.Label(
                    errorMessage
            );
            messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
            messageLabel.setWrapText(true);
            messageLabel.setMaxWidth(400);

            placeholder.getChildren().addAll(icon, titleLabel, messageLabel);
            contentArea.getChildren().setAll(placeholder);

        } catch (Exception e) {
            System.err.println("❌ Failed to create error placeholder: " + e.getMessage());
        }
    }

    private void showSuccessNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();

        // Auto-close after 2 seconds (optional)
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                javafx.application.Platform.runLater(() -> alert.close());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}