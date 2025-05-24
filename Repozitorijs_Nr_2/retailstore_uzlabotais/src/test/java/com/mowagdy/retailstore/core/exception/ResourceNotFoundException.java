package com.mowagdy.retailstore.core.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceNotFoundExceptionTest {
    @Test
    void testExceptionMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        assertEquals("Resource not found", ex.getMessage());
    }
}