package com.koscom.myetf.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import com.koscom.myetf.entity.TransactionLog;

import java.util.List;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    public List<TransactionLog> findByChatId(String chatId);

    public List<TransactionLog> findByChatIdAndAccount(String chatId, String account);
}