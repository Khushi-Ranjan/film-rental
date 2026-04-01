package com.filmrental.controller;

import com.filmrental.dto.request.RentFilmRequest;
import com.filmrental.dto.request.ReturnFilmRequest;
import com.filmrental.dto.response.RentalResponse;
import com.filmrental.service.RentalService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/v1/rentals") @RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    // GET /rentals/customer/{customer_id}
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RentalResponse>> getRentalsByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(rentalService.getRentalsByCustomerId(customerId));
    }

    
}
