package com.filmrental.dto.response;
import lombok.Builder; import lombok.Data;
import java.time.LocalDateTime;
@Data @Builder
public class RentalResponse {
    private Integer rentalId;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private Integer customerId;
    private String customerName;
    private Integer inventoryId;
    private String filmTitle;
    private String status;
}
