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
      log.info("Preloading " + repository.save(new User("1502506769", "kim", "1111", "키움증권", 200000)));
      log.info("Preloading " + repository.save(new User("1502506769", "kim", "3333", "NH증권", 200000)));
      log.info("Preloading " + repository.save(new User("2", "lee", "2222", "키움증권", 100000)));
    };
  }
}