package com.koscom.myetf.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.koscom.myetf.entity.User;
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByChatId(String chatId);
}