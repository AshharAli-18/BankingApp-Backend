package com.Training.BankingApp.customerrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRequestService {
    @Autowired
    private CustomerRequestRepository customerRequestRepository;

    public void createCustomerRequest(CustomerRequestDTO customerRequestdto) {

        if(customerRequestRepository.existsByUsername(customerRequestdto.getUsername())) {
            throw new RuntimeException("Request already sent!");
        }
        if(customerRequestRepository.existsByEmail(customerRequestdto.getEmail())) {
            throw new RuntimeException("Request already sent!");
        }

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setId(System.currentTimeMillis());
        customerRequest.setUsername(customerRequestdto.getUsername());
        customerRequest.setEmail(customerRequestdto.getEmail());
        customerRequest.setPassword(customerRequestdto.getPassword());
        customerRequest.setName(customerRequestdto.getName());
        customerRequest.setAddress(customerRequestdto.getAddress());
        customerRequest.setCnic(customerRequestdto.getCnic());
        customerRequest.setAccountType(customerRequestdto.getAccountType());
        customerRequest.setPhoneNumber(customerRequestdto.getPhoneNumber());

        customerRequestRepository.save(customerRequest);


    }

    public List<CustomerRequest> getAllRequests() {
        return customerRequestRepository.findAll();
    }

    public CustomerRequest getRequest(int id) {
        CustomerRequest customerRequest=customerRequestRepository.findById(id).get();
        if(customerRequest!=null){
            return customerRequest;
        }
        else {
            return null;
        }
    }

    public void deleteRequest(int id) {
        CustomerRequest customerRequest=customerRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        customerRequestRepository.delete(customerRequest);

    }
}
