package com.oop.naingue.demo5;

import com.oop.naingue.demo5.controller.AdminUserController;
import com.oop.naingue.demo5.controller.BaseController;
import com.oop.naingue.demo5.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SchedMoToApplication extends Application {

    private Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Preload scenes
        preloadScene("login", "login-view.fxml");
        preloadScene("register", "register-view.fxml");
        preloadScene("admin-menu", "admin-menu-view.fxml");
        preloadScene("user-menu", "main-menu-view.fxml");
        preloadScene("admin-user", "admin-user-view.fxml");
        preloadScene("admin-booking", "admin-bookings-view.fxml");
        preloadScene("admin-rooms", "admin-rooms-view.fxml");
        preloadScene("user-booking-list", "user-booking-list-view.fxml");
        preloadScene("user-booking", "user-booking-view.fxml");
        preloadScene("payment-menu", "payment-view.fxml");
        preloadScene("receipt", "receipt-view.fxml");

        // Show initial scene safely (no user yet)
        switchScene("login");

        primaryStage.setTitle("SchedMoTo - Hotel Booking System");
        primaryStage.setResizable(false);
        primaryStage.show();

        // Attach close handler for admin-user scene
        primaryStage.setOnCloseRequest(event -> {
            Scene adminUserScene = scenes.get("admin-user");
            if (adminUserScene != null) {
                FXMLLoader loader = (FXMLLoader) adminUserScene.getUserData();
                Object controller = loader.getController();
                if (controller instanceof AdminUserController auc) {
                    auc.onClose();
                }
            }
        });
    }

    private void preloadScene(String name, String fxmlFile) throws IOException {
        URL fxmlUrl = getClass().getResource("/com/oop/naingue/demo5/" + fxmlFile);
        if (fxmlUrl == null) fxmlUrl = getClass().getResource("/" + fxmlFile);
        if (fxmlUrl == null) throw new IOException("FXML not found: " + fxmlFile);

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(loader.load());
        scene.setUserData(loader);

        Object controller = loader.getController();
        if (controller instanceof BaseController baseController) {
            baseController.setApp(this);
        }

        scenes.put(name, scene);
    }

    public Object getController(String sceneName) {
        Scene scene = scenes.get(sceneName);
        if (scene == null) return null;
        FXMLLoader loader = (FXMLLoader) scene.getUserData();
        return loader.getController();
    }

    public void switchScene(String name, User loggedInUser) {
        Scene scene = scenes.get(name);
        if (scene == null) return;

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        FXMLLoader loader = (FXMLLoader) scene.getUserData();
        Object controller = loader.getController();
        if (controller instanceof BaseController baseController) {
            baseController.setCurrentUser(loggedInUser);
            baseController.onSceneShown();
        }

        if (loggedInUser != null) {
            System.out.println("Switching to scene: " + name + " for user " + loggedInUser.getUserName());
        } else {
            System.out.println("Switching to scene: " + name + " (no user logged in)");
        }
    }

    public void switchScene(String name) {
        switchScene(name, currentUser);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
