package br.com.erudio.integrationtests.controllers.withjson;

import br.com.erudio.Startup;
import br.com.erudio.config.TestConfigs;
import br.com.erudio.integrationtests.dto.AccountCredentialsDTO;
import br.com.erudio.integrationtests.dto.TokenDTO;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Startup.class, AuthControllerTest.TestConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int randomPort;

    private static TokenDTO tokenDto;

    @BeforeAll
    static void setUp() {
        tokenDto = new TokenDTO();
    }

    @Test
    @Order(1)
    void signin() {
        AccountCredentialsDTO credentials =
            new AccountCredentialsDTO("teste", "admin123");

        tokenDto = given()
                .basePath("/auth/signin")
                    .port(randomPort)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(credentials)
                    .when()
                .post()
                    .then()
                    .statusCode(200)
                        .extract()
                        .body()
                        .as(TokenDTO.class);

        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
    }

    @Test
    @Order(2)
    void refreshToken() {
        tokenDto = given()
                .basePath("/auth/refresh")
                .port(randomPort)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("username", tokenDto.getUsername())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDto.getRefreshToken())
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenDTO.class);

        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
    }


    // Configuração de teste para mockar o JavaMailSender
    @Configuration
    static class TestConfig {
        @Bean
        public JavaMailSender javaMailSender() {
            return org.mockito.Mockito.mock(JavaMailSender.class);
        }
    }
}