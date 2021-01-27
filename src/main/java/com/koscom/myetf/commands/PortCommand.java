package com.koscom.myetf.commands;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.TelegramMessageBot.BotCallbackData;

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
        
        List<sector> arSector = new ArrayList<>();
        arSector.add(new sector(20, "자동차", new Color(0xFF6D63)));
        arSector.add(new sector(15, "반도체", new Color(0xFF9652)));
        arSector.add(new sector(7, "건강", new Color(0xFFCE43)));
        arSector.add(new sector(8, "은행", new Color(0xFFE700)));
        arSector.add(new sector(6, "에너지화학", new Color(0xBEE74B)));
        arSector.add(new sector(5, "철강", new Color(0x73D26C)));
        arSector.add(new sector(4, "미디어통신", new Color(0x63EBDD)));
        arSector.add(new sector(32, "건설", new Color(0x73B5E5)));
        arSector.add(new sector(3, "증권", new Color(0xA58BD5)));
        String fileName = new String(RandomStringUtils.randomAlphanumeric(10) + ".png");
        try {
			saveImage(fileName, arSector);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
        
        SendPhoto photo = new SendPhoto();
        photo.setChatId(callbackquery.getMessage().getChatId());
        photo.setCaption("포트폴리오");
        File file = new File(fileName);
        photo.setPhoto(file);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
        rowInline.add(new InlineKeyboardButton().setText("리밸런싱하시겠습니까?").setCallbackData(BotCallbackData.rebal.name()));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        photo.setReplyMarkup(markupInline);
        
        try {
            m_telebot.execute(answerCallbackQuery);
            m_telebot.execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}

	public class sector
	{
		sector(int nRate, String strName, Color cColor)
		{
			this.nRate = nRate;
			this.strName = strName;
			this.cColor = cColor;
		}
		int nRate;
		String strName;
		Color cColor;
	}
	
	static void string(BufferedImage img, List<sector> arSector) {
		
	}
	
    static void paintOnImage(BufferedImage img, List<sector> arSector) {
        // get a drawable Graphics2D (subclass of Graphics) object 
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // some sample drawing
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        
        int nCircleWidth = (int)(img.getWidth() * 0.7);
        int nCircleStart = (int)((img.getWidth() - nCircleWidth) * 0.5);
        
        float dLastAngle = 0.f;
        int nlastPos = nCircleStart * 2 + nCircleWidth;

        Font font = g2d.getFont();
		try {
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("BMJUA_ttf.ttf"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        font = font.deriveFont((float)(img.getWidth() * 0.07 * 0.6));
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        
        for(sector s : arSector)
        {
            g2d.setColor(s.cColor);
            
            float dAngle = (float) Math.ceil(360 * 0.01 * s.nRate);
            g2d.fillArc(nCircleStart, nCircleStart, nCircleWidth, nCircleWidth, (int) Math.ceil(dLastAngle), (int) Math.ceil(dAngle));
            dLastAngle += dAngle;
            //int nHeight = (int)(s.nRate * (double)img.getHeight() * 0.01);
            //g2d.fillRect(0, nLast, img.getWidth(), nLast + nHeight);
            g2d.setColor(Color.gray);
            g2d.fillRoundRect((int)(img.getWidth() * 0.05), nlastPos, (int)(img.getWidth() * 0.15), (int)(img.getWidth() * 0.07), (int)(img.getWidth() * 0.02), (int)(img.getWidth() * 0.02));

            g2d.fillRoundRect(img.getWidth() - (int)(img.getWidth() * 0.2), nlastPos, (int)(img.getWidth() * 0.15), (int)(img.getWidth() * 0.07), (int)(img.getWidth() * 0.02), (int)(img.getWidth() * 0.02));

            g2d.setColor(s.cColor);
            g2d.fillRoundRect((int)(img.getWidth() * 0.25), nlastPos, (int)(img.getWidth() * 0.5), (int)(img.getWidth() * 0.07), (int)(img.getWidth() * 0.02), (int)(img.getWidth() * 0.02));
            
            g2d.setColor(Color.WHITE);
            String str = s.strName;
            g2d.drawString(str, (int)(img.getWidth() * 0.5 - metrics.stringWidth(str) * 0.5), nlastPos + (int)(img.getWidth() * 0.07 * 0.68));

            str = s.nRate + "%";
            g2d.drawString(str, (int)((img.getWidth() * 0.05 + img.getWidth() * 0.15 * 0.5) - metrics.stringWidth(str) * 0.5), nlastPos + (int)(img.getWidth() * 0.07 * 0.68));
            g2d.drawString(str, (int)(((img.getWidth() - img.getWidth() * 0.2)+ img.getWidth() * 0.15 * 0.5) - metrics.stringWidth(str) * 0.5), nlastPos + (int)(img.getWidth() * 0.07 * 0.68));
            
            nlastPos += (int)(img.getWidth() * 0.1);
        }
        
        g2d.dispose();
    
    }
    
    public static void saveImage(String destination, List<sector> arSector) throws IOException {

        // instantiate a new BufferedImage (subclass of Image) instance 
        BufferedImage img = new BufferedImage(700, 1500, BufferedImage.TYPE_INT_ARGB);
        
        paintOnImage(img, arSector);

        // ImageIO provides several write methods with different outputs
        ImageIO.write(img, "png", new File(destination));
    }
}
