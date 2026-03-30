package br.com.erudio.integrationtests.testcontainers;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public abstract class AbstractIntegrationTest {

    @MockitoBean
    private JavaMailSender javaMailSender;

    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.41")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withExposedPorts(3306)
            .waitingFor(Wait.forListeningPort());

    // Garante que inicie só 1 vez
    static {
        mysqlContainer.start();
    }

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> mysqlContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> mysqlContainer.getUsername());
        registry.add("spring.datasource.password", () -> mysqlContainer.getPassword());
        registry.add("spring.flyway.url", () -> mysqlContainer.getJdbcUrl());
        registry.add("spring.flyway.user", () -> mysqlContainer.getUsername());
        registry.add("spring.flyway.password", () -> mysqlContainer.getPassword());
        registry.add("spring.datasource.driver-class-name", () -> mysqlContainer.getDriverClassName());
        // Atenção: Flyway ativado nos testes, mudar "update" para "none" no futuro,
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }
}