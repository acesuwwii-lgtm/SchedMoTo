package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.User;
import com.oop.naingue.demo5.repositories.UserRepository;
import com.oop.naingue.demo5.utils.PassHasher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {
    private UserRepository userRepository;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    public void initialize() {
        this.userRepository = new UserRepository();
    }

    private void clearField() {
        userNameField.clear();
        passwordField.clear();
    }

    @FXML
    private void onLoginSubmit() {
        String userName = userNameField.getText();
        String password = passwordField.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please input both username and password.");
            return;
        }

        User user = userRepository.findByUserName(userName);

        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid username or password.");
            return;
        }

        boolean passwordMatch = PassHasher.verifyPass(password, user.getPassword());
        if (!passwordMatch) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid username or password.");
            return;
        }

        clearField();

        showAlert(Alert.AlertType.INFORMATION, "Login Success", "Login Successful.");

        if ("admin".equalsIgnoreCase(userName)) {
            app.switchScene("admin-menu");
        } else {
            app.switchScene("user-menu");
        }

        System.out.println("User logged in");
    }

    @FXML
    private void onRegisterSubmit() {
        if (app != null) {
            app.switchScene("register");
        }
        System.out.println("trying to go to register");
    }

}