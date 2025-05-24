package com.mowagdy.retailstore.core.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestWithLongIdTest {

    @Test
    void testDefaultConstructor() {
        RequestWithLongId request = new RequestWithLongId();
        assertEquals(0L, request.getId());
    }

    @Test
    void testParameterizedConstructor() {
        RequestWithLongId request = new RequestWithLongId(5L);
        assertEquals(5L, request.getId());
    }

    @Test
    void testSetterAndGetter() {
        RequestWithLongId request = new RequestWithLongId();
        request.setId(10L);
        assertEquals(10L, request.getId());
    }
}
