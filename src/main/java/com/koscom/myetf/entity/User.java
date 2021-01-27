package com.koscom.myetf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId;
    private String name;
    private String accountName;
    private String account;
    private int totMoney;

    public User() {
    }

    public User(String chatId, String name, String account, String accountName, int totMoney) {
        this.chatId = chatId;
        this.name = name;
        this.account = account;
        this.accountName = accountName;
        this.totMoney = totMoney;
    }

    public Long getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public int getTotMoney() {
        return totMoney;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setName(String name) {
        this.name = name;
    }

	public void setAccount(String account) {
        this.account = account;
    }

    public void setTotMoney(int totMoney) {
        this.totMoney = totMoney;
    }

    public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId='" + chatId + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", accountName='" + accountName + '\'' +
                ", totMoney=" + totMoney +
                '}';
    }
}
