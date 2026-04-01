package com.filmrental.service;

import com.filmrental.dto.response.RentalResponse;
import com.filmrental.entity.Customer;
import com.filmrental.entity.Film;
import com.filmrental.entity.Inventory;
import com.filmrental.entity.Rental;
import com.filmrental.mapper.RentalMapper;
import com.filmrental.repository.CustomerRepository;
import com.filmrental.repository.InventoryRepository;
import com.filmrental.repository.PaymentRepository;
import com.filmrental.repository.RentalRepository;
import com.filmrental.repository.StaffRepository;
import com.filmrental.service.impl.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock private RentalRepository rentalRepository;
    @Mock private CustomerRepository customerRepository;
    @Mock private InventoryRepository inventoryRepository;
    @Mock private StaffRepository staffRepository;
    @Mock private PaymentRepository paymentRepository;
    @Mock private RentalMapper rentalMapper;

    @InjectMocks
    private RentalServiceImpl rentalService;

    private Customer customer;
    private Inventory inventory;
    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setFilmId(1);
        film.setTitle("ACADEMY DINOSAUR");

        inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setFilm(film);

        customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
    }

    @Test
    void getAllRentals_positive_returnsListOfRentalResponses() {
        Rental rental = new Rental();
        rental.setRentalId(1);
        rental.setCustomer(customer);
        rental.setInventory(inventory);

        RentalResponse response = RentalResponse.builder()
                .rentalId(1)
                .customerName("John Doe")
                .filmTitle("ACADEMY DINOSAUR")
                .build();

        when(rentalRepository.findAll()).thenReturn(List.of(rental));
        when(rentalMapper.toResponse(rental)).thenReturn(response);

        List<RentalResponse> result = rentalService.getAllRentals();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getRentalId());
    }

    @Test
    void getAllRentals_negative_returnsEmptyList() {
        when(rentalRepository.findAll()).thenReturn(Collections.emptyList());

        List<RentalResponse> result = rentalService.getAllRentals();

        assertEquals(0, result.size());
    }

    @Test
    void getRentalsByCustomerId_positive_returnsCustomerRentals() {
        Rental rental = new Rental();
        rental.setRentalId(5);
        rental.setCustomer(customer);
        rental.setInventory(inventory);

        RentalResponse response = RentalResponse.builder()
                .rentalId(5)
                .customerName("John Doe")
                .filmTitle("ACADEMY DINOSAUR")
                .build();

        when(rentalRepository.findByCustomer_CustomerId(1)).thenReturn(List.of(rental));
        when(rentalMapper.toResponse(rental)).thenReturn(response);

        List<RentalResponse> result = rentalService.getRentalsByCustomerId(1);

        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getRentalId());
    }

    @Test
    void getRentalsByCustomerId_negative_returnsEmptyList() {
        when(rentalRepository.findByCustomer_CustomerId(999)).thenReturn(Collections.emptyList());

        List<RentalResponse> result = rentalService.getRentalsByCustomerId(999);

        assertEquals(0, result.size());
    }
}
