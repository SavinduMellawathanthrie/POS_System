package com.backend.SpringbootBackend.Data.Record;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "customer_invoice")
public class CustomerInvoice {
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

    public CustomerInvoice(){}

}
