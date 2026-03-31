package com.filmrental.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreInventoryResponse {
    private Integer inventoryId;
    private Integer filmId;
    private String filmTitle;
    private Integer storeId;
    private String managerFirstName;
    private String managerLastName;
}