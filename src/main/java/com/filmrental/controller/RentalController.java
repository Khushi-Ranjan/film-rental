package com.filmrental.controller;

import com.filmrental.dto.request.RentFilmRequest;
import com.filmrental.dto.request.ReturnFilmRequest;
import com.filmrental.dto.response.RentalResponse;
import com.filmrental.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/rentals") @RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    // GET /rentals/customer/{customer_id}
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RentalResponse>> getRentalsByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(rentalService.getRentalsByCustomerId(customerId));
    }

    // POST /rentals/rent
//    @PostMapping("/rent")
//    public ResponseEntity<RentalResponse> rentFilm(@RequestBody @Valid RentFilmRequest request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.rentFilm(request));
//    }
//
//    // PUT /rentals/return
//    @PutMapping("/return")
//    public ResponseEntity<RentalResponse> returnFilm(@RequestBody @Valid ReturnFilmRequest request) {
//        return ResponseEntity.ok(rentalService.returnFilm(request));
//    }
//
//    // DELETE /rentals/{rental_id}
//    @DeleteMapping("/{rentalId}")
//    public ResponseEntity<Void> deleteRental(@PathVariable Integer rentalId) {
//        rentalService.deleteRental(rentalId);
//        return ResponseEntity.noContent().build();
//    }
}
