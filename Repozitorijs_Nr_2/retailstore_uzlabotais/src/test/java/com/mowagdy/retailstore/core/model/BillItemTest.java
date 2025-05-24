package com.mowagdy.retailstore.core.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BillItemTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        BillItem item = new BillItem();
        item.setId(1L);
        item.setName("Test Item");
        item.setQuantity(5);
        item.setSingleItemPrice(20.0);
        item.setType(ItemType.GROCERIES); // Assuming ItemType is an enum
        Bill bill = new Bill(); // Create a dummy bill
        item.setBill(bill);

        assertEquals(1L, item.getId());
        assertEquals("Test Item", item.getName());
        assertEquals(5, item.getQuantity());
        assertEquals(20.0, item.getSingleItemPrice());
        assertEquals(ItemType.GROCERIES, item.getType());
        assertEquals(bill, item.getBill());
    }

    @Test
    void testAllArgsConstructor() {
        Bill bill = new Bill();
        BillItem item = new BillItem(ItemType.GROCERIES, "Another Item", 15.0, 3, bill);

        assertEquals(ItemType.GROCERIES, item.getType());
        assertEquals("Another Item", item.getName());
        assertEquals(15.0, item.getSingleItemPrice());
        assertEquals(3, item.getQuantity());
        assertEquals(bill, item.getBill());
    }
}
