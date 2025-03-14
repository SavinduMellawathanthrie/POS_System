package com.backend.SpringbootBackend.Service.Record;

import com.backend.SpringbootBackend.Data.Item.Accessory;
import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Data.Record.CustomerInvoice;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Repository.AccessoryRepository;
import com.backend.SpringbootBackend.Repository.ClothingRepository;
import com.backend.SpringbootBackend.Repository.CustomerInvoiceRepository;
import com.backend.SpringbootBackend.Utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
@Service
public class CustomerInvoiceService {

    private static final Logger LOGGER = Logger.getLogger(CustomerInvoiceService.class.getName());

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;

    @Autowired
    private ClothingRepository clothingRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    /**
     * Create multiple CustomerInvoices and return the saved invoices.
     *
     * @param customerInvoices a List of CustomerInvoice objects to create
     * @return List of created CustomerInvoice objects
     */
    @Transactional
    public List<CustomerInvoice> createCustomerInvoices(List<CustomerInvoice> customerInvoices) {
        LOGGER.info("Creating customer invoices...");
        String billID = Utilities.entityIDGenerator('B');
        LocalDate today = LocalDate.now();

        for (CustomerInvoice invoice : customerInvoices) {
            String itemCode = invoice.getItemCode();
            double unitPrice = 0;

            // Check if the item exists in the clothing repository
            Clothing clothingItem = clothingRepository.findByItemCode(itemCode);
            if (clothingItem != null) {
                LOGGER.info("Clothing item found: " + itemCode);
                unitPrice = clothingItem.getUnitRetailPrice();
                clothingItem.setQuantity(clothingItem.getQuantity() - invoice.getQuantity());
                clothingRepository.save(clothingItem);
            }
            // Check if the item exists in the accessory repository
            else {
                Accessory accessoryItem = accessoryRepository.findByItemCode(itemCode);
                if (accessoryItem != null) {
                    LOGGER.info("Accessory item found: " + itemCode);
                    unitPrice = accessoryItem.getUnitRetailPrice();
                    accessoryItem.setQuantity(accessoryItem.getQuantity() - invoice.getQuantity());
                    accessoryRepository.save(accessoryItem);
                } else {
                    LOGGER.warning("Item not found with itemCode: " + itemCode);
                    throw new ResourceNotFoundException("Item not found with itemCode: " + itemCode);
                }
            }

            // Calculate total and net value
            double total = unitPrice * invoice.getQuantity();
            double netValue = total - (total * invoice.getDiscount() / 100);

            // Update invoice fields
            invoice.setBillID(billID);
            invoice.setDate(today);
            invoice.setUnitPrice(unitPrice);
            invoice.setTotal(total);
            invoice.setNetValue(netValue);
        }

        LOGGER.info("Customer invoices created successfully.");
        return customerInvoiceRepository.saveAll(customerInvoices);
    }

    /**
     * Update CustomerInvoices for a given bill ID.
     *
     * @param billID the bill ID associated with the invoices to update
     * @param updatedInvoices the new list of CustomerInvoice objects
     * @return List of updated CustomerInvoice objects
     */
    @Transactional
    public List<CustomerInvoice> updateCustomerInvoice(String billID, List<CustomerInvoice> updatedInvoices) {
        LOGGER.info("Updating customer invoices for billID: " + billID);
        List<CustomerInvoice> existingInvoices = customerInvoiceRepository.findByBillID(billID);
        if (existingInvoices.isEmpty()) {
            LOGGER.warning("No invoices found for billID: " + billID);
            throw new ResourceNotFoundException("No customer invoices found with billID: " + billID);
        }

        // Restore stock quantities for old invoices
        for (CustomerInvoice invoice : existingInvoices) {
            String itemCode = invoice.getItemCode();

            Clothing clothingItem = clothingRepository.findByItemCode(itemCode);
            if (clothingItem != null) {
                clothingItem.setQuantity(clothingItem.getQuantity() - invoice.getQuantity());
                clothingRepository.save(clothingItem);
            }
            else {
                Accessory accessoryItem = accessoryRepository.findByItemCode(itemCode);
                if (accessoryItem != null) {
                    accessoryItem.setQuantity(accessoryItem.getQuantity() - invoice.getQuantity());
                    accessoryRepository.save(accessoryItem);
                }
            }
        }

        // Deduct stock for updated invoices
        LocalDate today = LocalDate.now();
        for (CustomerInvoice invoice : updatedInvoices) {
            String itemCode = invoice.getItemCode();
            double unitPrice = 0;

            Clothing clothingItem = clothingRepository.findByItemCode(itemCode);
            if (clothingItem != null) {
                unitPrice = clothingItem.getUnitRetailPrice();
                clothingItem.setQuantity(clothingItem.getQuantity() + invoice.getQuantity());
                clothingRepository.save(clothingItem);
            } else {
                Accessory accessoryItem = accessoryRepository.findByItemCode(itemCode);
                if (accessoryItem != null) {
                    unitPrice  = accessoryItem.getUnitRetailPrice();
                    accessoryItem.setQuantity(accessoryItem.getQuantity() + invoice.getQuantity());
                    accessoryRepository.save(accessoryItem);
                } else {
                    LOGGER.warning("Item not found with itemCode: " + itemCode);
                    throw new ResourceNotFoundException("Item not found with itemCode: " + itemCode);
                }
            }

            double total = unitPrice * invoice.getQuantity();
            double netValue = total - (total * invoice.getDiscount() / 100);

            invoice.setDate(today);
            invoice.setNetValue(netValue);
            invoice.setTotal(total);
        }

        LOGGER.info("Customer invoices updated successfully for billID: " + billID);
        return createCustomerInvoices(updatedInvoices);
    }

    /**
     * Retrieve CustomerInvoices by bill ID.
     *
     * @param billID the bill ID
     * @return List of CustomerInvoice objects
     */
    public List<CustomerInvoice> getCustomerInvoicesByBillID(String billID) {
        LOGGER.info("Fetching customer invoices for billID: " + billID);
        List<CustomerInvoice> customerInvoices = customerInvoiceRepository.findByBillID(billID);
        if (customerInvoices.isEmpty()) {
            LOGGER.warning("No customer invoices found for billID: " + billID);
            throw new ResourceNotFoundException("No customer invoices found with billID: " + billID);
        }
        LOGGER.info("Customer invoices retrieved for billID: " + billID);
        return customerInvoices;
    }

    /**
     * Retrieve all CustomerInvoices.
     *
     * @return List of all CustomerInvoice objects
     */
    public List<CustomerInvoice> getAllCustomerInvoices() {
        LOGGER.info("Fetching all customer invoices...");
        List<CustomerInvoice> invoices = customerInvoiceRepository.findAll();
        LOGGER.info("All customer invoices retrieved.");
        return invoices;
    }

    /**
     * Delete CustomerInvoices by bill ID.
     *
     * @param billID the bill ID
     */
    @Transactional
    public void deleteCustomerInvoices(String billID) {
        LOGGER.info("Deleting customer invoices for billID: " + billID);
        List<CustomerInvoice> invoicesToDelete = customerInvoiceRepository.findByBillID(billID);
        if (invoicesToDelete.isEmpty()) {
            LOGGER.warning("No customer invoices found for billID: " + billID);
            throw new ResourceNotFoundException("No customer invoices found with billID: " + billID);
        }
        customerInvoiceRepository.deleteAll(invoicesToDelete);
        LOGGER.info("Customer invoices deleted for billID: " + billID);
    }

    /**
     * Retrieve all CustomerInvoices with a date after the specified date.
     *
     * @param startDate the starting date (exclusive)
     */
    public List<Clothing> getClothingInvoicesAfterDate(LocalDate startDate) {
        LOGGER.info("Fetching clothing invoices after date: " + startDate);
        List<CustomerInvoice> invoices = customerInvoiceRepository.findByDateAfter(startDate);
        List<String> clothingItemCodes = clothingRepository.findAllItemCodes();
        List<Clothing> clothingInvoices = new ArrayList<>();

        for (String itemCode : clothingItemCodes) {
            Clothing clothingItem = clothingRepository.findByItemCode(itemCode);
            Clothing clothingItemHolder = new Clothing();
            clothingItemHolder = clothingItem;
            clothingItemHolder.setQuantity(0);
            for (CustomerInvoice invoice : invoices) {
                if (Objects.equals(invoice.getItemCode(), itemCode)){
                    clothingItemHolder.setQuantity(clothingItemHolder.getQuantity() + invoice.getQuantity());
                }
            }
            clothingInvoices.add(clothingItemHolder);
        }
        try {
            clothingInvoices.sort((c1, c2) -> Integer.compare(c2.getQuantity(), c1.getQuantity()));
        } catch (Exception e) {
            LOGGER.severe("Error sorting clothing invoices: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOGGER.info("Clothing invoices retrieved after date: " + startDate);
        return clothingInvoices;
    }

    public List<Accessory> getAccessoryInvoicesAfterDate(LocalDate startDate) {
        LOGGER.info("Fetching accessory invoices after date: " + startDate);
        List<CustomerInvoice> invoices = customerInvoiceRepository.findByDateAfter(startDate);
        List<String> accessoryItemCodes = accessoryRepository.findAllItemCodes();
        List<Accessory> accessoryInvoices = new ArrayList<>();

        for (String itemCode : accessoryItemCodes) {
            Accessory accessoryItem = accessoryRepository.findByItemCode(itemCode);
            Accessory accessoryItemHolder = new Accessory();
            accessoryItemHolder = accessoryItem;
            accessoryItemHolder.setQuantity(0);

            for (CustomerInvoice invoice : invoices) {
                if (Objects.equals(invoice.getItemCode(), itemCode)) {
                    accessoryItemHolder.setQuantity(accessoryItemHolder.getQuantity() + invoice.getQuantity());
                }
            }
            accessoryInvoices.add(accessoryItemHolder);
        }
        accessoryInvoices.sort((c1, c2) -> Integer.compare(c2.getQuantity(), c1.getQuantity()));
        LOGGER.info("Accessory invoices retrieved after date: " + startDate);
        return accessoryInvoices;
    }

}
