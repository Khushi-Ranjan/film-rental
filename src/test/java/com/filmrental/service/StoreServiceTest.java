package com.filmrental.service;

import com.filmrental.entity.*;
import com.filmrental.dto.response.StoreResponse;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.repository.StoreRepository;
import com.filmrental.service.impl.StoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.filmrental.dto.response.StoreInventoryResponse;
import com.filmrental.repository.InventoryRepository;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock private StoreRepository storeRepository;
    @Mock private InventoryRepository inventoryRepository;
    @InjectMocks private StoreServiceImpl storeService;

    private Store store;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        Country country = new Country();
        country.setCountryId(1);
        country.setCountry("Canada");

        City city = new City();
        city.setCityId(1);
        city.setCity("Lethbridge");
        city.setCountry(country);

        Address address = new Address();
        address.setAddressId(1);
        address.setAddress("47 MySakila Drive");
        address.setCity(city);

        Staff staff = new Staff();
        staff.setStaffId(1);
        staff.setFirstName("Mike");
        staff.setLastName("Hillyer");

        store = new Store();
        store.setStoreId(1);
        store.setManagerStaff(staff);
        store.setAddress(address);

        Film film = new Film();
        film.setFilmId(1);
        film.setTitle("ACADEMY DINOSAUR");

        inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setFilm(film);
        inventory.setStore(store);
    }
    // API 1: GET /api/v1/stores/{store_id}
    // Positive test
    // store exists → should return correct response
    @Test
    void getStoreById_storeExists_returnsStoreResponse() {
        // Arrange
        when(storeRepository.findById(1))
                .thenReturn(Optional.of(store));

        // Act
        StoreResponse result = storeService.getStoreById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1,                    result.getStoreId());
        assertEquals("47 MySakila Drive",  result.getAddress());
        assertEquals("Lethbridge",         result.getCity());
        assertEquals("Canada",             result.getCountry());
        assertEquals("Mike",               result.getManagerFirstName());
        assertEquals("Hillyer",            result.getManagerLastName());
        verify(storeRepository, times(1)).findById(1);
    }

    // Negative test
    // store does not exist → should throw 404
    @Test
    void getStoreById_storeNotFound_throwsResourceNotFoundException() {
        // Arrange
        when(storeRepository.findById(99))
                .thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> storeService.getStoreById(99));

        assertEquals("Store not found with id: 99", ex.getMessage());
        verify(storeRepository, times(1)).findById(99);
    }

    // API 2: GET /api/v1/stores/{store_id}/inventory
    // Positive test
    // store exists and has inventory → should return inventory list

    @Test
    void getStoreInventory_storeExists_returnsInventoryList() {
        // Arrange
        when(storeRepository.findById(1))
                .thenReturn(Optional.of(store));
        when(inventoryRepository.findByStore_StoreId(1))
                .thenReturn(List.of(inventory));

        // Act
        List<StoreInventoryResponse> result =
                storeService.getStoreInventory(1);

        // Assert
        assertNotNull(result);
        assertEquals(1,                  result.size());
        assertEquals(1,                  result.get(0).getInventoryId());
        assertEquals(1,                  result.get(0).getFilmId());
        assertEquals("ACADEMY DINOSAUR", result.get(0).getFilmTitle());
        assertEquals(1,                  result.get(0).getStoreId());
        assertEquals("Mike",             result.get(0).getManagerFirstName());
        assertEquals("Hillyer",          result.get(0).getManagerLastName());
        verify(storeRepository,     times(1)).findById(1);
        verify(inventoryRepository, times(1)).findByStore_StoreId(1);
    }

    // Negative test
    // store does not exist → should throw 404
    // inventory should never be queried
    @Test
    void getStoreInventory_storeNotFound_throwsResourceNotFoundException() {
        // Arrange
        when(storeRepository.findById(99))
                .thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> storeService.getStoreInventory(99));

        assertEquals("Store not found with id: 99", ex.getMessage());
        verify(storeRepository,     times(1)).findById(99);
        verify(inventoryRepository, never()).findByStore_StoreId(any());
    }
}