package com.koscom.myetf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String charId;
    private String account;
    private String sectorCode;
    private String sellBuyDiv; // 1 - sell, 2 - buy
    private int quantity;
    private int price;
    private String createdTime;

    public TransactionLog() {
    }

    public TransactionLog(String charId, String account, String sectorCode, String sellBuyDiv, int quantity, int price, String createdTime) {
        this.charId = charId;
        this.account = account;
        this.sectorCode = sectorCode;
        this.sellBuyDiv = sellBuyDiv;
        this.quantity = quantity;
        this.price = price;
        this.createdTime = createdTime;
    }

    public String getCharId() {
        return charId;
    }

    public void setCharId(String charId) {
        this.charId = charId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getSellBuyDiv() {
        return sellBuyDiv;
    }

    public void setSellBuyDiv(String sellBuyDiv) {
        this.sellBuyDiv = sellBuyDiv;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
                "id=" + id +
                ", charId='" + charId + '\'' +
                ", account='" + account + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                ", sellBuyDiv='" + sellBuyDiv + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", createdTime=" + createdTime +
                '}';
    }
}
