package com.oop.naingue.demo5.repositories;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.oop.naingue.demo5.models.Booking;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BookingRepository extends BaseRepository<Booking> {

    public BookingRepository() {
        super();
        initCollection("bookings"); // MongoDB collection name
    }

    @Override
    protected Booking convert(Document document) {
        Booking booking = new Booking();
        booking.setId(document.getObjectId("_id"));
        booking.setBookingId(document.getInteger("bookingId"));
        booking.setUserId(document.getObjectId("userId"));
        booking.setRoomId(document.getInteger("roomId"));
        booking.setCheckedInAt(document.getDate("checkedInAt"));
        booking.setCheckedOutAt(document.getDate("checkedOutAt"));

        String statusStr = document.getString("bookingStatus");
        if (statusStr != null) {
            try {
                booking.setBookingStatus(Booking.BookingStatus.valueOf(statusStr));
            } catch (IllegalArgumentException e) {
                booking.setBookingStatus(null); // fallback if invalid
            }
        }

        booking.setPaymentId(document.getInteger("paymentId", 0));
        booking.setFullName(document.getString("fullName"));
        booking.setContactInfo(document.getString("contactInfo"));
        booking.setRoomNumber(document.getString("roomNumber"));
        booking.setRoomType(document.getString("roomType"));

        return booking;
    }

    public Booking findByBookingId(int bookingId) {
        Document document = this.collection.find(eq("bookingId", bookingId)).first();
        return document == null ? null : convert(document);
    }

    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        FindIterable<Document> docs = this.collection.find();

        try (MongoCursor<Document> cursor = docs.iterator()) {
            while (cursor.hasNext()) {
                bookings.add(convert(cursor.next()));
            }
        }
        return bookings;
    }

    public List<Booking> findByUserId(ObjectId userId) {
        List<Booking> bookings = new ArrayList<>();
        FindIterable<Document> docs = this.collection.find(eq("userId", userId));

        try (MongoCursor<Document> cursor = docs.iterator()) {
            while (cursor.hasNext()) {
                bookings.add(convert(cursor.next()));
            }
        }
        return bookings;
    }

    public void insert(Booking booking) {
        this.collection.insertOne(booking.toDocument());
    }

    public void update(int bookingId, Booking booking) {
        Document updated = new Document()
                .append("userId", booking.getUserId())
                .append("roomId", booking.getRoomId())
                .append("checkedInAt", booking.getCheckedInAt())
                .append("checkedOutAt", booking.getCheckedOutAt())
                .append("bookingStatus", booking.getBookingStatus() != null ? booking.getBookingStatus().name() : null)
                .append("paymentId", booking.getPaymentId())
                .append("fullName", booking.getFullName())
                .append("contactInfo", booking.getContactInfo())
                .append("roomNumber", booking.getRoomNumber())
                .append("roomType", booking.getRoomType());

        this.collection.updateOne(eq("bookingId", bookingId), new Document("$set", updated));
    }

    public void deleteByBookingId(int bookingId) {
        this.collection.deleteOne(eq("bookingId", bookingId));
    }
}
