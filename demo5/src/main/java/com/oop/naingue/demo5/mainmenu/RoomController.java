package com.oop.naingue.demo5.mainmenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RoomController {

    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, String> colRoomId;
    @FXML private TableColumn<Room, String> colRoomNo;
    @FXML private TableColumn<Room, String> colType;
    @FXML private TableColumn<Room, String> colStatus;
    @FXML private TableColumn<Room, Integer> colPrice;

    private final ObservableList<Room> roomList = FXCollections.observableArrayList();
    private final RoomDAO roomDAO = new RoomDAO(); // ✅ Make sure RoomDAO is in same package

    private void initialize() {
        // ✅ Set up table columns
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // ✅ Style "Status" column — Java 8 compatible version
        colStatus.setCellFactory(column -> new TableCell<Room, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);

                    // ✅ Use classic switch for Java 8/11 compatibility
                    switch (status) {
                        case "Available":
                            setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                            break;
                        case "Booked":
                            setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                            break;
                        case "Under Maintenance":
                            setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                            break;
                        case "Capacity Full":
                            setStyle("-fx-text-fill: gray; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("");
                            break;
                    }
                }
            }
        });

        // ✅ Load room data
        loadRoomsFromDatabase();
    }


    private void loadRoomsFromDatabase() {
        roomList.clear();
        List<Room> roomsFromDB = roomDAO.getAllRooms();

        if (roomsFromDB == null || roomsFromDB.isEmpty()) {
            System.out.println("⚠️ No rooms found in MongoDB. Inserting demo data...");
            createDefaultRooms();
            roomsFromDB = roomDAO.getAllRooms();
        }

        roomList.addAll(roomsFromDB);
        roomTable.setItems(roomList);
    }

    // 👇 Create demo data if DB is empty
    private void createDefaultRooms() {
        String[] types = {"Single", "Double", "Suite"};
        int[] prices = {1500, 3000, 5000};
        String[] statuses = {"Available", "Booked", "Under Maintenance", "Capacity Full"};

        for (int floor = 1; floor <= 3; floor++) {
            int roomCount = 1;
            for (int i = 0; i < types.length; i++) {
                for (int j = 0; j < 2; j++) {
                    String roomId = "F" + floor + types[i].charAt(0) + (j + 1);
                    String roomNo = floor + String.format("%02d", roomCount++);
                    String type = types[i];
                    String status = statuses[(int) (Math.random() * statuses.length)];
                    int price = prices[i];

                    roomDAO.addRoom(new Room(roomId, roomNo, type, status, price));
                }
            }
        }
        System.out.println("✅ Sample rooms inserted into MongoDB!");
    }

    // 🔁 Optional: refresh data (e.g., via button in FXML)
    @FXML
    private void refreshRooms() {
        loadRoomsFromDatabase();
    }
}
