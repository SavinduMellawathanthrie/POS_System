package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Item.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClothingRepository extends JpaRepository<Clothing, String> {
    // Custom query methods
    Clothing findByItemCode(String itemCode); // Finds clothing by item code
    Optional<Clothing> findByBarcode(String barcode);   // Finds clothing by barcode
    Optional<Clothing> findByDescription(String description); // Finds clothing by description

    // Other query methods, if needed
    List<Clothing> findByCategory(String category);
    List<Clothing> findByColor(String color);
    List<Clothing> findBySize(String size);

    @Query("SELECT c.itemCode FROM Clothing c")
    List<String> findAllItemCodes();
}
