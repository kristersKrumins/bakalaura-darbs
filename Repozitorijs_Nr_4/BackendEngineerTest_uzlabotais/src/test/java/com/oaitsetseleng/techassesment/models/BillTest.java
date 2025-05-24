package com.oaitsetseleng.techassesment.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    @DisplayName("Bill and Items should assign and expose fields through constructor and getters")
    void testConstructorAndGetters() {
        Items item1 = new Items("milk", "groceries", 100.0, 2);
        Items item2 = new Items("charger", "electronics", 50.0, 1);

        Items[] items = new Items[] {item1, item2};
        Bill bill = new Bill(123, items);

        assertEquals(123, bill.getPersonId());
        assertNotNull(bill.getItems());
        assertEquals(2, bill.getItems().length);

        assertEquals("groceries", bill.getItems()[0].getType());
        assertEquals("milk", bill.getItems()[0].getName());
        assertEquals(100.0, bill.getItems()[0].getPrice());
        assertEquals(2, bill.getItems()[0].getQuantity());

        assertEquals("electronics", bill.getItems()[1].getType());
        assertEquals("charger", bill.getItems()[1].getName());
        assertEquals(50.0, bill.getItems()[1].getPrice());
        assertEquals(1, bill.getItems()[1].getQuantity());

        // Validate defaults from constructor
        assertEquals(0.0, bill.getDiscount());
        assertEquals(0.0, bill.getTotal());
        assertEquals(0.0, bill.getInitialTotal());
    }

    @Test
    @DisplayName("Default constructor should allow setting values later")
    void testDefaultConstructor() {
        Bill bill = new Bill();
        Items item = new Items("pen", "stationery", 1.0, 3);
        Items[] items = new Items[] {item};

        bill.setItems(items);
        bill.setPersonId(42);
        bill.setDiscount(5.5);
        bill.setTotal(55.0);
        bill.setInitialTotal(60.0);

        assertEquals(42, bill.getPersonId());
        assertEquals(1, bill.getItems().length);
        assertEquals("stationery", bill.getItems()[0].getType());
        assertEquals(5.5, bill.getDiscount());
        assertEquals(55.0, bill.getTotal());
        assertEquals(60.0, bill.getInitialTotal());
    }

    @Test
    @DisplayName("Empty items array should be handled without exception")
    void testEmptyItems() {
        Bill bill = new Bill(999, new Items[]{});
        assertEquals(999, bill.getPersonId());
        assertNotNull(bill.getItems());
        assertEquals(0, bill.getItems().length);
    }

    @Test
    @DisplayName("Null items array should be handled gracefully")
    void testNullItems() {
        Bill bill = new Bill();
        bill.setItems(null);
        bill.setPersonId(0);

        assertNull(bill.getItems());
        assertEquals(0, bill.getPersonId());
    }
}
