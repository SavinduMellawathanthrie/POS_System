package com.backend.SpringbootBackend.Controller.Item;

import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Service.Item.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/clothing")
public class ClothingController {

    private final ClothingService clothingService;

    @Autowired
    public ClothingController(ClothingService clothingService) {
        this.clothingService = clothingService;
    }

    @GetMapping
    public ResponseEntity<List<Clothing>> getAllClothing() {
        List<Clothing> clothingList = clothingService.getAllClothing();
        return ResponseEntity.ok(clothingList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clothing> getClothingById(@PathVariable String id) {
        Clothing clothing = clothingService.getClothingById(id);
        return ResponseEntity.ok(clothing);
    }

    @PostMapping
    public ResponseEntity<Clothing> createClothing(@RequestBody Clothing clothing) {
        Clothing createdClothing = clothingService.createClothing(clothing);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClothing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clothing> updateClothing(@PathVariable String id, @RequestBody Clothing updatedClothing) {
        Clothing updated = clothingService.updateClothing(id, updatedClothing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClothing(@PathVariable String id) {
        clothingService.deleteClothing(id);
        return ResponseEntity.ok("Clothing item deleted successfully.");
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Clothing>> getClothingByCategory(@PathVariable String category) {
        List<Clothing> clothingList = clothingService.getClothingByCategory(category);
        return ResponseEntity.ok(clothingList);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Clothing>> getClothingByColor(@PathVariable String color) {
        List<Clothing> clothingList = clothingService.getClothingByColor(color);
        return ResponseEntity.ok(clothingList);
    }

    @GetMapping("/size/{size}")
    public ResponseEntity<List<Clothing>> getClothingBySize(@PathVariable String size) {
        List<Clothing> clothingList = clothingService.getClothingBySize(size);
        return ResponseEntity.ok(clothingList);
    }
}
