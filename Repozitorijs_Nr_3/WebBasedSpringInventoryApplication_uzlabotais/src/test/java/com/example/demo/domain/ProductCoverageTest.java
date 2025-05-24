package com.example.demo.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Extra branch-coverage tests for {@link Product}.
 */
class ProductCoverageTest {

    @Test
    void addingSamePartTwice_doesNotDuplicate() {
        Product product = new Product();
        InhousePart part = new InhousePart();

        product.addPart(part);
        product.addPart(part);

        assertEquals(1, product.getParts().size());
    }

    @Test
    void partsSet_isLiveAndMutable() {
        Product product = new Product();
        OutsourcedPart part = new OutsourcedPart();

        product.addPart(part);
        assertTrue(product.getParts().remove(part));
        assertFalse(product.getParts().contains(part));
        assertTrue(part.getProducts().contains(product));
    }

    @Test
    void setParts_replacesWithNewContents() {
        Product product = new Product();
        InhousePart part = new InhousePart();
        Set<Part> customParts = new HashSet<>();
        customParts.add(part);

        product.setParts(customParts);

        assertSame(customParts, product.getParts());
        assertEquals(1, product.getParts().size());
    }

    @Test
    void fullConstructor_setsAllFields() {
        Product product = new Product(77L, "Hammer", 15.5, 20);
        assertEquals(77L, product.getId());
        assertEquals("Hammer", product.getName());
        assertEquals(15.5, product.getPrice());
        assertEquals(20, product.getInv());
    }

    @Test
    void equals_handlesNullAndDifferentClass() {
        Product p = new Product();
        p.setId(10);

        assertNotEquals(p, null);
        assertNotEquals(p, "not a product");
        assertEquals(p, p);
    }

    @Test
    void hashCode_matchesIdContract() {
        Product p = new Product();
        p.setId(123L);

        long id = 123L;
        int expected = (int)(id ^ (id >>> 32));
        assertEquals(expected, p.hashCode());
    }
}