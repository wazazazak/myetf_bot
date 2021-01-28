package com.koscom.myetf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Portfolio {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private String chatId;
	  private String portfolioKey;
	  private String portfolioName;
	  private String sectorCodeGroup;
	  private String sectorPortionGroup;
	  /*
	   * {
	   * 	"chatId" : "1502506769",
	   * 	"portfolioKey" : "1111",
	   * 	"portfolioName" : "김성수 포트폴리오",
	   * 	"sectorCodeGroup" : "091180|091160|091170|999999",
	   * 	"sectorPortionGroup" : "30.0|50.0|15.0|5.0"
	   * }
	   */
	  
	  
	public Portfolio(String chatId, String portfolioKey, String portfolioName, String sectorCodeGroup,
			String sectorPortionGroup) {
		this.chatId = chatId;
		this.portfolioKey = portfolioKey;
		this.portfolioName = portfolioName;
		this.sectorCodeGroup = sectorCodeGroup;
		this.sectorPortionGroup = sectorPortionGroup;
	}

	public Portfolio() {
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getPortfolioKey() {
		return portfolioKey;
	}

	public void setPortfolioKey(String portfolioKey) {
		this.portfolioKey = portfolioKey;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	public String getSectorCodeGroup() {
		return sectorCodeGroup;
	}

	public void setSectorCodeGroup(String sectorCodeGroup) {
		this.sectorCodeGroup = sectorCodeGroup;
	}

	public String getSectorPortionGroup() {
		return sectorPortionGroup;
	}

	public void setSectorPortionGroup(String sectorPortionGroup) {
		this.sectorPortionGroup = sectorPortionGroup;
	}

	@Override
	public String toString() {
		return "Portfolio [chatId=" + chatId + ", portfolioKey=" + portfolioKey + ", portfolioName=" + portfolioName
				+ ", sectorCodeGroup=" + sectorCodeGroup + ", sectorPortionGroup=" + sectorPortionGroup + "]";
	}
}
