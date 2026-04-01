package com.filmrental.service;

import com.filmrental.entity.Customer;
import com.filmrental.entity.Payment;
import com.filmrental.entity.Rental;
import com.filmrental.dto.response.CustomerResponse;
import com.filmrental.dto.response.PaymentResponse;
import com.filmrental.dto.response.RentalResponse;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.mapper.CustomerMapper;
import com.filmrental.mapper.PaymentMapper;
import com.filmrental.mapper.RentalMapper;
import com.filmrental.repository.CustomerRepository;
import com.filmrental.repository.PaymentRepository;
import com.filmrental.repository.RentalRepository;
import com.filmrental.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock private CustomerRepository customerRepository;
    @Mock private RentalRepository rentalRepository;
    @Mock private PaymentRepository paymentRepository;
    @Mock private CustomerMapper customerMapper;
    @Mock private RentalMapper rentalMapper;
    @Mock private PaymentMapper paymentMapper;

    @InjectMocks private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setActive(1);
    }

    // ─── GET /customers/{customer_id} ────────────────────────────────

    @Test
    void getCustomerById_customerExists_returnsCustomerResponse() {
        CustomerResponse expected = CustomerResponse.builder()
                .customerId(1).firstName("John").lastName("Doe").build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerMapper.toResponse(customer)).thenReturn(expected);

        CustomerResponse result = customerService.getCustomerById(1);

        assertNotNull(result);
        assertEquals(1, result.getCustomerId());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void getCustomerById_customerNotFound_throwsResourceNotFoundException() {
        when(customerRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.getCustomerById(99));

        verify(customerRepository, times(1)).findById(99);
    }

    // ─── GET /customers/{customer_id}/rentals ────────────────────────

    @Test
    void getCustomerRentals_customerExists_returnsRentalList() {
        Rental rental = new Rental();
        rental.setRentalId(10);
        rental.setCustomer(customer);

        RentalResponse rentalResponse = RentalResponse.builder()
                .rentalId(10).customerName("John Doe").status("ACTIVE").build();

        when(customerRepository.existsById(1)).thenReturn(true);
        when(rentalRepository.findByCustomer_CustomerId(1)).thenReturn(List.of(rental));
        when(rentalMapper.toResponse(rental)).thenReturn(rentalResponse);

        List<RentalResponse> result = customerService.getCustomerRentals(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getRentalId());
    }

    @Test
    void getCustomerRentals_customerNotFound_throwsResourceNotFoundException() {
        when(customerRepository.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.getCustomerRentals(99));

        verify(rentalRepository, never()).findByCustomer_CustomerId(any());
    }

    // ─── GET /customers/{customer_id}/payments ───────────────────────

    @Test
    void getCustomerPayments_customerExists_returnsPaymentList() {
        Payment payment = new Payment();
        payment.setPaymentId(20);
        payment.setCustomer(customer);
        payment.setAmount(new BigDecimal("4.99"));

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(20).amount(new BigDecimal("4.99")).build();

        when(customerRepository.existsById(1)).thenReturn(true);
        when(paymentRepository.findByCustomer_CustomerId(1)).thenReturn(List.of(payment));
        when(paymentMapper.toResponse(payment)).thenReturn(paymentResponse);

        List<PaymentResponse> result = customerService.getCustomerPayments(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(20, result.get(0).getPaymentId());
    }

    @Test
    void getCustomerPayments_customerNotFound_throwsResourceNotFoundException() {
        when(customerRepository.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.getCustomerPayments(99));

        verify(paymentRepository, never()).findByCustomer_CustomerId(any());
    }
}