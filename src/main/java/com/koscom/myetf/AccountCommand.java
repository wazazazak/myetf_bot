package com.koscom.myetf;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.TelegramMessageBot.BotCallbackData;

public class AccountCommand extends MyetfCommand{
	public AccountCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}

	public void execute()
	{
        SendMessage message = new SendMessage();

        message.setChatId(m_update.getMessage().getChatId());
        message.setText("MYETF\n계좌선택");
        
        List<String> arAccount = new ArrayList<>();
        arAccount.add("키움증권 110123213123");
        arAccount.add("미래에셋증권 11011123314");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
        for(String strAccount : arAccount)
        {
            List < InlineKeyboardButton > rowInline = new ArrayList < > ();
            rowInline.add(new InlineKeyboardButton().setText(strAccount).setCallbackData(BotCallbackData.menu.name() + ":" + strAccount));
            rowsInline.add(rowInline);
        }
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
}
