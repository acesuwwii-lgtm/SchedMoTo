package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Booking;
import com.oop.naingue.demo5.repositories.BookingRepository;
import com.oop.naingue.demo5.utils.UserRefreshService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdminBookingsController extends BaseController {

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableColumn<Booking, Integer> bookingIdCol;
    @FXML
    private TableColumn<Booking, String> bookingStatusCol;
    @FXML
    private TableColumn<Booking, Integer> roomIdCol;
    @FXML
    private TableColumn<Booking, Integer> paymentCol;
    @FXML
    private TableColumn<Booking, Object> userIdCol; // Keep as Object or String for display

    private BookingRepository bookingRepository;
    private final ObservableList<Booking> bookingsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        bookingRepository = new BookingRepository();

        // Bind columns
        bookingIdCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getBookingId()).asObject());
        roomIdCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRoomId()).asObject());
        paymentCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getPaymentId()).asObject());
        bookingStatusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBookingStatus()));
        userIdCol.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getUserId()));

        bookingsTable.setItems(bookingsList);

        loadBookings();
    }

    private void loadBookings() {
        bookingsList.clear();
        List<Booking> allBookings = bookingRepository.findAll();
        bookingsList.addAll(allBookings);
    }

    @FXML
    private void onLogoutSubmit() {
        app.switchScene("login");
    }

    @FXML
    private void onUserSubmit() {
        app.switchScene("admin_user");
    }

    @FXML
    private void onBookingSubmit() {
        // Already here
    }

    @FXML
    private void onRoomSubmit() {
        app.switchScene("admin_room");
    }

    @FXML
    private void onPaymentSubmit() {
        app.switchScene("admin_payment");
    }
}
