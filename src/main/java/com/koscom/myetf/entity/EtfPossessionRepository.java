package com.koscom.myetf.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtfPossessionRepository extends JpaRepository<EtfPossession, Long> {
    public EtfPossession findByChatIdAndAccountAndSectorCode(String chatId, String account, String sectorCode);
    public List<EtfPossession> findByChatIdAndAccount(String chatId, String account);
    public List<EtfPossession> findByChatId(String chatId);
}