package com.oop.naingue.demo5.mainmenu;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class BookingDAO {

    private final MongoCollection<Document> collection;

    public BookingDAO() {
        MongoClient client = MongoClients.create("mongodb+srv://admin123:admin@cluster0.b0o4ard.mongodb.net/?appName=Cluster0");
        MongoDatabase database = client.getDatabase("Schedmoto");
        collection = database.getCollection("bookings");
    }

    public void addBooking(Booking booking) {
        Document doc = new Document("bookingId", booking.getBookingId())
                .append("customerId", booking.getCustomerId())
                .append("fullName", booking.getFullName())
                .append("contactInfo", booking.getContactInfo())
                .append("cmbMethod", booking.getCmbMethod())
                .append("roomNo", booking.getRoomNo())
                .append("roomType", booking.getRoomType())
                .append("checkInDate", booking.getCheckInDate().toString())
                .append("checkOutDate", booking.getCheckOutDate().toString());

        collection.insertOne(doc);
        System.out.println("🗃️ Booking saved to MongoDB: " + booking.getBookingId());
    }
}
