package com.crypto.tracker.listener;

import com.crypto.tracker.bot.CryptoPriceTrackingBot;
import com.crypto.tracker.dto.CryptoPriceChangedDto;
import com.crypto.tracker.event.CryptoPriceChangedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;

@Component
public class CryptoPriceChangeListener implements ApplicationListener<CryptoPriceChangedEvent> {
    private static final String NOTIFICATION_TEMPLATE = "Currency '%s' changed from %s to %s";
    private final CryptoPriceTrackingBot bot;
    private final DecimalFormat decimalFormat;

    public CryptoPriceChangeListener(CryptoPriceTrackingBot currencyBot) {
        this.bot = currencyBot;
        decimalFormat = new DecimalFormat("#");
        decimalFormat.setMaximumFractionDigits(12);
    }

    @Override
    public void onApplicationEvent(CryptoPriceChangedEvent event) {
        List<CryptoPriceChangedDto> changedCryptoPrices = event.getChangedCryptoPrices();
        StringBuilder message = new StringBuilder();
        changedCryptoPrices.forEach(changedPrice -> {
            String changedPriceMessage = String.format(
                    NOTIFICATION_TEMPLATE,
                    changedPrice.getCryptoSymbol(),
                    changedPrice.getOldPrice(),
                    changedPrice.getNewPrice()
            );
            message.append(changedPriceMessage).append("\n");
        });
        bot.sendNotification(event.getChatId(), message.toString());
    }
}
