package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private PartService partService;

    @Test
    public void showForm_shouldReturnAddProductFormView() throws Exception {
        Part dummyPart = new Part() {{
            setId(1L);
            setName("Gear");
            setPrice(9.99);
            setInv(10);
            setMinInventory(1);
            setMaxInventory(100);
        }};

        when(partService.findAll()).thenReturn(Collections.singletonList(dummyPart));

        mockMvc.perform(get("/products/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-product-form"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("parts"))
                .andExpect(model().attributeExists("availparts"))
                .andExpect(model().attributeExists("assparts"));
    }
}