package com.koscom.myetf.controller;

import com.koscom.myetf.entity.EtfPossession;
import com.koscom.myetf.entity.EtfPossession;
import com.koscom.myetf.entity.EtfPossessionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class EtfPossessionController {

  private final EtfPossessionRepository repository;

  EtfPossessionController(EtfPossessionRepository repository) {
    this.repository = repository;
  }

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/etfpossession")
  List<EtfPossession> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]
  @GetMapping("/etfpossession/{charId}/{account}")
  List<EtfPossession> findByCharIdAndAccount(@PathVariable String charId, @PathVariable String account) {
    return repository.findByCharIdAndAccount(charId, account);
  }

  @Transactional
  @PutMapping("/etfpossession")
  EtfPossession updateEtfPossession(@RequestBody EtfPossession newEtfPossession) {
//    System.out.printf("%s, %s, %s\n", newEtfPossession.getCharId(), newEtfPossession.getAccount(), newEtfPossession.getSectorCode());
    EtfPossession EtfPossession = repository.findByCharIdAndAccountAndSectorCode(newEtfPossession.getCharId(), newEtfPossession.getAccount(), newEtfPossession.getSectorCode());
//    System.out.println(EtfPossession);
    EtfPossession.update(newEtfPossession.getSectorPossession());

    return repository.save(EtfPossession);
  }

  @Transactional
  @PostMapping("/etfpossession")
  EtfPossession newEtfPossession(@RequestBody EtfPossession newEtfPossession) {
    return repository.save(newEtfPossession);
  }
//
//  // Single item
//
//  @GetMapping("/EtfPossession/{id}")
//  EtfPossession one(@PathVariable Long id) {
//
//    return repository.findById(id)
//      .orElseThrow(() -> new EtfPossessionNotFoundException(id));
//  }
//
//  @PutMapping("/EtfPossession/{id}")
//  EtfPossession replaceEtfPossession(@RequestBody EtfPossession newEtfPossession, @PathVariable Long id) {
//
//    return repository.findById(id)
//      .map(EtfPossession -> {
//        EtfPossession.setName(newEtfPossession.getName());
//        EtfPossession.setRole(newEtfPossession.getRole());
//        return repository.save(EtfPossession);
//      })
//      .orElseGet(() -> {
//        newEtfPossession.setId(id);
//        return repository.save(newEtfPossession);
//      });
//  }
//
//  @DeleteMapping("/EtfPossession/{id}")
//  void deleteEtfPossession(@PathVariable Long id) {
//    repository.deleteById(id);
//  }
}