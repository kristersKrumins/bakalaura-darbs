package com.example.demo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Core behaviour tests for {@link Product}.
 */
class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Starter-Kit", 19.99, 7);
    }

    /* ───────── constructor / simple accessors ───────── */

    @Test
    void constructor_populatesFields() {
        assertEquals("Starter-Kit", product.getName());
        assertEquals(19.99, product.getPrice());
        assertEquals(7,       product.getInv());
        assertTrue(product.getParts().isEmpty());
    }

    @Test
    void gettersAndSetters_roundTrip() {
        product.setName("Bundle");
        product.setPrice(55.0);
        product.setInv(11);

        assertAll(
            () -> assertEquals("Bundle", product.getName()),
            () -> assertEquals(55.0,     product.getPrice()),
            () -> assertEquals(11,       product.getInv())
        );
    }

    /* ───────── addPart ↔ bi-directional link ───────── */

    @Test
    void addPart_linksInhousePart() {
        InhousePart in = new InhousePart();
        product.addPart(in);

        assertTrue(product.getParts().contains(in));
        assertTrue(in.getProducts().contains(product));
    }

    @Test
    void addPart_linksOutsourcedPart() {
        OutsourcedPart out = new OutsourcedPart();
        product.addPart(out);

        assertTrue(product.getParts().contains(out));
        assertTrue(out.getProducts().contains(product));
    }

    /* ───────── equals, hashCode, toString ───────── */

    @Test
    void equalsAndHashCode_useIdOnly() {
        Product p1 = new Product(); p1.setId(42L);
        Product p2 = new Product(); p2.setId(42L);
        Product p3 = new Product(); p3.setId(99L);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
    }

    @Test
    void toString_returnsName() {
        product.setName("Widget-Pack");
        assertEquals("Widget-Pack", product.toString());
    }
}
