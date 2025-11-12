package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Booking;
import com.oop.naingue.demo5.repositories.BookingRepository;
import com.oop.naingue.demo5.utils.BookingRefreshService;
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

    @FXML private TableView<Booking> bookingsTable;
    @FXML private TableColumn<Booking, Integer> bookingIdCol;
    @FXML private TableColumn<Booking, String> bookingStatusCol;
    @FXML private TableColumn<Booking, Integer> roomIdCol;
    @FXML private TableColumn<Booking, Integer> paymentCol;
    @FXML private TableColumn<Booking, String> userIdCol;
    @FXML private TableColumn<Booking, String> fullNameCol;
    @FXML private TableColumn<Booking, String> contactInfoCol;
    @FXML private TableColumn<Booking, String> roomTypeCol;
    @FXML private TableColumn<Booking, String> checkInCol;
    @FXML private TableColumn<Booking, String> checkOutCol;

    private BookingRepository bookingRepository;
    private final ObservableList<Booking> bookingsList = FXCollections.observableArrayList();
    private BookingRefreshService refreshService;
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
                                ? capitalize(c.getValue().getBookingStatus().name())
                                : "N/A"
                )
        );

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

        // Load immediately
        loadBookings();

        // Start periodic refresh (every 1 minute, can adjust)
        refreshService = new BookingRefreshService(bookingRepository, bookingsList);
        refreshService.start(0, 1); // initial delay 0 minutes, period 1 minute
    }

    private void loadBookings() {
        List<Booking> allBookings = bookingRepository.findAll();
        bookingsList.setAll(allBookings);
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return "";
        return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
    }

    @FXML
    private void onLogoutSubmit() {
        stopRefresh();
        app.switchScene("login");
    }

    @FXML
    private void onUserSubmit() {
        stopRefresh();
        app.switchScene("admin-user");
    }

    @FXML
    private void onBookingSubmit() {
        // Already here
    }

    @FXML
    private void onRoomSubmit() {
        stopRefresh();
        app.switchScene("admin-rooms");
    }

    @FXML
    private void onPaymentSubmit() {
        stopRefresh();
        app.switchScene("admin_payment");
    }

    private void stopRefresh() {
        if (refreshService != null) {
            refreshService.stop();
        }
    }
}
