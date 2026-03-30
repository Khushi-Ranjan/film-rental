package com.filmrental.mapper;

import com.filmrental.entity.Rental;
import com.filmrental.dto.response.RentalResponse;
import org.springframework.stereotype.Component;
@Component
public class RentalMapper {
    public RentalResponse toResponse(Rental r) {
        String filmTitle = (r.getInventory() != null && r.getInventory().getFilm() != null)
                ? r.getInventory().getFilm().getTitle() : null;
        String customerName = r.getCustomer() != null
                ? r.getCustomer().getFirstName() + " " + r.getCustomer().getLastName() : null;
        return RentalResponse.builder()
                .rentalId(r.getRentalId()).rentalDate(r.getRentalDate()).returnDate(r.getReturnDate())
                .customerId(r.getCustomer() != null ? r.getCustomer().getCustomerId() : null)
                .customerName(customerName)
                .inventoryId(r.getInventory() != null ? r.getInventory().getInventoryId() : null)
                .filmTitle(filmTitle)
                .status(r.getReturnDate() == null ? "ACTIVE" : "RETURNED").build();
    }
}
