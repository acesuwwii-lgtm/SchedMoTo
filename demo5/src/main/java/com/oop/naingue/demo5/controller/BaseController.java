package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.SchedMoToApplication;
import com.oop.naingue.demo5.models.User;
import javafx.scene.control.Alert;

public abstract class BaseController  {
    protected SchedMoToApplication app;
    protected User currentUser;

    public void setApp(SchedMoToApplication app) {
        this.app = app;
    }

    public SchedMoToApplication getApp() {
        return app;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    protected void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onSceneShown() {}
}
