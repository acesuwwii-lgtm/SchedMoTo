package com.oop.naingue.demo5.mainmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main Menu Controller - Handles navigation between different views
 */
public class MainmenuController {

    @FXML private StackPane contentArea;
    @FXML private Button btnDashboard;
    @FXML private Button btnRooms;
    @FXML private Button btnBooking;
    @FXML private Button btnPayment;

    @FXML
    public void initialize() {
        System.out.println("✅ MainmenuController initialized");
        // Load default view
        loadUI("DashboardView.fxml");
    }

    @FXML
    private void handleDashboard() {
        System.out.println("📊 Loading Dashboard...");
        loadUI("DashboardView.fxml");
    }

    @FXML
    private void handleRoom() {
        System.out.println("🏨 Loading Rooms...");
        loadUI("Room.fxml");
    }

    @FXML
    private void handleBooking() {
        System.out.println("📅 Loading Booking...");
        loadUI("BookingList.fxml");
    }

    @FXML
    private void handlePayment() {
        System.out.println("💳 Loading Payment...");
        loadUI("Payment.fxml");
    }

    @FXML
    private void handleAdmin() {
        System.out.println("👤 Admin button clicked!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Admin Panel");
        alert.setHeaderText("Admin Panel");
        alert.setContentText("Admin functionality coming soon!");
        alert.showAndWait();
    }

    /**
     * Load FXML file into content area
     */
    private void loadUI(String fxmlFile) {
        try {
            String fullPath = "/com/oop/naingue/demo5/mainmenu/" + fxmlFile;
            System.out.println("🔍 Loading FXML: " + fullPath);

            var resource = getClass().getResource(fullPath);
            if (resource == null) {
                throw new IOException("FXML file not found at: " + fullPath);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Node node = loader.load();

            contentArea.getChildren().setAll(node);
            System.out.println("✅ Successfully loaded: " + fxmlFile);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Failed to load " + fxmlFile + ": " + e.getMessage());
            showErrorPlaceholder(fxmlFile, e.getMessage());
        }
    }


    /**
     * Show placeholder when view is not available
     */
    private void showPlaceholder(String viewName) {
        try {
            javafx.scene.layout.VBox placeholder = new javafx.scene.layout.VBox(20);
            placeholder.setAlignment(javafx.geometry.Pos.CENTER);
            placeholder.setStyle("-fx-padding: 50; -fx-background-color: #f9fafc;");

            javafx.scene.control.Label icon = new javafx.scene.control.Label("📄");
            icon.setStyle("-fx-font-size: 64px;");

            javafx.scene.control.Label titleLabel = new javafx.scene.control.Label(
                    viewName.replace(".fxml", "")
            );
            titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #333;");

            javafx.scene.control.Label messageLabel = new javafx.scene.control.Label(
                    "This view is under construction"
            );
            messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

            placeholder.getChildren().addAll(icon, titleLabel, messageLabel);
            contentArea.getChildren().setAll(placeholder);

        } catch (Exception e) {
            System.err.println("❌ Failed to create placeholder: " + e.getMessage());
        }
    }

    /**
     * Show error placeholder
     */
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

    /**
     * Handle logout
     */
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/oop/naingue/demo5/login-view.fxml")
            );
            Parent loginRoot = loader.load();

            Stage stage = (Stage) contentArea.getScene().getWindow();
            Scene scene = new Scene(loginRoot, 500, 600);
            stage.setScene(scene);
            stage.setTitle("Login - SchedMoTo");
            stage.setResizable(false);
            stage.show();

            System.out.println("✅ Logged out successfully");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Failed to load login view: " + e.getMessage());
        }
    }
}