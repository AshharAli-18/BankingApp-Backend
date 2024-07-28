package com.Training.BankingApp.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions(Integer page, Integer size) {
        if(page<0){
            page=0;
        }
        if(size>1000){
            size=1000;
        }
        return transactionRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Transaction> getAllByAccountId(long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public List<Transaction> getAllByTransferId(long transferId) {
        return transactionRepository.findByTransferId(transferId);
    }


}
