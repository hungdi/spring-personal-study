package com.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserFacadeService {

    private final AsyncUserService asyncUserService;

    public String getUserProfileSummary(String userId) throws Exception {
        CompletableFuture<String> infoFuture = asyncUserService.getUserInfo(userId);
        CompletableFuture<String> orderFuture = asyncUserService.getOrderSummary(userId);
        CompletableFuture<String> notiFuture = asyncUserService.getNotificationSetting(userId);

        CompletableFuture<Void> all = CompletableFuture.allOf(infoFuture, orderFuture, notiFuture);

        // 모든 작업이 끝날 때까지 대기
        all.join();

        // 결과 모음
        String result = Stream.of(infoFuture, orderFuture, notiFuture)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" | "));

        return result;
    }
}
