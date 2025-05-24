package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.Product;
import com.example.demo.service.InhousePartServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AddInhousePartController}. Scenario matrix:
 * <ol>
 *   <li>GET form display</li>
 *   <li>POST with inventory outside min‑max → stays on form</li>
 *   <li>POST with new, valid part → persists and confirms</li>
 *   <li>POST with existing part id → copies products then persists</li>
 * </ol>
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AddInhousePartControllerTest {

    @Mock
    ApplicationContext context;

    @Mock
    InhousePartServiceImpl inhousePartService;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    AddInhousePartController controller;

    @BeforeEach
    void setUp() {
        controller = new AddInhousePartController();
        // Inject mocked Spring context
        ReflectionTestUtils.setField(controller, "context", context);
        when(context.getBean(InhousePartServiceImpl.class)).thenReturn(inhousePartService);
    }

    @Test
    void showFormAddInhousePart_displaysBlankForm() {
        String view = controller.showFormAddInhousePart(model);
        assertEquals("InhousePartForm", view);
        verify(model).addAttribute(eq("inhousepart"), any(InhousePart.class));
    }

    @Test
    void submitForm_returnsForm_whenInventoryOutOfRange() {
        // Given inventory below min
        InhousePart invalid = new InhousePart("LowInv", 10.0, 5, 10, 20, 1100);
        invalid.setId(1L);

        // When
        String view = controller.submitForm(invalid, bindingResult, model);

        // Then
        assertEquals("InhousePartForm", view);
        verify(bindingResult).rejectValue(eq("inv"), anyString(), anyString());
        verify(inhousePartService, never()).save(any());
    }

    @Test
    void submitForm_persistsAndConfirms_whenInventoryValid() {
        InhousePart valid = new InhousePart("Good", 15.0, 15, 10, 20, 1200);
        valid.setId(2L);

        when(inhousePartService.findById((int) valid.getId())).thenReturn(null);
        // save is void → just allow it silently
        doNothing().when(inhousePartService).save(any());

        String view = controller.submitForm(valid, bindingResult, model);

        assertEquals("confirmationaddpart", view);
        verify(inhousePartService).save(valid);
    }

    @Test
    void submitForm_copiesProducts_whenExistingPartFound() {
        InhousePart submitted = new InhousePart("Copy", 20.0, 15, 10, 20, 1300);
        submitted.setId(3L);

        // Existing part with one product
        InhousePart existing = new InhousePart("Copy", 20.0, 15, 10, 20, 1300);
        existing.setId(3L);
        Product product = new Product("Prod", 50.0, 5);
        Set<Product> products = new HashSet<>();
        products.add(product);
        existing.setProducts(products);

        when(inhousePartService.findById((int) submitted.getId())).thenReturn(existing);
        doNothing().when(inhousePartService).save(any());

        String view = controller.submitForm(submitted, bindingResult, model);

        assertEquals("confirmationaddpart", view);
        assertEquals(1, submitted.getProducts().size(), "Products should be copied from existing to new part");
        verify(inhousePartService).save(submitted);
    }
}
