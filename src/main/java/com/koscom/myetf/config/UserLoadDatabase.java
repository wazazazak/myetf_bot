package com.koscom.mymyetf.config;

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
      log.info("Preloading " + repository.save(new User("1", "kim", "1111", 200000)));
      log.info("Preloading " + repository.save(new User("1", "kim", "3333", 200000)));
      log.info("Preloading " + repository.save(new User("2", "lee", "2222", 100000)));
    };
  }
}