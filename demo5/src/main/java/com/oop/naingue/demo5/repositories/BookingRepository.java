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
        initCollection("bookings");
    }

    @Override
    protected Booking convert(Document document) {
        Booking booking = new Booking();
        booking.setId(document.getObjectId("_id"));
        booking.setBookingId(document.getInteger("bookingId", 0));
        booking.setUserId(document.getObjectId("userId"));
        booking.setRoomId(document.getInteger("roomId"));
        booking.setCheckedInAt(document.getDate("checkedInAt"));
        booking.setCheckedOutAt(document.getDate("checkedOutAt"));

        String statusStr = document.getString("bookingStatus");
        if (statusStr != null) {
            try {
                booking.setBookingStatus(Booking.BookingStatus.valueOf(statusStr));
            } catch (IllegalArgumentException e) {
                booking.setBookingStatus(null);
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

    public Booking findById(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        return doc != null ? convert(doc) : null;
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

    // --- Generate next bookingId ---
    private int getNextBookingId() {
        Document lastBooking = collection.find()
                .sort(new Document("bookingId", -1))
                .first();
        if (lastBooking == null) return 1;
        return lastBooking.getInteger("bookingId", 0) + 1;
    }

    public void insert(Booking booking) {
        booking.setBookingId(getNextBookingId());
        Document doc = booking.toDocument();
        collection.insertOne(doc);
        booking.setId(doc.getObjectId("_id"));
    }

    public void update(ObjectId id, Booking booking) {
        Document updated = booking.toDocument();
        this.collection.replaceOne(eq("_id", id), updated);
    }

    public void deleteByBookingId(int bookingId) {
        this.collection.deleteOne(eq("bookingId", bookingId));
    }
}
