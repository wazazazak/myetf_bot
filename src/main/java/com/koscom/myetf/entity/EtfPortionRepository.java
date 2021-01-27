package com.koscom.myetf.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import com.koscom.myetf.entity.EtfPortion;
import java.util.List;

public interface EtfPortionRepository extends JpaRepository<EtfPortion, Long> {
    public EtfPortion findByChatIdAndAccountAndSectorCode(String chatId, String account, String sectorCode);
    public List<EtfPortion> findByChatIdAndAccount(String chatId, String account);
    public List<EtfPortion> findByChatId(String chatId);
}