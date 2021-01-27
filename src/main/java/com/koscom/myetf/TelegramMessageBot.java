package com.koscom.myetf;

import static org.springframework.http.HttpHeaders.USER_AGENT;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.commands.AccountCommand;
import com.koscom.myetf.commands.MenuCommand;
import com.koscom.myetf.commands.PortCommand;
import com.koscom.myetf.commands.RebalCommand;
import com.koscom.myetf.commands.SettingCommand;

@Component
public class TelegramMessageBot extends TelegramLongPollingBot { //
//    private final String BOT_NAME = "myetf_bot"; //Bot Name
//    private final String AUTH_KEY = "1573207271:AAEJPCeEhVU4O59zVZI2xzZ1T1PebgceaBE"; //Bot Auth-Key

	// 한이
    private final String BOT_NAME = "myETF_testBot"; //Bot Name
    private final String AUTH_KEY = "1435740482:AAHP7NH8H_7hNPYhZGe7WcjGUMeQW4rQf9k"; //Bot Auth-Key

	// 기혁
//	private final String BOT_NAME = "myetftestbot"; // Bot Name
//	private final String AUTH_KEY = "1617147400:AAGgo9RAT8JG7nHCSJhuSgRohxyRPjkS3MY"; // Bot Auth-Key

	@Override
	public String getBotUsername() {
		return BOT_NAME;
	}

	@Override
	public String getBotToken() {
		return AUTH_KEY;
	}

	public enum BotCallbackData {
		rebal, myport, account, setting, menu
	}

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
			if (update.getMessage().getText().compareToIgnoreCase("/start") == 0) {
				AccountCommand accountCommand = new AccountCommand(this, update);
				accountCommand.execute();
			}
		}
		if (update.hasCallbackQuery()) {
			CallbackQuery callbackquery = update.getCallbackQuery();
			String stringMessage = callbackquery.getData();

			if (StringUtils.left(stringMessage, BotCallbackData.menu.name().length() + 1)
					.compareToIgnoreCase(BotCallbackData.menu.name() + ":") == 0) {
				MenuCommand menuCommand = new MenuCommand(this, update);
				menuCommand.execute();
			} else if (BotCallbackData.rebal.name().compareToIgnoreCase(stringMessage) == 0) {
				RebalCommand rebalCommand = new RebalCommand(this, update);
				rebalCommand.execute();
			} else if (BotCallbackData.myport.name().compareToIgnoreCase(stringMessage) == 0) {
				PortCommand portCommand = new PortCommand(this, update);
				portCommand.execute();
			} else if (BotCallbackData.setting.name().compareToIgnoreCase(stringMessage) == 0) {
				SettingCommand settingCommand = new SettingCommand(this, update);
				settingCommand.execute();
			} else {
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