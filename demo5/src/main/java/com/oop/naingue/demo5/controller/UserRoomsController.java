package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Rooms;
import com.oop.naingue.demo5.repositories.RoomsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

public class UserRoomsController extends BaseController {

    @FXML
    private VBox roomsListContent;

    private final RoomsRepository roomsRepository = new RoomsRepository();

    @FXML
    public void initialize() {
        roomsListContent.getChildren().clear();
        for (Rooms room : roomsRepository.findAll()) {
            roomsListContent.getChildren().add(createRoomCard(room));
        }
    }

    private HBox createRoomCard(Rooms room) {
        HBox card = new HBox();
        card.setSpacing(20);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 10;" +
                " -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        // Room details
        VBox details = new VBox();
        details.setSpacing(5);

        Label roomNumber = new Label("Room Number: " + room.getRoomNumber());
        Label roomType = new Label("Type: " + room.getRoomType());
        Label roomPrice = new Label("Price: $" + room.getRoomPrice());
        Label roomCapacity = new Label("Capacity: " + room.getRoomCapacity());
        Label status = new Label("Status: " + room.getStatus());

        Button bookBtn = new Button("Book");
        bookBtn.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand;");

        // Disable button if room is Booked or Under-Maintenance
        String roomStatus = room.getStatus().trim().toLowerCase();
        if (roomStatus.equals("booked") || roomStatus.equals("under-maintenance")) {
            bookBtn.setDisable(true);
            bookBtn.setText("Unavailable");
            bookBtn.setStyle("-fx-background-color: #d1d5db; -fx-text-fill: #6b7280; -fx-font-weight: bold;" +
                    " -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: default;");
        } else {
            bookBtn.setOnAction(event -> openBooking(room));
        }

        details.getChildren().addAll(roomNumber, roomType, roomPrice, roomCapacity, status, bookBtn);

        card.getChildren().add(details);
        return card;
    }


    private void openBooking(Rooms room) {
        // Switch to booking scene
        app.switchScene("user-booking");

        // Autofill booking form
        UserBookingsController bookingController = (UserBookingsController) app.getController("user-booking");
        if (bookingController != null) {
            bookingController.setRoomForBooking(room);
        }
    }

    @FXML
    private void onLogoutSubmit() {
        app.switchScene("login");
    }

    @FXML
    private void onRoomSubmit() {
        // stay on this scene
    }

    @FXML
    private void onBookingSubmit() {
        app.switchScene("user-booking");
    }

    @FXML
    private void onBookListSubmit() {
        // already here
    }

    @FXML
    private void onPaymentSubmit() {
        app.switchScene("user-payment");
    }
}
