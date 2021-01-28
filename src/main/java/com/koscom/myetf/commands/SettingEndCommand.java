package com.koscom.myetf.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.util.Pair;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.TelegramMessageBot.BotCallbackData;
import com.koscom.myetf.TelegramMessageBot.CSessionData;
import com.koscom.myetf.commands.PortCommand.sector;

public class SettingEndCommand extends MyetfCommand{
	public SettingEndCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}
    
	public void execute()
	{   
        CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());

        SendMessage message = new SendMessage();

        message.setChatId(GetChatId());
        message.setText("해당 포트폴리오 구성을 공유하시겠습니까?");
        
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
        
    	rowInline.add(new InlineKeyboardButton().setText("네").setCallbackData(BotCallbackData.settingexport.name()));
    	rowInline.add(new InlineKeyboardButton().setText("아니오").setCallbackData(BotCallbackData.myport.name()));
        rowsInline.add(rowInline);
        
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
        	data.strState = BotCallbackData.settingend.name();
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		AnswerQuery();
	}
}
