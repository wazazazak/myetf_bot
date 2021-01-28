package com.koscom.myetf;

import static org.springframework.http.HttpHeaders.USER_AGENT;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.commands.AccountCommand;
import com.koscom.myetf.commands.MenuCommand;
import com.koscom.myetf.commands.PortCommand;
import com.koscom.myetf.commands.RebalCommand;
import com.koscom.myetf.commands.SettingArgCommand;
import com.koscom.myetf.commands.SettingCommand;
import com.koscom.myetf.commands.SettingExportCommand;
import com.koscom.myetf.commands.SettingImportCommand;
import com.koscom.myetf.commands.SettingRateCommand;

@Component
public class TelegramMessageBot extends TelegramLongPollingBot { //
    private final String BOT_NAME = "myetf_bot"; //Bot Name
    private final String AUTH_KEY = "1573207271:AAEJPCeEhVU4O59zVZI2xzZ1T1PebgceaBE"; //Bot Auth-Key

	// 한이
//    private final String BOT_NAME = "myETF_testBot"; //Bot Name
//    private final String AUTH_KEY = "1435740482:AAHP7NH8H_7hNPYhZGe7WcjGUMeQW4rQf9k"; //Bot Auth-Key

	// 기혁
//	private final String BOT_NAME = "myetftestbot"; // Bot Name
//	private final String AUTH_KEY = "1617147400:AAGgo9RAT8JG7nHCSJhuSgRohxyRPjkS3MY"; // Bot Auth-Key


	public TelegramMessageBot() {
		super();
		mSessionData = new HashMap<String, CSessionData>();
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public String getBotUsername() {
		return BOT_NAME;
	}

	@Override
	public String getBotToken() {
		return AUTH_KEY;
	}

	public enum BotCallbackData {
		rebal, myport, account, setting, menu, settingarg, settingtype, settingimport, settingexport, settingend
	}
	
	public class CSessionData
	{
		CSessionData()
		{
			strState = "";
			strAccount = "";
			strSettingCode = "";
			mSectorRates = new HashMap<String, Integer>();
			mSectorRates.put("226490", 0);
			mSectorRates.put("229200", 0);
			mSectorRates.put("305720", 0);
			mSectorRates.put("091160", 0);
			mSectorRates.put("091170", 0);
			mSectorRates.put("091180", 0);
			mSectorRates.put("102970", 0);
			mSectorRates.put("117460", 0);
			mSectorRates.put("117680", 0);
			mSectorRates.put("117700", 0);
			mSectorRates.put("244580", 0);
			mSectorRates.put("266370", 0);
			mSectorRates.put("132030", 0);
			mSectorRates.put("152380", 0);
			mSectorRates.put("308620", 0);
			mSectorRates.put("999999", 100);
		}
		public String strState;
		public String strAccount;
		public String strSettingCode;
		public HashMap<String, Integer> mSectorRates;
	}
	
	public HashMap<String, CSessionData> mSessionData;
	/**
	 * 메세지를 받으면 처리하는 로직
	 * 
	 * @param update
	 */
	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {

//			String urlParameters = Long.toString(update.getMessage().getChatId());
//			try {
//				sendGet("http://localhost:8000/user/auth/" + urlParameters);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			String strChatId = update.getMessage().getChatId().toString();
			if (update.getMessage().getText().compareToIgnoreCase("/start") == 0) {
				mSessionData.put(strChatId, new CSessionData());
				AccountCommand accountCommand = new AccountCommand(this, update);
				accountCommand.execute();
			}
			else if(mSessionData.get(strChatId) != null && mSessionData.get(strChatId).strState.contains(BotCallbackData.settingarg.name()))
			{
				SettingRateCommand srCommand = new SettingRateCommand(this, update);
				srCommand.execute();
			}
			else
			{
	            SendMessage message = new SendMessage();
	            message.setChatId(update.getMessage().getChatId());
	            message.setText("잘못된 명령입니다.\n/Start");
	            try {
	                execute(message);
	            } catch (TelegramApiException e) {
	                e.printStackTrace();
	            }
			}
		}
		if (update.hasCallbackQuery()) {
			
			CallbackQuery callbackquery = update.getCallbackQuery();
			String stringMessage = callbackquery.getData();

			String strChatId = callbackquery.getMessage().getChatId().toString();
			if(!mSessionData.containsKey(strChatId)) return;
			CSessionData data = mSessionData.get(strChatId);
			data.strState.compareTo(BotCallbackData.account.name());
			
			if (StringUtils.left(stringMessage, BotCallbackData.menu.name().length())
					.compareToIgnoreCase(BotCallbackData.menu.name()) == 0 
					&& (data.strState.compareTo(BotCallbackData.account.name()) == 0
					|| data.strState.compareTo(BotCallbackData.myport.name()) == 0)) {
				data.strAccount = stringMessage.substring(BotCallbackData.menu.name().length() + 1);
				MenuCommand menuCommand = new MenuCommand(this, update);
				menuCommand.execute();
			}
			else if (StringUtils.left(stringMessage, BotCallbackData.settingarg.name().length() + 1)
					.compareToIgnoreCase(BotCallbackData.settingarg.name() + ":") == 0
					&& data.strState.compareTo(BotCallbackData.setting.name()) == 0) {
				data.strSettingCode = stringMessage.substring(BotCallbackData.settingarg.name().length() + 1);
				SettingArgCommand settingArgCommand = new SettingArgCommand(this, update);
				settingArgCommand.execute();
			} 
			else if (BotCallbackData.rebal.name().compareToIgnoreCase(stringMessage) == 0
					&& (data.strState.compareTo(BotCallbackData.myport.name()) == 0
					|| data.strState.compareTo(BotCallbackData.settingarg.name()) == 0)) {
				RebalCommand rebalCommand = new RebalCommand(this, update);
				rebalCommand.execute();
			}
			else if (BotCallbackData.myport.name().compareToIgnoreCase(stringMessage) == 0
					&& (data.strState.compareTo(BotCallbackData.menu.name()) == 0
					|| data.strState.compareTo(BotCallbackData.settingend.name()) == 0)) {
				PortCommand portCommand = new PortCommand(this, update);
				portCommand.execute();
			}
			else if (BotCallbackData.setting.name().compareToIgnoreCase(stringMessage) == 0
					&& (data.strState.compareTo(BotCallbackData.menu.name()) == 0
					|| data.strState.compareTo(BotCallbackData.settingarg.name()) == 0)) {
				SettingCommand settingCommand = new SettingCommand(this, update);
				settingCommand.execute();
			}
			else if (BotCallbackData.settingexport.name().compareToIgnoreCase(stringMessage) == 0
					&& data.strState.compareTo(BotCallbackData.settingend.name()) == 0) {
				SettingExportCommand settingExCommand = new SettingExportCommand(this, update);
				settingExCommand.execute();
			}
			else if (BotCallbackData.settingimport.name().compareToIgnoreCase(stringMessage) == 0
					&& data.strState.compareTo(BotCallbackData.menu.name()) == 0) {
				SettingImportCommand settingImCommand = new SettingImportCommand(this, update);
				settingImCommand.execute();
			}
			else {
				AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
				answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
				answerCallbackQuery.setShowAlert(false);
				answerCallbackQuery.setText("");
				try {
					execute(answerCallbackQuery);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	protected String sendGet(String targetUrl) throws Exception {
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        System.out.println("HTTP 응답 코드 : " + responseCode);
        System.out.println("HTTP body : " + response.toString());
        
    	// JSON 문자열 JAVA로 변환
        String jsonTxt = response.toString();
        
        return jsonTxt;
    }
}