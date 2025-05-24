package com.mowagdy.retailstore.core.dto;

import com.mowagdy.retailstore.core.model.User;
import com.mowagdy.retailstore.core.model.UserType;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BillFetchingResponseUserTest {

    @Test
    void testDefaultConstructorAndSetters() {
        BillFetchingResponseUser user = new BillFetchingResponseUser();
        Date date = new Date();

        user.setId(1L);
        user.setName("Alice");
        user.setUserType(UserType.AFFILIATE);
        user.setRegisteredAt(date);

        assertEquals(1L, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals(UserType.AFFILIATE, user.getUserType());
        assertEquals(date, user.getRegisteredAt());
    }

    @Test
    void testFullConstructor() {
        Date date = new Date();
        BillFetchingResponseUser user = new BillFetchingResponseUser(2L, "Bob", UserType.CUSTOMER, date);

        assertEquals(2L, user.getId());
        assertEquals("Bob", user.getName());
        assertEquals(UserType.CUSTOMER, user.getUserType());
        assertEquals(date, user.getRegisteredAt());
    }

    @Test
    void testUserConstructor() {
        Date date = new Date();
        User modelUser = new User("Charlie", UserType.EMPLOYEE, date);
        modelUser.setId(3L);

        BillFetchingResponseUser user = new BillFetchingResponseUser(modelUser);

        assertEquals(3L, user.getId());
        assertEquals("Charlie", user.getName());
        assertEquals(UserType.EMPLOYEE, user.getUserType());
        assertEquals(date, user.getRegisteredAt());
    }
}
