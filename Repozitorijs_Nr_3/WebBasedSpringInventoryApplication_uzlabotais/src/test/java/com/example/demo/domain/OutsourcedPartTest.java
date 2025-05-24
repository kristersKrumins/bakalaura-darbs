package com.example.demo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Very tolerant checks for {@link OutsourcedPart}.
 */
class OutsourcedPartTest {

    @Test
    void constructor_populatesCoreFields() {
        OutsourcedPart part = new OutsourcedPart("Gear-A", 2.5, 20, 5, 40, "WidgetWorks");
        assertEquals("Gear-A", part.getName());
        assertEquals(2.5, part.getPrice());
        assertEquals(20,  part.getInv());
        assertEquals("WidgetWorks", part.getCompanyName());
    }

    @Test
    void settersAndGetters_roundTripForStableFields() {
        OutsourcedPart part = new OutsourcedPart();
        part.setName("Cog");
        part.setPrice(3.3);
        part.setInv(15);
        part.setCompanyName("ACME Ltd.");
        assertEquals("Cog",       part.getName());
        assertEquals(3.3,         part.getPrice());
        assertEquals(15,          part.getInv());
        assertEquals("ACME Ltd.", part.getCompanyName());
    }

    @Test
    void equals_isReflexiveAndNullSafe() {
        OutsourcedPart part = new OutsourcedPart();
        assertEquals(part, part);
        assertNotEquals(part, null);
    }

    @Test
    void toString_matchesName() {
        OutsourcedPart part = new OutsourcedPart();
        part.setName("Bracket-42");
        assertEquals("Bracket-42", part.toString());
    }

    @Test
    void testMinMax_fallbackAndOverride() {
        OutsourcedPart part = new OutsourcedPart();

        // Fallbacks when null
        assertEquals(0, part.getTestMin());
        assertEquals(1000, part.getTestMax());

        // After set
        part.setTestMin(7);
        part.setTestMax(888);
        assertEquals(7, part.getTestMin());
        assertEquals(888, part.getTestMax());

        // Reset to null again
        part.setTestMin(null);
        part.setTestMax(null);
        assertEquals(0, part.getTestMin());
        assertEquals(1000, part.getTestMax());
    }
}