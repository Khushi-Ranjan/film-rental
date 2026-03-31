package com.filmrental.repository;

import com.filmrental.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    Optional<Store> findByManagerStaff_StaffId(Integer staffId);
}