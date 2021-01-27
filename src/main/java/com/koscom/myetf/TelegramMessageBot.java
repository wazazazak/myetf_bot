package com.koscom.myetf;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramMessageBot extends TelegramLongPollingBot { //
    private final String BOT_NAME = "myetf_bot"; //Bot Name
    private final String AUTH_KEY = "1573207271:AAEJPCeEhVU4O59zVZI2xzZ1T1PebgceaBE"; //Bot Auth-Key
    private final String CHAT_ID = "1560682736"; //Chat ID

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return AUTH_KEY;
    }

    /**
     * 메세지를 받으면 처리하는 로직
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            //TODO:컴포넌트 및 메서드 분리
            //String stringMessage = update.getMessage().getText();
            SendMessage message = new SendMessage();

            message.setChatId(update.getMessage().getChatId());
            message.setText("MYETF");

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
            List < InlineKeyboardButton > rowInline = new ArrayList < > ();
            rowInline.add(new InlineKeyboardButton().setText("내 포트폴리오 확인").setCallbackData("1"));
            rowInline.add(new InlineKeyboardButton().setText("리밸런싱").setCallbackData("2"));
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if(update.hasCallbackQuery())
        {
            CallbackQuery callbackquery = update.getCallbackQuery();
            String stringMessage = callbackquery.getData();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
            answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
            answerCallbackQuery.setShowAlert(false);
            answerCallbackQuery.setText(stringMessage);

            SendMessage message = new SendMessage();
            message.setChatId(callbackquery.getMessage().getChatId());
            message.setText(stringMessage);
            try {
                execute(answerCallbackQuery);
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 메세지 전달
     * @param sendMessage
     */
    public void sendMessage(String sendMessage) {
        SendMessage message = new SendMessage()
                .setChatId(CHAT_ID)
                .setText(sendMessage);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
