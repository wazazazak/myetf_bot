package com.koscom.myetf.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

public class SettingImportExeCommand extends MyetfCommand{
	public SettingImportExeCommand(TelegramLongPollingBot telebot, Update update) {
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
		arOrdered.add("140700");
		arOrdered.add("140710");
		arOrdered.add("102960");
		arOrdered.add("244580");
		arOrdered.add("266370");
		arOrdered.add("266420");
		arOrdered.add("300950");
		arOrdered.add("132030");
		arOrdered.add("152380");
		arOrdered.add("308620");
		arOrdered.add("251350");
		arOrdered.add("999999");
	}
    
	List<String> arOrdered =  new ArrayList<>();
	public void execute()
	{
		try {
			CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());
			
			String portfolioKey = new String();
			portfolioKey = m_update.getMessage().getText();
			
			/** [PORTFOLIO] 공유코드로 포트폴리오 정보 가져오기 */
			String jsonTxt = new String();
			jsonTxt = sendGet("http://localhost:8000/portfoliokey/" + portfolioKey);
			System.out.println("[PORTFOLIO] 공유코드로 포트폴리오 정보 가져오기 >> " + jsonTxt);
			
            if( StringUtils.isNotBlank(jsonTxt) ) {
            	JSONParser jsonParser = new JSONParser();
            	Object resultObj = jsonParser.parse(jsonTxt);
            	JSONObject resultObject = (JSONObject) resultObj;
            	
            	String sectorCodeGroup = resultObject.get("sectorCodeGroup").toString();
            	String sectorPortionGroup = resultObject.get("sectorPortionGroup").toString();
            	System.out.println("sectorCodeGroup >> " + sectorCodeGroup);
            	System.out.println("sectorPortionGroup >> " + sectorPortionGroup);
            	
            	for(String strKey : data.mSectorRates.keySet())
            	{
            		data.mSectorRates.put(strKey, 0);
            	}
            	String[] sectorCodeGroupArr = StringUtils.split(sectorCodeGroup, '|');
            	String[] sectorPortionGroupArr = StringUtils.split(sectorPortionGroup, '|');
            	for(int i=0; i<sectorCodeGroupArr.length; i++ ) {
            		System.out.println(i + "번째 sectorCode >> " + sectorCodeGroupArr[i]);
                	System.out.println(i + "번째 sectorPortion >> " + Float.parseFloat(sectorPortionGroupArr[i]));
                	data.mSectorRates.put(sectorCodeGroupArr[i], (int) Float.parseFloat(sectorPortionGroupArr[i]));
            	}
            	
            	data.strState = BotCallbackData.setting.name();
            	SettingCommand cmd = new SettingCommand(m_telebot, m_update);
                cmd.execute();
            	
            } else {
            	
            	System.out.println("존재하지 않는 포트폴리오 공유코드입니다.");
                SendMessage message = new SendMessage();
    	        message.setChatId(GetChatId());
    	        message.setText("존재하지 않는 포트폴리오 공유코드입니다.");
    			
    	        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
    	        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
    	        
    	        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
    			rowsInline.add(rowInline);
    			rowInline = new ArrayList<>();
    	        rowsInline.add(rowInline);
    	        
    	        markupInline.setKeyboard(rowsInline);
    	        message.setReplyMarkup(markupInline);
    	        m_telebot.execute(message);
    	        
    	        data.strState = BotCallbackData.account.name();
    	        MenuCommand cmd = new MenuCommand(m_telebot, m_update);
    	        cmd.execute();
    	        
            }
            
            
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
		AnswerQuery();
	}
}
