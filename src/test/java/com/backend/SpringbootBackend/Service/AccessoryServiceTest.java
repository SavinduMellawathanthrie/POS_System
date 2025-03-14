package com.backend.SpringbootBackend.Service;

import com.backend.SpringbootBackend.Data.Item.Accessory;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Repository.AccessoryRepository;
import com.backend.SpringbootBackend.Service.Item.AccessoryService;
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
public class AccessoryServiceTest {

    @Mock
    private AccessoryRepository accessoryRepository; // Mock repository

    @InjectMocks
    private AccessoryService accessoryService; // Inject mock repository into service

    private Accessory accessory1, accessory2;

    @BeforeEach
    void setUp() {
        accessory1 = new Accessory();
        accessory1.setId("A001");
        accessory1.setWarrantyPeriod("12 months");

        accessory2 = new Accessory();
        accessory2.setId("A002");
        accessory2.setWarrantyPeriod("24 months");
    }

    @Test
    void testGetAllAccessories() {
        when(accessoryRepository.findAll()).thenReturn(Arrays.asList(accessory1, accessory2));

        List<Accessory> accessories = accessoryService.getAllAccessories();

        assertEquals(2, accessories.size());
        verify(accessoryRepository, times(1)).findAll();
    }

    @Test
    void testGetAccessoryById() {
        when(accessoryRepository.findById("A001")).thenReturn(Optional.of(accessory1));

        Accessory found = accessoryService.getAccessoryById("A001");

        assertNotNull(found);
        assertEquals("12 months", found.getWarrantyPeriod());
        verify(accessoryRepository, times(1)).findById("A001");
    }

    @Test
    void testGetAccessoryById_NotFound() {
        when(accessoryRepository.findById("A999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accessoryService.getAccessoryById("A999"));
    }

    @Test
    void testCreateAccessory() {
        when(accessoryRepository.save(any(Accessory.class))).thenReturn(accessory1);

        Accessory created = accessoryService.createAccessory(accessory1);

        assertNotNull(created);
        assertEquals("A001", created.getId());
        verify(accessoryRepository, times(1)).save(accessory1);
    }

    @Test
    void testUpdateAccessory() {
        when(accessoryRepository.findById("A001")).thenReturn(Optional.of(accessory1));
        when(accessoryRepository.save(any(Accessory.class))).thenReturn(accessory1);

        Accessory updated = accessoryService.updateAccessory("A001", accessory1);

        assertNotNull(updated);
        assertEquals("A001", updated.getId());
        verify(accessoryRepository, times(1)).findById("A001");
        verify(accessoryRepository, times(1)).save(accessory1);
    }

    @Test
    void testDeleteAccessory() {
        when(accessoryRepository.existsById("A001")).thenReturn(true);
        doNothing().when(accessoryRepository).deleteById("A001");

        accessoryService.deleteAccessory("A001");

        verify(accessoryRepository, times(1)).deleteById("A001");
    }

    @Test
    void testDeleteAccessory_NotFound() {
        when(accessoryRepository.existsById("A999")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> accessoryService.deleteAccessory("A999"));
    }
}
