package com.retial.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RetailApplicationTest {

    @Test
    void contextLoads() {
        // Just verifying the app context starts successfully
    }

    @Test
void testMainMethod() {
    System.setProperty("server.port", "0"); // use random port
    RetailApplication.main(new String[] {});
}

}
