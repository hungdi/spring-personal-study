package com.study.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncUserService {
    @Async("taskExecutor")
    public CompletableFuture<String> getUserInfo(String userId) {
        sleep(100); // 가짜 delay
        return CompletableFuture.completedFuture("UserInfo for " + userId);
    }

    @Async("taskExecutor")
    public CompletableFuture<String> getOrderSummary(String userId) {
        sleep(200);
        return CompletableFuture.completedFuture("OrderSummary for " + userId);
    }

    @Async("taskExecutor")
    public CompletableFuture<String> getNotificationSetting(String userId) {
        sleep(150);
        return CompletableFuture.completedFuture("NotificationSetting for " + userId);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
