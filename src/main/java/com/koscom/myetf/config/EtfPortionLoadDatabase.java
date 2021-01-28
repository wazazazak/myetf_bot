package com.koscom.myetf.config;

import com.koscom.myetf.entity.EtfPortion;
import com.koscom.myetf.entity.EtfPortionRepository;
import com.koscom.myetf.entity.User;
import com.koscom.myetf.entity.EtfPortion;

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
	  	log.info("Preloading " + repository.save(new EtfPortion("1502506769", "160635473367600099", "091180", 30)));
	    log.info("Preloading " + repository.save(new EtfPortion("1502506769", "160635473367600099", "091160", 50)));
	    log.info("Preloading " + repository.save(new EtfPortion("1502506769", "160635473367600099", "091170", 15)));
	    log.info("Preloading " + repository.save(new EtfPortion("1502506769", "160635473367600099", "999999", 5)));
	    log.info("Preloading " + repository.save(new EtfPortion("1502506769", "160646584478700099", "091160", 50)));
	    log.info("Preloading " + repository.save(new EtfPortion("1502506769", "160646584478700099", "091150", 20)));
	    log.info("Preloading " + repository.save(new EtfPortion("1502506769", "160646584478700099", "091150", 20)));
	    log.info("Preloading " + repository.save(new EtfPortion("1502506769", "160646584478700099", "999999", 10)));
	    log.info("Preloading " + repository.save(new EtfPortion("1351754505", "160657695589800099", "091170", 50)));
	    log.info("Preloading " + repository.save(new EtfPortion("1351754505", "160657695589800099", "091170", 45)));
	    log.info("Preloading " + repository.save(new EtfPortion("1351754505", "160657695589800099", "999999", 5)));
	    log.info("Preloading " + repository.save(new EtfPortion("1560682736", "160657695589888888", "999999", 100)));
	    log.info("Preloading " + repository.save(new EtfPortion("1560682736", "160657695589888877", "999999", 100)));
    };
  }
}