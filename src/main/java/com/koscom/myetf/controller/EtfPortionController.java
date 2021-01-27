package com.koscom.myetf.controller;

import com.koscom.myetf.entity.EtfPortion;
import com.koscom.myetf.entity.EtfPortionRepository;
import com.koscom.myetf.entity.EtfPortion;
import com.koscom.myetf.entity.EtfPortion;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RestController
class EtfPortionController {

  private final EtfPortionRepository repository;

  EtfPortionController(EtfPortionRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/etfportion")
  List<EtfPortion> all() {
    return repository.findAll();
  }

  @GetMapping("/etfportion/{chatId}/{account}")
  List<EtfPortion> findByChatIdAndAccount(@PathVariable String chatId, @PathVariable String account) {
    return repository.findByChatIdAndAccount(chatId, account);
  }

  @GetMapping("/etfportion/{chatId}/{account}/{sectorCode}")
  EtfPortion findByChatIdAndAccountAndSectorCode(@PathVariable String chatId, @PathVariable String account, @PathVariable String sectorCode) {
		EtfPortion etfPortion = repository.findByChatIdAndAccountAndSectorCode(chatId, account, sectorCode);
//		System.out.println(etfPortion);
		if(etfPortion == null) {
			return new EtfPortion(chatId, account, sectorCode, 0);
		} else {
			return etfPortion;
		}
//    return repository.findByChatIdAndAccountAndSectorCode(chatId, account, sectorCode);
  }

  @Transactional
  @PutMapping("/etfportion")
  EtfPortion updateEtfPortion(@RequestBody EtfPortion newEtfPortion) {
    EtfPortion etfPortion = repository.findByChatIdAndAccountAndSectorCode(newEtfPortion.getChatId(), newEtfPortion.getAccount(), newEtfPortion.getSectorCode());
    etfPortion.update(newEtfPortion.getSectorPortion());

    return repository.save(etfPortion);
  }

  @Transactional
  @PostMapping("/etfportion")
  EtfPortion newEtfportion(@RequestBody EtfPortion newEtfportion) {
    return repository.save(newEtfportion);
  }
}