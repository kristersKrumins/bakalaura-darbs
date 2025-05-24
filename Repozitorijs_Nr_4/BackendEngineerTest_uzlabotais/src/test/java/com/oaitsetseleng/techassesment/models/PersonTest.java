package com.oaitsetseleng.techassesment.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    @DisplayName("Constructor with 4 arguments should assign all fields correctly")
    void testFullConstructor() {
        Person person = new Person(1, "Alice", "Employee", 5);

        assertEquals(1, person.getPersonId());
        assertEquals("Alice", person.getName());
        assertEquals("Employee", person.getType());
        assertEquals(5, person.getYears());
    }

    @Test
    @DisplayName("Constructor with 3 arguments should assign personId, name, and type")
    void testPartialConstructor() {
        Person person = new Person(2, "Bob", "Affiliate");

        assertEquals(2, person.getPersonId());
        assertEquals("Bob", person.getName());
        assertEquals("Affiliate", person.getType());
    }

    @Test
    @DisplayName("Setters should update fields, and getters should reflect changes")
    void testSettersAndGetters() {
        Person person = new Person(0, "", "");

        person.setPersonId(3);
        person.setName("Carol");
        person.setType("Customer");
        person.setYears(10);

        assertEquals(3, person.getPersonId());
        assertEquals("Carol", person.getName());
        assertEquals("Customer", person.getType());
        assertEquals(10, person.getYears());
    }
}
