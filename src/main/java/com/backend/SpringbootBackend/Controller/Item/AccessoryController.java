package com.backend.SpringbootBackend.Controller.Item;

import com.backend.SpringbootBackend.Data.Item.Accessory;
import com.backend.SpringbootBackend.Service.Item.AccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/accessory")
public class AccessoryController {

    private final AccessoryService accessoryService;

    @Autowired
    public AccessoryController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    @GetMapping
    public ResponseEntity<List<Accessory>> getAllAccessories() {
        List<Accessory> accessories = accessoryService.getAllAccessories();
        return ResponseEntity.ok(accessories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accessory> getAccessoryById(@PathVariable String id) {
        Accessory accessory = accessoryService.getAccessoryById(id);
        return ResponseEntity.ok(accessory);
    }

    @PostMapping
    public ResponseEntity<Accessory> createAccessory(@RequestBody Accessory accessory) {
        Accessory createdAccessory = accessoryService.createAccessory(accessory);
        return ResponseEntity.ok(createdAccessory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accessory> updateAccessory(@PathVariable String id, @RequestBody Accessory updatedAccessory) {
        Accessory updated = accessoryService.updateAccessory(id, updatedAccessory);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessory(@PathVariable String id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Accessory>> getAccessoriesByCategory(@PathVariable String category) {
        List<Accessory> accessories = accessoryService.getAccessoriesByCategory(category);
        return ResponseEntity.ok(accessories);
    }

    @GetMapping("/warranty/{warrantyPeriod}")
    public ResponseEntity<List<Accessory>> getAccessoriesByWarrantyPeriod(@PathVariable String warrantyPeriod) {
        List<Accessory> accessories = accessoryService.getAccessoriesByWarrantyPeriod(warrantyPeriod);
        return ResponseEntity.ok(accessories);
    }
}
