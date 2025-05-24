package com.mowagdy.retailstore.core.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserCreationResponseTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 1L;
        Date now = new Date();
        UserCreationResponse response = new UserCreationResponse(id, now);

        assertEquals(id, response.getId());
        assertEquals(now, response.getRegisteredAt());
    }

    @Test
    void testSetters() {
        UserCreationResponse response = new UserCreationResponse();
        Long id = 2L;
        Date date = new Date();

        response.setId(id);
        response.setRegisteredAt(date);

        assertEquals(id, response.getId());
        assertEquals(date, response.getRegisteredAt());
    }
}
