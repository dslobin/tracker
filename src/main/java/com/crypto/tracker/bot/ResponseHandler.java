package com.crypto.tracker.bot;

import lombok.Getter;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Set;

@Getter
public class ResponseHandler {
    private final SilentSender sender;
    private final DBContext dbContext;
    private Set<Long> chats;

    public ResponseHandler(SilentSender sender, DBContext dbContext) {
        this.sender = sender;
        this.dbContext = dbContext;
        this.chats = dbContext.getSet("HashSet");
    }

    public void replyToStart(Long chatId) {
        sender.execute(createMsg(chatId, "Welcome to the cryptocurrency prices tracking bot!"));
        chats.add(chatId);
    }

    public void stopChat(long chatId) {
        chats.remove(chatId);
        sender.execute(createMsg(chatId, "Bye!"));
    }

    public void replyText(long chatId, String message) {
        sender.execute(createMsg(chatId, message));
    }

    private SendMessage createMsg(Long chatId, String value) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(value);
        return sendMessage;
    }
}
