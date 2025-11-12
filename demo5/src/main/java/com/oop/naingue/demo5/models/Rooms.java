package com.oop.naingue.demo5.models;

import org.bson.Document;

public class Rooms extends BaseModel {
    private int roomId;        // Primary key
    private int roomNumber;
    private String roomType;
    private double roomPrice;
    private String status;     // e.g., "Available", "Occupied", etc.
    private int roomCapacity;

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    @Override
    public Document toDocument() {
        Document doc = new Document();
        if (getId() != null) doc.append("_id", getId());
        doc.append("roomId", roomId);
        doc.append("roomNumber", roomNumber);
        doc.append("roomType", roomType);
        doc.append("roomPrice", roomPrice);
        doc.append("status", status);
        doc.append("roomCapacity", roomCapacity);
        return doc;
    }
}
