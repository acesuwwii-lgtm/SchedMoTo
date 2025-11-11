package com.oop.naingue.demo5.mainmenu;

import com.oop.naingue.demo5.controller.BookingListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class RoomController {

    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, String> colRoomId;
    @FXML private TableColumn<Room, String> colRoomNo;
    @FXML private TableColumn<Room, String> colType;
    @FXML private TableColumn<Room, String> colStatus;
    @FXML private TableColumn<Room, Double> colPrice;

    private final ObservableList<Room> roomList = FXCollections.observableArrayList();
    private RoomDAO roomDAO;

    @FXML
    private void initialize() {
        roomDAO = new RoomDAO();


        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        loadRooms();


        roomTable.setOnMouseClicked(this::handleRoomClick);
    }

    private void loadRooms() {
        roomList.clear();
        List<Room> rooms = roomDAO.getAllRooms();
        if (rooms != null) roomList.addAll(rooms);
        roomTable.setItems(roomList);
    }

    private void handleRoomClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Room selected = roomTable.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            String status = selected.getStatus();


            if (!"Available".equalsIgnoreCase(status)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Room Unavailable");
                alert.setHeaderText("Cannot book this room");
                alert.setContentText("Room " + selected.getRoomNo() + " is currently: " + status);
                alert.getButtonTypes().setAll(javafx.scene.control.ButtonType.CLOSE);
                alert.showAndWait();
                return;
            }


            openBookingList(selected);
        }
    }

    private void openBookingList(Room room) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/BookingList.fxml"));
            Parent root = loader.load();

            BookingListController bookingController = loader.getController();
            bookingController.setSelectedRoom(room);


            if (CurrentUser.getInstance().isLoggedIn()) {
                bookingController.setUserInfo(
                        CurrentUser.getInstance().getFullName(),
                        CurrentUser.getInstance().getContactInfo()
                );
            }


            bookingController.setSelectedRoom(room);


            Stage stage = new Stage();
            stage.setTitle("Booking Information");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to load booking page");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }
}
