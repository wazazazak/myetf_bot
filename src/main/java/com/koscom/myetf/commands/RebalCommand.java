package com.koscom.myetf.commands;

import java.text.DecimalFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.entity.EtfPortion;

public class RebalCommand extends MyetfCommand{
	public RebalCommand(TelegramLongPollingBot telebot, Update update) {
		super(telebot, update);
		// TODO Auto-generated constructor stub
	}

	public void execute()
	{
		try {
			
			String jsonTxt = new String();
			
			// 0. DB - 보유 주식 수 조회
			/* etfpossession/chatId/account */
			jsonTxt = sendGet("http://localhost:8000/etfpossession/1502506769/160635473367600099");
			
			/*
			 *       종목     | 종목코드 | 보유수량
			 * -------------------------------
			 *  KODEX 자동차  | 091180 | 30
			 *  KODEX 반도체  | 091160 | 50
			 *  KODEX 은행   | 091170 | 20
			 */
			
			/**
			 * 1. 현재 자산 총액 구하기
			 * (보유주 * 현재 시세) + 예수금 총액 = 보유주식의 총액
			 */
			double totalAmt = getTotalAmt(jsonTxt);
			
			/*
			 *       종목		| 종목코드 | 목표보유비중
			 * -------------------------------
			 *  KODEX 자동차	| 091180 | 30%
			 *  KODEX 반도체	| 091160 | 40%
			 *  KODEX 은행	| 091170 | 25%
			 *  현금 			| 999999 | 5%
			 */
			/**
			 * 2. 목표 포트폴리오 비중으로 변경하기 위한 매수, 매도 수량 계산
			 */
			jsonTxt = sendGet("http://localhost:8000/etfportion/1502506769/160635473367600099");
			
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArr = (JSONArray)jsonParser.parse(jsonTxt);
			for(int i=0;i<jsonArr.size();i++) {
				System.out.println("[json] jsonArr >> " + jsonArr.get(i));
				
				String subJsonStr = jsonArr.get(i).toString();
				JSONObject subJsonObj = (JSONObject) jsonParser.parse(subJsonStr);
				//System.out.println("trdPrc >> " + resultObject.get("trdPrc"));
				String sectorCode = subJsonObj.get("sectorCode").toString();
				System.out.println("[jsonArr] sectorCode >> " + sectorCode);
				
				String sectorPortion = subJsonObj.get("sectorPortion").toString();
				System.out.println("[jsonArr] sectorPortion >> " + sectorPortion);
				double fSectorPortion = Double.parseDouble(sectorPortion);
				
				/**
				 * 2-1. 목표 보유 종목별 총 금액 계산
				 */
				double sectorTotalPrice = totalAmt * fSectorPortion/100;
				System.out.println("■■■■■ 종목코드 >> " + sectorCode + " / 목표보유비중 >> " + sectorPortion + "% / 목표보유금액 >> " + sectorTotalPrice);
				
				// 현재 시세를 반영한 목표 보유 종목별 수량
				double sectorPrice = 0;	// 현재 시세
				
				if( "999999".equals(sectorCode) == false ) {
					// 현금 아닐 때
					sectorPrice = getPrice(sectorCode);		// [코스콤 API] 종목별 시세 조회 -> 종목 당 보유 자산 구하기
				}
				else {
					// 현금일 때
					sectorPrice = 1;	// 현금 -> 주가 1원으로 계산
				}
				
				/**
				 * 2-2. 목표 보유 종목별 수량 계산
				 */
				int targetQt = 0;	// 목표수량
				targetQt = (int) Math.floor(sectorTotalPrice / sectorPrice);	// 소수첫째자리에서 버림
				System.out.println("■■■■■ 종목코드 >> " + sectorCode + " / 목표보유비중 >> " + sectorPortion + "% / 목표보유금액 >> " + sectorTotalPrice
										+ "/ 현재 시세 >> " + sectorPrice + " / 목표보유수량 >> " + targetQt);
				
				/**
				 * 2-3. 목표 보유 수량과 현재 보유 수량의 차이 구하기
				 */
				/* /etfpossession/{chatId}/{account}/{sectorCode} */
				String jsonHoldQt = new String();
				jsonHoldQt = sendGet("http://localhost:8000/etfpossession/1502506769/160635473367600099/"+sectorCode);
				
	            Object objHoldQt = jsonParser.parse(jsonHoldQt);
	            JSONObject jsonObjHoldQt = (JSONObject) objHoldQt;
	            System.out.println("섹터 [" + jsonObjHoldQt.get("sectorCode") + "] 현재 보유 수량 >> " + jsonObjHoldQt.get("sectorPossession"));
	            
	            /**
	             * 2-4. 매도/매수 수량 계산
	             * 목표 보유 수량 +- 현재 보유 수량
	             */
	            int holdingQt = Integer.parseInt(jsonObjHoldQt.get("sectorPossession").toString());
				if( targetQt > holdingQt ) {	// 목표수량 > 현재수량 => 매수
					// 매수 수량
					int buyQt = targetQt - holdingQt;
					System.out.println("섹터 [" + jsonObjHoldQt.get("sectorCode") + "] 매수 수량 >> " + buyQt);
					
					// 보유 상태면 PUT , 미보유상태면 POST
//					{
//					    "chatId": "1",
//					    "account": "110123213123",
//					    "sectorCode": "091180",
//					    "sectorPossession": 20
//					}
					
				} else {	// 현재수량 > 목표수량 => 매도
					// 매도 수량
					int sellQt = holdingQt - targetQt;
					System.out.println("섹터 [" + jsonObjHoldQt.get("sectorCode") + "] 매도 수량 >> " + sellQt);
					
				}
				
				
				
				
				
			}
			
			/**
			 * 3. 매수/매도 후 남은 금액을 현금으로,,
			 * 현자산 - 보유수량(금액) => 현금
			 */
			
			
			CallbackQuery callbackquery = m_update.getCallbackQuery();
			AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
			answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
			answerCallbackQuery.setShowAlert(false);
			answerCallbackQuery.setText("");
			
			SendMessage message = new SendMessage();
//		        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//		        List <List< InlineKeyboardButton >> rowsInline = new ArrayList< >();
//		        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
//		        rowInline.add(new InlineKeyboardButton().setText("ㅇㅇ").setCallbackData("1"));
//		        rowsInline.add(rowInline);
//		        markupInline.setKeyboard(rowsInline);
//		        message.setReplyMarkup(markupInline);
			
			// 금액 포맷팅
	        DecimalFormat formatter = new DecimalFormat("###,###");
	     	String fmTotalAmt = formatter.format(totalAmt);
			
			String msgText = new String();
			msgText += "현재 자산 총액은 "+ fmTotalAmt + "원입니다.";
			msgText += "\n";
			msgText += "\n리밸런싱 완료!";
			
			message.setText(msgText);
			message.setChatId(callbackquery.getMessage().getChatId());
			
			m_telebot.execute(answerCallbackQuery);
            m_telebot.execute(message);
			
		} catch (TelegramApiException e) {
			e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
	}
}
