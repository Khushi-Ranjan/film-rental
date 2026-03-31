package com.filmrental.service.impl;

import com.filmrental.dto.response.PaymentResponse;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.mapper.PaymentMapper;
import com.filmrental.repository.PaymentRepository;
import com.filmrental.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional(readOnly=true)
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly=true)
    public PaymentResponse getPaymentById(Integer id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    @Override
    @Transactional(readOnly=true)
    public List<PaymentResponse> getPaymentsByCustomerId(Integer customerId) {
        if (!paymentRepository.existsByCustomer_CustomerId(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
        return paymentRepository.findByCustomer_CustomerId(customerId)
                .stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePayment(Integer id) {
        if (!paymentRepository.existsById(id))
            throw new ResourceNotFoundException("Payment not found with id: " + id);
        paymentRepository.deleteById(id);
    }
}