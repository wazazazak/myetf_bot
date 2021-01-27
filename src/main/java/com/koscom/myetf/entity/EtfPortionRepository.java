package com.koscom.myetf.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtfPortionRepository extends JpaRepository<com.koscom.myetf.entity.EtfPortion, Long> {
    public com.koscom.myetf.entity.EtfPortion findByCharIdAndAccountAndSectorCode(String charId, String account, String sectorCode);
    public List<com.koscom.myetf.entity.EtfPortion> findByCharIdAndAccount(String charId, String account);
//    @Query("update p Etf_Portion p set p.where p.charId = ?1 and p.account = ?2")
//    User getByCharIdAndAccountAndSectorCode(String charId, String account);
}