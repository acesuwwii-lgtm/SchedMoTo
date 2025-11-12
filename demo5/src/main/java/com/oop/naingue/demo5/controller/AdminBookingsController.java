package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Booking;
import com.oop.naingue.demo5.repositories.BookingRepository;
import javafx.beans.property.SimpleIntegerProperty;
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
    private TableColumn<Booking, String> userIdCol;
    @FXML
    private TableColumn<Booking, String> fullNameCol;
    @FXML
    private TableColumn<Booking, String> contactInfoCol;
    @FXML
    private TableColumn<Booking, String> roomTypeCol;
    @FXML
    private TableColumn<Booking, String> checkInCol;
    @FXML
    private TableColumn<Booking, String> checkOutCol;

    private BookingRepository bookingRepository;
    private final ObservableList<Booking> bookingsList = FXCollections.observableArrayList();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @FXML
    public void initialize() {
        bookingRepository = new BookingRepository();

        // Bind table columns
        bookingIdCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getBookingId()).asObject());
        userIdCol.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getUserId() != null ? c.getValue().getUserId().toHexString() : "N/A"));
        roomIdCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRoomId()).asObject());
        paymentCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getPaymentId()).asObject());
        bookingStatusCol.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getBookingStatus() != null
                                ? c.getValue().getBookingStatus().name().charAt(0)
                                + c.getValue().getBookingStatus().name().substring(1).toLowerCase()
                                : "N/A"
                )
        );

        // Additional columns (mocked or joined data depending on your model)
        fullNameCol.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getFullName() != null ? c.getValue().getFullName() : "N/A"));
        contactInfoCol.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getContactInfo() != null ? c.getValue().getContactInfo() : "N/A"));
        roomTypeCol.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getRoomType() != null ? c.getValue().getRoomType() : "N/A"));

        checkInCol.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getCheckedInAt() != null ? dateFormat.format(c.getValue().getCheckedInAt()) : "N/A"));
        checkOutCol.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getCheckedOutAt() != null ? dateFormat.format(c.getValue().getCheckedOutAt()) : "N/A"));

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
