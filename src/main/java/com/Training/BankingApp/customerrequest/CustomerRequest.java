package com.Training.BankingApp.customerrequest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "customerrequests")
public class CustomerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String address;
    private String password;
    private String email;
    private Long phoneNumber;
    private String accountType;
    private String cnic;

}