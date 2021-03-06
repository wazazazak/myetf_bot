package com.koscom.myetf.commands;

import static org.springframework.http.HttpHeaders.USER_AGENT;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.koscom.myetf.TelegramMessageBot;

public abstract class MyetfCommand {
	
	TelegramMessageBot m_telebot;
	Update m_update;
	private HashMap<String, String> mProdNames;
	
	public abstract void execute();
	
	public MyetfCommand(TelegramLongPollingBot telebot, Update update) {
		m_telebot = (TelegramMessageBot)telebot;
		m_update = update;
		mProdNames = new HashMap<>();
		mProdNames.put("999999", "현금");
    }
	
	protected Long GetChatId()
	{
		if(m_update.hasCallbackQuery())
		{
			return m_update.getCallbackQuery().getMessage().getChatId();
		}
		else if(m_update.hasMessage())
		{
			return m_update.getMessage().getChatId();
		}
		return 0L;
	}

	protected void AnswerQuery()
	{
		if(m_update.hasCallbackQuery())
		{
	        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
	        answerCallbackQuery.setCallbackQueryId(m_update.getCallbackQuery().getId());
	        answerCallbackQuery.setShowAlert(false);
	        answerCallbackQuery.setText("");
			try {
				m_telebot.execute(answerCallbackQuery);
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * DB 조회
	 * @param targetUrl
	 * @throws Exception
	 */
	protected String sendGet(String targetUrl) throws Exception {
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        System.out.println("HTTP 응답 코드 : " + responseCode);
        System.out.println("HTTP body : " + response.toString());
        
    	// JSON 문자열 JAVA로 변환
        String jsonTxt = response.toString();
        
        return jsonTxt;
    }
	
	/**
	 * DB INSERT
	 * @param targetUrl
	 * @param parameters
	 * @throws Exception
	 */
	protected String sendPost(String targetUrl, String parameters) throws Exception {
		URL url = new URL(targetUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST"); // HTTP POST 메소드 설정 
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true); // POST 파라미터 전달을 위한 설정
        
        System.out.println("Send post request  : " + targetUrl + " + " +parameters);
        
        // Send post request 
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close(); 
		
        // print result 
		System.out.println("HTTP 응답 코드 : " + responseCode);
		System.out.println("HTTP body : " + response.toString());
		
		// JSON 문자열 JAVA로 변환
        String jsonTxt = response.toString();
        
        return jsonTxt;
    }
	
	/**
	 * DB UPDATE
	 * @param targetUrl
	 * @param parameters
	 * @throws Exception
	 */
	protected String sendPut(String targetUrl, String parameters) throws Exception {
		URL url = new URL(targetUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("PUT"); // HTTP POST 메소드 설정 
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true); // POST 파라미터 전달을 위한 설정
        
        System.out.println("Send post request  : " + targetUrl + " + " +parameters);
        
        // Send post request 
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close(); 
		
        // print result 
		System.out.println("HTTP 응답 코드 : " + responseCode);
		System.out.println("HTTP body : " + response.toString());
		
		// JSON 문자열 JAVA로 변환
        String jsonTxt = response.toString();
        
        return jsonTxt;
    }
	
	/**
	 * [코스콤 API] 종목별 시세 조회 -> 종목 당 보유 자산 구하기
	 * @param issuecode
	 * @return
	 */
	public double getPrice(String issuecode) {
		if(issuecode.compareTo("999999") == 0) return 1;
		BufferedReader br = null;
		double price = 0;
		
        try {
        	
        	String marketcode = "kospi";	// 시장구분
        	//String issuecode = "091180";		// 종목코드
        	
        	String urlstr = "https://sandbox-apigw.koscom.co.kr/v2/market/stocks"
        			+ "/"+marketcode+"/"+issuecode+"/price"
        			+ "?apikey=l7xx269a193d8850413389aab28e01032509";
        	URL url = new URL(urlstr);
        	HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
        	urlconnection.setRequestMethod("GET");
        	br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
        	
        	String json = new String();
        	String line = new String();
        	
        	while((line = br.readLine()) != null) {
        		json = json + line + "\n";
        	}
        	
        	//System.out.println("json >> " + json);
        	
        	// JSON 문자열 JAVA로 변환
        	JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;
            //System.out.println("result >> " + jsonObject.get("result"));
            
            String result = jsonObject.get("result").toString();
            Object resultObj = jsonParser.parse(result);
            JSONObject resultObject = (JSONObject) resultObj;
            System.out.println("trdPrc >> " + issuecode + " : " + resultObject.get("trdPrc"));
            price = Double.parseDouble(resultObject.get("trdPrc").toString());
        	
        } catch (Exception e) {
        	System.out.println(e.getMessage());
		}
        
        return price;
	}

	public String getProdName(String issuecode) {
		
		if(mProdNames.containsKey(issuecode)) return mProdNames.get(issuecode);
		
		BufferedReader br = null;
		String name = "";
		
        try {
        	String marketcode = "kospi";	// 시장구분
        	//String issuecode = "091180";		// 종목코드
        	
        	String urlstr = "https://sandbox-apigw.koscom.co.kr/v2/market/stocks"
        			+ "/"+marketcode+"/"+issuecode+"/master"
        			+ "?apikey=l7xx269a193d8850413389aab28e01032509";
        	URL url = new URL(urlstr);
        	HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
        	urlconnection.setRequestMethod("GET");
        	br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
        	
        	String json = new String();
        	String line = new String();
        	
        	while((line = br.readLine()) != null) {
        		json = json + line + "\n";
        	}
        	
        	//System.out.println("json >> " + json);
        	
        	// JSON 문자열 JAVA로 변환
        	JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;
            //System.out.println("result >> " + jsonObject.get("result"));
            
            String result = jsonObject.get("result").toString();
            Object resultObj = jsonParser.parse(result);
            JSONObject resultObject = (JSONObject) resultObj;
            name = resultObject.get("isuKorAbbrv").toString();
        	
        } catch (Exception e) {
        	System.out.println(e.getMessage());
		}
        
        mProdNames.put(issuecode, name);
        return name;
	}
	/**
	 * 자산 총액 구하기
	 * @param issuecode
	 * @return
	 */
	public double getTotalAmt(String jsonTxt) {
		
		double totalAmt = 0;	// 자산 총액
		
        try {
        	
        	JSONParser jsonParser = new JSONParser();
			JSONArray jsonarr = (JSONArray)jsonParser.parse(jsonTxt);
			for(int i=0;i<jsonarr.size();i++){
				System.out.println("[json] sectorPossession >> " + jsonarr.get(i));
				
				String subJsonStr = jsonarr.get(i).toString();
				JSONObject subJsonObj = (JSONObject) jsonParser.parse(subJsonStr);
				//System.out.println("trdPrc >> " + resultObject.get("trdPrc"));
				String sectorCode = subJsonObj.get("sectorCode").toString();
				System.out.println("[value] sectorCode >> " + sectorCode);
				
				
				double sectorPrice = 0;
				
				if( "999999".equals(sectorCode) == false ) {
					// 현금 아닐 때
					sectorPrice = getPrice(sectorCode);		// [코스콤 API] 종목별 시세 조회 -> 종목 당 보유 자산 구하기
				} else {
					// 현금일 때
					sectorPrice = 1;	// 현금 -> 주가 1원으로 계산
				}
				
				String sectorPossession = subJsonObj.get("sectorPossession").toString();
				System.out.println("[value] sectorPossession >> " + sectorPossession);
				
				double sectorTotalPrice = 0;
				sectorTotalPrice = sectorPrice * Integer.parseInt(sectorPossession);
				System.out.println("■■■■■ sectorTotalPrice >> " + sectorTotalPrice);
				totalAmt += sectorTotalPrice;
				System.out.println("■■■■■ totalPrice >> " + totalAmt);
				
			}
        	
        } catch (Exception e) {
        	System.out.println(e.getMessage());
		}
        
        return totalAmt;
	}
}
