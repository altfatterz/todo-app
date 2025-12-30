package com.github.altfatterz.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TodoAppTests {

    @Autowired
    private PostgreSQLContainer postgres;

    @Test
    void contextLoads() {
        assertThat(postgres.isRunning());
        System.out.println(postgres.getDatabaseName());
        System.out.println(postgres.getJdbcUrl());
    }

}
