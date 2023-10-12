package com.crypto.tracker.service;

import com.crypto.tracker.client.CryptoCurrencyClient;
import com.crypto.tracker.dto.CryptoPriceChangedDto;
import com.crypto.tracker.dto.CryptoPriceResponse;
import com.crypto.tracker.event.CryptoPriceChangedEvent;
import com.crypto.tracker.mapper.UserCryptoPriceMapper;
import com.crypto.tracker.model.CryptoPrice;
import com.crypto.tracker.model.UserCryptoPrice;
import com.crypto.tracker.repository.UserCryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCryptoCurrencyService {
    private static final int PAGE_SIZE = 10;

    private final UserCryptoPriceRepository userCryptoPriceRepository;
    private final CryptoCurrencyClient cryptoCurrencyClient;
    private final UserCryptoPriceMapper userCryptoPriceMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${crypto.price.percentage-change}")
    private String priceChangeDelta;

    public void saveUserCryptoPrice(long chatId) {
        List<CryptoPriceResponse> pricesResponse = cryptoCurrencyClient.getCryptoCurrencyPrices();
        UserCryptoPrice userCryptoPrice = userCryptoPriceMapper.toUserCryptoPrice(pricesResponse, chatId);
        userCryptoPriceRepository.save(userCryptoPrice);
    }

    public void removeUserCryptoPrice(long chatId) {
        userCryptoPriceRepository.deleteByUserId(chatId);
    }

    public void checkUserCryptoPriceState() {
        List<CryptoPriceResponse> pricesResponse = cryptoCurrencyClient.getCryptoCurrencyPrices();
        Map<String, CryptoPrice> cryptoPrices = pricesResponse.stream()
                .collect(Collectors.toMap(
                        CryptoPriceResponse::getSymbol,
                        response -> new CryptoPrice(response.getSymbol(), response.getPrice())
                ));

        int pageNum = 0;
        Page<UserCryptoPrice> page;
        do {
            page = userCryptoPriceRepository.findAll(PageRequest.of(pageNum++, PAGE_SIZE, Sort.by("userId")));
            page.get().forEach(userCryptoPrice -> compareCryptoPrices(userCryptoPrice, cryptoPrices));
        } while (page.hasContent());
    }

    private void compareCryptoPrices(UserCryptoPrice userCryptoPrice, Map<String, CryptoPrice> cryptoPrices) {
        List<CryptoPrice> userCryptoPrices = userCryptoPrice.getCryptoPrices();
        List<CryptoPriceChangedDto> changedPrices = new ArrayList<>();
        userCryptoPrices.forEach(pricePair -> {
            BigDecimal oldCryptoPrice = pricePair.getPrice();
            BigDecimal currentPrice = cryptoPrices.get(pricePair.getSymbol()) == null ? BigDecimal.ZERO : pricePair.getPrice();
            if (getPercentage(oldCryptoPrice, currentPrice).compareTo(new BigDecimal(priceChangeDelta)) > 0) {
                changedPrices.add(new CryptoPriceChangedDto(pricePair.getSymbol(), oldCryptoPrice, currentPrice));
            }
        });
        publishCryptoPriceChangedEvent(userCryptoPrice.getUserId(), changedPrices);
    }

    private void publishCryptoPriceChangedEvent(long userId, List<CryptoPriceChangedDto> changedPrices) {
        CryptoPriceChangedEvent event = new CryptoPriceChangedEvent(this, userId, changedPrices);
        applicationEventPublisher.publishEvent(event);
    }

    private BigDecimal getPercentage(BigDecimal a, BigDecimal b) {
        BigDecimal result = (a.subtract(b)).multiply(BigDecimal.valueOf(100)).divide(a);
        return result.abs();
    }
}
