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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.TelegramMessageBot.BotCallbackData;
import com.koscom.myetf.TelegramMessageBot.CSessionData;

public class PortCommand extends MyetfCommand{
	public PortCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	    arColors = new ArrayList<>();
	    arColors.add(new Color(0xFF6D63));
	    arColors.add(new Color(0xFF9652));
	    arColors.add(new Color(0xFFCE43));
	    arColors.add(new Color(0xFFE700));
	    arColors.add(new Color(0xBEE74B));
	    arColors.add(new Color(0x73D26C));
	    arColors.add(new Color(0x63EBDD));
	    arColors.add(new Color(0x73B5E5));
	    arColors.add(new Color(0xA58BD5));
	    arColors.add(new Color(0x8BA5D5));
	    arColors.add(new Color(0xA58BA5));
	    arColors.add(new Color(0xEB63DD));
	    arColors.add(new Color(0xEBDD63));
	    arColors.add(new Color(0xFF5296));
	    arColors.add(new Color(0x5296FF));
	    arColors.add(new Color(0xCE43FF));
	    arColors.add(new Color(0x43CEFF));
	    arColors.add(new Color(0xE74BBE));
	    arColors.add(new Color(0xE7BE4B));
	}

	public List<Color> arColors;
	
	public void execute()
	{        
        CSessionData data = m_telebot.mSessionData.get(GetChatId().toString());

		String jsonTxt = new String();

        List<sector> arSector = new ArrayList<>();
		// 0. DB - 보유 주식 수 조회
		/* etfpossession/chatId/account */
		try {
			jsonTxt = sendGet("http://localhost:8000/etfportion/" + GetChatId().toString() + "/" + data.strAccount);

        	JSONParser jsonParser = new JSONParser();
			JSONArray jsonarr = (JSONArray)jsonParser.parse(jsonTxt);
			for(int i=0;i<jsonarr.size();i++){
				String subJsonStr = jsonarr.get(i).toString();
				JSONObject subJsonObj = (JSONObject) jsonParser.parse(subJsonStr);
				String sectorCode = subJsonObj.get("sectorCode").toString();
				int sectorRate = (int) Float.parseFloat(subJsonObj.get("sectorPortion").toString());
				
				String name = "";
				if( "999999".equals(sectorCode) )
				{
					name = "현금";
				}
				else
				{
					name = getProdName(sectorCode);
				}
				arSector.add(new sector(sectorRate, name, arColors.size() > i ? arColors.get(i) : Color.black));
			}
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return;
		}
		
        String fileName = new String(RandomStringUtils.randomAlphanumeric(10) + ".png");
        try {
			saveImage(fileName, arSector);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
        
        SendPhoto photo = new SendPhoto();
        photo.setChatId(GetChatId());
        photo.setCaption("포트폴리오");
        File file = new File(fileName);
        photo.setPhoto(file);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
        rowInline.add(new InlineKeyboardButton().setText("리밸런싱 하시겠습니까?").setCallbackData(BotCallbackData.rebal.name()));
        rowsInline.add(rowInline);
        List < InlineKeyboardButton > rowInline2 = new ArrayList < > ();
        rowInline2.add(new InlineKeyboardButton().setText("취소").setCallbackData(BotCallbackData.menu.name() + ":" + data.strAccount));
        rowsInline.add(rowInline2);
        markupInline.setKeyboard(rowsInline);
        photo.setReplyMarkup(markupInline);
        
        try {
        	data.strState = BotCallbackData.myport.name();
            m_telebot.execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		AnswerQuery();
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

        g2d.setColor(Color.BLACK);
        String strCol = "희망 비율";
        g2d.drawString(strCol, (int)((img.getWidth() * 0.05 + img.getWidth() * 0.15 * 0.5) - metrics.stringWidth(strCol) * 0.5), nlastPos - (int)(img.getWidth() * 0.07 * 0.68));
        strCol = "현재 비율";
        g2d.drawString(strCol, (int)(((img.getWidth() - img.getWidth() * 0.2)+ img.getWidth() * 0.15 * 0.5) - metrics.stringWidth(strCol) * 0.5), nlastPos - (int)(img.getWidth() * 0.07 * 0.68));
        
        for(sector s : arSector)
        {
        	if(s.nRate == 0) continue;
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
