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

public class SettingTypeCommand extends MyetfCommand{
	public SettingTypeCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}
    
	public void execute()
	{   
        CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());

        SendMessage message = new SendMessage();

        message.setChatId(GetChatId());
        message.setText("입력 방식을 선택하세요.");
        
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
        
    	rowInline.add(new InlineKeyboardButton().setText("직접입력").setCallbackData(BotCallbackData.setting.name()));
    	rowInline.add(new InlineKeyboardButton().setText("가져오기").setCallbackData(BotCallbackData.settingimport.name()));
        rowsInline.add(rowInline);
        
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
        	data.strState = BotCallbackData.settingtype.name();
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		AnswerQuery();
	}
}
