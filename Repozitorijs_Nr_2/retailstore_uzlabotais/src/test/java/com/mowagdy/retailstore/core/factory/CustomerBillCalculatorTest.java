package com.mowagdy.retailstore.core.factory;

import com.mowagdy.retailstore.core.model.BillItem;
import com.mowagdy.retailstore.core.model.ItemType;
import com.mowagdy.retailstore.core.model.User;
import com.mowagdy.retailstore.core.model.UserType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerBillCalculatorTest {

    private User userRegistered(long yearsAgo) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, (int) -yearsAgo);
        return new User("Test User", UserType.CUSTOMER, cal.getTime());
    }

    @Test
    void testDiscountForOldCustomerAndNonGroceryItem() {
        User user = userRegistered(3);
        List<BillItem> items = List.of(
                new BillItem(ItemType.BAKERY, "Cake", 10.0, 2, null) // Not groceries
        );

        CustomerBillCalculator calculator = new CustomerBillCalculator(user, items);
        double discount = calculator.calculatePercentageBasedDiscount();

        assertEquals(1.0, discount); // 20.0 * 0.05 = 1.0
    }

    @Test
    void testNoDiscountForRecentCustomer() {
        User user = userRegistered(1);
        List<BillItem> items = List.of(
                new BillItem(ItemType.BAKERY, "Bread", 10.0, 2, null)
        );

        CustomerBillCalculator calculator = new CustomerBillCalculator(user, items);
        double discount = calculator.calculatePercentageBasedDiscount();

        assertEquals(0.0, discount);
    }

    @Test
    void testNoDiscountForGroceryItems() {
        User user = userRegistered(5);
        List<BillItem> items = List.of(
                new BillItem(ItemType.GROCERIES, "Apples", 5.0, 5, null)
        );

        CustomerBillCalculator calculator = new CustomerBillCalculator(user, items);
        double discount = calculator.calculatePercentageBasedDiscount();

        assertEquals(0.0, discount);
    }
}
