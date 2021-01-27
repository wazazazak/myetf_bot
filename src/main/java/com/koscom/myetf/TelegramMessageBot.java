package com.koscom.myetf;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramMessageBot extends TelegramLongPollingBot { //
    private final String BOT_NAME = "myetf_bot"; //Bot Name
    private final String AUTH_KEY = "1573207271:AAEJPCeEhVU4O59zVZI2xzZ1T1PebgceaBE"; //Bot Auth-Key

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return AUTH_KEY;
    }

    enum BotCallbackData {
    	rebal, myport, account, setting, menu
    }
    
    /**
     * 메세지를 받으면 처리하는 로직
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

        	if(update.getMessage().getText().compareToIgnoreCase("/start") == 0)
        	{
            	AccountCommand accountCommand = new AccountCommand(this, update);
            	accountCommand.execute();      		
        	}
        }
        if(update.hasCallbackQuery())
        {
            CallbackQuery callbackquery = update.getCallbackQuery();
            String stringMessage = callbackquery.getData();
            
            if(StringUtils.left(stringMessage, BotCallbackData.menu.name().length() + 1).compareToIgnoreCase(BotCallbackData.menu.name() + ":") == 0)
            {
            	MenuCommand menuCommand = new MenuCommand(this, update);
            	menuCommand.execute();
            }
            else if(BotCallbackData.rebal.name().compareToIgnoreCase(stringMessage) == 0)
            {
            	RebalCommand rebalCommand = new RebalCommand(this, update);
            	rebalCommand.execute();
            }
            else if(BotCallbackData.myport.name().compareToIgnoreCase(stringMessage) == 0)
            {
            	PortCommand portCommand = new PortCommand(this, update);
            	portCommand.execute();
            }
            else if(BotCallbackData.setting.name().compareToIgnoreCase(stringMessage) == 0)
            {
            	SettingCommand settingCommand = new SettingCommand(this, update);
            	settingCommand.execute();
            }
        }
    }
}