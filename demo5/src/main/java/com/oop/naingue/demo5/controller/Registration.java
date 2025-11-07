package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.data.DatabaseConnection;
import com.oop.naingue.demo5.data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Registration implements Initializable {

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextField txtAddress;
    @FXML private TextField txtPhone;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private Label lblError;
    @FXML private Label lblSuccess;

    private DatabaseConnection databaseConnection;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[+]?[0-9]{10,15}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDatabase();
        setupErrorClearListeners();
        System.out.println("RegistrationController initialized successfully!");
    }

    private void initializeDatabase() {
        try {
            databaseConnection = new DatabaseConnection();
            System.out.println("Database connection initialized successfully!");
        } catch (Exception e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
            showErrorMessage("Database connection failed. Please check your connection and try again.");
        }
    }

    private void setupErrorClearListeners() {
        if (txtUsername != null) {
            txtUsername.textProperty().addListener((obs, oldVal, newVal) -> clearMessages());
        }
        if (txtEmail != null) {
            txtEmail.textProperty().addListener((obs, oldVal, newVal) -> clearMessages());
        }
        if (txtPassword != null) {
            txtPassword.textProperty().addListener((obs, oldVal, newVal) -> clearMessages());
        }
    }

    @FXML
    private void handleRegister() {
        clearMessages();

        if (databaseConnection == null) {
            showErrorMessage("Database connection is not available. Please try again.");
            return;
        }

        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        String phone = txtPhone.getText().trim();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (username.isEmpty() || email.isEmpty() || address.isEmpty() ||
                phone.isEmpty() || password.isEmpty()) {
            showErrorMessage("Please fill in all required fields (marked with *)");
            return;
        }

        if (username.length() < 3) {
            showErrorMessage("Username must be at least 3 characters long");
            return;
        }

        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            showErrorMessage("Username can only contain letters, numbers, and underscores");
            return;
        }

        if (!isValidEmail(email)) {
            showErrorMessage("Please enter a valid email address (e.g., user@example.com)");
            return;
        }

        if (!isValidPhone(phone)) {
            showErrorMessage("Invalid phone number format. Use format: +1234567890 or 1234567890");
            return;
        }

        if (password.length() < 6) {
            showErrorMessage("Password must be at least 6 characters long");
            return;
        }

        if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*[0-9].*")) {
            showErrorMessage("Password must contain at least one letter and one number");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showErrorMessage("Passwords do not match. Please re-enter.");
            return;
        }

        if (databaseConnection.usernameExists(username)) {
            showErrorMessage("Username already exists. Please choose a different username.");
            return;
        }

        if (databaseConnection.emailExists(email)) {
            showErrorMessage("This email is already registered. Please login or use a different email.");
            return;
        }

        UserData newUser = new UserData(username, email, address, phone, password);
        boolean registrationSuccess = databaseConnection.registerUser(newUser);

        if (registrationSuccess) {
            System.out.println("New user registered in database:");
            System.out.println("  Username: " + username);
            System.out.println("  Email: " + email);
            System.out.println("  Address: " + address);
            System.out.println("  Phone: " + phone);

            showSuccessMessage("Account created successfully! You can now login.");
            clearForm();
            showSuccessAlert(
                    "Registration Successful",
                    "Welcome to SchedMoTo!",
                    "Your account has been created successfully.\n\nUsername: " + username +
                            "\nEmail: " + email + "\n\nYou can now login with your credentials."
            );
        } else {
            showErrorMessage("Registration failed. Please try again.");
        }
    }

    @FXML
    private void handleShowLogin(javafx.event.ActionEvent event) {
        try {
            if (databaseConnection != null) {
                databaseConnection.close();
            }

            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/oop/naingue/demo5/login-view.fxml")
            );
            javafx.scene.Parent loginRoot = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage)
                    ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(loginRoot);
            stage.setScene(scene);
            stage.setTitle("Login - SchedMoTo");
            stage.show();

            System.out.println("Login view loaded successfully!");

        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load login-view.fxml: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText("Failed to Load Login Screen");
            alert.setContentText("Could not load the login view. Please check the console for details.");
            alert.showAndWait();
        }
    }

    private void clearForm() {
        if (txtUsername != null) txtUsername.clear();
        if (txtEmail != null) txtEmail.clear();
        if (txtAddress != null) txtAddress.clear();
        if (txtPhone != null) txtPhone.clear();
        if (txtPassword != null) txtPassword.clear();
        if (txtConfirmPassword != null) txtConfirmPassword.clear();
    }

    private void showErrorMessage(String message) {
        if (lblError != null) {
            lblError.setText(message);
            lblError.setVisible(true);
        }
        if (lblSuccess != null) {
            lblSuccess.setVisible(false);
        }
    }

    private void showSuccessMessage(String message) {
        if (lblSuccess != null) {
            lblSuccess.setText(message);
            lblSuccess.setVisible(true);
        }
        if (lblError != null) {
            lblError.setVisible(false);
        }
    }

    private void clearMessages() {
        if (lblError != null) {
            lblError.setVisible(false);
        }
        if (lblSuccess != null) {
            lblSuccess.setVisible(false);
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    private void showSuccessAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void cleanup() {
        if (databaseConnection != null) {
            databaseConnection.close();
            System.out.println("Database connection closed in cleanup");
        }
    }
}
