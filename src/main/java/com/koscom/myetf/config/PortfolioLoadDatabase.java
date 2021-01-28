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
	  	log.info("Preloading " + repository.save(new Portfolio("1502506769", "09port", "영구 포트폴리오", "251350|308620|999999|132030", "25.0|25.0|25.0|25.0")));
	  	log.info("Preloading " + repository.save(new Portfolio("1502506769", "3070port", "3070", "308620|251350", "30.0|70.0")));
	  	log.info("Preloading " + repository.save(new Portfolio("1502506769", "4060port", "4060", "308620|251350", "40.0|60.0")));
    };
  }
}