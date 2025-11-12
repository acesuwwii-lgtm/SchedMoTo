package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Rooms;
import com.oop.naingue.demo5.repositories.RoomsRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class AdminRoomsController extends BaseController {

    @FXML private TableView<Rooms> roomsTable;
    @FXML private TableColumn<Rooms, Integer> roomIdCol;
    @FXML private TableColumn<Rooms, Integer> roomNumberCol;
    @FXML private TableColumn<Rooms, String> roomTypeCol;
    @FXML private TableColumn<Rooms, Double> roomPriceCol;
    @FXML private TableColumn<Rooms, String> statusCol;
    @FXML private TableColumn<Rooms, Integer> roomCapacityCol;

    @FXML private TextField roomNumberField;
    @FXML private TextField roomTypeField;
    @FXML private TextField roomPriceField;
    @FXML private TextField statusField;
    @FXML private TextField roomCapacityField;

    private final RoomsRepository roomsRepository = new RoomsRepository();
    private final ObservableList<Rooms> roomsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Bind columns
        roomIdCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRoomId()).asObject());
        roomNumberCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRoomNumber()).asObject());
        roomTypeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRoomType()));
        roomPriceCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getRoomPrice()).asObject());
        statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        roomCapacityCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRoomCapacity()).asObject());

        loadRooms();
        roomsTable.setItems(roomsList);

        // Click table row to populate fields for edit
        roomsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) populateFields(newSel);
        });
    }

    private void loadRooms() {
        roomsList.setAll(roomsRepository.findAll());
    }

    private void populateFields(Rooms room) {
        roomNumberField.setText(String.valueOf(room.getRoomNumber()));
        roomTypeField.setText(room.getRoomType());
        roomPriceField.setText(String.valueOf(room.getRoomPrice()));
        statusField.setText(room.getStatus());
        roomCapacityField.setText(String.valueOf(room.getRoomCapacity()));
    }

    private int generateRoomId() {
        List<Rooms> allRooms = roomsRepository.findAll();
        return allRooms.stream()
                .mapToInt(Rooms::getRoomId)
                .max()
                .orElse(0) + 1;  // start from 1 if no rooms
    }

    @FXML
    private void onAddSubmit() {
        try {
            Rooms room = new Rooms();
            room.setRoomId(generateRoomId()); // assign new sequential roomId
            room.setRoomNumber(Integer.parseInt(roomNumberField.getText()));
            room.setRoomType(roomTypeField.getText());
            room.setRoomPrice(Double.parseDouble(roomPriceField.getText()));
            room.setStatus(statusField.getText());
            room.setRoomCapacity(Integer.parseInt(roomCapacityField.getText()));

            roomsRepository.insert(room);
            loadRooms();
            clearFields();
        } catch (Exception e) {
            showError("Invalid input. Please check all fields.");
        }
    }

    @FXML
    private void onEditSubmit() {
        Rooms selected = roomsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("No room selected.");
            return;
        }
        try {
            selected.setRoomNumber(Integer.parseInt(roomNumberField.getText()));
            selected.setRoomType(roomTypeField.getText());
            selected.setRoomPrice(Double.parseDouble(roomPriceField.getText()));
            selected.setStatus(statusField.getText());
            selected.setRoomCapacity(Integer.parseInt(roomCapacityField.getText()));

            roomsRepository.update(selected.getRoomId(), selected);
            loadRooms();
            clearFields();
        } catch (Exception e) {
            showError("Invalid input. Please check all fields.");
        }
    }

    @FXML
    private void onDelSubmit() {
        Rooms selected = roomsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("No room selected.");
            return;
        }
        roomsRepository.deleteByRoomId(selected.getRoomId());
        loadRooms();
        clearFields();
    }

    private void clearFields() {
        roomNumberField.clear();
        roomTypeField.clear();
        roomPriceField.clear();
        statusField.clear();
        roomCapacityField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML private void onLogoutSubmit() { app.switchScene("login"); }
    @FXML private void onUserSubmit() { app.switchScene("admin-user"); }
    @FXML private void onBookingSubmit() { app.switchScene("admin-booking"); }
    @FXML private void onRoomSubmit() { /* Already here */ }
    @FXML private void onPaymentSubmit() { app.switchScene("admin_payment"); }
}
