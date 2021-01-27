package com.koscom.myetf.config;

import com.koscom.myetf.entity.EtfPossession;
import com.koscom.myetf.entity.EtfPossessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EtfPossessionLoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(EtfPossessionLoadDatabase.class);

  @Bean
  CommandLineRunner etfPossessioninitDatabase(EtfPossessionRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new EtfPossession("1", "1111", "S501", 500)));
      log.info("Preloading " + repository.save(new EtfPossession("1", "3333", "S502", 100)));
      log.info("Preloading " + repository.save(new EtfPossession("2", "2222", "S503", 200)));
    };
  }
}