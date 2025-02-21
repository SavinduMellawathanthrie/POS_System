package com.backend.SpringbootBackend.Data.Item;

import jakarta.persistence.*;

@Entity
@Table(name = "accessory")
public class Accessory extends Item {

    private String warrantyPeriod;

    public Accessory() {}

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
