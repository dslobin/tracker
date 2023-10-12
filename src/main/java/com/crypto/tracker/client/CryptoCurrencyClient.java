package com.crypto.tracker.client;

import com.crypto.tracker.dto.CryptoPriceResponse;

import java.util.List;

public interface CryptoCurrencyClient {
    List<CryptoPriceResponse> getCryptoCurrencyPrices();
}
