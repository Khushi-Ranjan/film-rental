package com.filmrental.service.impl;

import com.filmrental.entity.*;
import com.filmrental.dto.request.RentFilmRequest;
import com.filmrental.dto.request.ReturnFilmRequest;
import com.filmrental.dto.response.RentalResponse;
import com.filmrental.exception.BusinessException;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.mapper.RentalMapper;
import com.filmrental.repository.*;
import com.filmrental.service.RentalService;
import com.filmrental.util.FeeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final InventoryRepository inventoryRepository;
    private final StaffRepository staffRepository;
    private final PaymentRepository paymentRepository;
    private final RentalMapper rentalMapper;

    @Override @Transactional(readOnly=true)
    public List<RentalResponse> getAllRentals() {
        return rentalRepository.findAll().stream().map(rentalMapper::toResponse).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly=true)
    public RentalResponse getRentalById(Integer id) {
        return rentalMapper.toResponse(rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id)));
    }

    @Override @Transactional
    public RentalResponse rentFilm(RentFilmRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));
        if (customer.getActive() == 0) throw new BusinessException("Customer account is inactive");

        List<Inventory> inventoryList = inventoryRepository.findByFilm_FilmId(request.getFilmId());
        if (inventoryList.isEmpty())
            throw new ResourceNotFoundException("No inventory found for film id: " + request.getFilmId());

        Inventory available = inventoryList.stream()
                .filter(inv -> !rentalRepository.existsByInventory_InventoryIdAndReturnDateIsNull(inv.getInventoryId()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("No available copies for film id: " + request.getFilmId()));

        Staff staff = staffRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("Default staff not found"));

        Rental rental = new Rental();
        rental.setRentalDate(LocalDateTime.now());
        rental.setInventory(available);
        rental.setCustomer(customer);
        rental.setStaff(staff);
        return rentalMapper.toResponse(rentalRepository.save(rental));
    }

    @Override @Transactional
    public RentalResponse returnFilm(ReturnFilmRequest request) {
        Rental rental = rentalRepository.findById(request.getRentalId())
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + request.getRentalId()));
        if (rental.getReturnDate() != null)
            throw new BusinessException("Film already returned for rental id: " + request.getRentalId());

        rental.setReturnDate(LocalDateTime.now());
        Rental saved = rentalRepository.save(rental);

        Staff staff = staffRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("Default staff not found"));

        java.math.BigDecimal fee = FeeCalculator.calculate(
                rental.getInventory().getFilm().getRentalRate(),
                rental.getRentalDate(), rental.getReturnDate());

        Payment payment = new Payment();
        payment.setCustomer(rental.getCustomer());
        payment.setStaff(staff);
        payment.setRental(saved);
        payment.setAmount(fee);
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);

        return rentalMapper.toResponse(saved);
    }

    @Override @Transactional(readOnly=true)
    public List<RentalResponse> getRentalsByCustomerId(Integer customerId) {
        return rentalRepository.findByCustomer_CustomerId(customerId)
                .stream().map(rentalMapper::toResponse).collect(Collectors.toList());
    }

    @Override @Transactional
    public void deleteRental(Integer id) {
        if (!rentalRepository.existsById(id))
            throw new ResourceNotFoundException("Rental not found with id: " + id);
        rentalRepository.deleteById(id);
    }
}
