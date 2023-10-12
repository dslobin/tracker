package com.crypto.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CryptoPriceChangedDto {
    private String cryptoSymbol;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
}
