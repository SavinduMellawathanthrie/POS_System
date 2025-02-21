package com.backend.SpringbootBackend.Service;

import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Repository.ClothingRepository;
import com.backend.SpringbootBackend.Service.Item.ClothingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito support
public class ClothingServiceTest {

    @Mock
    private ClothingRepository clothingRepository; // Mocking repository

    @InjectMocks
    private ClothingService clothingService; // Injecting the mock into service

    private Clothing clothing1, clothing2;

    @BeforeEach
    void setUp() {
        clothing1 = new Clothing("C001", "Shirt", 25.0, 10, "Men", "Blue", "M");
        clothing2 = new Clothing("C002", "Jeans", 50.0, 5, "Women", "Black", "L");
    }

    @Test
    void testGetAllClothing() {
        when(clothingRepository.findAll()).thenReturn(Arrays.asList(clothing1, clothing2));

        List<Clothing> clothingList = clothingService.getAllClothing();

        assertEquals(2, clothingList.size());
        verify(clothingRepository, times(1)).findAll();
    }

    @Test
    void testGetClothingById() {
        when(clothingRepository.findById("C001")).thenReturn(Optional.of(clothing1));

        Clothing found = clothingService.getClothingById("C001");

        assertNotNull(found);
        assertEquals("Shirt", found.getDescription());
        verify(clothingRepository, times(1)).findById("C001");
    }

    @Test
    void testCreateClothing() {
        when(clothingRepository.save(any(Clothing.class))).thenReturn(clothing1);

        clothingService.createClothing(clothing1);

        verify(clothingRepository, times(1)).save(clothing1);
    }

    @Test
    void testDeleteClothing() {
        doNothing().when(clothingRepository).deleteById("C001");

        clothingService.deleteClothing("C001");

        verify(clothingRepository, times(1)).deleteById("C001");
    }
}
