package com.mowagdy.retailstore.core.dto;

import com.mowagdy.retailstore.core.model.UserType;
import com.mowagdy.retailstore.core.model.ItemType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DtoModelTest {

    @Test
    void testBillCreationRequestAndItem() {
        List<BillCreationRequestItem> items = new ArrayList<>();
        items.add(new BillCreationRequestItem(ItemType.BAKERY, "Bread", 2.5, 3));

        BillCreationRequest request = new BillCreationRequest(1L, items);
        assertEquals(1L, request.getUserId());
        assertEquals(1, request.getItems().size());

        BillCreationRequestItem item = request.getItems().get(0);
        assertEquals("Bread", item.getName());
        assertEquals(ItemType.BAKERY, item.getType());
        assertEquals(2.5, item.getSingleItemPrice());
        assertEquals(3, item.getQuantity());
    }

    @Test
void testBillCreationResponse() {
    Date now = new Date();
    BillCreationResponse response = new BillCreationResponse(1L, 100.0, 90.0, now);

    assertEquals(1L, response.getId());
    assertEquals(100.0, response.getTotalPrice());
    assertEquals(90.0, response.getNetPayable());
    assertEquals(now, response.getCreatedAt());

    response.setTotalPrice(120.0);
    response.setNetPayable(110.0);
    response.setCreatedAt(new Date());
    response.setId(2L);

    assertEquals(120.0, response.getTotalPrice());
    assertEquals(110.0, response.getNetPayable());
    assertEquals(2L, response.getId());
}


    @Test
    void testUserCreationRequest() {
        UserCreationRequest request = new UserCreationRequest("Alice", UserType.EMPLOYEE);
        assertEquals("Alice", request.getName());
        assertEquals(UserType.EMPLOYEE, request.getUserType());

        request.setName("Bob");
        request.setUserType(UserType.CUSTOMER);
        assertEquals("Bob", request.getName());
        assertEquals(UserType.CUSTOMER, request.getUserType());
    }

    @Test
    void testUserCreationResponse() {
        Date date = new Date();
        UserCreationResponse response = new UserCreationResponse(5L, date);

        assertEquals(5L, response.getId());
        assertEquals(date, response.getRegisteredAt());

        response.setId(6L);
        response.setRegisteredAt(new Date());
        assertEquals(6L, response.getId());
    }

    @Test
    void testRequestWithLongId() {
        RequestWithLongId request = new RequestWithLongId(123L);
        assertEquals(123L, request.getId());

        request.setId(456L);
        assertEquals(456L, request.getId());
    }
}
