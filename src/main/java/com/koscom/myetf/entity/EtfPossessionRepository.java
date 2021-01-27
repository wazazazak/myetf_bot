package com.koscom.myetf.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtfPossessionRepository extends JpaRepository<EtfPossession, Long> {
    public EtfPossession findByCharIdAndAccountAndSectorCode(String charId, String account, String sectorCode);
    public List<EtfPossession> findByCharIdAndAccount(String charId, String account);
//    @Query("update p Etf_Portion p set p.where p.charId = ?1 and p.account = ?2")
//    User getByCharIdAndAccountAndSectorCode(String charId, String account);
}