package com.mjc.school;

import com.mjc.school.controller.impl.NewsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MainTest {

    @Autowired
    private NewsController newsController;

    @Test
    void test() {
        assertNotNull(newsController);
    }
}