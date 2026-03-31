package com.filmrental.mapper;

import com.filmrental.entity.Payment;
import com.filmrental.dto.response.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponse toResponse(Payment p) {
        String customerName = p.getCustomer() != null
                ? p.getCustomer().getFirstName() + " " + p.getCustomer().getLastName()
                : null;

        return PaymentResponse.builder()
                .paymentId(p.getPaymentId())
                .customerId(p.getCustomer() != null ? p.getCustomer().getCustomerId() : null)
                .customerName(customerName)
                .rentalId(p.getRental() != null ? p.getRental().getRentalId() : null)
                .amount(p.getAmount())
                .paymentDate(p.getPaymentDate())
                .status("PAID")
                .build();
    }
}