package com.koscom.myetf.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionLogRepository extends JpaRepository<com.koscom.myetf.entity.TransactionLog, Long> {

    public List<com.koscom.myetf.entity.TransactionLog> findByCharId(String charId);

    public List<com.koscom.myetf.entity.TransactionLog> findByCharIdAndAccount(String charId, String account);
//    @Query("select p from User p where p.charId = ?1 and p.account = ?2")
//    User getByCharIdAndAccount(String charId, String account);
}