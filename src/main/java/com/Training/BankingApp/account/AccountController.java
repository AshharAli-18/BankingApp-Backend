package com.Training.BankingApp.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/api/getAccount/{accountId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public Account getAccount(@PathVariable("accountId") long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping("/api/getAccoutByUserId/{userId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')or hasRole('ROLE_ADMIN')")
    public Account getAccuntByUserId(@PathVariable("userId") long userId) {
        return accountService.getAccountByUserId(userId);
    }

    @GetMapping("/api/getAllAccounts")
//    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Account> getAllAccounts(@RequestParam(name = "page", defaultValue ="0")Integer page, @RequestParam(name = "size", defaultValue ="10")Integer size) {
        return accountService.getAllAccounts(page, size);
    }

    @PutMapping("/api/admin/updateAccount/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateAccount(@PathVariable Long id, @RequestBody AccountUpdateRequest accountUpdateRequest) {
        accountUpdateRequest.setAccountId(id);  // Set the ID from the path variable
        accountService.updateAccount(accountUpdateRequest);
    }

    @DeleteMapping("/api/admin/deleteAccount/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @PostMapping("/api/admin/createAccount")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registration(@RequestBody AccountCreateRequest accountCreateRequest) {
        try{
            accountService.createAccount(accountCreateRequest);
            return ResponseEntity.ok("Account Created Successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
