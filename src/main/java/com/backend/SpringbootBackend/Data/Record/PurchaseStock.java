package com.backend.SpringbootBackend.Data.Record;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "upcoming-order-record")
public class PurchaseStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dbCode;

    private String orderID;
    private LocalDate orderDate;
    private boolean receivedOrder;
    private int quantity;
    private double grossTotal;
    private double netTotal;
    private String supplierID;
    private String itemCode;


    public PurchaseStock(){}

    public int getDbCode() {
        return dbCode;
    }

    public void setDbCode(int dbCode) {
        this.dbCode = dbCode;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public boolean isReceivedOrder() {
        return receivedOrder;
    }

    public void setReceivedOrder(boolean receivedOrder) {
        this.receivedOrder = receivedOrder;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(double grossTotal) {
        this.grossTotal = grossTotal;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    // intentionally left blank
}

