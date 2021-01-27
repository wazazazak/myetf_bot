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

    private String chatId;
    private String account;
    private String sectorCode;
    private String sellBuyDiv; // 1 - sell, 2 - buy
    private int quantity;
    private int price;
    private String createdTime;

    public TransactionLog() {
    }

    public TransactionLog(String chatId, String account, String sectorCode, String sellBuyDiv, int quantity, int price, String createdTime) {
        this.chatId = chatId;
        this.account = account;
        this.sectorCode = sectorCode;
        this.sellBuyDiv = sellBuyDiv;
        this.quantity = quantity;
        this.price = price;
        this.createdTime = createdTime;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
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
                ", chatId='" + chatId + '\'' +
                ", account='" + account + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                ", sellBuyDiv='" + sellBuyDiv + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", createdTime=" + createdTime +
                '}';
    }
}
