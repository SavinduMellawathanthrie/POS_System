package com.backend.SpringbootBackend.Data.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int dbCode;

    private String supplierID;
    private String name;
    private String nic;
    private String address;
    private String phone;
    private String email;

    public Supplier(){}

    public int getDbCode() {
        return dbCode;
    }

    public void setDbCode(int dbCode) {
        this.dbCode = dbCode;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String id) {
        this.supplierID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
