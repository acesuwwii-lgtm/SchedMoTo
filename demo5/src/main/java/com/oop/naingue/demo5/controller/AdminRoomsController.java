package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Rooms;
import com.oop.naingue.demo5.repositories.RoomsRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminRoomsController extends BaseController {

    @FXML private TableView<Rooms> roomsTable;
    @FXML private TableColumn<Rooms, Integer> roomIdCol;
    @FXML private TableColumn<Rooms, Integer> roomNumberCol;
    @FXML private TableColumn<Rooms, String> roomTypeCol;
    @FXML private TableColumn<Rooms, Double> roomPriceCol;
    @FXML private TableColumn<Rooms, String> statusCol;
    @FXML private TableColumn<Rooms, Integer> roomCapacityCol;

    private final RoomsRepository roomsRepository = new RoomsRepository();
    private final ObservableList<Rooms> roomsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        roomIdCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRoomId()).asObject());
        roomNumberCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRoomNumber()).asObject());
        roomTypeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRoomType()));
        roomPriceCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getRoomPrice()).asObject());
        statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        roomCapacityCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRoomCapacity()).asObject());

        roomsList.setAll(roomsRepository.findAll());
        roomsTable.setItems(roomsList);
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
       app.switchScene("admin-booking");
    }

    @FXML
    private void onRoomSubmit() {
        // Already here
    }

    @FXML
    private void onPaymentSubmit() {
        app.switchScene("admin_payment");
    }
}
