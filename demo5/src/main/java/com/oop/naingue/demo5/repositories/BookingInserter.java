package com.oop.naingue.demo5.repositories;

import com.oop.naingue.demo5.models.Booking;
import org.bson.types.ObjectId;

import java.util.Date;

public class BookingInserter {

    public static void main(String[] args) {
        BookingRepository bookingRepository = new BookingRepository();

        // Create a new booking
        Booking booking = new Booking();

        // Set booking details
        booking.setBookingId(101); // example booking ID, change if needed
        booking.setUserId(new ObjectId("6913dd498e32ef3e5d8ba511")); // replace with a valid User ObjectId
        booking.setRoomId(12);
        booking.setBookingStatus(Booking.BookingStatus.CONFIRMED); // use enum, not string
        booking.setPaymentId(5001);

        // Set check-in and check-out dates
        Date now = new Date();
        booking.setCheckedInAt(now);
        booking.setCheckedOutAt(new Date(now.getTime() + 24 * 60 * 60 * 1000)); // +1 day

        // Optional metadata fields (so you don’t get nulls in Mongo)
        booking.setFullName("John Doe");
        booking.setContactInfo("john.doe@example.com");
        booking.setRoomNumber("12A");
        booking.setRoomType("Deluxe");

        // Insert into MongoDB
        bookingRepository.insert(booking);

        System.out.println("Booking inserted successfully with ID: " + booking.getBookingId());
    }
}
