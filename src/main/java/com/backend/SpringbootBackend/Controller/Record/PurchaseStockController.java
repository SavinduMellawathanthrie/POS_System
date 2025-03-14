package com.backend.SpringbootBackend.Controller.Record;

import com.backend.SpringbootBackend.Data.Item.Accessory;
import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Data.Record.PurchaseStock;
import com.backend.SpringbootBackend.Service.Record.PurchaseStockService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'USER')")
@RestController
@RequestMapping("/api/upcoming-orders")
@CrossOrigin(origins = "*") // Allows cross-origin requests (if needed)
public class PurchaseStockController {

    private final PurchaseStockService purchaseStockService;

    public PurchaseStockController(PurchaseStockService purchaseStockService) {
        this.purchaseStockService = purchaseStockService;
    }

    // Create a new upcoming order
    @PostMapping
    public ResponseEntity<List<PurchaseStock>> createUpcomingOrder(@RequestBody List<PurchaseStock> order) {
        List<PurchaseStock> savedOrder = purchaseStockService.createUpcomingOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    // Get an order by ID (dbCode as int)
    @GetMapping("/{dbCode}")
    public ResponseEntity<PurchaseStock> getUpcomingOrderById(@PathVariable int dbCode) {
        PurchaseStock order = purchaseStockService.getUpcomingOrderById(dbCode);
        return ResponseEntity.ok(order);
    }

    // Get all upcoming orders
    @GetMapping
    public ResponseEntity<List<PurchaseStock>> getAllUpcomingOrders() {
        List<PurchaseStock> orders = purchaseStockService.getAllUpcomingOrders();
        return ResponseEntity.ok(orders);
    }

    // Update an existing order (dbCode as int)
    @PutMapping("/{dbCode}")
    public ResponseEntity<List<PurchaseStock>> updateUpcomingOrder(
            @PathVariable int dbCode,
            @RequestBody List<PurchaseStock> updatedOrder) {
        List<PurchaseStock> orders = purchaseStockService.updateUpcomingOrder(dbCode, updatedOrder);
        return ResponseEntity.ok(orders);
    }

    // Delete an order by ID (dbCode as int)
    @DeleteMapping("/{dbCode}")
    public ResponseEntity<String> deleteUpcomingOrder(@PathVariable int dbCode) {
        purchaseStockService.deleteUpcomingOrder(dbCode);
        return ResponseEntity.ok("Order deleted successfully with dbCode: " + dbCode);
    }

    // Get clothing supply records after a given date
    @GetMapping("/clothing/after-date")
    public ResponseEntity<List<Clothing>> getClothingSupplyAfterDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        List<Clothing> clothingRecords = purchaseStockService.getClothingSupplyAfterDate(startDate);

        if (clothingRecords.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clothingRecords);
    }

    // Get accessory supply records after a given date
    @GetMapping("/accessories/after-date")
    public ResponseEntity<List<Accessory>> getAccessorySupplyAfterDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        List<Accessory> accessoryRecords = purchaseStockService.getAccessorySupplyAfterDate(startDate);

        if (accessoryRecords.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(accessoryRecords);
    }
}
