package br.com.erudio.integrationtests.testConnectionMySQL;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MysqlConnectionTest {

    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.41")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withStartupTimeout(java.time.Duration.ofSeconds(120));

    @Test
    void testMysqlConnection() throws SQLException {
        mysqlContainer.start(); // start manual aqui
        try (Connection conn = DriverManager.getConnection(
                mysqlContainer.getJdbcUrl(),
                mysqlContainer.getUsername(),
                mysqlContainer.getPassword())) {
            System.out.println("Conectou com sucesso!");
        } finally {
            mysqlContainer.stop();
        }
    }
}