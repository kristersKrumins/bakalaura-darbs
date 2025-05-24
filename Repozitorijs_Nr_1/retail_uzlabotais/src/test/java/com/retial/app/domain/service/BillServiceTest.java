package com.retial.app.domain.service;

import com.retial.app.domain.entity.Bill;
import com.retial.app.domain.entity.Item;
import com.retial.app.domain.entity.User;
import com.retial.app.domain.enums.UserType;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillServiceTest {

    private final BillService billService = new BillService();

    @Test
    void testCalculateNetPrice_ForEmployee() {
        User user = new User();
        user.setType(UserType.EMPLOYEE);

        Item item1 = new Item(1L, "Item1", 100.0, false);
        Item item2 = new Item(2L, "Grocery", 50.0, true);
        Bill bill = new Bill(1L, List.of(item1, item2));

        double result = billService.calculateNetPrice(user, bill);

        double expected = 150 - (100 * 0.30) - 5; // 30% off non-grocery (100), + $5 off for $100 in total
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testCalculateNetPrice_ForLoyalCustomer() {
        User user = new User();
        user.setType(UserType.CUSTOMER);
        user.setStartDate(DateTime.now().minusYears(3).toDate()); // over 2 years

        Item item = new Item(3L, "Item", 200.0, false);
        Bill bill = new Bill(2L, List.of(item));

        double result = billService.calculateNetPrice(user, bill);

        double expected = 200 - (200 * 0.05) - 10; // $10 = 5 for every 100
        assertEquals(expected, result, 0.001);
     }
     @Test
     void testCalculateNetPrice_UserTypeNull() {
        User user = new User(); // no type
        Item item = new Item(4L, "Item", 100.0, false);
        Bill bill = new Bill(3L, List.of(item));

        double result = billService.calculateNetPrice(user, bill);

       // No discount, just 5 off for every 100
       double expected = 100 - 0 - 5;
       assertEquals(expected, result, 0.001);
    }

    @Test
    void testCalculateNetPrice_OnlyGroceries() {
       User user = new User();
       user.setType(UserType.AFFILIATE);
       Item item = new Item(5L, "Banana", 200.0, true);
       Bill bill = new Bill(4L, List.of(item));

      // Affiliate discount ignored, only 10 off (5 for every 100)
    double result = billService.calculateNetPrice(user, bill);
    double expected = 200 - 0 - 10;
    assertEquals(expected, result, 0.001);
}

@Test
void testCalculateNetPrice_CustomerLessThan2Years() {
    User user = new User();
    user.setType(UserType.CUSTOMER);
    user.setStartDate(DateTime.now().minusMonths(18).toDate());

    Item item = new Item(6L, "TV", 100.0, false);
    Bill bill = new Bill(5L, List.of(item));

    // No loyalty discount, just 5 for every 100
    double result = billService.calculateNetPrice(user, bill);
    double expected = 100 - 0 - 5;
    assertEquals(expected, result, 0.001);
}

}
