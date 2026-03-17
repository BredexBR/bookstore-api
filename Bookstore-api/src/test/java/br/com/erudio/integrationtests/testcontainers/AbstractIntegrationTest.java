package br.com.erudio.integrationtests.testcontainers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.41")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withExposedPorts(3306) // garante que a porta 3306 do container está exposta
            .waitingFor(Wait.forListeningPort()); // espera até que a porta TCP esteja pronta

    // Define as propriedades do Spring Boot dinamicamente
    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> mysqlContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> mysqlContainer.getUsername());
        registry.add("spring.datasource.password", () -> mysqlContainer.getPassword());
        registry.add("spring.datasource.driver-class-name", () -> mysqlContainer.getDriverClassName());
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }
}