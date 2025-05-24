package com.mowagdy.retailstore.infrastructure.service;

import com.mowagdy.retailstore.core.dto.UserCreationRequest;
import com.mowagdy.retailstore.core.dto.UserCreationResponse;
import com.mowagdy.retailstore.infrastructure.repo.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mowagdy.retailstore.core.model.UserType;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    
}