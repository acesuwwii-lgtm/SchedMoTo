module com.oop.naingue.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.desktop;
    requires jbcrypt;
    requires javafx.base;

    opens com.oop.naingue.demo5 to javafx.fxml;
    exports com.oop.naingue.demo5;
    exports com.oop.naingue.demo5.controller;
    opens com.oop.naingue.demo5.controller to javafx.fxml;
    exports com.oop.naingue.demo5.models;
    opens com.oop.naingue.demo5.models to javafx.fxml;
}