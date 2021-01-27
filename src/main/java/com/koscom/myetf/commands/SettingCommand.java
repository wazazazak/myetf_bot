package com.koscom.myetf.commands;

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
		arSectorCodes = new ArrayList<>();
		arSectorCodes.add("226490");
		arSectorCodes.add("229200");
		arSectorCodes.add("305720");
		arSectorCodes.add("091160");
		arSectorCodes.add("091170");
		arSectorCodes.add("091180");
		arSectorCodes.add("102970");
		arSectorCodes.add("117460");
		arSectorCodes.add("117680");
		arSectorCodes.add("117700");
		arSectorCodes.add("140700");
		arSectorCodes.add("140710");
		arSectorCodes.add("102960");
		arSectorCodes.add("244580");
		arSectorCodes.add("266370");
		arSectorCodes.add("266420");
		arSectorCodes.add("300950");
		arSectorCodes.add("132030");
		arSectorCodes.add("152380");
		arSectorCodes.add("308620");
	}

	List<String> arSectorCodes;
	
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

        for(int i = 0; i < Math.ceil(arSectorCodes.size() / 2.f); ++i)
        {
	        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
        	for(int k = 0; k < 2; ++k)
        	{
        		if(arSectorCodes.size() <= i * 2 + k) break;
        		String code = arSectorCodes.get(i * 2 + k);
        		String name = getProdName(code).substring(6);
        		int nRate = 0;
        		name += ( " (" + nRate + "%)");
    	        rowInline.add(new InlineKeyboardButton().setText(name).setCallbackData(BotCallbackData.settingarg.name() + ":" + code));
        	}
	        rowsInline.add(rowInline);
        }
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
        rowInline.add(new InlineKeyboardButton().setText("현금").setCallbackData(BotCallbackData.settingarg.name() + ":999999"));
        rowInline.add(new InlineKeyboardButton().setText("저장").setCallbackData(BotCallbackData.settingarg.name() + ":1"));
        rowInline.add(new InlineKeyboardButton().setText("취소").setCallbackData(BotCallbackData.settingarg.name() + ":0"));
        rowsInline.add(rowInline);
        
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
        	m_telebot.execute(answerCallbackQuery);
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
}
