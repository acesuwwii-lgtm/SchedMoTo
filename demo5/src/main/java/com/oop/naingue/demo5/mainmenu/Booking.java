package com.oop.naingue.demo5.mainmenu;

import java.time.LocalDate;

public class Booking {
    private String bookingId;
    private String customerId;
    private String fullName;
    private String contactInfo;
    private String roomNo;
    private String roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String cmbMethod;

    // No-argument constructor (required for your current approach)
    public Booking() {
    }

    // All the setter methods
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public void setCmbMethod(String paymentMethod) { this.cmbMethod = paymentMethod; }


    // Getter methods (for BookingDAO)
    public String getBookingId() { return bookingId; }
    public String getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getContactInfo() { return contactInfo; }
    public String getRoomNo() { return roomNo; }
    public String getRoomType() { return roomType; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public String getCmbMethod() { return cmbMethod; }

}