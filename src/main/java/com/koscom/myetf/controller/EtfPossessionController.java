package com.koscom.myetf.controller;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.koscom.myetf.entity.EtfPossession;
import com.koscom.myetf.entity.EtfPossessionRepository;

@RestController
class EtfPossessionController {

  private final EtfPossessionRepository repository;

  EtfPossessionController(EtfPossessionRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/etfpossession")
  List<EtfPossession> all() {
    return repository.findAll();
  }

  @GetMapping("/etfpossession/{chatId}/{account}")
  List<EtfPossession> findByChatIdAndAccount(@PathVariable String chatId, @PathVariable String account) {
    return repository.findByChatIdAndAccount(chatId, account);
  }

  @GetMapping("/etfpossession/{chatId}/{account}/{sectorCode}")
  EtfPossession findByChatIdAndAccountAndSectorCode(@PathVariable String chatId, @PathVariable String account, @PathVariable String sectorCode) {
    return repository.findByChatIdAndAccountAndSectorCode(chatId, account, sectorCode);
  }
  
  @Transactional
  @PutMapping("/etfpossession")
  EtfPossession updateEtfPossession(@RequestBody EtfPossession newEtfPossession) {
    EtfPossession EtfPossession = repository.findByChatIdAndAccountAndSectorCode(newEtfPossession.getChatId(), newEtfPossession.getAccount(), newEtfPossession.getSectorCode());
    EtfPossession.update(newEtfPossession.getSectorPossession());

    return repository.save(EtfPossession);
  }

  @Transactional
  @PostMapping("/etfpossession")
  EtfPossession newEtfPossession(@RequestBody EtfPossession newEtfPossession) {
    return repository.save(newEtfPossession);
  }
}