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

    private String charId;
    private String name;
    private String account;
    private int totMoney;

    public User() {
    }

    public User(String charId, String name, String account, int totMoney) {
        this.charId = charId;
        this.name = name;
        this.account = account;
        this.totMoney = totMoney;
    }

    public Long getId() {
        return id;
    }

    public String getCharId() {
        return charId;
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

    public void setCharId(String charId) {
        this.charId = charId;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", charId='" + charId + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", totMoney=" + totMoney +
                '}';
    }
}
