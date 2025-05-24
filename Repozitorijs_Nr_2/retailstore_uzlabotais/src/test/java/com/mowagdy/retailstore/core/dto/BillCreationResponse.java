package com.mowagdy.retailstore.core.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillCreationResponseTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 1L;
        Double totalPrice = 200.0;
        Double netPayable = 150.0;
        Date now = new Date();

        BillCreationResponse response = new BillCreationResponse(id, totalPrice, netPayable, now);

        assertEquals(id, response.getId());
        assertEquals(totalPrice, response.getTotalPrice());
        assertEquals(netPayable, response.getNetPayable());
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void testSetters() {
        BillCreationResponse response = new BillCreationResponse();
        Long id = 2L;
        Double totalPrice = 500.0;
        Double netPayable = 400.0;
        Date now = new Date();

        response.setId(id);
        response.setTotalPrice(totalPrice);
        response.setNetPayable(netPayable);
        response.setCreatedAt(now);

        assertEquals(id, response.getId());
        assertEquals(totalPrice, response.getTotalPrice());
        assertEquals(netPayable, response.getNetPayable());
        assertEquals(now, response.getCreatedAt());
    }
}
