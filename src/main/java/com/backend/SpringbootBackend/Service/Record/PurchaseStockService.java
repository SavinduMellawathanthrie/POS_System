package com.backend.SpringbootBackend.Service.Record;

import com.backend.SpringbootBackend.Data.Entity.Supplier;
import com.backend.SpringbootBackend.Data.Item.Accessory;
import com.backend.SpringbootBackend.Data.Item.Clothing;
import com.backend.SpringbootBackend.Data.Record.PurchaseStock;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Repository.AccessoryRepository;
import com.backend.SpringbootBackend.Repository.ClothingRepository;
import com.backend.SpringbootBackend.Repository.PurchaseStockRepository;
import com.backend.SpringbootBackend.Repository.SupplierRepository;
import com.backend.SpringbootBackend.Utilities.Utilities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PurchaseStockService {

    private final PurchaseStockRepository purchaseStockRepository;
    private final ClothingRepository clothingRepository;
    private final AccessoryRepository accessoryRepository;
    private final SupplierRepository supplierRepository;

    public PurchaseStockService(
            PurchaseStockRepository purchaseStockRepository,
            ClothingRepository clothingRepository,
            AccessoryRepository accessoryRepository,
            SupplierRepository supplierRepository
    ) {
        this.purchaseStockRepository = purchaseStockRepository;
        this.clothingRepository = clothingRepository;
        this.accessoryRepository = accessoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public List<PurchaseStock> createUpcomingOrder(List<PurchaseStock> orders) {
        try {
            String orderID = Utilities.entityIDGenerator('U');
            for (PurchaseStock order : orders) {
                String itemCode = order.getItemCode();
                double unitStockPrice;
                Supplier supplier = supplierRepository.findBySupplierID(order.getSupplierID());
                if (supplier != null) {
                    Clothing clothingItem = clothingRepository.findByItemCode(itemCode);
                    if (clothingItem != null) {
                        unitStockPrice = clothingItem.getUnitStockPrice();
                        if (order.isReceivedOrder()) {
                            clothingItem.setQuantity(clothingItem.getQuantity() + order.getQuantity());
                            clothingRepository.save(clothingItem);
                        }
                    }
                    // Check if the item exists in the accessory repository
                    else {
                        Accessory accessoryItem = accessoryRepository.findByItemCode(itemCode);
                        if (accessoryItem != null) {
                            unitStockPrice = accessoryItem.getUnitStockPrice();
                            if (order.isReceivedOrder()) {
                                accessoryItem.setQuantity(accessoryItem.getQuantity() + order.getQuantity());
                                accessoryRepository.save(accessoryItem);
                            }
                        } else {
                            throw new ResourceNotFoundException("Item not found with itemCode: " + itemCode);
                        }
                    }
                } else {
                    throw new ResourceNotFoundException("Supplier not found with supplierID: " + order.getSupplierID());
                }

                // Calculate total and net value
                double total = unitStockPrice * order.getQuantity();

                // Update record fields
                order.setOrderID(orderID);
                order.setGrossTotal(total);
            }
            return purchaseStockRepository.saveAll(orders);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to create upcoming orders: " + e.getMessage(), e);
        }
    }

    // Retrieve an upcoming order by ID
    public PurchaseStock getUpcomingOrderById(int dbCode) {
        try {
            return purchaseStockRepository.findById(dbCode)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with dbCode: " + dbCode));
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error retrieving order with dbCode: " + dbCode, e);
        }
    }

    // Retrieve all upcoming orders
    public List<PurchaseStock> getAllUpcomingOrders() {
        try {
            return purchaseStockRepository.findAll();
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to retrieve all upcoming orders: " + e.getMessage(), e);
        }
    }

    // Update an existing upcoming order
    @Transactional
    public List<PurchaseStock> updateUpcomingOrder(int dbCode, List<PurchaseStock> updatedOrders) {
        // Retrieve all existing upcoming orders by the given dbCode
        List<PurchaseStock> existingOrders = purchaseStockRepository.findByOrderID(updatedOrders.get(0).getOrderID());
        if (existingOrders.isEmpty()) {
            throw new ResourceNotFoundException("No upcoming orders found with orderID: " + updatedOrders.get(0).getOrderID());
        }

        // Restore stock quantities from the existing orders
        for (PurchaseStock order : existingOrders) {

            Supplier supplier = supplierRepository.findBySupplierID(order.getSupplierID());
            if (supplier == null) {
                throw new ResourceNotFoundException("Supplier not found with supplierID: " + order.getSupplierID());
            }

            String itemCode = order.getItemCode();
            int orderQuantity = order.getQuantity();

            Clothing clothingOpt = clothingRepository.findByItemCode(itemCode);
            if (clothingOpt != null) {
                if (order.isReceivedOrder()) {
                    clothingOpt.setQuantity(clothingOpt.getQuantity() - orderQuantity);
                    clothingRepository.save(clothingOpt);
                }
            } else {
                Accessory accessoryOpt = accessoryRepository.findByItemCode(itemCode);
                if (accessoryOpt != null) {
                    if (order.isReceivedOrder()) {
                        accessoryOpt.setQuantity(accessoryOpt.getQuantity() - orderQuantity);
                        accessoryRepository.save(accessoryOpt);
                    }
                } else {
                    throw new ResourceNotFoundException("Item not found with itemCode: " + itemCode);
                }
            }
        }

        // Now process each updated order to deduct the new stock quantity and update order details
        List<PurchaseStock> updatedOrderList = new ArrayList<>();
        for (PurchaseStock updatedOrder : updatedOrders) {
            String itemCode = updatedOrder.getItemCode();
            int newQuantity = updatedOrder.getQuantity();

            Clothing clothingOpt = clothingRepository.findByItemCode(itemCode);
            if (clothingOpt != null) {
                if (updatedOrder.isReceivedOrder()) {
                    clothingOpt.setQuantity(clothingOpt.getQuantity() + newQuantity);
                    clothingRepository.save(clothingOpt);
                }
            } else {
                Accessory accessoryOpt = accessoryRepository.findByItemCode(itemCode);
                if (accessoryOpt != null) {
                    if (updatedOrder.isReceivedOrder()) {
                        accessoryOpt.setQuantity(accessoryOpt.getQuantity() + newQuantity);
                        accessoryRepository.save(accessoryOpt);
                    }
                } else {
                    throw new ResourceNotFoundException("Item not found : " + itemCode);
                }
            }

            // Update order details as per updatedOrder.
            updatedOrderList.add(purchaseStockRepository.save(updatedOrder));
        }
        return updatedOrderList;
    }

    // Delete an upcoming order by ID
    public void deleteUpcomingOrder(int dbCode) {
        try {
            PurchaseStock order = getUpcomingOrderById(dbCode);
            purchaseStockRepository.delete(order);
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw if it's a not-found error
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to delete order with dbCode: " + dbCode, e);
        }
    }

    public List<Clothing> getClothingSupplyAfterDate(LocalDate startDate) {
        List<PurchaseStock> records = purchaseStockRepository.findByOrderDateAfter(startDate);
        List<Clothing> clothingItemCodes = clothingRepository.findAll();
        List<Clothing> clothingRecords = new ArrayList<>();

        for (Clothing clothingItem : clothingItemCodes) {
            Clothing clothingItemHolder;
            clothingItemHolder = clothingItem;
            clothingItemHolder.setQuantity(0);
            for (PurchaseStock record : records) {
                if (Objects.equals(clothingItemHolder.getItemCode(), record.getItemCode())) {
                    clothingItemHolder.setQuantity(clothingItemHolder.getQuantity() + record.getQuantity());
                }
            }
            clothingRecords.add(clothingItemHolder);
        }
        clothingRecords.sort((c1, c2) -> Integer.compare(c2.getQuantity(), c1.getQuantity()));
        return clothingRecords;
    }

    public List<Accessory> getAccessorySupplyAfterDate(LocalDate startDate) {
        List<PurchaseStock> records = purchaseStockRepository.findByOrderDateAfter(startDate);
        List<Accessory> accessoryItemCodes = accessoryRepository.findAll();
        List<Accessory> accessoryRecords = new ArrayList<>();

        for (Accessory accessoryItem : accessoryItemCodes) {
            Accessory accessoryItemHolder;
            accessoryItemHolder = accessoryItem;
            accessoryItemHolder.setQuantity(0);
            for (PurchaseStock record : records) {
                if (Objects.equals(accessoryItemHolder.getItemCode(), record.getItemCode())) {
                    accessoryItemHolder.setQuantity(accessoryItemHolder.getQuantity() + record.getQuantity());
                }
            }
            accessoryRecords.add(accessoryItemHolder);
        }
        accessoryRecords.sort((c1, c2) -> Integer.compare(c2.getQuantity(), c1.getQuantity()));
        return accessoryRecords;
    }
}
