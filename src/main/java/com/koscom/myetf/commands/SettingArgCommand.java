package com.koscom.myetf.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
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

public class SettingArgCommand extends MyetfCommand{
	public SettingArgCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}
	
	public void execute()
	{
        CallbackQuery callbackquery = m_update.getCallbackQuery();

        CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());
        
        String strCallback = callbackquery.getData().toString().substring(BotCallbackData.settingarg.name().length() + 1);
        
        if(strCallback.compareTo("1") == 0)
        {
        	int total = 0;
        	for(String strKey : data.mSectorRates.keySet())
        	{
        		total += data.mSectorRates.get(strKey);
        	}
        	if(total > 100)
        	{
                SendMessage message = new SendMessage();
                message.setChatId(GetChatId());

                message.setText("총합이 100%가 넘습니다.");
                try {
                    m_telebot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            	SettingCommand settingCommand = new SettingCommand(m_telebot, m_update);
            	settingCommand.execute();
        	}
        	else
        	{
        		if(total < 100)
        		{
                    SendMessage message = new SendMessage();
                    message.setChatId(GetChatId());

                    message.setText("남는 비율은 현금으로 보냅니다.");
                    try {
                        m_telebot.execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }	
                    data.mSectorRates.put("999999", data.mSectorRates.get("999999") + 100 - total);
        		}
        		try
        		{
            		for(String strKey : data.mSectorRates.keySet())
                	{
                		JSONObject jsonObjPossession = new JSONObject();
        				//jsonObj.put("chatId", m_update.getMessage().getChatId());
        				jsonObjPossession.put("chatId"		, GetChatId().toString());
        				jsonObjPossession.put("account"		, data.strAccount);
        				jsonObjPossession.put("sectorCode"	, strKey);
        				jsonObjPossession.put("sectorPortion", (float)data.mSectorRates.get(strKey));
        				String strJsonPossession = jsonObjPossession.toJSONString();
        				String resultJson = new String();
        				String jsonHoldQt = new String();
    					jsonHoldQt = sendGet("http://localhost:8000/etfportion/"+ GetChatId().toString() + "/" + data.strAccount + "/" + strKey);
        				if( StringUtils.isNotBlank(jsonHoldQt) ) {	// 보유
        					
        					// [ETF_POSSESSION] update (PUT)
        					resultJson = sendPut("http://localhost:8000/etfportion", strJsonPossession);
        					
        				} else {	// 미보유
        					
        					// [ETF_POSSESSION] insert (POST)
        					resultJson = sendPost("http://localhost:8000/etfportion", strJsonPossession);
        				}
                	}
        		}
        		catch(Exception e)
        		{
        			
        		}
				
	        	RebalCommand rebalCommand = new RebalCommand(m_telebot, m_update);
	        	rebalCommand.execute();
        	}
        }
        else if(strCallback.compareTo("0") == 0)
        {
        	MenuCommand menuCommand = new MenuCommand(m_telebot, m_update);
        	menuCommand.execute();
        }
        else
        {
            SendMessage message = new SendMessage();
            message.setChatId(GetChatId());

    		String name = getProdName(strCallback);
            message.setText(name + "의 비율을 입력하세요");
            try {
            	data.strState = callbackquery.getData().toString();
                m_telebot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
		AnswerQuery();
	}
}
