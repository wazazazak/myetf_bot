package com.koscom.myetf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public
class EtfPortion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String chatId;
  private String account;
  private String sectorCode;
  private double sectorPortion;

  public EtfPortion() {
  }

  public EtfPortion(String chatId, String account, String sectorCode, double sectorPortion) {
    this.chatId = chatId;
    this.account = account;
    this.sectorCode = sectorCode;
    this.sectorPortion = sectorPortion;
  }

  public String getChatId() {
    return chatId;
  }

  public String getAccount() {
    return account;
  }

  public String getSectorCode() {
    return sectorCode;
  }

  public double getSectorPortion() {
    return sectorPortion;
  }

  public void setSectorPortion(double sectorPortion) {
    this.sectorPortion = sectorPortion;
  }

  @Override
  public String toString() {
    return "EtfPortion{" +
            "id=" + id +
            ", chatId='" + chatId + '\'' +
            ", account='" + account + '\'' +
            ", sectorCode='" + sectorCode + '\'' +
            ", sectorPortion=" + sectorPortion +
            '}';
  }

  public void update(double sectorPortion) {
    setSectorPortion(sectorPortion);
  }
}