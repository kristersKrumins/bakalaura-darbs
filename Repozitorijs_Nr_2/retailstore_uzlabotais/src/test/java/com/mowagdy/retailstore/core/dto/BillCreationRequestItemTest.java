package com.mowagdy.retailstore.core.dto;

import com.mowagdy.retailstore.core.model.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillCreationRequestItemTest {

    @Test
    void testConstructorAndGetters() {
        BillCreationRequestItem item = new BillCreationRequestItem(
                ItemType.BAKERY, "Bread", 2.5, 3
        );

        assertEquals(ItemType.BAKERY, item.getType());
        assertEquals("Bread", item.getName());
        assertEquals(2.5, item.getSingleItemPrice());
        assertEquals(3, item.getQuantity());
    }

    @Test
    void testSetters() {
        BillCreationRequestItem item = new BillCreationRequestItem();

        item.setType(ItemType.GROCERIES);
        item.setName("Milk");
        item.setSingleItemPrice(1.75);
        item.setQuantity(4);

        assertEquals(ItemType.GROCERIES, item.getType());
        assertEquals("Milk", item.getName());
        assertEquals(1.75, item.getSingleItemPrice());
        assertEquals(4, item.getQuantity());
    }
}
