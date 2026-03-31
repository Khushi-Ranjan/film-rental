package com.filmrental.controller;

import com.filmrental.dto.response.StoreInventoryResponse;
import com.filmrental.dto.response.StoreResponse;
import com.filmrental.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // GET /api/stores/1
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse> getStoreById(@PathVariable Integer storeId) {
        return ResponseEntity.ok(storeService.getStoreById(storeId));
    }

    @GetMapping("/manager/{managerStaffId}")
    public ResponseEntity<StoreResponse> getManagerById(@PathVariable Integer managerStaffId) {
        return ResponseEntity.ok(storeService.getManagerById(managerStaffId));
    }

    @GetMapping("/{storeId}/inventory")
    public ResponseEntity<List<StoreInventoryResponse>> getStoreInventory(
            @PathVariable Integer storeId) {
        return ResponseEntity.ok(storeService.getStoreInventory(storeId));
    }
}