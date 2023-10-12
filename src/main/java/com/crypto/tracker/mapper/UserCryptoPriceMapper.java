package com.crypto.tracker.mapper;

import com.crypto.tracker.dto.CryptoPriceResponse;
import com.crypto.tracker.model.CryptoPrice;
import com.crypto.tracker.model.UserCryptoPrice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserCryptoPriceMapper {

    public UserCryptoPrice toUserCryptoPrice(List<CryptoPriceResponse> prices, long chatId) {
        UserCryptoPrice userCryptoPrice = new UserCryptoPrice();
        userCryptoPrice.setUserId(chatId);
        userCryptoPrice.setCryptoPrices(toCryptoPrice(prices));
        userCryptoPrice.setLastAccessed(LocalDateTime.now());
        return userCryptoPrice;
    }

    private List<CryptoPrice> toCryptoPrice(List<CryptoPriceResponse> prices) {
        if (prices == null) {
            return null;
        }
        return prices.stream()
                .map(cryptoPrice -> new CryptoPrice(cryptoPrice.getSymbol(), cryptoPrice.getPrice()))
                .toList();
    }
}
