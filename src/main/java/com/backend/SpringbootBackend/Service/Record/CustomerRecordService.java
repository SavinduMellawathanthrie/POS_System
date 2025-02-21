package com.backend.SpringbootBackend.Service.Record;

import com.backend.SpringbootBackend.Data.Item.Accessory;
import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Data.Record.CustomerRecord;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Repository.AccessoryRepository;
import com.backend.SpringbootBackend.Repository.ClothingRepository;
import com.backend.SpringbootBackend.Repository.CustomerRecordRepository;
import com.backend.SpringbootBackend.Utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerRecordService {

    @Autowired
    private CustomerRecordRepository customerRecordRepository;

    @Autowired
    private ClothingRepository clothingRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    /**
     * Create multiple CustomerRecords and return the saved records.
     *
     * @param customerRecords a List of CustomerRecord objects to create
     * @return List of created CustomerRecord objects
     * @PreAuthorised
     */
    @Transactional
    public List<CustomerRecord> createCustomerRecords(List<CustomerRecord> customerRecords) {
        String billID = Utilities.entityIDGenerator('B');
        LocalDate today = LocalDate.now();

        for (CustomerRecord record : customerRecords) {
            String itemCode = record.getItemCode();
            double unitPrice = 0;

            // Check if the item exists in the clothing repository
            Clothing clothingItem = clothingRepository.findByItemCode(itemCode);
            if (clothingItem != null) {
                unitPrice = clothingItem.getUnitRetailPrice();
                clothingItem.setQuantity(clothingItem.getQuantity() - record.getQuantity());
                clothingRepository.save(clothingItem);
            }
            // Check if the item exists in the accessory repository
            else {
                Accessory accessoryItem = accessoryRepository.findByItemCode(itemCode);
                if (accessoryItem != null) {
                    unitPrice = accessoryItem.getUnitRetailPrice();
                    accessoryItem.setQuantity(accessoryItem.getQuantity() - record.getQuantity());
                    accessoryRepository.save(accessoryItem);
                } else {
                    throw new ResourceNotFoundException("Item not found with itemCode: " + itemCode);
                }
            }

            // Calculate total and net value
            double total = unitPrice * record.getQuantity();
            double netValue = total - (total * record.getDiscount() / 100);

            // Update record fields
            record.setBillID(billID);
            record.setDate(today);
            record.setUnitPrice(unitPrice);
            record.setTotal(total);
            record.setNetValue(netValue);
        }

        return customerRecordRepository.saveAll(customerRecords);
    }

    /**
     * Update CustomerRecords for a given bill ID.
     *
     * @param billID the bill ID associated with the records to update
     * @param updatedRecords the new list of CustomerRecord objects
     * @return List of updated CustomerRecord objects
     */
    @Transactional
    public List<CustomerRecord> updateCustomerRecord(String billID, List<CustomerRecord> updatedRecords) {
        List<CustomerRecord> existingRecords = customerRecordRepository.findByBillID(billID);
        if (existingRecords.isEmpty()) {
            throw new ResourceNotFoundException("No customer records found with billID: " + billID);
        }
        // Restore stock quantities for old records
        for (CustomerRecord record : existingRecords) {
            String itemCode = record.getItemCode();

            Clothing clothingItem = clothingRepository.findByItemCode(itemCode);
            if (clothingItem != null) {
                clothingItem.setQuantity(clothingItem.getQuantity() - record.getQuantity());
                clothingRepository.save(clothingItem);
            }
            else {
                Accessory accessoryItem = accessoryRepository.findByItemCode(itemCode);
                if (accessoryItem != null) {
                    accessoryItem.setQuantity(accessoryItem.getQuantity() - record.getQuantity());
                    accessoryRepository.save(accessoryItem);
                }
            }
        }

        // Deduct stock for updated records
        LocalDate today = LocalDate.now();
        for (CustomerRecord record : updatedRecords) {
            String itemCode = record.getItemCode();

            Clothing clothingItem = clothingRepository.findByItemCode(itemCode);
            double unitPrice = 0;
            if (clothingItem != null) {
                unitPrice = clothingItem.getUnitRetailPrice();
                clothingItem.setQuantity(clothingItem.getQuantity() + record.getQuantity());
                clothingRepository.save(clothingItem);
            } else {
                Accessory accessoryItem = accessoryRepository.findByItemCode(itemCode);
                if (accessoryItem != null) {
                    unitPrice  = accessoryItem.getUnitRetailPrice();
                    accessoryItem.setQuantity(accessoryItem.getQuantity() + record.getQuantity());
                    accessoryRepository.save(accessoryItem);
                } else {
                    throw new ResourceNotFoundException("Item not found with itemCode: " + itemCode);
                }
            }

            double total = unitPrice * record.getQuantity();
            double netValue = total - (total * record.getDiscount() / 100);

            record.setDate(today);
            record.setNetValue(netValue);
            record.setTotal(total);
        }
        return createCustomerRecords(updatedRecords);
    }

    /**
     * Retrieve CustomerRecords by bill ID.
     *
     * @param billID the bill ID
     * @return List of CustomerRecord objects
     */
    public List<CustomerRecord> getCustomerRecordsByBillID(String billID) {
        List<CustomerRecord> customerRecords = customerRecordRepository.findByBillID(billID);
        if (customerRecords.isEmpty()) {
            throw new ResourceNotFoundException("No customer records found with billID: " + billID);
        }
        return customerRecords;
    }

    /**
     * Retrieve all CustomerRecords.
     *
     * @return List of all CustomerRecord objects
     */
    public List<CustomerRecord> getAllCustomerRecords() {
        return customerRecordRepository.findAll();
    }

    /**
     * Delete CustomerRecords by bill ID.
     *
     * @param billID the bill ID
     */
    @Transactional
    public void deleteCustomerRecords(String billID) {
        List<CustomerRecord> recordsToDelete = customerRecordRepository.findByBillID(billID);
        if (recordsToDelete.isEmpty()) {
            throw new ResourceNotFoundException("No customer records found with billID: " + billID);
        }
        customerRecordRepository.deleteAll(recordsToDelete);
    }

    /**
     * Retrieve all CustomerRecords with a date after the specified date.
     *
     * @param startDate the starting date (exclusive)
     */
    public List<Clothing> getClothingRecordsAfterDate(LocalDate startDate) {

        List<CustomerRecord> records = customerRecordRepository.findByDateAfter(startDate);
        List<String> clothingItemCodes = clothingRepository.findAllItemCodes();
        List<Clothing> clothingRecords = new ArrayList<>();

        for (String itemCode : clothingItemCodes) {
            Clothing clothingItem = clothingRepository.findByItemCode(itemCode);
            Clothing clothingItemHolder = new Clothing();
            clothingItemHolder = clothingItem;
            clothingItemHolder.setQuantity(0);
            for (CustomerRecord record : records) {
                if (Objects.equals(record.getItemCode(), itemCode)){
                    clothingItemHolder.setQuantity(clothingItemHolder.getQuantity()+record.getQuantity());
                }
            }
            clothingRecords.add(clothingItemHolder);
        }
        try {
            clothingRecords.sort((c1, c2) -> Integer.compare(c2.getQuantity(), c1.getQuantity()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clothingRecords;
    }

    public List<Accessory> getAccessoryRecordsAfterDate(LocalDate startDate) {

        List<CustomerRecord> records = customerRecordRepository.findByDateAfter(startDate);
        List<String> accessoryItemCodes = accessoryRepository.findAllItemCodes();
        List<Accessory> accessoryRecords = new ArrayList<>();

        for (String itemCode : accessoryItemCodes) {
            Accessory accessoryItem = accessoryRepository.findByItemCode(itemCode);
            Accessory accessoryItemHolder = new Accessory();
            accessoryItemHolder = accessoryItem;
            accessoryItemHolder.setQuantity(0);

            for (CustomerRecord record : records) {
                if (Objects.equals(record.getItemCode(), itemCode)) {
                    accessoryItemHolder.setQuantity(accessoryItemHolder.getQuantity() + record.getQuantity());
                }
            }
            accessoryRecords.add(accessoryItemHolder);
        }
        accessoryRecords.sort((c1, c2) -> Integer.compare(c2.getQuantity(), c1.getQuantity()));
        return accessoryRecords;
    }

}
