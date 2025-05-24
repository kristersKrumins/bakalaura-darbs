package com.oaitsetseleng.techassesment.controllers;

import com.oaitsetseleng.techassesment.models.Bill;
import com.oaitsetseleng.techassesment.models.Items;
import com.oaitsetseleng.techassesment.models.Person;
import com.oaitsetseleng.techassesment.views.BillView;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BillControllerTest {

    @Mock
    private BillView billView;

    @InjectMocks
    private BillController billController;

    private Items mockItem(double price, int quantity, String type) {
        Items item = mock(Items.class);
        when(item.getPrice()).thenReturn(price);
        when(item.getQuantity()).thenReturn(quantity);
        when(item.getType()).thenReturn(type);
        return item;
    }

    @Test
    @DisplayName("getTotal multiplies price × quantity and sums the items")
    void getTotal_basic() {
        Items item1 = mockItem(20.0, 2, "electronics"); // 40
        Items item2 = mockItem(5.0, 4, "groceries");    // 20

        Bill bill = mock(Bill.class);
        when(bill.getItems()).thenReturn(new Items[]{item1, item2});

        double total = billController.getTotal(bill);
        assertEquals(60.0, total, 0.0001);
    }

    @Test
    @DisplayName("getDiscount applies percentage + $5-per-$100 rule for an employee")
    void getDiscount_employee() {
        Items discountable = mockItem(100.0, 1, "electronics");
        Items grocery = mockItem(100.0, 1, "groceries");

        Bill bill = mock(Bill.class);
        when(bill.getItems()).thenReturn(new Items[]{discountable, grocery});
        when(bill.getPersonId()).thenReturn(100);

        double discount = billController.getDiscount(bill, billController.generatePersons());
        assertEquals(35.0, discount, 0.0001);
    }

    @Test
    @DisplayName("getPerson returns an existing person or a generic placeholder")
    void getPerson_paths() {
        List<Person> people = billController.generatePersons();

        Person employee = billController.getPerson(100, people);
        assertEquals("Employee", employee.getType());

        Person generic = billController.getPerson(999, people);
        assertTrue(generic.getType() == null || generic.getType().isEmpty());
        assertEquals(999, generic.getPersonId());
    }

    @Test
    @DisplayName("calculateBill sets total, discount, and initialTotal")
    void testCalculateBill() {
        Items item1 = new Items("monitor", "electronics", 250.0, 1);
        Items item2 = new Items("apple", "groceries", 100.0, 1);
        Bill input = new Bill(400, new Items[]{item1, item2}); // Affiliate

        Bill result = billController.calculateBill(input);
        assertEquals(350.0, result.getInitialTotal(), 0.0001);
        assertTrue(result.getDiscount() > 0);
        assertTrue(result.getTotal() < 350.0);
    }

    @Test
    @DisplayName("Customer <2 years gets only flat discount")
    void testDiscountForYoungCustomerAndHighTotal() {
        Items item = new Items("tv", "electronics", 250.0, 1);
        Bill bill = new Bill(300, new Items[]{item}); // Angela, Customer <2yrs

        double discount = billController.getDiscount(bill, billController.generatePersons());
        assertEquals(10.0, discount, 0.0001);
    }

    @Test
    @DisplayName("getDiscountableTotal excludes groceries")
    void testDiscountableTotalSkipsGroceriesOnly() {
        Items grocery1 = new Items("bread", "groceries", 2.0, 5);
        Items grocery2 = new Items("milk", "groceries", 3.0, 3);
        Bill bill = new Bill(123, new Items[]{grocery1, grocery2});

        double discountable = billController.getDiscountableTotal(bill);
        assertEquals(0.0, discountable);
    }

    @Test
    @DisplayName("Non-matching type triggers only flat $5-per-$100 discount")
    void testNonMatchingPersonType() {
        Items item = new Items("lamp", "electronics", 250.0, 1);
        Bill bill = new Bill(555, new Items[]{item});

        Person custom = new Person(555, "X", new String("Other"), 0);
        List<Person> people = new ArrayList<>();
        people.add(custom);

        double discount = billController.getDiscount(bill, people);
        assertEquals(10.0, discount, 0.0001);
    }

    @Test
    @DisplayName("Customer >= 2 years gets 5% discount and flat $5/100")
    void testCustomerWithLoyalty() {
        Items item = new Items("laptop", "electronics", 200.0, 1);
        Bill bill = new Bill(888, new Items[]{item});

        Person loyal = new Person(888, "Zed", "Customer", 3);
        List<Person> people = List.of(loyal);

        double discount = billController.getDiscount(bill, people);
        // 5% of 200 = 10, plus $5 flat for 1 x 100 = 15
        assertEquals(15.0, discount, 0.0001);
    }
}
