package com.crypto.tracker.bot;

import com.crypto.tracker.config.TelegramConfigProperties;
import com.crypto.tracker.service.UserCryptoCurrencyService;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class CryptoPriceTrackingBot extends AbilityBot {
    private final TelegramConfigProperties tgProperties;
    private final ResponseHandler responseHandler;
    private final UserCryptoCurrencyService userCryptoCurrencyService;

    public CryptoPriceTrackingBot(
            TelegramConfigProperties tgProperties,
            UserCryptoCurrencyService userCryptoCurrencyService
    ) {
        super(tgProperties.getToken(), tgProperties.getName());
        this.tgProperties = tgProperties;
        this.userCryptoCurrencyService = userCryptoCurrencyService;
        this.responseHandler = new ResponseHandler(silent, db);
    }

    public Ability startBot() {
        return Ability
                .builder()
                .name("start")
                .info("Start the bot")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> start(ctx.chatId()))
                .build();
    }

    public Ability stopBot() {
        return Ability
                .builder()
                .name("stop")
                .info("Stop the bot")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> stop(ctx.chatId()))
                .build();
    }

    public Ability restartBot() {
        return Ability
                .builder()
                .name("restart")
                .info("Restart the bot")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> start(ctx.chatId()))
                .build();
    }

    private void start(long chatId) {
        if (tgProperties.getUserLimit() == responseHandler.getChats().size()) {
            responseHandler.replyText(chatId, "Sorry, the bot is currently unavailable. Try again later.");
            return;
        }
        userCryptoCurrencyService.saveUserCryptoPrice(chatId);
        responseHandler.replyToStart(chatId);
    }

    private void stop(long chatId) {
        userCryptoCurrencyService.removeUserCryptoPrice(chatId);
        responseHandler.stopChat(chatId);
    }

    public void sendNotification(Long chatId, String message) {
        responseHandler.replyText(chatId, message);
    }

    @Override
    public long creatorId() {
        return 1L;
    }
}
