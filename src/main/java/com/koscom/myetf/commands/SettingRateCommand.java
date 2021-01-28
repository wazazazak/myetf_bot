package com.koscom.myetf.commands;

import java.awt.Color;
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

public class SettingRateCommand extends MyetfCommand {
    private User user;

    public SettingRateCommand(TelegramLongPollingBot telebot, Update update) {
        super(telebot, update);
        // TODO Auto-generated constructor stub
    }

    public void execute() {

        String strCode = m_telebot.mSessionData.get(GetChatId().toString()).strState.substring(BotCallbackData.settingarg.name().length() + 1);

        SendMessage message = new SendMessage();
        message.setChatId(GetChatId());
        String strMessage = m_update.getMessage().getText();
        int rate = 0;
        boolean fail = false;
        try
        {
        	rate = Integer.parseInt(strMessage);
            if(rate < 0 || rate > 100) fail = true;        	
        } catch(Exception e) {
            fail = true;
        }
        
        if(fail)
        {
            message.setText("0 ~ 100 사이 숫자를 입력하세요!");
            
            try {
                m_telebot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }	
        }
        else
        {
        	m_telebot.mSessionData.get(GetChatId().toString()).mSectorRates.put(strCode, rate);
        	SettingCommand cmd = new SettingCommand(m_telebot, m_update);
        	cmd.execute();
        }
    }
}
