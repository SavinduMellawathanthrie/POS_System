package com.backend.SpringbootBackend.Service.Item;

import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Repository.ClothingRepository;
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
public class ClothingService {

    private final ClothingRepository clothingRepository;
    private static final Logger LOGGER = Logger.getLogger(ClothingService.class.getName());

    @Autowired
    public ClothingService(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    public List<Clothing> getAllClothing() {
        LOGGER.log(Level.INFO, "Fetching all clothing items...");
        try {
            List<Clothing> clothingList = clothingRepository.findAll();
            LOGGER.log(Level.INFO, "Successfully fetched {0} clothing items.", clothingList.size());
            return clothingList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch all clothing items.", e);
            throw new ServiceRuntimeException("Failed to fetch all clothing items", e);
        }
    }

    public Clothing getClothingById(String id) {
        LOGGER.log(Level.INFO, "Fetching clothing item with ID: {0}", id);
        return clothingRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.log(Level.WARNING, "Clothing item not found with ID: {0}", id);
                    return new ResourceNotFoundException("Clothing item not found with ID: " + id);
                });
    }

    @Transactional
    public Clothing createClothing(Clothing clothing) {
        LOGGER.log(Level.INFO, "Creating new clothing item: {0}", clothing);

        if (clothingRepository.findByItemCode(clothing.getItemCode()) != null) {
            LOGGER.log(Level.WARNING, "Item Code {0} already exists.", clothing.getItemCode());
            throw new ServiceRuntimeException("Item Code already exists.");
        }
        if (clothingRepository.findByBarcode(clothing.getBarcode()).isPresent()) {
            LOGGER.log(Level.WARNING, "Barcode {0} already exists.", clothing.getBarcode());
            throw new ServiceRuntimeException("Barcode already exists.");
        }
        if (clothingRepository.findByDescription(clothing.getDescription()).isPresent()) {
            LOGGER.log(Level.WARNING, "Description {0} already exists.", clothing.getDescription());
            throw new ServiceRuntimeException("Description already exists.");
        }

        List<Clothing> allClothingItems = clothingRepository.findAll();
        List<String> clothingIDs = allClothingItems.stream().map(Clothing::getId).toList();
        String clothingID = Utilities.itemIDGenerator('C', clothingIDs);
        clothing.setId(clothingID);

        Clothing savedClothing = clothingRepository.save(clothing);
        LOGGER.log(Level.INFO, "Clothing Item successfully created with ID: {0}", clothingID);
        return savedClothing;
    }

    @Transactional
    public Clothing updateClothing(String id, Clothing updatedClothing) {
        LOGGER.log(Level.INFO, "Updating clothing item with ID: {0}", id);

        Clothing existingClothing = getClothingById(id);

        if (!existingClothing.equals(updatedClothing)) {
            if (clothingRepository.findByItemCode(updatedClothing.getItemCode()) != null && !existingClothing.getItemCode().equals(updatedClothing.getItemCode())) {
                LOGGER.log(Level.WARNING, "Item Code {0} already exists.", updatedClothing.getItemCode());
                throw new ServiceRuntimeException("Item Code already exists.");
            }
            if (clothingRepository.findByBarcode(updatedClothing.getBarcode()).isPresent() && !existingClothing.getBarcode().equals(updatedClothing.getBarcode())) {
                LOGGER.log(Level.WARNING, "Barcode {0} already exists.", updatedClothing.getBarcode());
                throw new ServiceRuntimeException("Barcode already exists.");
            }
            if (clothingRepository.findByDescription(updatedClothing.getDescription()).isPresent() && !existingClothing.getDescription().equals(updatedClothing.getDescription())) {
                LOGGER.log(Level.WARNING, "Description {0} already exists.", updatedClothing.getDescription());
                throw new ServiceRuntimeException("Description already exists.");
            }
        }

        Clothing savedClothing = clothingRepository.save(updatedClothing);
        LOGGER.log(Level.INFO, "Successfully updated clothing item with ID: {0}", id);
        return savedClothing;
    }

    @Transactional
    public void deleteClothing(String id) {
        LOGGER.log(Level.INFO, "Deleting clothing item with ID: {0}", id);

        if (!clothingRepository.existsById(id)) {
            LOGGER.log(Level.WARNING, "Attempt to delete non-existing clothing item with ID: {0}", id);
            throw new ResourceNotFoundException("Item not found with ID: " + id);
        }

        clothingRepository.deleteById(id);
        LOGGER.log(Level.INFO, "Successfully deleted clothing item with ID: {0}", id);
    }

    public List<Clothing> getClothingByCategory(String category) {
        LOGGER.log(Level.INFO, "Fetching clothing items by category: {0}", category);
        try {
            List<Clothing> clothingList = clothingRepository.findByCategory(category);
            LOGGER.log(Level.INFO, "Found {0} clothing items in category: {1}", new Object[]{clothingList.size(), category});
            return clothingList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch clothing items by category: {0}", category);
            throw new ServiceRuntimeException("Failed to fetch clothing by category: " + category, e);
        }
    }

    public List<Clothing> getClothingByColor(String color) {
        LOGGER.log(Level.INFO, "Fetching clothing items by color: {0}", color);
        try {
            List<Clothing> clothingList = clothingRepository.findByColor(color);
            LOGGER.log(Level.INFO, "Found {0} clothing items with color: {1}", new Object[]{clothingList.size(), color});
            return clothingList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch clothing items by color: {0}", color);
            throw new ServiceRuntimeException("Failed to fetch clothing by color: " + color, e);
        }
    }

    public List<Clothing> getClothingBySize(String size) {
        LOGGER.log(Level.INFO, "Fetching clothing items by size: {0}", size);
        try {
            List<Clothing> clothingList = clothingRepository.findBySize(size);
            LOGGER.log(Level.INFO, "Found {0} clothing items with size: {1}", new Object[]{clothingList.size(), size});
            return clothingList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch clothing items by size: {0}", size);
            throw new ServiceRuntimeException("Failed to fetch clothing by size: " + size, e);
        }
    }
}
