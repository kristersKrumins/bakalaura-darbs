package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link MainScreenControllerr}.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MainScreenControllerrTest {

    @Mock
    PartService partService;
    @Mock
    ProductService productService;
    @Mock
    Model model;

    MainScreenControllerr controller;

    @BeforeEach
    void setUp() {
        controller = new MainScreenControllerr(partService, productService);
    }

    // ---------- GET /mainscreen ----------
    @Test
    void listPartsAndProducts_withoutKeywords_loadsAllAndReturnsView() {
        // stub services
        when(partService.listAll(null)).thenReturn(List.of());
        when(productService.listAll(null)).thenReturn(List.of());

        String view = controller.listPartsandProducts(model, null, null);

        assertEquals("mainscreen", view);
        verify(partService).listAll(null);
        verify(productService).listAll(null);

        // ensure attributes added
        verify(model).addAttribute(eq("parts"), any());
        verify(model).addAttribute("partkeyword", null);
        verify(model).addAttribute(eq("products"), any());
        verify(model).addAttribute("productkeyword", null);
    }

    @Test
    void listPartsAndProducts_withKeywords_passesKeywordsThrough() {
        String partKw = "gear";
        String prodKw = "bundle";

        when(partService.listAll(partKw)).thenReturn(List.of(mock(Part.class)));
        when(productService.listAll(prodKw)).thenReturn(List.of(mock(Product.class)));

        String view = controller.listPartsandProducts(model, partKw, prodKw);

        assertEquals("mainscreen", view);
        verify(partService).listAll(partKw);
        verify(productService).listAll(prodKw);

        verify(model).addAttribute("partkeyword", partKw);
        verify(model).addAttribute("productkeyword", prodKw);
    }
}
