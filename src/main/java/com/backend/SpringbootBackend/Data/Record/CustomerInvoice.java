package com.backend.SpringbootBackend.Data.Record;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Entity
@Table(name = "customerrecords")
public class CustomerRecord {
    private int customerId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dbCode;

    private String billID;
    private LocalDate date;
    private String itemCode;
    private int quantity;
    private double unitPrice;
    private double discount;
    private double total;
    private double netValue;

    public CustomerRecord(){}

    public void setDbCode(int dbCode) {
        this.dbCode = dbCode;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setNetValue(double netValue) {
        this.netValue = netValue;
    }
}
