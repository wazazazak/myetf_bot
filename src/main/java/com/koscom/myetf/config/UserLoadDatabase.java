package com.koscom.myetf.config;

import com.koscom.myetf.entity.User;
import com.koscom.myetf.entity.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserLoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(UserLoadDatabase.class);

  @Bean
  CommandLineRunner userinitDatabase(UserRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new User("1502506769", "김기혁", "160635473367600099", "다이아몬드증권", 30000000)));
      log.info("Preloading " + repository.save(new User("1502506769", "김기혁", "160646584478700099", "STAR증권", 50000000)));
      log.info("Preloading " + repository.save(new User("1351754505", "장한이", "160657695589800099", "사이버증권", 80000000)));
      log.info("Preloading " + repository.save(new User("1560682736", "김성수", "160657695589888888", "사이버증권", 80000000)));
      log.info("Preloading " + repository.save(new User("1560682736", "김성수", "160657695589888877", "코스콤증권", 50000000)));
    };
  }
}