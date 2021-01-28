package com.koscom.myetf.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.koscom.myetf.entity.User;

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
import com.koscom.myetf.commands.PortCommand.sector;

import javax.servlet.http.HttpSession;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class AccountCommand extends MyetfCommand {
    private User user;

    public AccountCommand(TelegramLongPollingBot telebot, Update update) {
        super(telebot, update);
        // TODO Auto-generated constructor stub
    }

    public void execute() {
        SendMessage message = new SendMessage();

        message.setChatId(GetChatId());
        message.setText("MYETF\n계좌선택");
        try { // get method test
//            System.out.println(user.getChatId());
        	String jsonTxt = new String();
			jsonTxt = sendGet("http://localhost:8000/user/" + GetChatId());
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonarr = (JSONArray)jsonParser.parse(jsonTxt);
	        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
	        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
			for(int i=0;i<jsonarr.size();i++){
				String subJsonStr = jsonarr.get(i).toString();
				JSONObject subJsonObj = (JSONObject) jsonParser.parse(subJsonStr);
				String account = subJsonObj.get("account").toString();
				String accountName = subJsonObj.get("accountName").toString();

	            List<InlineKeyboardButton> rowInline = new ArrayList<>();
	            rowInline.add(new InlineKeyboardButton().setText(accountName + " " + account).setCallbackData(BotCallbackData.menu.name() + ":" + account));
	            rowsInline.add(rowInline);
			}
	        markupInline.setKeyboard(rowsInline);
	        message.setReplyMarkup(markupInline);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
        	m_telebot.mSessionData.get(GetChatId().toString()).strState = BotCallbackData.account.name();
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
