package rest;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ConsultarERemoverSimulacao {

	
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost/api/v1";
		RestAssured.port = 8080;
		RestAssured.basePath = "simulacoes";
	}

	
	
	@Test
	public void A_consultarSimulacaoPorCPF() {		
		given()
			.log().all()
		.when()
		.get("/66414919004")
		.then()
			.log().all()
			.statusCode(200)
			.body(Matchers.is("{\"id\":11,\"nome\":\"Fulano\",\"cpf\":\"66414919004\",\"email\":\"fulano@gmail.com\",\"valor\":11000.00,\"parcelas\":3,\"seguro\":true}"));
			;
	}
	
	@Test
	public void B_consultarSimulacaoPorCPFInexistente() {		
		given()
			.log().all()
		.when()
		.get("/66414919999")
		.then()
			.log().all()
			.statusCode(404);
	}
	
	@Test
	public void C_consultarTodasSimulacoes() {		
		given()
			.log().all()
		.when()
		.get()
		.then()
			.log().all()
			.statusCode(200)
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("user.json"))
			;
	}

	
	@Test
	public void D_deletarSimulacaoIdum() {		
		given()
			.log().all()
		.when()
		.delete("/11")
		.then()
			.log().all()
			.statusCode(204);
	}
	
	@Test
	public void E_deletarSimulacaoIdDois() {		
		given()
			.log().all()
		.when()
		.delete("/12")
		.then()
			.log().all()
			.statusCode(204);
	}
	
	
	@Test
	public void F_deletarSimulacaoIdNaoEncontrado() {		
		given()
			.log().all()
		.when()
		.delete("/99")
		.then()
			.log().all()
			.statusCode(404)
			.body(Matchers.is("{\"erros\":{\"mensagem\":\"Simulação não encontrada\"}}"));
	}
	
	@Test
	public void G_consultarSimulacoesVazio() {		
		given()
			.log().all()
		.when()
		.get()
		.then()
			.log().all()
			.statusCode(204)
			.body(Matchers.is("[]"));
			
	}
	
	
	
	
	
}
