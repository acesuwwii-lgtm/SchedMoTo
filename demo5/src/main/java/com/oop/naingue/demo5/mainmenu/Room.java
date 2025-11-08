package com.oop.naingue.demo5.mainmenu;

public class Room {
    private String roomId;
    private String roomNo;
    private String type;
    private String status;
    private int price;

    public Room(String roomId, String roomNo, String type, String status, int price) {
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.type = type;
        this.status = status;
        this.price = price;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
