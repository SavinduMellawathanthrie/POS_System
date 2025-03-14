package com.backend.SpringbootBackend.Data.Record;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
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

    // intentionally left blank
}

