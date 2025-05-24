package com.mowagdy.retailstore.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mowagdy.retailstore.core.dto.*;
import com.mowagdy.retailstore.core.model.ItemType;
import com.mowagdy.retailstore.infrastructure.service.BillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BillController.class)
class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillService billService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddBillReturnsExpectedResponse() throws Exception {
        BillCreationResponse mockResponse = new BillCreationResponse(
    1L, 100.0, 90.0, new java.util.Date()
);

        when(billService.addBill(any(BillCreationRequest.class))).thenReturn(mockResponse);

        BillCreationRequest mockRequest = new BillCreationRequest(
                1L,
                Collections.singletonList(
                        new BillCreationRequestItem(ItemType.BAKERY, "Bread", 5.0, 2)
                )
        );

        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPrice").value(100.0))
            .andExpect(jsonPath("$.netPayable").value(90.0));
    }

    @Test
    void testGetSingleBillReturnsExpectedResponse() throws Exception {
        BillFetchingResponse response = new BillFetchingResponse();
        response.setId(123L);
        response.setTotalPrice(50.0);
        response.setNetPayable(45.0);

        when(billService.getBill(any(RequestWithLongId.class))).thenReturn(response);

        mockMvc.perform(get("/bills/123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(123))
            .andExpect(jsonPath("$.totalPrice").value(50.0))
            .andExpect(jsonPath("$.netPayable").value(45.0));
    }
}
