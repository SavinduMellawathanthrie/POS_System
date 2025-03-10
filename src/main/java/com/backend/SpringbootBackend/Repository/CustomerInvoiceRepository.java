package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Record.CustomerInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRecordRepository extends JpaRepository<CustomerInvoice, String> {
    List<CustomerInvoice> findByBillID(String billID);
    List<CustomerInvoice> findByDateAfter(LocalDate date);
}
