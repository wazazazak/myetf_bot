package com.koscom.myetf.config;

import com.koscom.myetf.entity.EtfPossession;
import com.koscom.myetf.entity.EtfPossessionRepository;
import com.koscom.myetf.entity.User;

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
    	log.info("Preloading " + repository.save(new EtfPossession("1502506769", "160635473367600099", "091180", 30)));
        log.info("Preloading " + repository.save(new EtfPossession("1502506769", "160635473367600099", "091160", 50)));
        log.info("Preloading " + repository.save(new EtfPossession("1502506769", "160635473367600099", "091170", 15)));
        log.info("Preloading " + repository.save(new EtfPossession("1502506769", "160635473367600099", "999999", 5)));
        log.info("Preloading " + repository.save(new EtfPossession("1502506769", "160646584478700099", "091160", 50)));
        log.info("Preloading " + repository.save(new EtfPossession("1502506769", "160646584478700099", "091150", 10)));
        log.info("Preloading " + repository.save(new EtfPossession("1502506769", "160646584478700099", "091150", 10)));
        log.info("Preloading " + repository.save(new EtfPossession("1502506769", "160646584478700099", "999999", 10)));
        log.info("Preloading " + repository.save(new EtfPossession("1351754505", "160657695589800099", "091170", 80)));
        log.info("Preloading " + repository.save(new EtfPossession("1351754505", "160657695589800099", "091170", 90)));
        log.info("Preloading " + repository.save(new EtfPossession("1351754505", "160657695589800099", "999999", 20)));
        log.info("Preloading " + repository.save(new EtfPossession("1560682736", "160657695589888888", "999999", 80000000)));
        log.info("Preloading " + repository.save(new EtfPossession("1560682736", "160657695589888877", "999999", 50000000)));
    };
  }
}