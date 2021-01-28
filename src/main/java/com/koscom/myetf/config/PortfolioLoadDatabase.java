package com.koscom.myetf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.koscom.myetf.entity.Portfolio;
import com.koscom.myetf.entity.PortfolioRepository;

@Configuration
class PortfolioLoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(PortfolioLoadDatabase.class);

  @Bean
  CommandLineRunner etfPortfolioinitDatabase(PortfolioRepository repository) {

    return args -> {
	  	log.info("Preloading " + repository.save(new Portfolio("1502506769", "160635473367600099", "091180", 30)));
	    log.info("Preloading " + repository.save(new Portfolio("1502506769", "160635473367600099", "091160", 50)));
	    log.info("Preloading " + repository.save(new Portfolio("1502506769", "160635473367600099", "091170", 15)));
    };
  }
}