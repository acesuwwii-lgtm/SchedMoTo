package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.User;
import com.oop.naingue.demo5.repositories.BaseRepository;
import com.oop.naingue.demo5.repositories.UserRepository;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;
import com.oop.naingue.demo5.utils.PassHasher;

public class RegisterController extends BaseController {
    private BaseRepository<User> userRepository;

    @FXML
    private TextField userNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmField;

    private Validator validator;

    public void initialize() {
        this.userRepository = new UserRepository();
        this.validator = new Validator();

        validator.createCheck().dependsOn("username", userNameField.textProperty())
                .withMethod(c -> {
                    String v = c.get("username");
                    if (v == null || v.trim().isEmpty()) {
                        c.error("Username cannot be empty");
                    }
                }).decorates(userNameField).immediate();

        validator.createCheck().dependsOn("email", emailField.textProperty()).withMethod(c -> {
            String v = c.get("email");
            if (v == null || v.trim().isEmpty()) {
                c.error("Email cannot be empty");
            } else if (!v.matches("\\S+@\\S+\\.\\S+")) {
                c.error("Email format is invalid.");
            }
        }).decorates(emailField).immediate();

        validator.createCheck().dependsOn("phone", phoneField.textProperty()).withMethod(c -> {
            String v = c.get("phone");
            if (v == null || v.trim().isEmpty()) {
                c.error("Phone cannot be empty");
            } else if (!v.matches("\\d{11,13}")) {
                c.error("Phone number must contain only digits.");
            }
        }).decorates(phoneField).immediate();

        validator.createCheck()
                .dependsOn("address", addressField.textProperty())
                .withMethod(c -> {
                    String v = c.get("address");
                    if (v == null || v.trim().isEmpty()) {
                        c.error("Address is required.");
                    }
                })
                .decorates(addressField)
                .immediate();

        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .withMethod(c -> {
                    String v = c.get("password");
                    if (v == null || v.isEmpty()) {
                        c.error("Password is required.");
                    }
                })
                .decorates(passwordField)
                .immediate();

        validator.createCheck()
                .dependsOn("confirm", confirmField.textProperty())
                .dependsOn("password", passwordField.textProperty())
                .withMethod(c -> {
                    String pass = c.get("password");
                    String confirm = c.get("confirm");
                    if (confirm == null || confirm.isEmpty()) {
                        c.error("Please confirm your password.");
                    } else if (!confirm.equals(pass)) {
                        c.error("Passwords do not match.");
                    }
                })
                .decorates(confirmField)
                .immediate();


    }

    @FXML
    protected void onRegSubmit() {
        validator.validate();
        if (validator.containsErrors()) {
            return;
        }

        String hashedPass = PassHasher.hashPassword(passwordField.getText());

        User user = new User();
        user.setUserName(userNameField.getText());
        user.setEmail(emailField.getText());
        user.setAddress(addressField.getText());
        user.setPhone(phoneField.getText());
        user.setPassword(hashedPass);

        userRepository.insert(user);

        showAlert(Alert.AlertType.INFORMATION, "Account Creation Successful",
                "Your Account Created Successfully");

        clearFields();
        app.switchScene("login");
    }

    @FXML
    protected void onLoginSubmit() {
        clearFields();
        app.switchScene("login");
    }

    @FXML
    private void clearFields() {
        userNameField.clear();
        emailField.clear();
        addressField.clear();
        phoneField.clear();
        passwordField.clear();
        confirmField.clear();
    }
}
