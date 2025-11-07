package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.data.DatabaseConnection;
import com.oop.naingue.demo5.data.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML private VBox loginView;
    @FXML private TextField txtLoginUsername;
    @FXML private PasswordField txtLoginPassword;
    @FXML private Label lblLoginError;
    @FXML private Hyperlink linkSignUp;
    @FXML private Button btnLogin;

    @FXML private VBox signUpView;
    @FXML private TextField txtSignUpUsername;
    @FXML private TextField txtSignUpEmail;
    @FXML private TextField txtSignUpPhone;
    @FXML private PasswordField txtSignUpPassword;
    @FXML private PasswordField txtSignUpConfirmPassword;
    @FXML private CheckBox chkTerms;
    @FXML private Label lblSignUpError;
    @FXML private Hyperlink linkLogin;
    @FXML private Button btnSignUp;

    private final DatabaseConnection database = new DatabaseConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("loginController initialized");
        showLoginView();
    }

    @FXML
    private void handleLoginSubmit(ActionEvent event) {
        try {
            lblLoginError.setVisible(false);
            String username = txtLoginUsername.getText().trim();
            String password = txtLoginPassword.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                showError(lblLoginError, "Please enter both username and password.");
                return;
            }

            UserData loggedInUser = database.loginUser(username, password);
            if (loggedInUser != null) {
                showSuccessAlert("Login Successful", "Welcome back " + username + "!");
                loadDashboard();
            } else {
                showError(lblLoginError, "Invalid username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError(lblLoginError, "Unexpected error: " + e.getMessage());
        }
    }

    @FXML
    private void handleSignUpSubmit(ActionEvent event) {
        try {
            lblSignUpError.setVisible(false);

            String username = txtSignUpUsername.getText().trim();
            String email = txtSignUpEmail.getText().trim();
            String phone = txtSignUpPhone.getText().trim();
            String password = txtSignUpPassword.getText().trim();
            String confirmPassword = txtSignUpConfirmPassword.getText().trim();

            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                showError(lblSignUpError, "Please fill in all required fields.");
                return;
            }

            if (password.length() < 6) {
                showError(lblSignUpError, "Password must be at least 6 characters long.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showError(lblSignUpError, "Passwords do not match.");
                return;
            }

            if (!chkTerms.isSelected()) {
                showError(lblSignUpError, "You must agree to the Terms and Conditions.");
                return;
            }

            if (database.usernameExists(username)) {
                showError(lblSignUpError, "Username already exists.");
                return;
            }

            if (database.emailExists(email)) {
                showError(lblSignUpError, "Email already exists.");
                return;
            }

            UserData newUser = new UserData(username, email, phone, "", password);
            boolean registered = database.registerUser(newUser);

            if (registered) {
                showSuccessAlert("Account Created", "Your account has been created successfully!");
                clearSignUpForm();
                showLoginView();
            } else {
                showError(lblSignUpError, "Registration failed. Try again later.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError(lblSignUpError, "Unexpected error: " + e.getMessage());
        }
    }

    @FXML
    private void handleShowSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/registration-view.fxml"));
            Parent registrationRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(registrationRoot);
            stage.setScene(scene);
            stage.setTitle("Register - SchedMoTo");
            stage.show();

            System.out.println("Registration view loaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load registration-view.fxml: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText("Failed to Load Registration Screen");
            alert.setContentText("Could not load the registration view. Please check the console for details.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleShowLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/login-view.fxml"));
            Parent loginRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login - SchedMoTo");
            stage.show();

            System.out.println("Login view loaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load login-view.fxml: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText("Failed to Load Login Screen");
            alert.setContentText("Could not load the login view. Please check the console for details.");
            alert.showAndWait();
        }
    }

    private void showLoginView() {
        if (loginView != null && signUpView != null) {
            loginView.setVisible(true);
            loginView.setManaged(true);
            signUpView.setVisible(false);
            signUpView.setManaged(false);
        }
    }

    private void showSignUpView() {
        if (loginView != null && signUpView != null) {
            loginView.setVisible(false);
            loginView.setManaged(false);
            signUpView.setVisible(true);
            signUpView.setManaged(true);
        }
    }

    private void showError(Label label, String message) {
        label.setText(message);
        label.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 12px;");
        label.setVisible(true);
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearSignUpForm() {
        txtSignUpUsername.clear();
        txtSignUpEmail.clear();
        txtSignUpPhone.clear();
        txtSignUpPassword.clear();
        txtSignUpConfirmPassword.clear();
        chkTerms.setSelected(false);
    }

    private void loadDashboard() {
        VBox dashboard = new VBox(20);
        dashboard.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label welcomeLabel = new Label("Welcome to SchedMoTo Dashboard!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button logout = new Button("Logout");
        logout.setOnAction(e -> showLoginView());

        dashboard.getChildren().addAll(welcomeLabel, logout);

        Scene scene = new Scene(dashboard, 800, 600);
        Stage stage = (Stage) loginView.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("SchedMoTo - Dashboard");
    }
}
