package com.filmrental.repository;

import com.filmrental.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomer_CustomerId(Integer customerId);
    List<Payment> findByRental_RentalId(Integer rentalId);
    boolean existsByCustomer_CustomerId(Integer customerId);
}