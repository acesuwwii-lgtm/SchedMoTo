package com.oop.naingue.demo5.mainmenu;

import javafx.beans.property.*;

public class Room {
    private final StringProperty roomId;
    private final StringProperty roomNumber;
    private final StringProperty roomType;
    private final StringProperty status;
    private final DoubleProperty price;

    public Room(String roomId, String roomNumber, String roomType, String status, double price) {
        this.roomId = new SimpleStringProperty(roomId);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.roomType = new SimpleStringProperty(roomType);
        this.status = new SimpleStringProperty(status);
        this.price = new SimpleDoubleProperty(price);
    }


    public String getRoomId() { return roomId.get(); }     // Matches controller call
    public String getRoomNo() { return roomNumber.get(); } // For txtRoomNo
    public String getRoomNumber() { return roomNumber.get(); }
    public String getRoomType() { return roomType.get(); }
    public String getStatus() { return status.get(); }
    public double getPrice() { return price.get(); }

}
