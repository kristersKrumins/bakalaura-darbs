package com.mowagdy.retailstore.core.dto;

import com.mowagdy.retailstore.core.model.UserType;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserCreationRequestTest {

    @Test
    void testDefaultConstructor() {
        UserCreationRequest request = new UserCreationRequest();
        assertEquals("", request.getName());
        assertEquals(UserType.NONE, request.getUserType());
        assertNotNull(request.getRegisteredAt());
    }

    @Test
    void testParameterizedConstructor() {
        UserCreationRequest request = new UserCreationRequest("John Doe", UserType.CUSTOMER);
        assertEquals("John Doe", request.getName());
        assertEquals(UserType.CUSTOMER, request.getUserType());
    }

    @Test
    void testSettersAndGetters() {
        UserCreationRequest request = new UserCreationRequest();
        Date date = new Date();

        request.setName("Alice");
        request.setUserType(UserType.AFFILIATE);
        request.setRegisteredAt(date);

        assertEquals("Alice", request.getName());
        assertEquals(UserType.AFFILIATE, request.getUserType());
        assertEquals(date, request.getRegisteredAt());
    }
}
