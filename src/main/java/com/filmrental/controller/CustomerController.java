package com.filmrental.controller;

import com.filmrental.dto.response.CustomerResponse;
import com.filmrental.dto.response.PaymentResponse;
import com.filmrental.dto.response.RentalResponse;
import com.filmrental.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    //1st API - GET/customers/{customer_id}
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }


    // 2nd API - GET /customers/{customer_id}/rentals
    @GetMapping("/{customerId}/rentals")
    public ResponseEntity<List<RentalResponse>> getCustomerRentals(@PathVariable Integer customerId) {
        return ResponseEntity.ok(customerService.getCustomerRentals(customerId));
    }


    // 3rd API - GET /customers/{customerId}/payments
    @GetMapping("/{customerId}/payments")
    public ResponseEntity<List<PaymentResponse>> getCustomerPayments(@PathVariable Integer customerId) {
        return ResponseEntity.ok(customerService.getCustomerPayments(customerId));
    }
}
