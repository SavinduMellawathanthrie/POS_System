package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Record.PurchaseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseStockRepository extends JpaRepository<PurchaseStock, Integer> {  // Updated primary key type

    // Find all orders by orderID (grouping key for batch orders)
    List<PurchaseStock> findByOrderID(String orderID);

    // Retrieve all purchase records after a given date
    List<PurchaseStock> findByOrderDateAfter(LocalDate orderDate);
}
