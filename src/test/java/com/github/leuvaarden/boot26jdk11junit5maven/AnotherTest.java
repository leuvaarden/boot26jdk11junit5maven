package com.github.leuvaarden.boot26jdk11junit5maven;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnotherTest {
    @Test
    void contextLoads() {
        assertTrue(true);
    }
}
