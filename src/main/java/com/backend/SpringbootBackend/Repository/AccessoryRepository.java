package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Item.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccessoryRepository extends JpaRepository<Accessory, String> {
    // Custom query methods for unique field checks
    Accessory findByItemCode(String itemCode);
    Optional<Accessory> findByBarcode(String barcode);
    Optional<Accessory> findByDescription(String description);

    // Additional custom query methods
    List<Accessory> findByCategory(String category);
    List<Accessory> findByWarrantyPeriod(String warrantyPeriod);

    @Query("SELECT a.itemCode FROM Accessory a")
    List<String> findAllItemCodes();
}
