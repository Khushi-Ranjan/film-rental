package com.filmrental.service;

import com.filmrental.dto.request.RentFilmRequest;
import com.filmrental.dto.request.ReturnFilmRequest;
import com.filmrental.dto.response.RentalResponse;

import java.util.List;

public interface RentalService {

    List<RentalResponse> getAllRentals();

    RentalResponse getRentalById(Integer id);

    RentalResponse rentFilm(RentFilmRequest request);

    RentalResponse returnFilm(ReturnFilmRequest request);

    List<RentalResponse> getRentalsByCustomerId(Integer customerId);

    void deleteRental(Integer id);
}
