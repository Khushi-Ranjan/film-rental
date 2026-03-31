package com.filmrental.service;

import com.filmrental.dto.response.StoreInventoryResponse;
import com.filmrental.dto.response.StoreResponse;

import java.util.List;

public interface StoreService {
    StoreResponse getStoreById(Integer storeId);
    StoreResponse getManagerById(Integer managerStaffId);
    List<StoreInventoryResponse> getStoreInventory(Integer storeId);
}