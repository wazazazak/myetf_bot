package com.koscom.myetf.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.TelegramMessageBot.BotCallbackData;

public class SettingArgCommand extends MyetfCommand{
	public SettingArgCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}
	
	public void execute()
	{
        CallbackQuery callbackquery = m_update.getCallbackQuery();

        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText("");
        
        String strCallback = callbackquery.getData().toString().substring(BotCallbackData.settingarg.name().length() + 1);
        
        if(strCallback.compareTo("1") == 0)
        {
        	RebalCommand rebalCommand = new RebalCommand(m_telebot, m_update);
        	rebalCommand.execute();
        }
        else if(strCallback.compareTo("0") == 0)
        {
        	MenuCommand menuCommand = new MenuCommand(m_telebot, m_update);
        	menuCommand.execute();
        }
        else
        {
            SendMessage message = new SendMessage();
            message.setChatId(callbackquery.getMessage().getChatId());

    		String name = getProdName(strCallback);
            message.setText(name + "의 비율을 입력하세요");
            try {
                m_telebot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
	}
}
