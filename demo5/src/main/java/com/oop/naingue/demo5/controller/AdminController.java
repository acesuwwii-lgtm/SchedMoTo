package com.oop.naingue.demo5.controller;

import com.mongodb.client.*;
import com.oop.naingue.demo5.mainmenu.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bson.Document;

import java.time.LocalDate;
import java.util.Optional;

public class AdminController {

    // Sidebar Buttons
    @FXML private Button btnDashboard;
    @FXML private Button btnManageRooms;
    @FXML private Button btnManageBookings;
    @FXML private Button btnManageUsers;
    @FXML private Button btnPayments;
    @FXML private Button btnReports;
    @FXML private Label lblAdminName;

    // Views
    @FXML private VBox dashboardView;
    @FXML private VBox manageRoomsView;
    @FXML private VBox manageBookingsView;
    @FXML private VBox manageUsersView;
    @FXML private VBox paymentsView;
    @FXML private VBox reportsView;

    // Dashboard Stats
    @FXML private Label lblTotalRooms;
    @FXML private Label lblActiveBookings;
    @FXML private Label lblTotalUsers;
    @FXML private Label lblTotalRevenue;
    @FXML private TableView<BookingData> tblRecentBookings;
    @FXML private TableColumn<BookingData, String> colBookingId;
    @FXML private TableColumn<BookingData, String> colCustomerName;
    @FXML private TableColumn<BookingData, String> colRoomNo;
    @FXML private TableColumn<BookingData, String> colCheckIn;
    @FXML private TableColumn<BookingData, String> colCheckOut;
    @FXML private TableColumn<BookingData, String> colStatus;

    // Rooms Table
    @FXML private TableView<RoomData> tblRooms;
    @FXML private TableColumn<RoomData, String> colRoomId;
    @FXML private TableColumn<RoomData, String> colRoomNumber;
    @FXML private TableColumn<RoomData, String> colRoomType;
    @FXML private TableColumn<RoomData, String> colRoomStatus;
    @FXML private TableColumn<RoomData, Double> colRoomPrice;
    @FXML private TableColumn<RoomData, Void> colRoomActions;

    // Bookings Table
    @FXML private ComboBox<String> cmbBookingFilter;
    @FXML private TableView<BookingData> tblBookings;
    @FXML private TableColumn<BookingData, String> colBookId;
    @FXML private TableColumn<BookingData, String> colBookCustomer;
    @FXML private TableColumn<BookingData, String> colBookRoom;
    @FXML private TableColumn<BookingData, String> colBookCheckIn;
    @FXML private TableColumn<BookingData, String> colBookCheckOut;
    @FXML private TableColumn<BookingData, String> colBookStatus;
    @FXML private TableColumn<BookingData, Void> colBookActions;

    // Users Table
    @FXML private TableView<UserData> tblUsers;
    @FXML private TableColumn<UserData, String> colUsername;
    @FXML private TableColumn<UserData, String> colEmail;
    @FXML private TableColumn<UserData, String> colPhone;
    @FXML private TableColumn<UserData, String> colAddress;
    @FXML private TableColumn<UserData, String> colUserType;
    @FXML private TableColumn<UserData, Void> colUserActions;

    // Payments Table
    @FXML private TableView<PaymentData> tblPayments;
    @FXML private TableColumn<PaymentData, String> colPayBookingId;
    @FXML private TableColumn<PaymentData, String> colPayRoomNo;
    @FXML private TableColumn<PaymentData, Double> colPayAmount;
    @FXML private TableColumn<PaymentData, String> colPayMethod;
    @FXML private TableColumn<PaymentData, String> colPayDate;
    @FXML private TableColumn<PaymentData, String> colPayStatus;
    @FXML private TableColumn<PaymentData, String> colPayReceipt;

    // Reports
    @FXML private TextArea txtReportArea;

    private MongoDatabase database;

    @FXML
    public void initialize() {
        database = DatabaseConnection.getDatabase();
        lblAdminName.setText("Admin Panel");

        setupTables();
        cmbBookingFilter.setItems(FXCollections.observableArrayList(
                "All", "Pending", "Confirmed", "Cancelled", "Completed"
        ));
        cmbBookingFilter.setValue("All");

        loadDashboardStats();
        System.out.println("✅ Admin Dashboard initialized");
    }

    private void setupTables() {
        // Dashboard Recent Bookings
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Rooms Table
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colRoomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRoomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        colRoomActions.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox buttons = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-padding: 5 10;");
                deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 5 10;");
                buttons.setAlignment(Pos.CENTER);

                editBtn.setOnAction(e -> editRoom(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> deleteRoom(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttons);
            }
        });

        // Bookings Table
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colBookCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colBookRoom.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colBookCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colBookCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        colBookStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colBookActions.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<String> statusCombo = new ComboBox<>(
                    FXCollections.observableArrayList("Pending", "Confirmed", "Cancelled", "Completed")
            );
            private final Button updateBtn = new Button("Update");
            private final HBox box = new HBox(5, statusCombo, updateBtn);

            {
                box.setAlignment(Pos.CENTER);
                updateBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 10;");
                updateBtn.setOnAction(e -> {
                    BookingData data = getTableView().getItems().get(getIndex());
                    updateBookingStatus(data.getBookingId(), statusCombo.getValue());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    BookingData data = getTableView().getItems().get(getIndex());
                    statusCombo.setValue(data.getStatus());
                    setGraphic(box);
                }
            }
        });

        // Users Table
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colUserType.setCellValueFactory(new PropertyValueFactory<>("userType"));

        colUserActions.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 5 15;");
                deleteBtn.setOnAction(e -> deleteUser(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });

        // Payments Table
        colPayBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colPayRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colPayAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPayMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        colPayDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPayStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPayReceipt.setCellValueFactory(new PropertyValueFactory<>("receiptNo"));
    }

    // Navigation Methods
    @FXML
    private void showDashboard() {
        hideAllViews();
        dashboardView.setVisible(true);
        loadDashboardStats();
    }

    @FXML
    private void showManageRooms() {
        hideAllViews();
        manageRoomsView.setVisible(true);
        loadRooms();
    }

    @FXML
    private void showManageBookings() {
        hideAllViews();
        manageBookingsView.setVisible(true);
        loadBookings();
    }

    @FXML
    private void showManageUsers() {
        hideAllViews();
        manageUsersView.setVisible(true);
        loadUsers();
    }

    @FXML
    private void showPayments() {
        hideAllViews();
        paymentsView.setVisible(true);
        loadPayments();
    }

    @FXML
    private void showReports() {
        hideAllViews();
        reportsView.setVisible(true);
    }

    private void hideAllViews() {
        dashboardView.setVisible(false);
        manageRoomsView.setVisible(false);
        manageBookingsView.setVisible(false);
        manageUsersView.setVisible(false);
        paymentsView.setVisible(false);
        reportsView.setVisible(false);
    }

    // Load Data Methods
    private void loadDashboardStats() {
        try {
            // Count rooms
            long totalRooms = database.getCollection("rooms").countDocuments();
            lblTotalRooms.setText(String.valueOf(totalRooms));

            // Count active bookings
            long activeBookings = database.getCollection("bookings")
                    .countDocuments(new Document("status", "Confirmed"));
            lblActiveBookings.setText(String.valueOf(activeBookings));

            // Count users
            long totalUsers = database.getCollection("users").countDocuments();
            lblTotalUsers.setText(String.valueOf(totalUsers));

            // Calculate revenue (placeholder - implement based on your payment structure)
            lblTotalRevenue.setText("₱0.00");

            // Load recent bookings
            loadRecentBookings();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load dashboard stats: " + e.getMessage());
        }
    }

    private void loadRecentBookings() {
        ObservableList<BookingData> bookings = FXCollections.observableArrayList();
        try {
            MongoCollection<Document> collection = database.getCollection("bookings");
            for (Document doc : collection.find().limit(10)) {
                bookings.add(new BookingData(
                        doc.getString("bookingId"),
                        doc.getString("fullName"),
                        doc.getString("roomNo"),
                        doc.getString("checkInDate"),
                        doc.getString("checkOutDate"),
                        doc.getString("status")
                ));
            }
            tblRecentBookings.setItems(bookings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRooms() {
        ObservableList<RoomData> rooms = FXCollections.observableArrayList();
        try {
            MongoCollection<Document> collection = database.getCollection("rooms");
            for (Document doc : collection.find()) {
                rooms.add(new RoomData(
                        doc.getString("roomId"),
                        doc.getString("roomNo"),
                        doc.getString("type"),
                        doc.getString("status"),
                        doc.getInteger("price", 0)
                ));
            }
            tblRooms.setItems(rooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBookings() {
        ObservableList<BookingData> bookings = FXCollections.observableArrayList();
        try {
            MongoCollection<Document> collection = database.getCollection("bookings");
            String filter = cmbBookingFilter.getValue();

            FindIterable<Document> docs = filter.equals("All") ?
                    collection.find() :
                    collection.find(new Document("status", filter));

            for (Document doc : docs) {
                bookings.add(new BookingData(
                        doc.getString("bookingId"),
                        doc.getString("fullName"),
                        doc.getString("roomNo"),
                        doc.getString("checkInDate"),
                        doc.getString("checkOutDate"),
                        doc.getString("status")
                ));
            }
            tblBookings.setItems(bookings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        ObservableList<UserData> users = FXCollections.observableArrayList();
        try {
            MongoCollection<Document> collection = database.getCollection("users");
            for (Document doc : collection.find()) {
                users.add(new UserData(
                        doc.getString("username"),
                        doc.getString("email"),
                        doc.getString("phone"),
                        doc.getString("address"),
                        doc.getString("userType") != null ? doc.getString("userType") : "customer"
                ));
            }
            tblUsers.setItems(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPayments() {
        // Implement payment loading based on your database structure
        ObservableList<PaymentData> payments = FXCollections.observableArrayList();
        tblPayments.setItems(payments);
    }

    // Action Methods
    @FXML
    private void handleAddRoom() {
        Dialog<RoomData> dialog = new Dialog<>();
        dialog.setTitle("Add New Room");
        dialog.setHeaderText("Enter room details");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        VBox content = new VBox(10);
        TextField roomIdField = new TextField();
        roomIdField.setPromptText("Room ID");
        TextField roomNoField = new TextField();
        roomNoField.setPromptText("Room Number");
        ComboBox<String> typeCombo = new ComboBox<>(
                FXCollections.observableArrayList("Single", "Double", "Suite", "Deluxe")
        );
        typeCombo.setPromptText("Room Type");
        TextField priceField = new TextField();
        priceField.setPromptText("Price per night");

        content.getChildren().addAll(
                new Label("Room ID:"), roomIdField,
                new Label("Room Number:"), roomNoField,
                new Label("Type:"), typeCombo,
                new Label("Price:"), priceField
        );

        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == addButton) {
                try {
                    Document newRoom = new Document()
                            .append("roomId", roomIdField.getText())
                            .append("roomNo", roomNoField.getText())
                            .append("type", typeCombo.getValue())
                            .append("status", "Available")
                            .append("price", Integer.parseInt(priceField.getText()));

                    database.getCollection("rooms").insertOne(newRoom);
                    showAlert("Success", "Room added successfully!");
                    loadRooms();
                } catch (Exception e) {
                    showAlert("Error", "Failed to add room: " + e.getMessage());
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void editRoom(RoomData room) {
        // Implement room editing
        showAlert("Info", "Edit room: " + room.getRoomNumber());
    }

    private void deleteRoom(RoomData room) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Room");
        alert.setContentText("Are you sure you want to delete room " + room.getRoomNumber() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                database.getCollection("rooms").deleteOne(new Document("roomId", room.getRoomId()));
                showAlert("Success", "Room deleted successfully!");
                loadRooms();
            } catch (Exception e) {
                showAlert("Error", "Failed to delete room: " + e.getMessage());
            }
        }
    }

    private void updateBookingStatus(String bookingId, String newStatus) {
        try {
            database.getCollection("bookings").updateOne(
                    new Document("bookingId", bookingId),
                    new Document("$set", new Document("status", newStatus))
            );
            showAlert("Success", "Booking status updated!");
            loadBookings();
        } catch (Exception e) {
            showAlert("Error", "Failed to update status: " + e.getMessage());
        }
    }

    private void deleteUser(UserData user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setContentText("Are you sure you want to delete user " + user.getUsername() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                database.getCollection("users").deleteOne(new Document("username", user.getUsername()));
                showAlert("Success", "User deleted successfully!");
                loadUsers();
            } catch (Exception e) {
                showAlert("Error", "Failed to delete user: " + e.getMessage());
            }
        }
    }

    @FXML
    private void refreshBookings() {
        loadBookings();
    }

    @FXML
    private void generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== SCHEDMOTO ADMIN REPORT ===\n\n");
        report.append("Generated: ").append(LocalDate.now()).append("\n\n");

        try {
            long totalRooms = database.getCollection("rooms").countDocuments();
            long totalBookings = database.getCollection("bookings").countDocuments();
            long totalUsers = database.getCollection("users").countDocuments();

            report.append("SYSTEM STATISTICS:\n");
            report.append("-".repeat(50)).append("\n");
            report.append(String.format("Total Rooms: %d\n", totalRooms));
            report.append(String.format("Total Bookings: %d\n", totalBookings));
            report.append(String.format("Total Users: %d\n\n", totalUsers));

            report.append("BOOKING STATUS BREAKDOWN:\n");
            report.append("-".repeat(50)).append("\n");
            for (String status : new String[]{"Pending", "Confirmed", "Cancelled", "Completed"}) {
                long count = database.getCollection("bookings")
                        .countDocuments(new Document("status", status));
                report.append(String.format("%s: %d\n", status, count));
            }

            txtReportArea.setText(report.toString());
        } catch (Exception e) {
            showAlert("Error", "Failed to generate report: " + e.getMessage());
        }
    }

    @FXML
    private void exportReport() {
        showAlert("Info", "Export functionality coming soon!");
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/oop/naingue/demo5/login-view.fxml")
                );
                Parent loginRoot = loader.load();
                Stage stage = (Stage) btnDashboard.getScene().getWindow();
                stage.setScene(new Scene(loginRoot, 500, 600));
                stage.setTitle("Login - SchedMoTo");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Data Classes
    public static class RoomData {
        private String roomId, roomNumber, roomType, status;
        private double price;

        public RoomData(String roomId, String roomNumber, String roomType, String status, double price) {
            this.roomId = roomId;
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.status = status;
            this.price = price;
        }

        public String getRoomId() { return roomId; }
        public String getRoomNumber() { return roomNumber; }
        public String getRoomType() { return roomType; }
        public String getStatus() { return status; }
        public double getPrice() { return price; }
    }

    public static class BookingData {
        private String bookingId, customerName, roomNo, checkIn, checkOut, status;

        public BookingData(String bookingId, String customerName, String roomNo,
                           String checkIn, String checkOut, String status) {
            this.bookingId = bookingId;
            this.customerName = customerName;
            this.roomNo = roomNo;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.status = status;
        }

        public String getBookingId() { return bookingId; }
        public String getCustomerName() { return customerName; }
        public String getRoomNo() { return roomNo; }
        public String getCheckIn() { return checkIn; }
        public String getCheckOut() { return checkOut; }
        public String getStatus() { return status; }
    }

    public static class UserData {
        private String username, email, phone, address, userType;

        public UserData(String username, String email, String phone, String address, String userType) {
            this.username = username;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.userType = userType;
        }

        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getAddress() { return address; }
        public String getUserType() { return userType; }
    }

    public static class PaymentData {
        private String bookingId, roomNo, method, date, status, receiptNo;
        private double amount;

        public PaymentData(String bookingId, String roomNo, double amount,
                           String method, String date, String status, String receiptNo) {
            this.bookingId = bookingId;
            this.roomNo = roomNo;
            this.amount = amount;
            this.method = method;
            this.date = date;
            this.status = status;
            this.receiptNo = receiptNo;
        }

        public String getBookingId() { return bookingId; }
        public String getRoomNo() { return roomNo; }
        public double getAmount() { return amount; }
        public String getMethod() { return method; }
        public String getDate() { return date; }
        public String getStatus() { return status; }
        public String getReceiptNo() { return receiptNo; }
    }
}