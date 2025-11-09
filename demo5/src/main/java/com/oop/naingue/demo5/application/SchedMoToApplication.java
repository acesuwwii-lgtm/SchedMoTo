package com.oop.naingue.demo5.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class SchedMoToApplication extends Application {

    private Stage primaryStage;
    private final Map<String, FXMLLoader> scenes = new HashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Preload scenes
        preloadScene("login", "/com/oop/naingue/demo5/login-view.fxml");
        preloadScene("registration", "/com/oop/naingue/demo5/registration-view.fxml");
        preloadScene("mainmenu", "/com/oop/naingue/demo5/Mainmenu.fxml");

        // Show initial scene
        switchScene("login");

        primaryStage.setTitle("SchedMoTo - Hotel Booking System");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void preloadScene(String name, String fxmlPath) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.load();
        scenes.put(name, loader);
    }

    public void switchScene(String name) {
        FXMLLoader loader = scenes.get(name);
        if (loader != null) {
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } else {
            System.err.println("Scene not found: " + name);
        }
    }
}
