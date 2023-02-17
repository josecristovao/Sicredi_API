package rest;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class ConsultarRestricao {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost/api/v1/restricoes";
		RestAssured.port = 8080;
	}
	
	@Test
	public void consultarCPFComRestricao() {
		given()
		.when()
			.get("/97093236014")
		.then()
		.log().all()
		.statusCode(200)
		.body("mensagem", Matchers.is("O CPF 97093236014 tem restrição"))
		;
	}
	
	@Test
	public void consultarCPFSemRestricao() {
		given()
		.when()
			.get("/97093236019")
		.then()
		.log().all()
		.statusCode(204)
		.body(Matchers.is(""))
		;
	}
	
	
}
