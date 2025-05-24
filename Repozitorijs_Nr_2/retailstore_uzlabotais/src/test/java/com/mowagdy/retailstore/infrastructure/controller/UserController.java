package com.mowagdy.retailstore.infrastructure.controller;

import com.mowagdy.retailstore.core.dto.UserCreationRequest;
import com.mowagdy.retailstore.core.dto.UserCreationResponse;
import com.mowagdy.retailstore.infrastructure.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testAddUser() {
        UserCreationRequest request = new UserCreationRequest();
        UserCreationResponse expectedResponse = new UserCreationResponse();

        when(userService.addUser(request)).thenReturn(expectedResponse);

        UserCreationResponse response = userController.addUser(request);

        assertEquals(expectedResponse, response);
        verify(userService).addUser(request);
    }
}
