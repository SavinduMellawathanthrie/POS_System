package com.backend.SpringbootBackend.Service.Entity;

import com.backend.SpringbootBackend.Data.Entity.Supplier;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'USER')")
@Service
public class SupplierService {

    private static final Logger LOGGER = Logger.getLogger(SupplierService.class.getName());

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * Create a new supplier.
     *
     * @param supplier The supplier object to save.
     * @return The saved supplier object.
     */
    @Transactional
    public Supplier createSupplier(Supplier supplier) {
        LOGGER.info("Creating a new supplier: " + supplier.getName());
        Supplier savedSupplier = supplierRepository.save(supplier);
        LOGGER.info("Successfully created supplier with ID: " + savedSupplier);
        return savedSupplier;
    }

    /**
     * Retrieve all suppliers.
     *
     * @return A list of all suppliers.
     */
    public List<Supplier> getAllSuppliers() {
        LOGGER.info("Fetching all suppliers...");
        return supplierRepository.findAll();
    }

    /**
     * Retrieve a supplier by ID.
     *
     * @param id The ID of the supplier.
     * @return The supplier object.
     */
    public Supplier getSupplierById(Long id) {
        LOGGER.info("Fetching supplier with ID: " + id);
        return supplierRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warning("Supplier not found with ID: " + id);
                    return new ResourceNotFoundException("Supplier not found with id: " + id);
                });
    }

    /**
     * Update an existing supplier.
     *
     * @param id The ID of the supplier to update.
     * @param updatedSupplier The updated supplier details.
     * @return The updated supplier object.
     */
    @Transactional
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        LOGGER.info("Updating supplier with ID: " + id);
        Supplier existingSupplier = getSupplierById(id);

        existingSupplier.setName(updatedSupplier.getName());
        existingSupplier.setNic(updatedSupplier.getNic());
        existingSupplier.setAddress(updatedSupplier.getAddress());
        existingSupplier.setPhone(updatedSupplier.getPhone());
        existingSupplier.setEmail(updatedSupplier.getEmail());

        Supplier savedSupplier = supplierRepository.save(existingSupplier);
        LOGGER.info("Successfully updated supplier with ID: " + id);
        return savedSupplier;
    }

    /**
     * Delete a supplier by ID.
     *
     * @param id The ID of the supplier to delete.
     */
    @Transactional
    public void deleteSupplier(Long id) {
        LOGGER.info("Deleting supplier with ID: " + id);
        Supplier existingSupplier = getSupplierById(id);
        supplierRepository.delete(existingSupplier);
        LOGGER.info("Successfully deleted supplier with ID: " + id);
    }
}
