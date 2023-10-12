package com.crypto.tracker.client;

import com.crypto.tracker.dto.CryptoPriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MexcCryptoCurrencyClient implements CryptoCurrencyClient {
    private final RestTemplate restTemplate;

    @Value("${crypto.api.mexc.get-prices}")
    private String getCryptoPricesUri;

    @Override
    public List<CryptoPriceResponse> getCryptoCurrencyPrices() {
        CryptoPriceResponse[] receivedPrices = restTemplate
                .getForEntity(getCryptoPricesUri, CryptoPriceResponse[].class)
                .getBody();
        List<CryptoPriceResponse> cryptoCurrencyPrices = receivedPrices == null
                ? Collections.emptyList()
                : Arrays.asList(receivedPrices);
        log.info("Received {} crypto currency prices", cryptoCurrencyPrices.size());
        return cryptoCurrencyPrices;
    }
}
