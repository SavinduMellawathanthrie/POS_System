package com.backend.SpringbootBackend.Service.Item;

import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Repository.ClothingRepository;
import com.backend.SpringbootBackend.Utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClothingService {

    private final ClothingRepository clothingRepository;

    @Autowired
    public ClothingService(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    public List<Clothing> getAllClothing() {
        try {
            return clothingRepository.findAll();
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch all clothing items", e);
        }
    }

    public Clothing getClothingById(String id) {
        return clothingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clothing item not found with ID: " + id));
    }

    @Transactional
    public Clothing createClothing(Clothing clothing) {
        // Check for uniqueness before saving
        if (clothingRepository.findByItemCode(clothing.getItemCode()) != null) {
            throw new ServiceRuntimeException("Item Code already exists.");
        }
        if (clothingRepository.findByBarcode(clothing.getBarcode()).isPresent()) {
            throw new ServiceRuntimeException("Barcode already exists.");
        }
        if (clothingRepository.findByDescription(clothing.getDescription()).isPresent()) {
            throw new ServiceRuntimeException("Description already exists.");
        }

        // Generate a new clothing ID
        List<Clothing> allClothingItems = clothingRepository.findAll();
        List<String> clothingIDs = allClothingItems.stream()
                .map(Clothing::getId)
                .toList();
        String clothingID = Utilities.itemIDGenerator('C', clothingIDs);
        clothing.setId(clothingID);

        // Save and return the created clothing item
        return clothingRepository.save(clothing);
    }

    @Transactional
    public Clothing updateClothing(String id, Clothing updatedClothing) {
        Clothing existingClothing = getClothingById(id);

        // Prevent updating with duplicate values
        if (!existingClothing.equals(updatedClothing)) {
            if (clothingRepository.findByItemCode(updatedClothing.getItemCode()) != null) {
                throw new ServiceRuntimeException("Item Code already exists.");
            }
            if (clothingRepository.findByBarcode(updatedClothing.getBarcode()).isPresent()) {
                throw new ServiceRuntimeException("Barcode already exists.");
            }
            if (clothingRepository.findByDescription(updatedClothing.getDescription()).isPresent()) {
                throw new ServiceRuntimeException("Description already exists.");
            }
        }

        return clothingRepository.save(updatedClothing);
    }

    @Transactional
    public void deleteClothing(String id) {
        if (!clothingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with ID: " + id);
        }
        clothingRepository.deleteById(id);
    }

    public List<Clothing> getClothingByCategory(String category) {
        try {
            return clothingRepository.findByCategory(category);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch clothing by category: " + category, e);
        }
    }

    public List<Clothing> getClothingByColor(String color) {
        try {
            return clothingRepository.findByColor(color);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch clothing by color: " + color, e);
        }
    }

    public List<Clothing> getClothingBySize(String size) {
        try {
            return clothingRepository.findBySize(size);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch clothing by size: " + size, e);
        }
    }
}
