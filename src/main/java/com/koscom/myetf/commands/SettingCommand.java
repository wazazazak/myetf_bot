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

public class SettingCommand extends MyetfCommand{
	public SettingCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
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
		arOrdered.add("251350");
		arOrdered.add("999999");
	}
    
    List<String> arOrdered =  new ArrayList<>();
	public void execute()
	{   
        CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());

        SendMessage message = new SendMessage();

        message.setChatId(GetChatId());
        message.setText("수정하고자하는 항목을 선택하세요.");
        
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
        int count = 0;
        for(String strKey : arOrdered){ //저장된 key값 확인
        	if(count % 2 == 0)
        	{
        		if(!rowInline.isEmpty())
        		{
            		rowsInline.add(rowInline);
            		rowInline = new ArrayList<>();
        		}
        	}
        	++count;
    		String name = getProdName(strKey);
    		if(name.contains("KODEX ")) name = name.substring(6);
    		name += ( " (" + data.mSectorRates.get(strKey) + "%)");
	        rowInline.add(new InlineKeyboardButton().setText(name).setCallbackData(BotCallbackData.settingarg.name() + ":" + strKey));
        }

		rowsInline.add(rowInline);
		rowInline = new ArrayList<>();
    	rowInline.add(new InlineKeyboardButton().setText("저장").setCallbackData(BotCallbackData.settingarg.name() + ":1"));
    	rowInline.add(new InlineKeyboardButton().setText("취소").setCallbackData(BotCallbackData.settingarg.name() + ":0"));
        rowsInline.add(rowInline);
        
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
        	data.strState = BotCallbackData.setting.name();
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		AnswerQuery();
	}
}
