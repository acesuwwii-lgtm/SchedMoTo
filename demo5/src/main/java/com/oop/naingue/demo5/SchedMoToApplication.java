package com.oop.naingue.demo5;

import com.oop.naingue.demo5.controller.AdminUserController;
import com.oop.naingue.demo5.controller.BaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SchedMoToApplication extends Application {

    private Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();

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
        preloadScene("user-booking", "user-booking-view.fxml");
        preloadScene("payment-menu", "payment-view.fxml");
        preloadScene("receipt", "receipt-view.fxml");

        // Show initial scene
        switchScene("login");

        primaryStage.setTitle("SchedMoTo - Hotel BookingController System");
        primaryStage.setResizable(false);
        primaryStage.show();

        // Attach close handler for all scenes that need cleanup
        primaryStage.setOnCloseRequest(event -> {
            // Get admin-user controller from preloaded scene
            Scene adminUserScene = scenes.get("admin-user");
            if (adminUserScene != null) {
                FXMLLoader loader = (FXMLLoader) adminUserScene.getUserData();
                Object controller = loader.getController();
                if (controller instanceof AdminUserController auc) {
                    auc.onClose(); // stop refresh service
                }
            }
        });
    }

    private void preloadScene(String name, String fxmlFile) throws Exception {
        URL fxmlUrl = getClass().getResource("/com/oop/naingue/demo5/" + fxmlFile);
        if (fxmlUrl == null) fxmlUrl = getClass().getResource("/" + fxmlFile);
        if (fxmlUrl == null) throw new IOException("FXML not found: " + fxmlFile);

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(loader.load());
        scene.setUserData(loader);

//        // Load CSS if exists
//        String cssPath = "com/oop/naingue/demo5/" + name + ".css";
//        URL cssUrl = getClass().getResource(cssPath);
//        if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());

        // Inject app reference into controller
        Object controller = loader.getController();
        if (controller instanceof BaseController baseController) {
            baseController.setApp(this);
        }

        scenes.put(name, scene);
    }

    public void switchScene(String name) {
        Scene scene = scenes.get(name);
        if (scene == null) {
            System.err.println("Scene not found: " + name);
            return;
        }

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        FXMLLoader loader = (FXMLLoader) scene.getUserData();
        Object controller = loader.getController();
        if (controller instanceof BaseController baseController) {
//            baseController.setCurrentUser(loggedInUser);
            baseController.onSceneShown();
        }

        System.out.println("Switching to scene: " + name);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
