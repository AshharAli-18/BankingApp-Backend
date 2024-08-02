package com.Training.BankingApp.deletedaccount;

import com.Training.BankingApp.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity(name = "deletedaccounts")
public class DeletedAccount {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deletedaccountId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    private long userId;
    private String accountNumber;
    private String accountType;
    private long balance;
    private LocalDate openingDate;

}