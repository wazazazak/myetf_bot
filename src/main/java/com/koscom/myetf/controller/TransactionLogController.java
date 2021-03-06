package com.koscom.myetf.controller;

import com.koscom.myetf.entity.TransactionLog;
import com.koscom.myetf.entity.TransactionLogRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RestController
class TransactionLogController {

    private final TransactionLogRepository repository;

    TransactionLogController(TransactionLogRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping("/transactionlog")
    TransactionLog newTransactionLog(@RequestBody TransactionLog newTransactionLog) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String createdTime = dateFormat.format(calendar.getTime());
        newTransactionLog.setCreatedTime(createdTime);

        return repository.save(newTransactionLog);
    }

    @GetMapping("/transactionlog/{chatId}")
    List<TransactionLog> transactionLogByChatId(@PathVariable String chatId) {
        return repository.findByChatId(chatId);
    }

    @GetMapping("/transactionlog/{chatId}/{account}")
    List<TransactionLog> transactionLogByChatIdAndAccount(@PathVariable String chatId, @PathVariable String account) {
        return repository.findByChatIdAndAccount(chatId, account);
    }

}