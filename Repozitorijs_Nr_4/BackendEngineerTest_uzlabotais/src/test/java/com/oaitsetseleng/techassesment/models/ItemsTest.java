package com.oaitsetseleng.techassesment.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemsTest {

    @Test
    @DisplayName("Constructor and all getters should return correct values")
    void testConstructorAndGetters() {
        Items item = new Items("mouse", "electronics", 29.99, 3);

        assertEquals("mouse", item.getName());
        assertEquals("electronics", item.getType());
        assertEquals(29.99, item.getPrice());
        assertEquals(3, item.getQuantity());
    }

    @Test
    @DisplayName("All setters should update the fields correctly")
    void testSetters() {
        Items item = new Items("x", "x", 0.0, 0);

        item.setName("keyboard");
        item.setType("accessory");
        item.setPrice(49.99);
        item.setQuantity(2);

        assertEquals("keyboard", item.getName());
        assertEquals("accessory", item.getType());
        assertEquals(49.99, item.getPrice());
        assertEquals(2, item.getQuantity());
    }
}
