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
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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

    private DatabaseConnection database;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("LoginController initialized");
        initializeDatabase();
        showLoginView();
    }

    private void initializeDatabase() {
        try {
            database = new DatabaseConnection();
            System.out.println("Database connection established");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoginSubmit(ActionEvent event) {
        try {
            if (lblLoginError != null) {
                lblLoginError.setVisible(false);
            }

            String username = txtLoginUsername.getText().trim();
            String password = txtLoginPassword.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                showError(lblLoginError, "Please enter both username and password.");
                return;
            }

            if (database == null) {
                showError(lblLoginError, "Database connection error. Please restart the application.");
                return;
            }

            UserData loggedInUser = database.loginUser(username, password);

            if (loggedInUser != null) {
                System.out.println("Login successful for: " + username);

                String userType = getUserType(username);

                if ("admin".equalsIgnoreCase(userType)) {
                    showSuccessAlert("Admin Login", "Welcome Administrator!");
                    loadAdminDashboard(event);
                } else {
                    showSuccessAlert("Login Successful", "Welcome back " + username + "!");
                    loadMainMenu(event);
                }
            } else {
                showError(lblLoginError, "Invalid username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError(lblLoginError, "Unexpected error: " + e.getMessage());
        }
    }

    private String getUserType(String username) {
        try {
            com.mongodb.client.MongoClient mongoClient = com.mongodb.client.MongoClients.create(
                    "mongodb+srv://admin123:admin@cluster0.b0o4ard.mongodb.net/?appName=Cluster0"
            );
            com.mongodb.client.MongoDatabase db = mongoClient.getDatabase("Schedmoto");
            com.mongodb.client.MongoCollection<Document> usersCollection = db.getCollection("users");

            Document userDoc = usersCollection
                    .find(new Document("username", username))
                    .first();

            mongoClient.close();

            if (userDoc != null) {
                String userType = userDoc.getString("userType");
                return userType != null ? userType : "customer";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "customer";
    }

    @FXML
    private void handleSignUpSubmit(ActionEvent event) {
        try {
            if (lblSignUpError != null) {
                lblSignUpError.setVisible(false);
            }

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

            if (chkTerms != null && !chkTerms.isSelected()) {
                showError(lblSignUpError, "You must agree to the Terms and Conditions.");
                return;
            }

            if (database == null) {
                showError(lblSignUpError, "Database connection error. Please restart the application.");
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
            if (database != null) {
                database.close();
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/oop/naingue/demo5/registration-view.fxml")
            );
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

    private void loadAdminDashboard(ActionEvent event) {
        try {
            if (database != null) {
                database.close();
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/oop/naingue/demo5/Admin-view.fxml")
            );
            Parent adminRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(adminRoot, 1400, 800);
            stage.setScene(scene);
            stage.setTitle("SchedMoTo - Admin Dashboard");
            stage.setResizable(true);
            stage.show();

            System.out.println("Admin dashboard loaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load AdminDashboard.fxml: " + e.getMessage());

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Navigation Error");
            errorAlert.setHeaderText("Failed to Load Admin Dashboard");
            errorAlert.setContentText("Error: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    private void loadMainMenu(ActionEvent event) {
        try {
            if (database != null) {
                database.close();
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/oop/naingue/demo5/Mainmenu.fxml")
            );
            Parent mainMenuRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(mainMenuRoot, 1400, 800);
            stage.setScene(scene);
            stage.setTitle("SchedMoTo - Dashboard");
            stage.setResizable(true);
            stage.show();

            System.out.println("Main menu loaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load Mainmenu.fxml: " + e.getMessage());
            loadSimpleDashboard();
        }
    }

    private void showError(Label label, String message) {
        if (label != null) {
            label.setText(message);
            label.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 12px;");
            label.setVisible(true);
        }
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearSignUpForm() {
        if (txtSignUpUsername != null) txtSignUpUsername.clear();
        if (txtSignUpEmail != null) txtSignUpEmail.clear();
        if (txtSignUpPhone != null) txtSignUpPhone.clear();
        if (txtSignUpPassword != null) txtSignUpPassword.clear();
        if (txtSignUpConfirmPassword != null) txtSignUpConfirmPassword.clear();
        if (chkTerms != null) chkTerms.setSelected(false);
    }

    private void loadSimpleDashboard() {
        VBox dashboard = new VBox(20);
        dashboard.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label welcomeLabel = new Label("Welcome to SchedMoTo Dashboard!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/oop/naingue/demo5/login-view.fxml")
                );
                Parent loginRoot = loader.load();
                Stage stage = (Stage) dashboard.getScene().getWindow();
                stage.setScene(new Scene(loginRoot));
                stage.setTitle("Login - SchedMoTo");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        dashboard.getChildren().addAll(welcomeLabel, logout);

        Scene scene = new Scene(dashboard, 800, 600);
        Stage stage = (Stage) loginView.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("SchedMoTo - Dashboard");
    }

    public void cleanup() {
        if (database != null) {
            database.close();
            System.out.println("Database connection closed");
        }
    }
}