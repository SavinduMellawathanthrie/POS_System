package com.backend.SpringbootBackend.Service.Entity;

import com.backend.SpringbootBackend.Data.Entity.Supplier;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

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
        return supplierRepository.save(supplier);
    }

    /**
     * Retrieve all suppliers.
     *
     * @return A list of all suppliers.
     */
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    /**
     * Retrieve a supplier by ID.
     *
     * @param id The ID of the supplier.
     * @return The supplier object.
     */
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
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
        Supplier existingSupplier = getSupplierById(id);
        existingSupplier.setName(updatedSupplier.getName());
        existingSupplier.setNic(updatedSupplier.getNic());
        existingSupplier.setAddress(updatedSupplier.getAddress());
        existingSupplier.setPhone(updatedSupplier.getPhone());
        existingSupplier.setEmail(updatedSupplier.getEmail());
        return supplierRepository.save(existingSupplier);
    }

    /**
     * Delete a supplier by ID.
     *
     * @param id The ID of the supplier to delete.
     */
    @Transactional
    public void deleteSupplier(Long id) {
        Supplier existingSupplier = getSupplierById(id);
        supplierRepository.delete(existingSupplier);
    }
}
