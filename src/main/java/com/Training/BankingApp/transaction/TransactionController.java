package com.Training.BankingApp.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/getAllTransactions")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Transaction> getAllTransactions(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return transactionService.getAllTransactions(page, size);
    }


    @GetMapping("/api/getTransactionByAccountId/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public List<Transaction> getTransactionByAccountId(@PathVariable long id) {
        return transactionService.getAllByAccountId(id);
    }


    @GetMapping("/api/getTransactionByTransferId/{id}")
    @PreAuthorize(" hasRole('ROLE_ADMIN')")
    public List<Transaction> getTransactionByTransferId(@PathVariable long id) {
        return transactionService.getAllByTransferId(id);
    }

}
