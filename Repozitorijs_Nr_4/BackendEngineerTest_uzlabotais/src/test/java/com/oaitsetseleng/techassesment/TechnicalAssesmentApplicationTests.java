package com.oaitsetseleng.techassesment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TechnicalAssesmentApplicationTest {

    @Test
    @DisplayName("Application main method should run without exceptions")
    void testMainMethod() {
        assertDoesNotThrow(() -> TechnicalAssesmentApplication.main(new String[]{}));
    }
}
