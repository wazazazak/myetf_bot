package com.koscom.myetf.config;

import com.koscom.myetf.entity.EtfPortion;
import com.koscom.myetf.entity.EtfPortionRepository;
import com.koscom.myetf.entity.EtfPossession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EtfPortionLoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(EtfPortionLoadDatabase.class);

  @Bean
  CommandLineRunner etfPortioninitDatabase(EtfPortionRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new EtfPortion("1", "110123213123", "091180", 30)));
      log.info("Preloading " + repository.save(new EtfPortion("1", "110123213123", "091160", 40)));
      log.info("Preloading " + repository.save(new EtfPortion("1", "110123213123", "091170", 25)));
      log.info("Preloading " + repository.save(new EtfPortion("1", "110123213123", "999999", 5)));
      log.info("Preloading " + repository.save(new EtfPortion("2", "11011123314", "S503", 10)));
    };
  }
}