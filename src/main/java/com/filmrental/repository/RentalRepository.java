package com.filmrental.repository;

import com.filmrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findByCustomer_CustomerId(Integer customerId);
    Optional<Rental> findByInventory_InventoryIdAndReturnDateIsNull(Integer inventoryId);
    boolean existsByInventory_InventoryIdAndReturnDateIsNull(Integer inventoryId);
}
