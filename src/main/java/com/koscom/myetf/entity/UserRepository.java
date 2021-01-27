package com.koscom.myetf.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<com.koscom.myetf.entity.User, Long> {

    public List<com.koscom.myetf.entity.User> findByCharId(String charId);
//    @Query("select p from User p where p.charId = ?1 and p.account = ?2")
//    User getByCharIdAndAccount(String charId, String account);
}