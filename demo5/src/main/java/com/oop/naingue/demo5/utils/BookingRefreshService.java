package com.oop.naingue.demo5.utils;

import com.oop.naingue.demo5.models.Booking;
import com.oop.naingue.demo5.repositories.BookingRepository;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BookingRefreshService {

    private final BookingRepository bookingRepository;
    private final ObservableList<Booking> targetList;
    private ScheduledExecutorService scheduler;

    public BookingRefreshService(BookingRepository repo, ObservableList<Booking> listToUpdate) {
        this.bookingRepository = repo;
        this.targetList = listToUpdate;
    }

    public void start(long initialDelayMinutes, long periodMinutes) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            List<Booking> latestBookings = bookingRepository.findAll();
            Platform.runLater(() -> targetList.setAll(latestBookings));
        }, initialDelayMinutes, periodMinutes, TimeUnit.MINUTES);
    }

    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }
}
