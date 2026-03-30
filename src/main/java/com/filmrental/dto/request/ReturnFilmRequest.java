package com.filmrental.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReturnFilmRequest {
    @NotNull(message = "Rental ID is required")
    private Integer rentalId;
}
