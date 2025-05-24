package com.mowagdy.retailstore.core.dto;

import com.mowagdy.retailstore.core.model.Bill;
import com.mowagdy.retailstore.core.model.BillItem;
import com.mowagdy.retailstore.core.model.ItemType;
import com.mowagdy.retailstore.core.model.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BillFetchingResponseTest {

    @Test
    void testConstructorWithUserAndItems() {
        User user = new User(); // assuming empty constructor
        BillItem item = new BillItem(ItemType.GROCERIES, "Milk", 2.5, 2, null);
        BillFetchingResponse response = new BillFetchingResponse(1L, user, Arrays.asList(item), 5.0, new Date());

        assertEquals(1L, response.getId());
        assertNotNull(response.getUser());
        assertNotNull(response.getItems());
        assertEquals(5.0, response.getNetPayable());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void testConstructorWithBill() {
        Bill bill = new Bill();
        bill.setId(2L);
        bill.setUser(new User());
        BillItem item = new BillItem(ItemType.GROCERIES, "Bread", 1.5, 3, bill);
        bill.setItems(Arrays.asList(item));
        bill.setTotalPrice(4.5);
        bill.setNetPayable(4.0);
        bill.setCreatedAt(new Date());

        BillFetchingResponse response = new BillFetchingResponse(bill);

        assertEquals(2L, response.getId());
        assertNotNull(response.getUser());
        assertNotNull(response.getItems());
        assertEquals(4.5, response.getTotalPrice());
        assertEquals(4.0, response.getNetPayable());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void testSettersAndGetters() {
        BillFetchingResponse response = new BillFetchingResponse();

        response.setId(10L);
        response.setUser(new User());
        BillItem item = new BillItem(ItemType.GROCERIES, "Butter", 3.0, 1, null);
        response.setItems(Arrays.asList(item));
        response.setTotalPrice(3.0);
        response.setNetPayable(2.5);
        response.setCreatedAt(new Date());

        assertEquals(10L, response.getId());
        assertNotNull(response.getUser());
        assertNotNull(response.getItems());
        assertEquals(3.0, response.getTotalPrice());
        assertEquals(2.5, response.getNetPayable());
        assertNotNull(response.getCreatedAt());
    }
}
