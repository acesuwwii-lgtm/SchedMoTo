module com.oop.naingue.demo5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;

    opens com.oop.naingue.demo5 to javafx.fxml;
    exports com.oop.naingue.demo5;
    exports com.oop.naingue.demo5.controller;
    opens com.oop.naingue.demo5.controller to javafx.fxml;
    exports com.oop.naingue.demo5.application;
    opens com.oop.naingue.demo5.application to javafx.fxml;
    exports com.oop.naingue.demo5.data;
    opens com.oop.naingue.demo5.data to javafx.fxml;
}