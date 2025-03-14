package com.backend.SpringbootBackend.Service.Item;

import com.backend.SpringbootBackend.Data.Item.Accessory;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Repository.AccessoryRepository;
import com.backend.SpringbootBackend.Utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@Service
public class AccessoryService {

    private static final Logger LOGGER = Logger.getLogger(AccessoryService.class.getName());
    private final AccessoryRepository accessoryRepository;

    @Autowired
    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    public List<Accessory> getAllAccessories() {
        try {
            LOGGER.log(Level.INFO, "Fetching all accessories.");
            return accessoryRepository.findAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch all accessories.", e);
            throw new ServiceRuntimeException("Failed to fetch all accessories", e);
        }
    }

    public Accessory getAccessoryById(String id) {
        LOGGER.log(Level.INFO, "Fetching accessory with ID: {0}", id);
        return accessoryRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.log(Level.WARNING, "Accessory not found with ID: {0}", id);
                    return new ResourceNotFoundException("Accessory not found with ID: " + id);
                });
    }

    @Transactional
    public Accessory createAccessory(Accessory accessory) {
        LOGGER.log(Level.INFO, "Creating a new accessory with item code: {0}", accessory.getItemCode());

        // Check for uniqueness before saving
        if (accessoryRepository.findByItemCode(accessory.getItemCode()) != null) {
            LOGGER.log(Level.SEVERE, "Item Code already exists: {0}", accessory.getItemCode());
            throw new ServiceRuntimeException("Item Code already exists.");
        }
        if (accessoryRepository.findByBarcode(accessory.getBarcode()).isPresent()) {
            LOGGER.log(Level.SEVERE, "Barcode already exists: {0}", accessory.getBarcode());
            throw new ServiceRuntimeException("Barcode already exists.");
        }
        if (accessoryRepository.findByDescription(accessory.getDescription()).isPresent()) {
            LOGGER.log(Level.SEVERE, "Description already exists: {0}", accessory.getDescription());
            throw new ServiceRuntimeException("Description already exists.");
        }

        // Generate a new accessory ID
        List<Accessory> allAccessories = accessoryRepository.findAll();
        List<String> accessoryIDs = allAccessories.stream()
                .map(Accessory::getId)
                .toList();
        String accessoryID = Utilities.itemIDGenerator('A', accessoryIDs);
        accessory.setId(accessoryID);

        Accessory savedAccessory = accessoryRepository.save(accessory);
        LOGGER.log(Level.INFO, "Accessory created successfully with ID: {0}", accessoryID);
        return savedAccessory;
    }

    @Transactional
    public Accessory updateAccessory(String id, Accessory updatedAccessory) {
        LOGGER.log(Level.INFO, "Updating accessory with ID: {0}", id);
        Accessory existingAccessory = getAccessoryById(id);

        // Prevent updating with duplicate values
        if (!existingAccessory.equals(updatedAccessory)) {
            if (accessoryRepository.findByItemCode(updatedAccessory.getItemCode()) != null && !existingAccessory.getItemCode().equals(updatedAccessory.getItemCode())) {
                LOGGER.log(Level.WARNING, "Item Code already exists: {0}", updatedAccessory.getItemCode());
                throw new ServiceRuntimeException("Item Code already exists.");
            }
            if (accessoryRepository.findByBarcode(updatedAccessory.getBarcode()).isPresent()&& !existingAccessory.getBarcode().equals(updatedAccessory.getBarcode())) {
                LOGGER.log(Level.WARNING, "Barcode already exists: {0}", updatedAccessory.getBarcode());
                throw new ServiceRuntimeException("Barcode already exists.");
            }
            if (accessoryRepository.findByDescription(updatedAccessory.getDescription()).isPresent()&& !existingAccessory.getDescription().equals(updatedAccessory.getDescription())) {
                LOGGER.log(Level.WARNING, "Description already exists: {0}", updatedAccessory.getDescription());
                throw new ServiceRuntimeException("Description already exists.");
            }
        }

        Accessory savedAccessory = accessoryRepository.save(updatedAccessory);
        LOGGER.log(Level.INFO, "Accessory updated successfully with ID: {0}", id);
        return savedAccessory;
    }

    @Transactional
    public void deleteAccessory(String id) {
        LOGGER.log(Level.INFO, "Deleting accessory with ID: {0}", id);
        if (!accessoryRepository.existsById(id)) {
            LOGGER.log(Level.WARNING, "Accessory not found for deletion with ID: {0}", id);
            throw new ResourceNotFoundException("Item not found with ID: " + id);
        }
        accessoryRepository.deleteById(id);
        LOGGER.log(Level.INFO, "Accessory deleted successfully with ID: {0}", id);
    }

    public List<Accessory> getAccessoriesByCategory(String category) {
        try {
            LOGGER.log(Level.INFO, "Fetching accessories by category: {0}", category);
            return accessoryRepository.findByCategory(category);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch accessories by category: {0}", category);
            throw new ServiceRuntimeException("Failed to fetch accessories by category: " + category, e);
        }
    }

    public List<Accessory> getAccessoriesByWarrantyPeriod(String warrantyPeriod) {
        try {
            LOGGER.log(Level.INFO, "Fetching accessories by warranty period: {0}", warrantyPeriod);
            return accessoryRepository.findByWarrantyPeriod(warrantyPeriod);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch accessories by warranty period: {0}", warrantyPeriod);
            throw new ServiceRuntimeException("Failed to fetch accessories by warranty period: " + warrantyPeriod, e);
        }
    }
}
