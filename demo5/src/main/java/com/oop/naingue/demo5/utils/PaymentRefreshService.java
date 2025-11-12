package com.oop.naingue.demo5.utils;

import com.oop.naingue.demo5.models.Payment;
import com.oop.naingue.demo5.repositories.PaymentRepository;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PaymentRefreshService {

    private final PaymentRepository paymentRepository;
    private final ObservableList<Payment> targetList;
    private ScheduledExecutorService scheduler;

    public PaymentRefreshService(PaymentRepository repo, ObservableList<Payment> listToUpdate) {
        this.paymentRepository = repo;
        this.targetList = listToUpdate;
    }

    public void start(long initialDelayMinutes, long periodMinutes) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            List<Payment> latestPayments = paymentRepository.findAll();
            Platform.runLater(() -> targetList.setAll(latestPayments));
        }, initialDelayMinutes, periodMinutes, TimeUnit.MINUTES);
    }

    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }
}
