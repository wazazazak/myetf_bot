package com.koscom.myetf.controller;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.koscom.myetf.entity.Portfolio;
import com.koscom.myetf.entity.PortfolioRepository;

@RestController
class PortfolioController {

  private final PortfolioRepository repository;

  PortfolioController(PortfolioRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/portfolio/{chatId}")
  List<Portfolio> findByChatId(@PathVariable String chatId) {
    return repository.findByChatId(chatId);
  }
  
  @GetMapping("/portfoliokey/{portfolioKey}")
  Portfolio findByPortfolioKey(@PathVariable String portfolioKey) {
    return repository.findByPortfolioKey(portfolioKey);
  }

  @Transactional
  @PostMapping("/portfolio")
  Portfolio newPortfolio(@RequestBody Portfolio newPortfolio) {
    return repository.save(newPortfolio);
  }
}