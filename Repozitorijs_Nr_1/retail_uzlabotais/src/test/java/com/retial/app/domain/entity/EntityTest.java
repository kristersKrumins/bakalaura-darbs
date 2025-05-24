package com.retial.app.domain.entity;

import com.retial.app.domain.enums.UserType;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

    @Test
    void testUserFullEquality() {
        Date date = new Date();
        User u1 = new User(1L, "Alice", UserType.CUSTOMER, date);
        User u2 = new User(1L, "Alice", UserType.CUSTOMER, date);

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
        assertNotNull(u1.toString());
    }

    @Test
    void testUserNegativeEquality() {
        User u = new User(1L, "Alice", UserType.CUSTOMER, new Date());
        assertNotEquals(u, null);  // against null
        assertNotEquals(u, "some string"); // different class
    }

    @Test
    void testUserEmptyConstructor() {
        User u = new User();
        assertNull(u.getId());
        assertNull(u.getName());
        assertNull(u.getType());
        assertNull(u.getStartDate());
    }

    @Test
    void testItemFullEquality() {
        Item i1 = new Item(1L, "Milk", 2.5, true);
        Item i2 = new Item(1L, "Milk", 2.5, true);

        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
        assertTrue(i1.isGrocery());
        assertNotNull(i1.toString());
    }

    @Test
    void testItemNegativeEquality() {
        Item i = new Item(1L, "Milk", 2.5, true);
        assertNotEquals(i, null);
        assertNotEquals(i, "test");
    }

    @Test
    void testItemEmptyConstructor() {
        Item i = new Item();
        assertNull(i.getId());
        assertNull(i.getName());
        assertEquals(0.0, i.getPrice());
        assertFalse(i.isGrocery()); // default false
    }

    @Test
    void testBillFullEquality() {
        Item item = new Item(1L, "TV", 200.0, false);
        List<Item> items = List.of(item);
        Bill b1 = new Bill(1L, items);
        Bill b2 = new Bill(1L, items);

        assertEquals(b1, b2);
        assertEquals(b1.hashCode(), b2.hashCode());
        assertEquals(200.0, b1.getTotalAmount());
        assertEquals(200.0, b1.getTotalAmountExcludingGroceries());
        assertNotNull(b1.toString());
    }

    @Test
    void testBillNegativeEquality() {
        Bill bill = new Bill(1L, List.of());
        assertNotEquals(bill, null);
        assertNotEquals(bill, "string");
    }

    @Test
    void testBillEmptyConstructor() {
        Bill bill = new Bill();
        assertNull(bill.getId());
        assertNull(bill.getItems());
        assertEquals(0.0, bill.getTotalAmount());
        assertEquals(0.0, bill.getTotalAmountExcludingGroceries());
    }

    @Test
    void testBillWithNullItems() {
        Bill bill = new Bill(2L, null);
        assertEquals(0.0, bill.getTotalAmount());
        assertEquals(0.0, bill.getTotalAmountExcludingGroceries());
    }
@Test
void testUserBuilderDirectly() {
    User.UserBuilder builder = User.builder();
    builder.id(100L).name("BuilderGuy").type(UserType.AFFILIATE).startDate(new Date());
    User user = builder.build();

    assertEquals("BuilderGuy", user.getName());
    assertEquals(UserType.AFFILIATE, user.getType());
    assertEquals(100L, user.getId());
    assertNotNull(user.getStartDate());
}

@Test
void testItemBuilderDirectly() {
    Item.ItemBuilder builder = Item.builder();
    builder.id(200L).name("Cable").price(9.99).isGrocery(false);
    Item item = builder.build();

    assertEquals("Cable", item.getName());
    assertEquals(9.99, item.getPrice());
    assertEquals(200L, item.getId());
    assertFalse(item.isGrocery());
}
@Test
void testUserEqualsReflexiveAndSymmetry() {
    User user = new User(1L, "A", UserType.AFFILIATE, new Date());

    assertEquals(user, user); // reflexive
    assertNotEquals(user, null); // null check
    assertNotEquals(user, "NotAUser"); // different type
}
@Test
void testItemEqualsWithNulls() {
    Item i1 = new Item(null, null, 0.0, false);
    Item i2 = new Item(null, null, 0.0, false);

    assertEquals(i1, i2);
}
@Test
void testBillEqualsWithDifferentItems() {
    Item item1 = new Item(1L, "TV", 300.0, false);
    Item item2 = new Item(2L, "Mouse", 20.0, false);

    Bill b1 = new Bill(1L, List.of(item1));
    Bill b2 = new Bill(1L, List.of(item2)); // same id, different item list

    assertNotEquals(b1, b2); // branches inside list comparison
}
@Test
void testUserEqualsWithDifferentValues() {
    Date now = new Date();
    User u1 = new User(1L, "Alice", UserType.CUSTOMER, now);
    User u2 = new User(2L, "Bob", UserType.EMPLOYEE, now);

    assertNotEquals(u1, u2);
}

@Test
void testUserEqualsDifferentStartDate() {
    User u1 = new User(1L, "Alice", UserType.CUSTOMER, new Date(0));
    User u2 = new User(1L, "Alice", UserType.CUSTOMER, new Date());

    assertNotEquals(u1, u2); // same fields but different start date
}

@Test
void testUserHashCodeAndToString() {
    User user = new User(1L, "Test", UserType.AFFILIATE, new Date());
    int hash = user.hashCode();
    assertEquals(hash, user.hashCode()); // consistent hash
    assertNotNull(user.toString()); // toString branch hit
}
@Test
void testUserCanEqualCoverage() {
    User user = new User();
    assertTrue(user.canEqual(new User())); // true path
    assertFalse(user.canEqual("NotAUser")); // false path
}


}
