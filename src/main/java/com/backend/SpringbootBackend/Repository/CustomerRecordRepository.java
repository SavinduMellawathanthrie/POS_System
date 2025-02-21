package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Record.CustomerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRecordRepository extends JpaRepository<CustomerRecord, String> {
    List<CustomerRecord> findByBillID(String billID);
    List<CustomerRecord> findByDateAfter(LocalDate date);
}
