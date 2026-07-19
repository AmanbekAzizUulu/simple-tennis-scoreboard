package com.dandaev.edu.service;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD) // этот режим тестирования является по умолчанию
public class TestLifeCycleDemonstration {

    private final int instanceId;

    private static int counter = 0;

    public TestLifeCycleDemonstration() {
        instanceId = ++counter;
        System.out.println("Создан экземпляр №" + instanceId);
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("@BeforeAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("@BeforeEach для экземпляра №" + instanceId);
    }

    @Test
    void testOne() {
        System.out.println("testOne в экземпляре №" + instanceId);
    }

    @Test
    void testTwo() {
        System.out.println("testTwo в экземпляре №" + instanceId);
    }

    @AfterEach
    void afterEach() {
        System.out.println("@AfterEach для экземпляра №" + instanceId);
    }

    @AfterAll
    static void afterAll() {
        System.out.println("@AfterAll");
    }
}
