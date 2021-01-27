package commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PortCommand extends MyetfCommand{
	public PortCommand(TelegramLongPollingBot telebot, Update update) {
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
        
        System.out.println("aaaa");
        
        SendMessage message = new SendMessage();
        message.setChatId(callbackquery.getMessage().getChatId());
        message.setText("님의 포트폴리오는 이렇숩니다");
        
        try {
            m_telebot.execute(answerCallbackQuery);
            m_telebot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
}
