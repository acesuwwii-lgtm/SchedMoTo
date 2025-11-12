package com.oop.naingue.demo5.controller;

import com.oop.naingue.demo5.models.Booking;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.bson.types.ObjectId;

import java.util.Date;

public class UserBookingsController extends BaseController {

    @FXML
    private void onCancel() {
        System.out.println("cancel");
    }

    @FXML
    private void onSaveBooking() {
        System.out.println("save");
    }
}
