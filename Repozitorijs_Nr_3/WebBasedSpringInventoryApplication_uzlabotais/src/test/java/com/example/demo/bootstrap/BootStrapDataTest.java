package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Integration‑style unit tests for {@link BootStrapData} that align with the
 * current implementation (bulk **saveAll** calls instead of individual saves).
 * Running these tests should raise JaCoCo line coverage across the whole
 * Bootstrap block while keeping assertions realistic.
 */
@ExtendWith(MockitoExtension.class)
class BootStrapDataTest {

    @Mock
    PartRepository partRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    OutsourcedPartRepository outsourcedPartRepository;

    BootStrapData bootStrapData;

    @BeforeEach
    void setUp() {
        bootStrapData = new BootStrapData(partRepository, productRepository, outsourcedPartRepository);
    }

    @Test
    void run_createsData_whenRepositoriesAreEmpty() throws Exception {
        // Arrange: repositories report empty so Bootstrap should seed data
        when(partRepository.count()).thenReturn(0L);
        when(productRepository.count()).thenReturn(0L);
        when(outsourcedPartRepository.findAll()).thenReturn(List.of());

        // Echo‑back behaviour for bulk save methods so returned objects are non‑null
        when(partRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(productRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        bootStrapData.run();

        // Assert – verify that saveAll is invoked exactly once per repo
        ArgumentCaptor<List> partsCaptor = ArgumentCaptor.forClass(List.class);
        verify(partRepository).saveAll(partsCaptor.capture());

        ArgumentCaptor<List> productsCaptor = ArgumentCaptor.forClass(List.class);
        verify(productRepository).saveAll(productsCaptor.capture());

        // Validate captured parts (3 in‑house + 2 outsourced)
        @SuppressWarnings("unchecked")
        List<Part> savedParts = partsCaptor.getValue();
        assertEquals(5, savedParts.size(), "Expected five parts to be persisted");
        assertTrue(savedParts.stream().anyMatch(p -> p instanceof InhousePart));
        assertTrue(savedParts.stream().anyMatch(p -> p instanceof OutsourcedPart));

        // Validate captured products (each product must reference at least one part)
        @SuppressWarnings("unchecked")
        List<Product> savedProducts = productsCaptor.getValue();
        assertEquals(5, savedProducts.size(), "Expected five products to be persisted");
        assertTrue(savedProducts.stream().allMatch(p -> !p.getParts().isEmpty()), "Each product should include parts");
    }

    @Test
    void run_skipsCreation_whenRepositoriesNotEmpty() throws Exception {
        // Arrange: repositories already contain data
        when(partRepository.count()).thenReturn(10L);
        when(productRepository.count()).thenReturn(2L);

        // Act
        bootStrapData.run();

        // Assert – no seeding should occur
        verify(partRepository, never()).saveAll(any());
        verify(productRepository, never()).saveAll(any());
    }
}
