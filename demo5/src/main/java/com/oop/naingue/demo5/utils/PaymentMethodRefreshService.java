package com.oop.naingue.demo5.utils;

import com.oop.naingue.demo5.models.PaymentMethod;
import com.oop.naingue.demo5.repositories.PaymentMethodRepository;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PaymentMethodRefreshService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final ObservableList<PaymentMethod> targetList;
    private ScheduledExecutorService scheduler;

    public PaymentMethodRefreshService(PaymentMethodRepository repo, ObservableList<PaymentMethod> listToUpdate) {
        this.paymentMethodRepository = repo;
        this.targetList = listToUpdate;
    }

    public void start(long initialDelayMinutes, long periodMinutes) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            List<PaymentMethod> latestMethods = paymentMethodRepository.findAll();
            Platform.runLater(() -> targetList.setAll(latestMethods));
        }, initialDelayMinutes, periodMinutes, TimeUnit.MINUTES);
    }

    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }
}
