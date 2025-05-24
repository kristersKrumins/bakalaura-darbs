package com.mowagdy.retailstore.core.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldRequiredExceptionTest {

    @Test
    void testConstructorWithFieldAndMessage() {
        FieldRequiredException ex = new FieldRequiredException("userId", "User ID is required");

        assertEquals("User ID is required", ex.getMessage());
        assertTrue(ex.getArguments().containsKey("field"));
        assertEquals("userId", ex.getArguments().get("field"));
    }

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new RuntimeException("Underlying issue");
        FieldRequiredException ex = new FieldRequiredException("General failure", cause);

        assertEquals("General failure", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
