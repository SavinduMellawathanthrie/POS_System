package com.backend.SpringbootBackend.Service.Item;

import com.backend.SpringbootBackend.Data.Item.Accessory;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Repository.AccessoryRepository;
import com.backend.SpringbootBackend.Utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    @Autowired
    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    public List<Accessory> getAllAccessories() {
        try {
            return accessoryRepository.findAll();
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch all accessories", e);
        }
    }

    public Accessory getAccessoryById(String id) {
        return accessoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accessory not found with ID: " + id));
    }

    @Transactional
    public Accessory createAccessory(Accessory accessory) {
        // Check for uniqueness before saving
        if (accessoryRepository.findByItemCode(accessory.getItemCode()) != null) {
            throw new ServiceRuntimeException("Item Code already exists.");
        }
        if (accessoryRepository.findByBarcode(accessory.getBarcode()).isPresent()) {
            throw new ServiceRuntimeException("Barcode already exists.");
        }
        if (accessoryRepository.findByDescription(accessory.getDescription()).isPresent()) {
            throw new ServiceRuntimeException("Description already exists.");
        }

        // Generate a new accessory ID
        List<Accessory> allAccessories = accessoryRepository.findAll();
        List<String> accessoryIDs = allAccessories.stream()
                .map(Accessory::getId)
                .toList();
        String accessoryID = Utilities.itemIDGenerator('A', accessoryIDs);
        accessory.setId(accessoryID);

        // Save and return the created accessory item
        return accessoryRepository.save(accessory);
    }

    @Transactional
    public Accessory updateAccessory(String id, Accessory updatedAccessory) {
        Accessory existingAccessory = getAccessoryById(id);

        // Prevent updating with duplicate values
        if (!existingAccessory.equals(updatedAccessory)) {
            if (accessoryRepository.findByItemCode(updatedAccessory.getItemCode()) != null) {
                throw new ServiceRuntimeException("Item Code already exists.");
            }
            if (accessoryRepository.findByBarcode(updatedAccessory.getBarcode()).isPresent()) {
                throw new ServiceRuntimeException("Barcode already exists.");
            }
            if (accessoryRepository.findByDescription(updatedAccessory.getDescription()).isPresent()) {
                throw new ServiceRuntimeException("Description already exists.");
            }
        }

        return accessoryRepository.save(updatedAccessory);
    }

    @Transactional
    public void deleteAccessory(String id) {
        if (!accessoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with ID: " + id);
        }
        accessoryRepository.deleteById(id);
    }

    public List<Accessory> getAccessoriesByCategory(String category) {
        try {
            return accessoryRepository.findByCategory(category);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch accessories by category: " + category, e);
        }
    }

    public List<Accessory> getAccessoriesByWarrantyPeriod(String warrantyPeriod) {
        try {
            return accessoryRepository.findByWarrantyPeriod(warrantyPeriod);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch accessories by warranty period: " + warrantyPeriod, e);
        }
    }
}
