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
 * Tests for {@link PriceProductValidator} verifying price–vs–parts logic.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PriceProductValidatorTest {

    @Mock
    ApplicationContext context;
    @Mock
    ProductServiceImpl productService;

    @Mock
    Product persistedProduct;

    PriceProductValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PriceProductValidator();
        ReflectionTestUtils.setField(validator, "context", context);
        when(context.getBean(ProductServiceImpl.class)).thenReturn(productService);
    }

    @Test
    void isValid_returnsTrue_whenPriceGreaterOrEqualSumParts() {
        Part part1 = mock(Part.class);
        when(part1.getPrice()).thenReturn(5.0);
        Part part2 = mock(Part.class);
        when(part2.getPrice()).thenReturn(3.0);
        when(persistedProduct.getParts()).thenReturn(Set.of(part1, part2));
        when(productService.findById(1)).thenReturn(persistedProduct);

        Product productUnderTest = mock(Product.class);
        when(productUnderTest.getId()).thenReturn(1L);
        when(productUnderTest.getPrice()).thenReturn(10.0);

        assertTrue(validator.isValid(productUnderTest, null));
    }

    @Test
    void isValid_returnsFalse_whenPriceLessThanSumParts() {
        Part part1 = mock(Part.class);
        when(part1.getPrice()).thenReturn(5.0);
        when(persistedProduct.getParts()).thenReturn(Set.of(part1));
        when(productService.findById(1)).thenReturn(persistedProduct);

        Product productUnderTest = mock(Product.class);
        when(productUnderTest.getId()).thenReturn(1L);
        when(productUnderTest.getPrice()).thenReturn(4.0);

        assertFalse(validator.isValid(productUnderTest, null));
    }

    @Test
    void isValid_returnsTrue_whenProductIsNew() {
        Product newProduct = mock(Product.class);
        when(newProduct.getId()).thenReturn(0L);
        assertTrue(validator.isValid(newProduct, null));
    }

    @Test
    void isValid_assignsMyContext_whenContextNotNull() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(0L);  // triggers the early-return path

        assertTrue(validator.isValid(product, null));
        assertSame(context, PriceProductValidator.myContext);
    }
}