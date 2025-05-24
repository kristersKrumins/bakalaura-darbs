package com.retial.app.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retial.app.domain.entity.Bill;
import com.retial.app.domain.entity.Item;
import com.retial.app.domain.entity.User;
import com.retial.app.domain.enums.UserType;
import com.retial.app.domain.service.BillService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BillController.class)
public class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillService billService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCalculate() throws Exception {
        Item item = new Item(1L, "TV", 100.0, false);
        Bill bill = new Bill(1L, List.of(item));
        User user = new User(1L, "Alice", UserType.CUSTOMER, new Date());

        Mockito.when(billService.calculateNetPrice(any(User.class), any(Bill.class))).thenReturn(90.0);

        BillController.BillRequest request = new BillController.BillRequest(user, bill);

        mockMvc.perform(post("/api/bills/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("90.0"));
    }
}
