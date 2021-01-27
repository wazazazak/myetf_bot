package com.koscom.myetf.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

import com.koscom.myetf.commands.MyetfCommand;

public class PriceCommand extends MyetfCommand {
	public PriceCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}

	public void execute()
	{
		String price = new String();	// 현재 시세 
		
		// 코스콤 API -> 시세 정보 조회
        BufferedReader br = null;
        try {
        	String urlstr = "https://sandbox-apigw.koscom.co.kr/v2/market/stocks"
        			+ "/kospi/117700/price"
        			+ "?apikey=l7xx269a193d8850413389aab28e01032509";
        	URL url = new URL(urlstr);
        	HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
        	urlconnection.setRequestMethod("GET");
        	br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
        	
        	String json = new String();
        	String line = new String();
        	
        	while((line = br.readLine()) != null) {
        		json = json + line + "\n";
        	}
        	
        	//System.out.println("json >> " + json);
        	
        	// JSON 문자열 JAVA로 변환
        	JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;
            //System.out.println("result >> " + jsonObject.get("result"));
            
            String result = jsonObject.get("result").toString();
            Object resultObj = jsonParser.parse(result);
            JSONObject resultObject = (JSONObject) resultObj;
            //System.out.println("trdPrc >> " + resultObject.get("trdPrc"));
            price = resultObject.get("trdPrc").toString();
        	
        } catch (Exception e) {
        	System.out.println(e.getMessage());
		}
        
        
        CallbackQuery callbackquery = m_update.getCallbackQuery();
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText("");
        
        SendMessage message = new SendMessage();
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
//        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
//        rowInline.add(new InlineKeyboardButton().setText("ㅇㅇ").setCallbackData("1"));
//        rowsInline.add(rowInline);
//        markupInline.setKeyboard(rowsInline);
//        message.setReplyMarkup(markupInline);
        message.setChatId(callbackquery.getMessage().getChatId());
        message.setText("현재 시세는 "+ price + "원입니다.");
        
        
        try {
            m_telebot.execute(answerCallbackQuery);
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
}
