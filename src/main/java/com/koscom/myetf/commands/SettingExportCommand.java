package com.koscom.myetf.commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.TelegramMessageBot.CSessionData;

public class SettingExportCommand extends MyetfCommand{
	public SettingExportCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}
    
	public void execute()
	{   
        CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());

        SendMessage message = new SendMessage();

        message.setChatId(GetChatId());
        message.setText("공유코드");

        try {
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        
        MenuCommand cmd = new MenuCommand(m_telebot, m_update);
        cmd.execute();
        
		AnswerQuery();
	}
}
