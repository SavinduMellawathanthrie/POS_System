package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findBySupplierID(String supplierID);
}
