package com.retial.app.domain.service;

import com.retial.app.domain.entity.Bill;
import com.retial.app.domain.entity.User;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BillService {

    public double calculateNetPrice(User user, Bill bill) {

        double percentageDiscount = getPercentageDiscount(user);

        double totalAmount = bill.getTotalAmount();

        // For every $100 on the bill, there would be a $ 5 discount
        double discountOnBill = (int) (totalAmount / 100) * 5;

        // The percentage based discounts do not apply on groceries.
        double amountExcludingGroceries = bill.getTotalAmountExcludingGroceries();
        double percentageDiscountAmount = amountExcludingGroceries * percentageDiscount;

        return totalAmount - percentageDiscountAmount - discountOnBill;
    }

    private static double getPercentageDiscount(User user) {

        double percentageDiscount = 0.0;

        if (Objects.isNull(user.getType())) {
            return percentageDiscount;
        }

        switch (user.getType()) {
            // If the user is an employee of the store, he gets a 30% discount
            case EMPLOYEE -> percentageDiscount = 0.30;
            // If the user is an affiliate of the store, he gets a 10% discount
            case AFFILIATE -> percentageDiscount = 0.10;
            // If the user has been a customer for over 2 years, he gets a 5% discount.
            case CUSTOMER -> percentageDiscount =
                    Years.yearsBetween(new DateTime(user.getStartDate()), DateTime.now()).getYears() > 2 ? 0.05 : 0.0;
        }
        return percentageDiscount;
    }

}
