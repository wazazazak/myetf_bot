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
  private String charId;
  private String account;
  private String sectorCode;
  private int sectorPossession;

  public EtfPossession() {
  }

  public EtfPossession(String charId, String account, String sectorCode, int sectorPossession) {
    this.charId = charId;
    this.account = account;
    this.sectorCode = sectorCode;
    this.sectorPossession = sectorPossession;
  }

  public String getCharId() {
    return charId;
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
            ", charId='" + charId + '\'' +
            ", account='" + account + '\'' +
            ", sectorCode='" + sectorCode + '\'' +
            ", sectorPossession=" + sectorPossession +
            '}';
  }

  public void update(int sectorPossession) {
    setSectorPossession(sectorPossession);
  }
}