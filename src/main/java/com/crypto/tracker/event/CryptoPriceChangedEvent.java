package com.crypto.tracker.event;

import com.crypto.tracker.dto.CryptoPriceChangedDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class CryptoPriceChangedEvent extends ApplicationEvent {
    private long chatId;
    private List<CryptoPriceChangedDto> changedCryptoPrices;

    public CryptoPriceChangedEvent(
            Object source,
            long chatId,
            List<CryptoPriceChangedDto> changedCryptoPrices
    ) {
        super(source);
        this.chatId = chatId;
        this.changedCryptoPrices = changedCryptoPrices;
    }
}
