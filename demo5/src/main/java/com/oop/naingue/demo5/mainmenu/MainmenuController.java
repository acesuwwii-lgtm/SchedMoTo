package com.oop.naingue.demo5.mainmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import java.io.IOException;

public class MainmenuController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        loadUI("DashboardView.fxml"); // optional default view
    }

    @FXML
    private void handleDashboard() {
        loadUI("DashboardView.fxml");
    }

    @FXML
    private void handleRoom() {
        loadUI("Room.fxml");
    }

    @FXML
    private void handleBooking() {
        loadUI("BookingList.fxml");
    }

    @FXML
    private void handlePayment() {
        loadUI("Payment.fxml");
    }

    @FXML
    private void handleAdmin() {
        System.out.println("Admin button clicked!");
    }

    private void loadUI(String fxmlFile) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/oop/calihat/mainmenu/" + fxmlFile));
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Error loading " + fxmlFile + ": " + e.getMessage());
        }
    }
}
