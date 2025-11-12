package com.oop.naingue.demo5.models;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

public class Booking extends BaseModel {

    private int bookingId;      // primary key
    private ObjectId userId;    // foreign key to User
    private int roomId;
    private Date checkedInAt;
    private Date checkedOutAt;
    private String bookingStatus;
    private int paymentId;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getCheckedInAt() {
        return checkedInAt;
    }

    public void setCheckedInAt(Date checkedInAt) {
        this.checkedInAt = checkedInAt;
    }

    public Date getCheckedOutAt() {
        return checkedOutAt;
    }

    public void setCheckedOutAt(Date checkedOutAt) {
        this.checkedOutAt = checkedOutAt;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public Document toDocument() {
        Document doc = new Document();
        if (getId() != null) doc.append("_id", getId());
        doc.append("bookingId", bookingId);
        doc.append("userId", userId);
        doc.append("roomId", roomId);
        doc.append("checkedInAt", checkedInAt);
        doc.append("checkedOutAt", checkedOutAt);
        doc.append("bookingStatus", bookingStatus);
        doc.append("paymentId", paymentId);
        return doc;
    }
}
