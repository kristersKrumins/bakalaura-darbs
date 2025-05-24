package com.example.demo.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Project: demoDarbyFrameworks2-master
 * Package: com.example.demo.domain
 */
class PartTest {
    Part partIn;
    Part partOut;

    @BeforeEach
    void setUp() {
        partIn = new InhousePart();
        partOut = new OutsourcedPart();
    }

    @Test
    void getId() {
        Long idValue = 4L;
        partIn.setId(idValue);
        assertEquals(partIn.getId(), idValue);
        partOut.setId(idValue);
        assertEquals(partOut.getId(), idValue);
    }

    @Test
    void setId() {
        Long idValue = 4L;
        partIn.setId(idValue);
        assertEquals(partIn.getId(), idValue);
        partOut.setId(idValue);
        assertEquals(partOut.getId(), idValue);
    }

    @Test
    void getName() {
        String name = "test inhouse part";
        partIn.setName(name);
        assertEquals(name, partIn.getName());
        name = "test outsourced part";
        partOut.setName(name);
        assertEquals(name, partOut.getName());
    }

    @Test
    void setName() {
        String name = "test inhouse part";
        partIn.setName(name);
        assertEquals(name, partIn.getName());
        name = "test outsourced part";
        partOut.setName(name);
        assertEquals(name, partOut.getName());
    }

    @Test
    void testToString() {
        String name = "test inhouse part";
        partIn.setName(name);
        assertEquals(name, partIn.toString());
    }

    @Test
    void testEquals() {
        Part p1 = new InhousePart();
        Part p2 = new InhousePart();

        p1.setName("widget");
        p1.setPrice(10.0);
        p1.setInv(5);
        p1.setMinInventory(1);
        p1.setMaxInventory(10);

        p2.setName("widget");
        p2.setPrice(10.0);
        p2.setInv(5);
        p2.setMinInventory(1);
        p2.setMaxInventory(10);

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode() {
        Part p = new InhousePart();
        p.setName("widget");
        p.setPrice(10.0);
        p.setInv(5);
        p.setMinInventory(1);
        p.setMaxInventory(10);

        int hash = p.hashCode();
        assertEquals(p.hashCode(), hash);
    }

    @Test
    void testInventoryBelowMinimum() {
        int minInventory = 5;
        int inv = 4;

        partIn.setMinInventory(minInventory);
        partIn.setInv(inv);

        assertEquals(inv, partIn.getInv());

        partOut.setMinInventory(minInventory);
        partOut.setInv(inv);

        assertEquals(inv, partOut.getInv());
    }

    @Test
    void testInventoryAboveMaximum() {
        int maxInventory = 10;
        int inv = 11;

        partIn.setMaxInventory(maxInventory);
        partIn.setInv(inv);

        assertEquals(inv, partIn.getInv());

        partOut.setMaxInventory(maxInventory);
        partOut.setInv(inv);

        assertEquals(inv, partOut.getInv());
    }

    @Test
    void testSetMinMaxAlternativeMethods() {
        partIn.setMin(123);
        partIn.setMax(987);
        assertEquals(123, partIn.getMinInventory());
        assertEquals(987, partIn.getMaxInventory());

        partOut.setMin(222);
        partOut.setMax(888);
        assertEquals(222, partOut.getMinInventory());
        assertEquals(888, partOut.getMaxInventory());
    }
}
