package com.koscom.myetf;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RebalCommand extends MyetfCommand{
	public RebalCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}

	public void execute()
	{
        CallbackQuery callbackquery = m_update.getCallbackQuery();
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText("");
        
        System.out.println("aaaa");
        
        SendMessage message = new SendMessage();
        message.setText("리밸런싱 완료!");
        message.setChatId(callbackquery.getMessage().getChatId());
        
        try {
            m_telebot.execute(answerCallbackQuery);
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
}
