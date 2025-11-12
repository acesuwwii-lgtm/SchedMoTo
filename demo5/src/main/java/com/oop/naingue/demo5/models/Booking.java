package com.oop.naingue.demo5.models;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Date;

public class Booking extends BaseModel {

    private int bookingId;           // primary key
    private ObjectId userId;         // foreign key to User
    private int roomId;              // foreign key to Room
    private Date checkedInAt;
    private Date checkedOutAt;
    private BookingStatus bookingStatus;
    private int paymentId;

    // Convenience/display fields (from User and Room)
    private String fullName;         // user's full name
    private String contactInfo;      // user's contact info
    private String roomNumber;       // room number
    private String roomType;         // room type

    // Enum for booking status
    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        PAID // Add this to indicate payment completed
    }

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public ObjectId getUserId() { return userId; }
    public void setUserId(ObjectId userId) { this.userId = userId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public Date getCheckedInAt() { return checkedInAt; }
    public void setCheckedInAt(Date checkedInAt) { this.checkedInAt = checkedInAt; }

    public Date getCheckedOutAt() { return checkedOutAt; }
    public void setCheckedOutAt(Date checkedOutAt) { this.checkedOutAt = checkedOutAt; }

    public BookingStatus getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    // Convenience methods for LocalDate conversion
    public void setCheckedInAtFromLocalDate(LocalDate date) {
        if (date != null) this.checkedInAt = java.sql.Date.valueOf(date);
    }

    public void setCheckedOutAtFromLocalDate(LocalDate date) {
        if (date != null) this.checkedOutAt = java.sql.Date.valueOf(date);
    }

    @Override
    public Document toDocument() {
        Document doc = new Document();
        if (getId() != null) doc.append("_id", getId());
        doc.append("bookingId", bookingId)
                .append("userId", userId)
                .append("roomId", roomId)
                .append("checkedInAt", checkedInAt)
                .append("checkedOutAt", checkedOutAt)
                .append("bookingStatus", bookingStatus != null ? bookingStatus.name() : null)
                .append("paymentId", paymentId)
                .append("fullName", fullName)
                .append("contactInfo", contactInfo)
                .append("roomNumber", roomNumber)
                .append("roomType", roomType);
        return doc;
    }
}
