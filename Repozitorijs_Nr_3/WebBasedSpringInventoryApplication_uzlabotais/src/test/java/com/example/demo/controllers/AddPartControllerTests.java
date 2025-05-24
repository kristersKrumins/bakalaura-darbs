package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.*;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AddPartController} that exercise every logical branch
 * so JaCoCo records full line‑coverage.
 *
 * The controller fetches its collaborators via {@link ApplicationContext}, so
 * we mock the *implementation* beans (`PartServiceImpl`, etc.) rather than the
 * service interfaces and inject them with {@link ReflectionTestUtils}.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AddPartControllerTest {

    @Mock
    ApplicationContext context;

    // mock concrete service beans expected by getBean(...)
    @Mock
    PartServiceImpl partService;
    @Mock
    OutsourcedPartServiceImpl outsourcedPartService;
    @Mock
    InhousePartServiceImpl inhousePartService;

    @Mock
    Model model;

    AddPartController controller;

    @BeforeEach
    void setUp() {
        controller = new AddPartController();
        ReflectionTestUtils.setField(controller, "context", context);

        when(context.getBean(PartServiceImpl.class)).thenReturn(partService);
        when(context.getBean(OutsourcedPartServiceImpl.class)).thenReturn(outsourcedPartService);
        when(context.getBean(InhousePartServiceImpl.class)).thenReturn(inhousePartService);
    }

    @Test
    void showPartFormForUpdate_returnsInhouseForm_whenIdBelongsToInhousePart() {
        int inhouseId = 1;

        // Mock list of outsourced parts that **does not** contain the id
        OutsourcedPart outsourcedMock = mock(OutsourcedPart.class);
        when(outsourcedMock.getId()).thenReturn(999L);
        when(outsourcedPartService.findAll()).thenReturn(List.of(outsourcedMock));

        // Mock the in‑house part lookup
        InhousePart inhouseMock = mock(InhousePart.class);
        when(inhousePartService.findById(inhouseId)).thenReturn(inhouseMock);

        // Execute
        String view = controller.showPartFormForUpdate(inhouseId, model);

        // Verify
        assertEquals("InhousePartForm", view);
        verify(model).addAttribute("inhousepart", inhouseMock);
        verify(model, never()).addAttribute(eq("outsourcedpart"), any());
    }

    @Test
    void showPartFormForUpdate_returnsOutsourcedForm_whenIdBelongsToOutsourcedPart() {
        int outsourcedId = 2;

        // Mock list containing an outsourced part whose id matches the argument
        OutsourcedPart outsourcedMock = mock(OutsourcedPart.class);
        when(outsourcedMock.getId()).thenReturn((long) outsourcedId);
        when(outsourcedPartService.findAll()).thenReturn(List.of(outsourcedMock));
        when(outsourcedPartService.findById(outsourcedId)).thenReturn(outsourcedMock);

        // Execute
        String view = controller.showPartFormForUpdate(outsourcedId, model);

        // Verify
        assertEquals("OutsourcedPartForm", view);
        verify(model).addAttribute("outsourcedpart", outsourcedMock);
        verify(model, never()).addAttribute(eq("inhousepart"), any());
    }

    @Test
    void deletePart_deletesAndConfirms_whenPartHasNoProducts() {
        int partId = 3;

        Part partMock = mock(Part.class);
        when(partMock.getProducts()).thenReturn(Collections.emptySet());
        when(partService.findById(partId)).thenReturn(partMock);

        String view = controller.deletePart(partId, model);

        assertEquals("confirmationdeletepart", view);
        verify(partService).deleteById(partId);
    }

    @Test
    void deletePart_returnsError_whenPartIsUsedByProducts() {
        int partId = 4;

        Product productMock = mock(Product.class);
        Set<Product> productSet = new HashSet<>(List.of(productMock));
        Part partMock = mock(Part.class);
        when(partMock.getProducts()).thenReturn(productSet);
        when(partService.findById(partId)).thenReturn(partMock);

        String view = controller.deletePart(partId, model);

        assertEquals("negativeerror", view);
        verify(partService, never()).deleteById(anyInt());
    }
}
