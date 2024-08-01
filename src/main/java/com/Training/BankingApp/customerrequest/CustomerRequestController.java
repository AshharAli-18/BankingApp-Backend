package com.Training.BankingApp.customerrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRequestController {
    @Autowired
    CustomerRequestService customerRequestService;


    @PostMapping("/api/auth/customerRequest")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> customerrequest(@RequestBody CustomerRequestDTO customerRequestDTO) {
        try{
            System.out.print("customer request api called");
            customerRequestService.createCustomerRequest(customerRequestDTO);
            return ResponseEntity.ok("Customer Request Sent Successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/getAllRequests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CustomerRequest> getAllRequests() {
        return customerRequestService.getAllRequests();
    }

    @GetMapping("/api/getRequest/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CustomerRequest getRequest(@PathVariable int id) {
        return customerRequestService.getRequest(id);
    }


    @DeleteMapping("/api/deleteRequest/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteRequest(@PathVariable int id) {
        customerRequestService.deleteRequest(id);
    }



}
