package com.dandaev.edu.service.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    private Calculator calculator;

    public CalculatorTest(){
    }

    @BeforeEach
    void setUp(){
        calculator = new Calculator();
    }

    @Test
    void shouldReturnSumOfTwoNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }
}
