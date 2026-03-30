package com.filmrental.service;

import com.filmrental.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse getInventoryById(Integer inventoryId);
    List<InventoryResponse> getInventoryByStoreId(Integer storeId);
    List<InventoryResponse> getInventoryByFilmId(Integer filmId);
}
