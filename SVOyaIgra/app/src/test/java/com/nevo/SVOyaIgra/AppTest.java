package com.nevo.SVOyaIgra;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void appHasAGreeting() {
        GameApp classUnderTest = new GameApp();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
