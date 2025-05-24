package com.retial.app.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillTest {

    @Test
    void testGetTotalAmount_NullItems() {
        Bill bill = Bill.builder().items(null).build();
        assertEquals(0.0, bill.getTotalAmount(), "Total amount should be 0.0 when items are null");
    }

    @Test
    void testGetTotalAmount_EmptyItems() {
        Bill bill = Bill.builder().items(Collections.emptyList()).build();
        assertEquals(0.0, bill.getTotalAmount(), "Total amount should be 0.0 when items list is empty");
    }

    @Test
    void testGetTotalAmount_WithItems() {
        Item item1 = Item.builder()
                .id(1L)
                .name("Item1")
                .price(100.0)
                .isGrocery(false)
                .build();

        Item item2 = Item.builder()
                .id(2L)
                .name("Item2")
                .price(200.0)
                .isGrocery(true)
                .build();

        Bill bill = Bill.builder().items(Arrays.asList(item1, item2)).build();
        assertEquals(300.0, bill.getTotalAmount(), "Total amount should sum up item prices");
    }

    @Test
    void testGetTotalAmountExcludingGroceries_NullItems() {
        Bill bill = Bill.builder().items(null).build();
        assertEquals(0.0, bill.getTotalAmountExcludingGroceries(), "Amount excluding groceries should be 0.0 when items are null");
    }

    @Test
    void testGetTotalAmountExcludingGroceries_EmptyItems() {
        Bill bill = Bill.builder().items(Collections.emptyList()).build();
        assertEquals(0.0, bill.getTotalAmountExcludingGroceries(), "Amount excluding groceries should be 0.0 when items list is empty");
    }

    @Test
    void testGetTotalAmountExcludingGroceries_WithItems() {
        Item groceryItem = Item.builder()
                .id(1L)
                .name("GroceryItem")
                .price(50.0)
                .isGrocery(true)
                .build();

        Item nonGroceryItem = Item.builder()
                .id(2L)
                .name("NonGroceryItem")
                .price(150.0)
                .isGrocery(false)
                .build();

        Bill bill = Bill.builder().items(Arrays.asList(groceryItem, nonGroceryItem)).build();
        assertEquals(150.0, bill.getTotalAmountExcludingGroceries(), "Amount should sum only non-grocery item prices");
    }
}
