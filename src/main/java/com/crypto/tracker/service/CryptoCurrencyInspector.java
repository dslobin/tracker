package com.crypto.tracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoCurrencyInspector {
    private final UserCryptoCurrencyService userCryptoCurrencyService;

    @Scheduled(fixedDelayString = "${crypto.price.update-interval}")
    public void checkUserState() {
        userCryptoCurrencyService.checkUserCryptoPriceState();
    }
}
