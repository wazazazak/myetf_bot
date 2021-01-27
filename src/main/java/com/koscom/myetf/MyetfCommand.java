package com.koscom.myetf;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class MyetfCommand {
	public MyetfCommand(TelegramLongPollingBot telebot, Update update) {
		m_telebot = telebot;
		m_update = update;
    }
	
	TelegramLongPollingBot m_telebot;
	Update m_update;
	public abstract void execute();
}
