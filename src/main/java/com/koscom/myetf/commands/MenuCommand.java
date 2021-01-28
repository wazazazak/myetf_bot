package com.koscom.myetf.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

public class MenuCommand extends MyetfCommand{
	public MenuCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}

	public void execute()
	{
		
		try {
			CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());
			
			String jsonTxt = new String();
			
			// 0. DB - 보유 주식 수 조회
			/* etfpossession/chatId/account */
			/*
			 *       종목     | 종목코드 | 보유수량
			 * -------------------------------
			 *  KODEX 자동차  | 091180 | 30
			 *  KODEX 반도체  | 091160 | 50
			 *  KODEX 은행   | 091170 | 20
			 */
			
			// 1. 현재 자산 총액 구하기
			// 보유주 * 현재 시세 = 보유주식의 총액
			// + 예수금 총액
			double totalAmt = getTotalAmt(jsonTxt);

	        SendMessage message = new SendMessage();
	        
	        // 금액 포맷팅
	        DecimalFormat formatter = new DecimalFormat("###,###");
	     	String fmTotalAmt = formatter.format(totalAmt);
	        String msgText = new String();
			msgText += "현재 자산 총액은 "+ fmTotalAmt + "원입니다.\n포트폴리오를 확인하시겠습니까?";

			message.setText(msgText);
	        message.setChatId(GetChatId());
	        
	        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
	        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();

	        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
	        rowInline.add(new InlineKeyboardButton().setText("확인하기").setCallbackData(BotCallbackData.myport.name()));
	        rowInline.add(new InlineKeyboardButton().setText("설정하기").setCallbackData(BotCallbackData.setting.name()));
	        rowsInline.add(rowInline);
	        
	        markupInline.setKeyboard(rowsInline);
	        message.setReplyMarkup(markupInline);
			
	        data.strState = BotCallbackData.menu.name();
            m_telebot.execute(message);

			jsonTxt = sendGet("http://localhost:8000/etfportion/" + GetChatId().toString() + "/" + data.strAccount);

			data.mSectorRates.forEach((key, value)
				    -> data.mSectorRates.put(key, 0));
			
        	JSONParser jsonParser = new JSONParser();
			JSONArray jsonarr = (JSONArray)jsonParser.parse(jsonTxt);
			for(int i=0;i<jsonarr.size();i++){
				String subJsonStr = jsonarr.get(i).toString();
				JSONObject subJsonObj = (JSONObject) jsonParser.parse(subJsonStr);
				String sectorCode = subJsonObj.get("sectorCode").toString();
				int sectorRate = (int) Float.parseFloat(subJsonObj.get("sectorPortion").toString());
				data.mSectorRates.put(sectorCode, sectorRate);
			}
			
		} catch (TelegramApiException e) {
			e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
		AnswerQuery();
	}
}
