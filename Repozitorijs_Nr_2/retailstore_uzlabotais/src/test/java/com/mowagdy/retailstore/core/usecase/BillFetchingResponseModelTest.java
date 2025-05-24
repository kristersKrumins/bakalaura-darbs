package com.mowagdy.retailstore.core.usecase;

import com.mowagdy.retailstore.core.dto.BillFetchingResponse;
import com.mowagdy.retailstore.core.dto.BillFetchingResponseItem;
import com.mowagdy.retailstore.core.dto.BillFetchingResponseUser;
import com.mowagdy.retailstore.core.model.BillItem;
import com.mowagdy.retailstore.core.model.ItemType;
import com.mowagdy.retailstore.core.model.UserType;
import org.junit.jupiter.api.Test;
import com.mowagdy.retailstore.core.model.Bill;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BillFetchingResponseModelTest {

    @Test
    void testBillFetchingResponseDefaultConstructorAndSetters() {
        BillFetchingResponse response = new BillFetchingResponse();
        response.setId(100L);
        response.setTotalPrice(200.0);
        response.setNetPayable(180.0);

        assertEquals(100L, response.getId());
        assertEquals(200.0, response.getTotalPrice());
        assertEquals(180.0, response.getNetPayable());
    }

    @Test
    void testBillFetchingResponseItemFullConstructorAndMethods() {
        BillFetchingResponseItem item = new BillFetchingResponseItem(
                1L, ItemType.BAKERY, "Bread", 10.0, 2
        );

        assertEquals(1L, item.getId());
        assertEquals(ItemType.BAKERY, item.getType());
        assertEquals("Bread", item.getName());
        assertEquals(10.0, item.getSingleItemPrice());
        assertEquals(2, item.getQuantity());

        // Test setters
        item.setId(2L);
        item.setType(ItemType.GROCERIES);
        item.setName("Milk");
        item.setSingleItemPrice(3.5);
        item.setQuantity(5);

        assertEquals(2L, item.getId());
        assertEquals(ItemType.GROCERIES, item.getType());
        assertEquals("Milk", item.getName());
        assertEquals(3.5, item.getSingleItemPrice());
        assertEquals(5, item.getQuantity());
    }

    @Test
    void testBillFetchingResponseItemFromModelConstructor() {
        Bill mockedBill = new Bill();  // or pass a real Bill object if needed
        BillItem billItem = new BillItem(ItemType.BAKERY, "Bun", 1.5, 3, mockedBill);
        billItem.setId(10L);

        BillFetchingResponseItem item = new BillFetchingResponseItem(billItem);
        assertEquals(10L, item.getId());
        assertEquals(ItemType.BAKERY, item.getType());
        assertEquals("Bun", item.getName());
        assertEquals(1.5, item.getSingleItemPrice());
        assertEquals(3, item.getQuantity());
    }

    @Test
    void testBillFetchingResponseUser() {
        BillFetchingResponseUser user = new BillFetchingResponseUser();
        user.setId(1L);
        user.setName("Alice");
        user.setUserType(UserType.CUSTOMER);

        assertEquals(1L, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals(UserType.CUSTOMER, user.getUserType());
    }
}
