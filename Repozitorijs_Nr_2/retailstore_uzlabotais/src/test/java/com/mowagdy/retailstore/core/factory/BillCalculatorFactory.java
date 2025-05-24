package com.mowagdy.retailstore.core.factory;

import com.mowagdy.retailstore.core.model.BillItem;
import com.mowagdy.retailstore.core.model.User;
import com.mowagdy.retailstore.core.model.UserType;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BillCalculatorFactoryTest {

    @Test
    void testGetEmployeeBillCalculator() {
        User user = new User();
        user.setUserType(UserType.EMPLOYEE);

        BillCalculatorFactory factory = new BillCalculatorFactory(user, Collections.emptyList());
        assertTrue(factory.getBillCalculator() instanceof EmployeeBillCalculator);
    }

    @Test
    void testGetCustomerBillCalculator() {
        User user = new User();
        user.setUserType(UserType.CUSTOMER);

        BillCalculatorFactory factory = new BillCalculatorFactory(user, Collections.emptyList());
        assertTrue(factory.getBillCalculator() instanceof CustomerBillCalculator);
    }

    @Test
    void testGetAffiliateBillCalculator() {
        User user = new User();
        user.setUserType(UserType.AFFILIATE);

        BillCalculatorFactory factory = new BillCalculatorFactory(user, Collections.emptyList());
        assertTrue(factory.getBillCalculator() instanceof AffiliateBillCalculator);
    }
}