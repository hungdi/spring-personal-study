package com.study.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
class UserFacadeServiceTest {

    @Autowired
    UserFacadeService userFacadeService;

    @Test
    void getUserProfileSummary_성공() throws Exception {
        long start = System.currentTimeMillis();

        String result = userFacadeService.getUserProfileSummary("hong");

        long end = System.currentTimeMillis();

        System.out.println("🧾 결과: " + result);
        System.out.println("⏱ 실행 시간: " + (end - start) + "ms");

        assertThat(result).contains("UserInfo", "OrderSummary", "NotificationSetting");
        assertThat(end - start).isLessThan(400); // 병렬로 처리되면 400ms 안에 끝남
    }

    @Test
    void threadPoolWarmupTest() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        long t1 = System.currentTimeMillis();
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> sleep(100), executor);
        f1.join();
        long t2 = System.currentTimeMillis();

        long t3 = System.currentTimeMillis();
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() -> sleep(100), executor);
        f2.join();
        long t4 = System.currentTimeMillis();

        System.out.println("🔥 첫 실행 시간: " + (t2 - t1) + "ms");
        System.out.println("🚀 두 번째 실행 시간: " + (t4 - t3) + "ms");

        executor.shutdown();
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}