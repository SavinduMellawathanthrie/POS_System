package com.backend.SpringbootBackend.Service;

import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
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
class ClothingServiceTest {

    @Mock
    private ClothingRepository clothingRepository; // Mock repository

    @InjectMocks
    private ClothingService clothingService; // Inject mock repository into service

    private Clothing clothing1, clothing2;

    @BeforeEach
    void setUp() {
        clothing1 = new Clothing();
        clothing1.setId("C001");
        clothing1.setDescription("Shirt");
        clothing1.setUnitRetailPrice(25.00);
        clothing1.setQuantity(10);
        clothing1.setCategory("Men");
        clothing1.setColor("Blue");
        clothing1.setSize("M");

        clothing2 = new Clothing();
        clothing2.setId("C002");
        clothing2.setDescription("Jeans");
        clothing2.setUnitRetailPrice(50.00);
        clothing2.setQuantity(5);
        clothing2.setCategory("Women");
        clothing2.setColor("Black");
        clothing2.setSize("L");
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
    void testGetClothingById_NotFound() {
        when(clothingRepository.findById("C999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clothingService.getClothingById("C999"));
    }

    @Test
    void testCreateClothing() {
        when(clothingRepository.save(any(Clothing.class))).thenReturn(clothing1);

        Clothing created = clothingService.createClothing(clothing1);

        assertNotNull(created);
        assertEquals("C001", created.getId());
        verify(clothingRepository, times(1)).save(clothing1);
    }

    @Test
    void testUpdateClothing() {
        when(clothingRepository.findById("C001")).thenReturn(Optional.of(clothing1));
        when(clothingRepository.save(any(Clothing.class))).thenReturn(clothing1);

        Clothing updated = clothingService.updateClothing("C001", clothing1);

        assertNotNull(updated);
        assertEquals("C001", updated.getId());
        verify(clothingRepository, times(1)).findById("C001");
        verify(clothingRepository, times(1)).save(clothing1);
    }

    @Test
    void testUpdateClothing_NotFound() {
        when(clothingRepository.findById("C999")).thenReturn(Optional.empty());

        Clothing updatedClothing = new Clothing();
        updatedClothing.setId("C999");
        updatedClothing.setDescription("T-shirt");

        assertThrows(ResourceNotFoundException.class, () -> clothingService.updateClothing("C999", updatedClothing));
    }

    @Test
    void testDeleteClothing() {
        when(clothingRepository.existsById("C001")).thenReturn(true);
        doNothing().when(clothingRepository).deleteById("C001");

        clothingService.deleteClothing("C001");

        verify(clothingRepository, times(1)).deleteById("C001");
    }

    @Test
    void testDeleteClothing_NotFound() {
        when(clothingRepository.existsById("C999")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clothingService.deleteClothing("C999"));
    }
}
