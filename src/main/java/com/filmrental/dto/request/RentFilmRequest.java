package com.filmrental.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class RentFilmRequest {
    @NotNull(message = "Customer ID is required") private Integer customerId;
    @NotNull(message = "Film ID is required") private Integer filmId;
}
