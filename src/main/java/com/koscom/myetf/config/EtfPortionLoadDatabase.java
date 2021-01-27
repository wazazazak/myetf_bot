package com.koscom.myetf.config;

import com.koscom.myetf.entity.EtfPortion;
import com.koscom.myetf.entity.EtfPortionRepository;
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
      log.info("Preloading " + repository.save(new EtfPortion("1", "1111", "S501", 20)));
      log.info("Preloading " + repository.save(new EtfPortion("1", "3333", "S502", 90)));
      log.info("Preloading " + repository.save(new EtfPortion("2", "2222", "S503", 10)));
    };
  }
}