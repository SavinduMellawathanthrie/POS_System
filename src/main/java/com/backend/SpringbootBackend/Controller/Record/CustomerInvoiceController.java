package com.backend.SpringbootBackend.Controller.Record;

import com.backend.SpringbootBackend.Data.Record.CustomerInvoice;
import com.backend.SpringbootBackend.Service.Record.CustomerInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'USER')")
@RestController
@RequestMapping("/api/customer-invoices")
public class CustomerInvoiceController {

    @Autowired
    private CustomerInvoiceService customerInvoiceService;

    /**
     * Create multiple CustomerInvoices.
     *
     * @param customerInvoices List of CustomerInvoice objects
     * @return List of created CustomerInvoice objects
     */
    @PostMapping
    public ResponseEntity<List<CustomerInvoice>> createCustomerInvoices(@RequestBody List<CustomerInvoice> customerInvoices) {
        List<CustomerInvoice> createdInvoices = customerInvoiceService.createCustomerInvoices(customerInvoices);
        return ResponseEntity.ok(createdInvoices);
    }

    /**
     * Update CustomerInvoices for a given bill ID.
     *
     * @param billID the bill ID
     * @param updatedInvoices List of updated CustomerInvoice objects
     * @return List of updated CustomerInvoice objects
     */
    @PutMapping("/{billID}")
    public ResponseEntity<List<CustomerInvoice>> updateCustomerInvoice(
            @PathVariable String billID,
            @RequestBody List<CustomerInvoice> updatedInvoices) {
        List<CustomerInvoice> updatedRecords = customerInvoiceService.updateCustomerInvoice(billID, updatedInvoices);
        return ResponseEntity.ok(updatedRecords);
    }

    /**
     * Retrieve CustomerInvoices by bill ID.
     *
     * @param billID the bill ID
     * @return List of CustomerInvoice objects
     */
    @GetMapping("/{billID}")
    public ResponseEntity<List<CustomerInvoice>> getCustomerInvoicesByBillID(@PathVariable String billID) {
        List<CustomerInvoice> invoices = customerInvoiceService.getCustomerInvoicesByBillID(billID);
        return ResponseEntity.ok(invoices);
    }

    /**
     * Retrieve all CustomerInvoices.
     *
     * @return List of all CustomerInvoice objects
     */
    @GetMapping
    public ResponseEntity<List<CustomerInvoice>> getAllCustomerInvoices() {
        List<CustomerInvoice> invoices = customerInvoiceService.getAllCustomerInvoices();
        return ResponseEntity.ok(invoices);
    }

    /**
     * Delete CustomerInvoices by bill ID.
     *
     * @param billID the bill ID
     */
    @DeleteMapping("/{billID}")
    public ResponseEntity<Void> deleteCustomerInvoices(@PathVariable String billID) {
        customerInvoiceService.deleteCustomerInvoices(billID);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieve all Clothing invoices with a date after the specified date.
     *
     * @param startDate the starting date (exclusive)
     */
    @GetMapping("/clothing-after-date/{startDate}")
    public ResponseEntity<?> getClothingInvoicesAfterDate(@PathVariable String startDate) {
        LocalDate date = LocalDate.parse(startDate);
        return ResponseEntity.ok(customerInvoiceService.getClothingInvoicesAfterDate(date));
    }

    /**
     * Retrieve all Accessory invoices with a date after the specified date.
     *
     * @param startDate the starting date (exclusive)
     */
    @GetMapping("/accessories-after-date/{startDate}")
    public ResponseEntity<?> getAccessoryInvoicesAfterDate(@PathVariable String startDate) {
        LocalDate date = LocalDate.parse(startDate);
        return ResponseEntity.ok(customerInvoiceService.getAccessoryInvoicesAfterDate(date));
    }
}
