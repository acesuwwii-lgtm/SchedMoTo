package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.User;
import com.oop.naingue.demo5.repositories.UserRepository;
import com.oop.naingue.demo5.utils.PassHasher;
import com.oop.naingue.demo5.utils.UserRefreshService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUserController extends BaseController {


    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> userNameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> phoneCol;
    @FXML
    private TableColumn<User, String> addCol;
    @FXML
    private TableColumn<User, String> passCol;

    @FXML
    private TextField userNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField passwordField;

    private UserRefreshService refreshService;
    private UserRepository userRepository;
    private final Map<String, ObservableList<User>> lists = new HashMap<>();

    @FXML
    public void initialize() {
        userRepository = new UserRepository();

        // Initialize observable list
        lists.put("users", FXCollections.observableArrayList());

        // Bind columns to User properties
        bindColumns();

        // Attach list to table
        usersTable.setItems(lists.get("users"));

        // Load user data from MongoDB
        loadUsers();

        // Attach selection listener for filling text fields
        setupSelectionListener();

        // Start refresh service: first run after 10 mins, then every 10 mins
        refreshService = new UserRefreshService(userRepository, lists.get("users"));
        refreshService.start(10, 10);
    }

    private void bindColumns() {
        userNameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUserName()));
        emailCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        phoneCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhone()));
        addCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddress()));
        passCol.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getPlainPassword() != null && !c.getValue().getPlainPassword().isEmpty()
                                ? c.getValue().getPlainPassword()
                                : "********"
                )
        );
    }

    private void loadUsers() {
        lists.get("users").clear();
        List<User> allUsers = userRepository.findAll();
        lists.get("users").addAll(allUsers);
    }

    private void setupSelectionListener() {
        usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                userNameField.setText(newSelection.getUserName());
                emailField.setText(newSelection.getEmail());
                phoneField.setText(newSelection.getPhone());
                addressField.setText(newSelection.getAddress());
                passwordField.setText(newSelection.getPlainPassword() != null ? newSelection.getPlainPassword() : "");
            }
        });
    }

    @FXML
    private void onLogoutSubmit() {
        app.switchScene("login");
    }

    @FXML
    private void onUserSubmit() {
        System.out.println("User section active");
    }

    @FXML
    private void onBookingSubmit() {
        app.switchScene("booking");
    }

    @FXML
    private void onRoomSubmit() {
        System.out.println("Room section active");
    }

    @FXML
    private void onPaymentSubmit() {
        System.out.println("Payment section active");
    }

    @FXML
    private void onAddSubmit(javafx.event.ActionEvent event) {
        try {
            String userName = userNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            String plainPass = passwordField.getText().trim();

            if (userName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || plainPass.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all fields.");
                return;
            }

            if (userRepository.findByUserName(userName) != null) {
                showAlert(Alert.AlertType.WARNING, "Duplicate User", "A user with this username already exists.");
                return;
            }

            User user = new User();
            user.setUserName(userName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);
            user.setPassword(PassHasher.hashPassword(plainPass)); // hashed for DB
            user.setPlainPassword(plainPass);                     // for UI

            userRepository.insert(user);
            usersTable.getItems().add(user);

            userNameField.clear();
            emailField.clear();
            phoneField.clear();
            addressField.clear();
            passwordField.clear();

            showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add user: " + e.getMessage());
        }
    }


    @FXML
    private void onEditSubmit(javafx.event.ActionEvent event) {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to edit.");
            return;
        }

        try {
            String userName = userNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            String plainPass = passwordField.getText().trim();

            if (userName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all fields.");
                return;
            }

            User existing = userRepository.findByUserName(userName);
            if (existing != null && !existing.getId().equals(selectedUser.getId())) {
                showAlert(Alert.AlertType.WARNING, "Duplicate Username", "That username is already in use.");
                return;
            }

            selectedUser.setUserName(userName);
            selectedUser.setEmail(email);
            selectedUser.setPhone(phone);
            selectedUser.setAddress(address);

            if (!plainPass.isEmpty()) {
                selectedUser.setPassword(PassHasher.hashPassword(plainPass)); // hashed
                selectedUser.setPlainPassword(plainPass);                     // for UI
            }

            userRepository.update((ObjectId) selectedUser.getId(), selectedUser);
            usersTable.refresh();

            userNameField.clear();
            emailField.clear();
            phoneField.clear();
            addressField.clear();
            passwordField.clear();

            showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user: " + e.getMessage());
        }
    }

    @FXML
    private void onDelSubmit(javafx.event.ActionEvent event) {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to delete.");
            return;
        }

        try {
            // Optional confirmation dialog
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Delete User");
            confirm.setContentText("Are you sure you want to delete user: " + selectedUser.getUserName() + "?");

            confirm.showAndWait().ifPresent(response -> {
                if (response.getText().equalsIgnoreCase("OK") || response.getButtonData().isDefaultButton()) {
                    // Delete from MongoDB
                    userRepository.deleteById(selectedUser.getId());

                    // Remove from table
                    usersTable.getItems().remove(selectedUser);

                    showAlert(Alert.AlertType.INFORMATION, "Deleted", "User deleted successfully.");
                }
            });
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user: " + e.getMessage());
        }
    }

    public void onClose() {
        if (refreshService != null) {
            refreshService.stop();
        }
    }

}
