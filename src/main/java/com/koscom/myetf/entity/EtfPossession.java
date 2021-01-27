package com.koscom.myetf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public
class EtfPossession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String chatId;
  private String account;
  private String sectorCode;
  private int sectorPossession;

  public EtfPossession() {
  }

  public EtfPossession(String chatId, String account, String sectorCode, int sectorPossession) {
    this.chatId = chatId;
    this.account = account;
    this.sectorCode = sectorCode;
    this.sectorPossession = sectorPossession;
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

  public int getSectorPossession() {
    return sectorPossession;
  }

  public void setSectorPossession(int sectorPossession) {
    this.sectorPossession = sectorPossession;
  }

  @Override
  public String toString() {
    return "EtfPossession{" +
            "id=" + id +
            ", chatId='" + chatId + '\'' +
            ", account='" + account + '\'' +
            ", sectorCode='" + sectorCode + '\'' +
            ", sectorPossession=" + sectorPossession +
            '}';
  }

  public void update(int sectorPossession) {
    setSectorPossession(sectorPossession);
  }
}