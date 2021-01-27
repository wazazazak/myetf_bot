package commands;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
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
        
        List<sector> arSector = new ArrayList<>();
        arSector.add(new sector(20, "자동차", new Color(0x3A263A)));
        arSector.add(new sector(15, "반도체", new Color(0x312E41)));
        arSector.add(new sector(7, "건강", new Color(0x65A1A0)));
        arSector.add(new sector(8, "은행", new Color(0x8BC4C1)));
        arSector.add(new sector(6, "에너지화학", new Color(0xAAD4DA)));
        arSector.add(new sector(5, "철강", new Color(0xB9DEDF)));
        arSector.add(new sector(4, "미디어통신", new Color(0xE2B9C6)));
        arSector.add(new sector(32, "건설", new Color(0x804F69)));
        arSector.add(new sector(3, "증권", new Color(0x4A4C6D)));
        String fileName = new String(RandomStringUtils.randomAlphanumeric(10) + ".png");
        try {
			saveImage(fileName, arSector);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
        
        SendMessage message = new SendMessage();
        message.setChatId(callbackquery.getMessage().getChatId());
        message.setText("님의 포트폴리오는 이렇숩니다");
        SendPhoto photo = new SendPhoto();
        photo.setChatId(callbackquery.getMessage().getChatId());
        File file = new File(fileName);
        photo.setPhoto(file);
        
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
	
    static void paintOnImage(BufferedImage img, List<sector> arSector) {
        // get a drawable Graphics2D (subclass of Graphics) object 
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // some sample drawing
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        
        Font newFont = new Font("Arial", Font.PLAIN, 20);
        g2d.setFont(newFont);
        
        int nLast = 0;
        for(sector s : arSector)
        {
            g2d.setColor(s.cColor);
            int nHeight = (int)(s.nRate * (double)img.getHeight() * 0.01);
            g2d.fillRect(0, nLast, img.getWidth(), nLast + nHeight);

            g2d.setColor(Color.WHITE);
            g2d.drawString(s.strName, 0, nLast);
            nLast += nHeight;
        }
        
        
//        g2d.setColor(Color.WHITE);
//        g2d.drawLine(0, 0, 640, 480);
//        g2d.drawLine(0, 480, 640, 0);
//        g2d.setColor(Color.YELLOW);
//        g2d.drawOval(200, 100, 240, 280);
//        g2d.setColor(Color.RED);
//        g2d.drawRect(150, 70, 340, 340);
        //g2d.setColor(Color.YELLOW);
        //g2d.drawChars(new String("갓기혁").toCharArray(), 200, 100, 300, 280);
        
        // drawing on images can be very memory-consuming
        // so it's better to free resources early
        // it's not necessary, though
        g2d.dispose();
    
    }
    
    public static void saveImage(String destination, List<sector> arSector) throws IOException {

        // instantiate a new BufferedImage (subclass of Image) instance 
        BufferedImage img = new BufferedImage(300, 500, BufferedImage.TYPE_INT_ARGB);
        
        paintOnImage(img, arSector);

        // ImageIO provides several write methods with different outputs
        ImageIO.write(img, "png", new File(destination));
    }
}
