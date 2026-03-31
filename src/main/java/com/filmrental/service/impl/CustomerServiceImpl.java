package com.filmrental.service.impl;

import com.filmrental.dto.response.RentalResponse;
import com.filmrental.entity.Customer;
import com.filmrental.dto.response.CustomerResponse;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.mapper.CustomerMapper;
import com.filmrental.mapper.PaymentMapper;
import com.filmrental.repository.CustomerRepository;
import com.filmrental.repository.PaymentRepository;
import com.filmrental.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.filmrental.repository.RentalRepository;
import com.filmrental.mapper.RentalMapper;
import com.filmrental.dto.response.PaymentResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional(readOnly=true)
    public CustomerResponse getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toResponse(customer);
    }


    @Override
    @Transactional(readOnly = true)
    public List<RentalResponse> getCustomerRentals(Integer customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }

        return rentalRepository.findByCustomer_CustomerId(customerId)
                .stream()
                .map(rentalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getCustomerPayments(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
        return paymentRepository.findByCustomer_CustomerId(customerId)
                .stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }
}