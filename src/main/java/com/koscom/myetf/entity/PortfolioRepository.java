package com.koscom.myetf.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    public Portfolio findByPortfolioKey(String portfolioKey);
    public List<Portfolio> findByChatId(String chatId);
}
