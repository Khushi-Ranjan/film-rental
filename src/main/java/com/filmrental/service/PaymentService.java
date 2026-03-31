package com.filmrental.service;

import com.filmrental.dto.response.PaymentResponse;
import java.util.List;

public interface PaymentService {
    List<PaymentResponse> getAllPayments();
    PaymentResponse getPaymentById(Integer id);
    List<PaymentResponse> getPaymentsByCustomerId(Integer customerId);
    void deletePayment(Integer id);
}