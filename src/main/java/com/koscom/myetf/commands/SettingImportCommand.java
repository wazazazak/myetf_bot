package com.koscom.myetf.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.TelegramMessageBot.BotCallbackData;
import com.koscom.myetf.TelegramMessageBot.CSessionData;

public class SettingImportCommand extends MyetfCommand{
	public SettingImportCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
//		arOrdered.add("226490");
//		arOrdered.add("229200");
//		arOrdered.add("305720");
//		arOrdered.add("091160");
//		arOrdered.add("091170");
//		arOrdered.add("091180");
//		arOrdered.add("102970");
//		arOrdered.add("117460");
//		arOrdered.add("117680");
//		arOrdered.add("117700");
//		arOrdered.add("140700");
//		arOrdered.add("140710");
//		arOrdered.add("102960");
//		arOrdered.add("244580");
//		arOrdered.add("266370");
//		arOrdered.add("266420");
//		arOrdered.add("300950");
//		arOrdered.add("132030");
//		arOrdered.add("152380");
//		arOrdered.add("308620");
//		arOrdered.add("999999");
		
		arOrdered.add("226490");
		arOrdered.add("229200");
		arOrdered.add("305720");
		arOrdered.add("091160");
		arOrdered.add("091170");
		arOrdered.add("091180");
		arOrdered.add("102970");
		arOrdered.add("117460");
		arOrdered.add("117680");
		arOrdered.add("117700");
		arOrdered.add("244580");
		arOrdered.add("266370");
		arOrdered.add("132030");
		arOrdered.add("152380");
		arOrdered.add("308620");
		arOrdered.add("999999");
		
	}
    
	List<String> arOrdered =  new ArrayList<>();
	public void execute()
	{
		try {
			CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());
			
			SendMessage message = new SendMessage();
			
	        message.setChatId(GetChatId());
	        message.setText("공유코드를 입력하세요.");
			
	        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
	        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
	        
	        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
			rowsInline.add(rowInline);
			rowInline = new ArrayList<>();
	        rowsInline.add(rowInline);
	        
	        markupInline.setKeyboard(rowsInline);
	        message.setReplyMarkup(markupInline);

        	data.strState = BotCallbackData.settingimport.name();
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
		AnswerQuery();
	}
}
