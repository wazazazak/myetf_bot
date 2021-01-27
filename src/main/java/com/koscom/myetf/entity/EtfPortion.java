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
  private String charId;
  private String account;
  private String sectorCode;
  private double sectorPortion;

  public EtfPortion() {
  }

  public EtfPortion(String charId, String account, String sectorCode, double sectorPortion) {
    this.charId = charId;
    this.account = account;
    this.sectorCode = sectorCode;
    this.sectorPortion = sectorPortion;
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
            ", charId='" + charId + '\'' +
            ", account='" + account + '\'' +
            ", sectorCode='" + sectorCode + '\'' +
            ", sectorPortion=" + sectorPortion +
            '}';
  }

  public void update(double sectorPortion) {
    setSectorPortion(sectorPortion);
  }
}