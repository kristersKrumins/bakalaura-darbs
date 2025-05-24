package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link EnufPartsValidator} ensuring inventory adequacy rules.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EnufPartsValidatorTest {

    @Mock
    ApplicationContext context;
    @Mock
    ProductServiceImpl productService;
    @Mock
    Product persistedProduct;

    EnufPartsValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EnufPartsValidator();
        ReflectionTestUtils.setField(validator, "context", context);
        when(context.getBean(ProductServiceImpl.class)).thenReturn(productService);
    }

    @Test
    void isValid_returnsTrue_whenAllPartsHaveEnoughInventory() {
        Part part1 = mock(Part.class);
        when(part1.getInv()).thenReturn(10);
        when(persistedProduct.getParts()).thenReturn(Set.of(part1));
        when(persistedProduct.getInv()).thenReturn(5);
        when(productService.findById(1)).thenReturn(persistedProduct);

        Product productUnderTest = mock(Product.class);
        when(productUnderTest.getId()).thenReturn(1L);
        when(productUnderTest.getInv()).thenReturn(7); // delta = 2, part inv =10 → OK

        assertTrue(validator.isValid(productUnderTest, null));
    }

    @Test
    void isValid_returnsFalse_whenAnyPartInventoryTooLow() {
        Part part1 = mock(Part.class);
        when(part1.getInv()).thenReturn(1);
        when(persistedProduct.getParts()).thenReturn(Set.of(part1));
        when(persistedProduct.getInv()).thenReturn(5);
        when(productService.findById(1)).thenReturn(persistedProduct);

        Product productUnderTest = mock(Product.class);
        when(productUnderTest.getId()).thenReturn(1L);
        when(productUnderTest.getInv()).thenReturn(7); // delta =2, part inv=1 → fail

        assertFalse(validator.isValid(productUnderTest, null));
    }

    @Test
    void isValid_returnsTrue_whenProductIsNew() {
        Product newProduct = mock(Product.class);
        when(newProduct.getId()).thenReturn(0L);
        assertTrue(validator.isValid(newProduct, null));
    }
}
