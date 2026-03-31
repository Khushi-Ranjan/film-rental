package com.filmrental.controller;

import com.filmrental.dto.response.InventoryResponse;
import com.filmrental.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    // GET /inventory/{inventory_id}
    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Integer inventoryId) {
        return ResponseEntity.ok(inventoryService.getInventoryById(inventoryId));
    }
    // GET /inventory/store/{store_id}
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<InventoryResponse>> getInventoryByStore(@PathVariable Integer storeId) {
        return ResponseEntity.ok(inventoryService.getInventoryByStoreId(storeId));
    }
    // GET /inventory/film/{film_id}
    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<InventoryResponse>> getInventoryByFilm(@PathVariable Integer filmId) {
        return ResponseEntity.ok(inventoryService.getInventoryByFilmId(filmId));
    }


}
