package com.mowagdy.retailstore.infrastructure.service;

import com.mowagdy.retailstore.core.dto.*;
import com.mowagdy.retailstore.core.model.ItemType;
import com.mowagdy.retailstore.core.model.User;
import com.mowagdy.retailstore.core.model.UserType;
import com.mowagdy.retailstore.core.model.Bill;
import com.mowagdy.retailstore.infrastructure.repo.BillRepository;
import com.mowagdy.retailstore.infrastructure.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillServiceTest {

    private UserRepository userRepository;
    private BillRepository billRepository;
    private BillService billService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        billRepository = mock(BillRepository.class);
        billService = new BillService(userRepository, billRepository);
    }

    @Test
    void testAddBillCallsUseCaseAndReturnsResponse() {
        // Mock user
        User user = new User("Mo", UserType.EMPLOYEE, new Date());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Prepare request
        List<BillCreationRequestItem> items = List.of(
                new BillCreationRequestItem(ItemType.GROCERIES, "Milk", 3.5, 2)
        );
        BillCreationRequest request = new BillCreationRequest(1L, items);

        BillCreationResponse response = billService.addBill(request);

        assertNotNull(response);
        assertTrue(response.getTotalPrice() > 0);
        assertTrue(response.getNetPayable() > 0);
    }

    @Test
    void testGetBillCallsUseCaseAndReturnsResponse() {
        // Mock user + bill
        User user = new User("Mo", UserType.EMPLOYEE, new Date());
        Bill bill = new Bill(user);
        bill.setId(123L);
        bill.setItems(new ArrayList<>());

        when(billRepository.findById(123L)).thenReturn(Optional.of(bill));

        // Prepare request
        RequestWithLongId request = new RequestWithLongId(123L);
        BillFetchingResponse response = billService.getBill(request);

        assertNotNull(response);
        assertEquals(123L, response.getId());
    }
}

