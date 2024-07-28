package com.Training.BankingApp.account;

import com.Training.BankingApp.transaction.Transaction;
import com.Training.BankingApp.transaction.TransactionRepository;
import com.Training.BankingApp.transfer.Transfer;
import com.Training.BankingApp.transfer.TransferRepository;
import com.Training.BankingApp.user.User;
import com.Training.BankingApp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransferRepository transferRepository;

    public Account getAccount(long accountId) {
        Account account=accountRepository.findById(accountId).get();
        if(account!=null){
            return account;
        }
        else {
            return null;
        }
    }

    public Account getAccountByUserId(long userId) {
        Account account=accountRepository.findByUserId(userId);
        if(account!=null){
            return account;
        }
        else {
            return null;
        }
    }

    public String generateAccountNumber() {
        UUID uuid = UUID.randomUUID();
        // Encode UUID to Base64 and then trim it to desired length
        String base64UUID = Base64.getUrlEncoder().withoutPadding().encodeToString(toByteArray(uuid));
        return base64UUID.substring(0, 14);
    }

    private byte[] toByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }

        return buffer;
    }

    public List<Account> getAllAccounts(Integer page, Integer size) {
        if(page<0){
            page=0;
        }
        if(size>1000){
            size=1000;
        }
        return accountRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public void updateAccount(AccountUpdateRequest updateRequest) {
        Account account = accountRepository.findById(updateRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Update account details
        account.setAccountType(updateRequest.getAccountType());
        account.setBalance(updateRequest.getBalance());
        accountRepository.save(account);  // Save updated account

        // Update user details
        User user = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updateRequest.getUsername());
        user.setEmail(updateRequest.getEmail());
        user.setPhoneNumber(updateRequest.getPhone());
        user.setCnic(updateRequest.getCnic());
        user.setName(updateRequest.getName());
        userRepository.save(user);  // Save updated user
    }

    public void deleteAccount(long accountId) {
        Account account=accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        User user = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transaction=transactionRepository.findByAccountId(accountId);

        List<Transfer> transfer=transferRepository.findByFromAccountId(accountId);

        accountRepository.delete(account);
        userRepository.delete(user);
        transferRepository.deleteAll(transfer);
        transactionRepository.deleteAll(transaction);

    }

    public void createAccount(AccountCreateRequest accountCreateRequest) {
        if(userRepository.existsByUsername(accountCreateRequest.getUsername())) {
            throw new RuntimeException("User already registered!");
        }
        if(userRepository.existsByEmail(accountCreateRequest.getEmail())) {
            throw new RuntimeException("User already registered!");
        }

        User user = new User();
        user.setUsername(accountCreateRequest.getUsername());
        // user.setPassword(passwordEncoder.encode(accountCreateRequest.getPassword()));
        user.setPassword(accountCreateRequest.getPassword());
        user.setEmail(accountCreateRequest.getEmail());
        user.setPhoneNumber(accountCreateRequest.getPhoneNumber());
        user.setRoleId(2);
        user.setName(accountCreateRequest.getName());
        user.setAddress(accountCreateRequest.getAddress());
        user.setCnic(accountCreateRequest.getCnic());

        userRepository.save(user);

        Account account = new Account();
        account.setUserId(user.getUserId());
        account.setBalance(0);
        account.setOpeningDate(LocalDate.now());
        account.setAccountType(accountCreateRequest.getAccountType());
        account.setAccountNumber(generateAccountNumber());

        accountRepository.save(account);

    }

}
