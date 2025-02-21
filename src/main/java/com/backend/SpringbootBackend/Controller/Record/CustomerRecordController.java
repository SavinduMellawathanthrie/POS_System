package com.backend.SpringbootBackend.Controller.Record;

import com.backend.SpringbootBackend.Data.Item.Accessory;
import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Data.Record.CustomerRecord;
import com.backend.SpringbootBackend.Service.Record.CustomerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/customer-records")
public class CustomerRecordController {

    @Autowired
    private CustomerRecordService customerRecordService;

    @PostMapping
    public ResponseEntity<List<CustomerRecord>> createCustomerRecords(@RequestBody List<CustomerRecord> customerRecords) {
        List<CustomerRecord> savedRecords = customerRecordService.createCustomerRecords(customerRecords);
        return ResponseEntity.ok(savedRecords);
    }

    @PutMapping("/{billID}")
    public ResponseEntity<List<CustomerRecord>> updateCustomerRecords(
            @PathVariable String billID,
            @RequestBody List<CustomerRecord> updatedRecords) {
        List<CustomerRecord> updatedList = customerRecordService.updateCustomerRecord(billID, updatedRecords);
        return ResponseEntity.ok(updatedList);
    }

    @GetMapping("/{billID}")
    public ResponseEntity<List<CustomerRecord>> getCustomerRecordsByBillID(@PathVariable String billID) {
        List<CustomerRecord> records = customerRecordService.getCustomerRecordsByBillID(billID);
        return ResponseEntity.ok(records);
    }

    @GetMapping
    public ResponseEntity<List<CustomerRecord>> getAllCustomerRecords() {
        List<CustomerRecord> records = customerRecordService.getAllCustomerRecords();
        return ResponseEntity.ok(records);
    }

    @DeleteMapping("/{billID}")
    public ResponseEntity<Void> deleteCustomerRecords(@PathVariable String billID) {
        customerRecordService.deleteCustomerRecords(billID);
        return ResponseEntity.noContent().build();
    }
    /**
     * Get all clothing records after a specified date.
     * @param startDate the starting date (exclusive)
     * @return ResponseEntity with a List of Clothing objects
     */
    @GetMapping("/clothing/after-date")
    public ResponseEntity<List<Clothing>> getClothingRecordsAfterDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        List<Clothing> clothingRecords = customerRecordService.getClothingRecordsAfterDate(startDate);

        if (clothingRecords.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clothingRecords);
    }

    /**
     * Get all accessory records after a specified date.
     * @param startDate the starting date (exclusive)
     * @return ResponseEntity with a List of Accessory objects
     */
    @GetMapping("/accessories/after-date")
    public ResponseEntity<List<Accessory>> getAccessoryRecordsAfterDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        List<Accessory> accessoryRecords = customerRecordService.getAccessoryRecordsAfterDate(startDate);

        if (accessoryRecords.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(accessoryRecords);
    }
}
