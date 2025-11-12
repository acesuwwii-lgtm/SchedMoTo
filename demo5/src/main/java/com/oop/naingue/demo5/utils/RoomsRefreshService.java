package com.oop.naingue.demo5.utils;

import com.oop.naingue.demo5.models.Rooms;
import com.oop.naingue.demo5.repositories.RoomsRepository;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoomsRefreshService {

    private final RoomsRepository roomsRepository;
    private final ObservableList<Rooms> targetList;
    private ScheduledExecutorService scheduler;

    public RoomsRefreshService(RoomsRepository repo, ObservableList<Rooms> listToUpdate) {
        this.roomsRepository = repo;
        this.targetList = listToUpdate;
    }

    public void start(long initialDelayMinutes, long periodMinutes) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            List<Rooms> latestRooms = roomsRepository.findAll();
            Platform.runLater(() -> targetList.setAll(latestRooms));
        }, initialDelayMinutes, periodMinutes, TimeUnit.MINUTES);
    }

    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }
}
