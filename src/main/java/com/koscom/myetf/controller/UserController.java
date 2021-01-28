package com.koscom.myetf.controller;

import com.koscom.myetf.entity.User;
import com.koscom.myetf.entity.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

//    @GetMapping("/user")
//    List<User> all() {
//        return repository.findAll();
//    }

    @GetMapping("/user/{chatId}")
    List<User> userByChatId(@PathVariable String chatId) {
        return repository.findByChatId(chatId);
    }
    
//    @PutMapping("/user/{chatId}")
//    List<User> resetUserState(@PathVariable String chatId) {
//    	List<User> userList = repository.findByChatId(chatId);
//    	int listLen = userList.size();
//    	for(int i=0; i<listLen; ++i) {
//    		User user = userList.get(i);
//    		user.setState(null);
//    		repository.save(user);
//    	}
//    	
//        return repository.findByChatId(chatId);
//    }
//    
//    @PutMapping("/user/{chatId}/{account}/{state}")
//    User userByChatId(@PathVariable String chatId, @PathVariable String account, @PathVariable String state) {
//    	User user = repository.findByChatIdAndAccount(chatId, account);
//    	user.setState(state);
//    	repository.save(user);
//    	
//        return repository.findByChatIdAndAccount(chatId, account);
//    }

}