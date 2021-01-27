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

import com.koscom.myetf.TelegramMessageBot.BotCallbackData;

public class SettingCommand extends MyetfCommand{
	public SettingCommand(TelegramLongPollingBot telebot, Update update) {
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

        SendMessage message = new SendMessage();

        message.setChatId(callbackquery.getMessage().getChatId());
        message.setText("세팅하세용!");
        
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();

        {
	        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
	        rowInline.add(new InlineKeyboardButton().setText("자동차(0%)").setCallbackData("1"));
	        rowInline.add(new InlineKeyboardButton().setText("반도체(0%)").setCallbackData("2"));
	        rowInline.add(new InlineKeyboardButton().setText("건강(0%)").setCallbackData("2"));
	        rowsInline.add(rowInline);
        }
        {
	        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
	        rowInline.add(new InlineKeyboardButton().setText("자동차(0%)").setCallbackData("1"));
	        rowInline.add(new InlineKeyboardButton().setText("반도체(0%)").setCallbackData("2"));
	        rowInline.add(new InlineKeyboardButton().setText("건강(0%)").setCallbackData("2"));
	        rowsInline.add(rowInline);
        }
        {
	        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
	        rowInline.add(new InlineKeyboardButton().setText("자동차(0%)").setCallbackData("1"));
	        rowInline.add(new InlineKeyboardButton().setText("반도체(0%)").setCallbackData("2"));
	        rowInline.add(new InlineKeyboardButton().setText("건강(0%)").setCallbackData("2"));
	        rowsInline.add(rowInline);
        }
        {
	        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
	        rowInline.add(new InlineKeyboardButton().setText("확인").setCallbackData(BotCallbackData.menu.name()));
	        rowInline.add(new InlineKeyboardButton().setText("취소").setCallbackData(BotCallbackData.menu.name()));
	        rowsInline.add(rowInline);
        }
        
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
}
