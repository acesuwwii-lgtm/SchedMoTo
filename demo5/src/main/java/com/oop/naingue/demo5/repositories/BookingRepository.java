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
        initCollection("bookings"); // name of the collection in MongoDB
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
        booking.setBookingStatus(document.getString("bookingStatus"));
        booking.setPaymentId(document.getInteger("paymentId"));
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
                .append("bookingStatus", booking.getBookingStatus())
                .append("paymentId", booking.getPaymentId());

        this.collection.updateOne(eq("bookingId", bookingId), new Document("$set", updated));
    }

    public void deleteByBookingId(int bookingId) {
        this.collection.deleteOne(eq("bookingId", bookingId));
    }
}
