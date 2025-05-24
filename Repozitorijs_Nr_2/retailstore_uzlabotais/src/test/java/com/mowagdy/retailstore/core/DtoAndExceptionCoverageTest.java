package com.mowagdy.retailstore.core;

import com.mowagdy.retailstore.core.dto.*;
import com.mowagdy.retailstore.core.exception.FieldRequiredException;
import com.mowagdy.retailstore.core.exception.ResourceNotFoundException;
import com.mowagdy.retailstore.core.model.ItemType;
import com.mowagdy.retailstore.core.model.UserType;
import com.mowagdy.retailstore.core.model.User;
import com.mowagdy.retailstore.core.model.Bill;
import com.mowagdy.retailstore.core.model.BillItem;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DtoAndExceptionCoverageTest {

    @Test
void testFieldRequiredExceptionConstructor() {
    FieldRequiredException ex = new FieldRequiredException("Missing field", "userId");

    // Avoid strict message matching – just check that "userId" is included
    assertTrue(ex.getMessage().contains("userId"));
}


    @Test
    void testResourceNotFoundExceptionConstructors() {
        ResourceNotFoundException ex1 = new ResourceNotFoundException("Not found");
        assertEquals("Not found", ex1.getMessage());

        Throwable cause = new RuntimeException("Inner error");
        ResourceNotFoundException ex2 = new ResourceNotFoundException("Failed", cause);
        assertEquals("Failed", ex2.getMessage());
        assertEquals("Inner error", ex2.getCause().getMessage());
    }

    @Test
    void testBillFetchingResponseFieldsOnly() {
        BillFetchingResponse response = new BillFetchingResponse();
        response.setId(100L);
        response.setTotalPrice(250.0);
        response.setNetPayable(220.0);

        assertEquals(100L, response.getId());
        assertEquals(250.0, response.getTotalPrice());
        assertEquals(220.0, response.getNetPayable());
    }

    @Test
    void testBillFetchingResponseItemFields() {
        BillFetchingResponseItem item = new BillFetchingResponseItem();
        item.setId(1L);
        item.setType(ItemType.GROCERIES);
        item.setName("Milk");
        item.setSingleItemPrice(5.5);
        item.setQuantity(2);

        assertEquals(1L, item.getId());
        assertEquals(ItemType.GROCERIES, item.getType());
        assertEquals("Milk", item.getName());
        assertEquals(5.5, item.getSingleItemPrice());
        assertEquals(2, item.getQuantity());
    }

    @Test
    void testBillFetchingResponseUserFields() {
        BillFetchingResponseUser user = new BillFetchingResponseUser();
        user.setId(10L);
        user.setName("Alice");
        user.setUserType(UserType.AFFILIATE);

        assertEquals(10L, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals(UserType.AFFILIATE, user.getUserType());
    }
@Test
void testBillFetchingResponseWithModelTypes() {
    BillFetchingResponse response = new BillFetchingResponse();

    // Set base fields
    response.setId(100L);
    response.setTotalPrice(200.0);
    response.setNetPayable(180.0);

    // Setup user
    User user = new User();
    user.setId(1L);
    user.setName("John");
    user.setUserType(UserType.EMPLOYEE);

    response.setUser(user);

    // Setup items
    Bill bill = new Bill(user);
    BillItem item = new BillItem(ItemType.GROCERIES, "Milk", 3.5, 2, bill);
    item.setId(10L);

    List<BillItem> items = new ArrayList<>();
    items.add(item);

    response.setItems(items);

    // Assertions
    assertEquals(100L, response.getId());
    assertEquals(200.0, response.getTotalPrice());
    assertEquals(180.0, response.getNetPayable());

    assertNotNull(response.getUser());
    assertEquals("John", response.getUser().getName());

    assertEquals(1, response.getItems().size());
    assertEquals("Milk", response.getItems().get(0).getName());
    assertEquals(3.5, response.getItems().get(0).getSingleItemPrice());
}

}
