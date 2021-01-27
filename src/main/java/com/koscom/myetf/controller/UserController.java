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
}