package com.oop.naingue.demo5.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class loginApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/naingue/demo5/login-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 650);

            primaryStage.setTitle("SchedMoTo - Hotel Booking System");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to start JavaFX application", e);
        }
    }


    @Override
    public void init() throws Exception {
        System.out.println("🔧 JavaFX Application initializing...");
        super.init();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("JavaFX Application stopping...");
        super.stop();
    }
}