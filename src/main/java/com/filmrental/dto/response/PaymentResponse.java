package com.filmrental.dto.response;
import com.filmrental.dto.response.PaymentResponse;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Integer paymentId;
    private Integer customerId;
    private String customerName;
    private Integer rentalId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String status;
}