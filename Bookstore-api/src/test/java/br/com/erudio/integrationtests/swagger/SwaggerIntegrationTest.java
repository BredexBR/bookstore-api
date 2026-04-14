package br.com.erudio.integrationtests.swagger;

import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@LocalServerPort
	private int randomPort;

	@Test
	void shouldDisplaySwaggerUIPage() {
		var content = given()
				.basePath("/swagger-ui/index.html")
				.port(randomPort)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		assertTrue(content.contains("Swagger UI"));
	}

}