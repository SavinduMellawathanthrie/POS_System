package com.backend.SpringbootBackend.Data.Item;

import jakarta.persistence.*;

@MappedSuperclass
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int databaseCode;                           // IMMUTABLE

    private String id;
    private String itemCode;                     // ADMIN
    private String barcode;                      // ADMIN
    private String description;                  // ADMIN
    private double unitStockPrice;
    private double unitRetailPrice;
    private int quantity;
    private String category;                      // ADMIN


    public Item() {}

    public int getDatabaseCode() {
        return databaseCode;
    }

    public void setDatabaseCode(int databaseCode) {
        this.databaseCode = databaseCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitStockPrice() {
        return unitStockPrice;
    }

    public void setUnitStockPrice(double unitPrice) {
        this.unitStockPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getUnitRetailPrice() {
        return unitRetailPrice;
    }

    public void setUnitRetailPrice(double unitRetailPrice) {
        this.unitRetailPrice = unitRetailPrice;
    }
    // Intentionally left blank
}
