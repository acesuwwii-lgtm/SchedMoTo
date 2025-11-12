package com.oop.naingue.demo5.utils;

import com.oop.naingue.demo5.models.User;
import com.oop.naingue.demo5.repositories.UserRepository;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserRefreshService {

    private final UserRepository userRepository;
    private final ObservableList<User> targetList;
    private ScheduledExecutorService scheduler;

    public UserRefreshService(UserRepository repo, ObservableList<User> listToUpdate) {
        this.userRepository = repo;
        this.targetList = listToUpdate;
    }

    public void start(long initialDelayMinutes, long periodMinutes) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            List<User> latestUsers = userRepository.findAll();

            // preserve plain passwords for users already in the table
            Map<Object, String> plainPasswords = new HashMap<>();
            for (User u : targetList) {
                if (u.getPlainPassword() != null && !u.getPlainPassword().isEmpty()) {
                    plainPasswords.put(u.getId(), u.getPlainPassword());
                }
            }

            for (User u : latestUsers) {
                if (plainPasswords.containsKey(u.getId())) {
                    u.setPlainPassword(plainPasswords.get(u.getId()));
                }
            }

            Platform.runLater(() -> targetList.setAll(latestUsers));
        }, initialDelayMinutes, periodMinutes, TimeUnit.MINUTES);
    }

    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }
}
