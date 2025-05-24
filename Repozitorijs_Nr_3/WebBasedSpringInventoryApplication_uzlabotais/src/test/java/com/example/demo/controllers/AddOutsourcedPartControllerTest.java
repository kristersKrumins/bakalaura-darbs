package com.example.demo.controllers;

import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Product;
import com.example.demo.service.OutsourcedPartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AddOutsourcedPartController}.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AddOutsourcedPartControllerTest {

    @Mock ApplicationContext context;
    @Mock OutsourcedPartServiceImpl outsourcedPartService;
    @Mock Model model;
    @Mock BindingResult bindingResult;

    AddOutsourcedPartController controller;

    @BeforeEach
    void setUp() {
        controller = new AddOutsourcedPartController();
        ReflectionTestUtils.setField(controller, "context", context);
        when(context.getBean(OutsourcedPartServiceImpl.class))
                .thenReturn(outsourcedPartService);
    }

    // ---------- GET ----------
    @Test
    void showFormAddOutsourcedPart_displaysBlankForm() {
        String view = controller.showFormAddOutsourcedPart(model);

        assertEquals("OutsourcedPartForm", view);
        verify(model).addAttribute(eq("outsourcedpart"),
                                   any(OutsourcedPart.class));
    }

    // ---------- POST ----------
    @Test
    void submitForm_returnsForm_whenInventoryOutOfRange() {
        // inv 5 is outside [10,20] so the controller should reject it
        OutsourcedPart invalid =
                new OutsourcedPart("Bad", 10.0, 5, 10, 20, "Comp");
        invalid.setId(1L);

        // tell the BindingResult that errors exist after rejectValue()
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = controller.submitForm(invalid, bindingResult, model);

        assertEquals("OutsourcedPartForm", view);
        verify(bindingResult).rejectValue(eq("inv"), anyString(), anyString());
        verify(outsourcedPartService, never()).save(any());
    }

    @Test
    void submitForm_persistsAndConfirms_whenInventoryValid() {
        OutsourcedPart valid =
                new OutsourcedPart("Good", 15.0, 15, 10, 20, "Comp");
        valid.setId(2L);

        when(outsourcedPartService.findById((int) valid.getId()))
                .thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(outsourcedPartService).save(any());

        String view = controller.submitForm(valid, bindingResult, model);

        assertEquals("confirmationaddpart", view);
        verify(outsourcedPartService).save(valid);
    }

    @Test
    void submitForm_copiesProducts_whenExistingPartFound() {
        OutsourcedPart submitted =
                new OutsourcedPart("Copy", 20.0, 15, 10, 20, "Comp");
        submitted.setId(3L);

        OutsourcedPart existing =
                new OutsourcedPart("Copy", 20.0, 15, 10, 20, "Comp");
        existing.setId(3L);
        Product product = new Product("Prod", 50.0, 5);
        Set<Product> prods = new HashSet<>();
        prods.add(product);
        existing.setProducts(prods);

        when(outsourcedPartService.findById((int) submitted.getId()))
                .thenReturn(existing);
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(outsourcedPartService).save(any());

        String view = controller.submitForm(submitted, bindingResult, model);

        assertEquals("confirmationaddpart", view);
        assertEquals(1, submitted.getProducts().size(),
                     "Products should be copied from existing part");
        verify(outsourcedPartService).save(submitted);
    }
}
