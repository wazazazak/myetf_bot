package com.koscom.myetf.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.TelegramMessageBot.CSessionData;

public class SettingImportCommand extends MyetfCommand{
	public SettingImportCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}
    
	public void execute()
	{
		
		try {
			
//			CallbackQuery callbackquery = m_update.getCallbackQuery();
//	        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//	        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
//	        answerCallbackQuery.setShowAlert(false);
//	        answerCallbackQuery.setText("");
	        CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());
	        
	        
	        /** portfolioKey 생성 */
	        // chatId+시분초(yyyyMMddHHmmss)
	        String portfolioKey = new String();
	        
	        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
			// 년월일시분초 14자리 포멧
			SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss");
			String createTime = fourteen_format.format(date_now);
			System.out.println("createTime >> " + createTime); // 14자리 포멧으로 출력한다
			
			portfolioKey = GetChatId().toString() + createTime;
	        
			
			/** sectorCodeGroup 생성 */
			String sectorCodeGroup = new String();
			for(String strKey : data.mSectorRates.keySet())
        	{
				sectorCodeGroup += strKey + "|";
        	}
			sectorCodeGroup = sectorCodeGroup.substring(0, sectorCodeGroup.length()-1);
			
			/** sectorPortionGroup 생성 */
			String sectorPortionGroup = new String();
			for(String strKey : data.mSectorRates.keySet())
        	{
				sectorPortionGroup += (float)data.mSectorRates.get(strKey) + "|";
        	}
			sectorPortionGroup = sectorPortionGroup.substring(0, sectorPortionGroup.length()-1);
			
//	        {
//        	  "chatId": "1502506769",
//        	  "portfolioKey": "1111",
//        	  "portfolioName": "김성수 포트폴리오",
//        	  "sectorCodeGroup": "091180|091160|091170|999999",
//        	  "sectorPortionGroup": "30.0|50.0|15.0|5.0"
//        	}
	        
	        JSONObject jsonObjPortfolio = new JSONObject();
			jsonObjPortfolio.put("chatId"				, GetChatId().toString());
			jsonObjPortfolio.put("portfolioKey"			, portfolioKey);
			//jsonObjPortfolio.put("portfolioName"		, GetChatId().toString()+);
			jsonObjPortfolio.put("sectorCodeGroup"		, sectorCodeGroup);	// 091180|091160|091170|999999
			jsonObjPortfolio.put("sectorPortionGroup"	, sectorPortionGroup);	// 30.0|50.0|15.0|5.0
			String strJsonTran = jsonObjPortfolio.toJSONString();
			
			// [PORTFOLIO] insert (POST)
			String resultJson = sendPost("http://localhost:8000/portfolio", strJsonTran);
			System.out.println("포트폴리오 저장 완료 >> " + resultJson);
	
	        SendMessage message = new SendMessage();
	        message.setChatId(GetChatId());
	        message.setText("공유코드 : " + portfolioKey);
	
	        try {
	            m_telebot.execute(message);
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	        
		} catch (TelegramApiException e) {
			e.printStackTrace();
	    } catch (Exception e){
	        e.printStackTrace();
	    }
		AnswerQuery();
	}
}
