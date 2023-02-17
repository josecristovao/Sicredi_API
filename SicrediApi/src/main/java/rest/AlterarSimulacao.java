package rest;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AlterarSimulacao {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost/api/v1";
		RestAssured.port = 8080;
		RestAssured.basePath = "simulacoes";
	}
	
	@Test
	public void alterarSimulacaoUsuarioInexistente() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126401");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.pathParam("cpf", "11111111111")
		.when()
		.put("/{cpf}")
		.then()
			.log().all()
			.statusCode(404)
			.body(Matchers.is("{\"mensagem\":{\"\":\"CPF não encontrado\"}}"));
	}
	
	
	
	
	@Test
	public void deveRealizarModificacoesCadastroMenosValor() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126401");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.pathParam("cpf", "66414919004")
		.when()
		.put("/{cpf}")
		.then()
			.log().all()
			.statusCode(200)
			.body("cpf", Matchers.is("07161126401"))
			.body("nome", Matchers.is("José"))
			.body("email", Matchers.is("jose@gmail.com"))
			.body("parcelas", Matchers.is(2))
			.body("seguro", Matchers.is(true));
	}
	
	
	@Test
	public void deveRealizarModificacoesCadastroMenosValorComEmailInvalido() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126401");
		params.put("nome", "José");
		params.put("email", "jose");
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.pathParam("cpf", "66414919004")
		.when()
		.put("/{cpf}")
		.then()
			.log().all()
			.statusCode(404)
			.body(Matchers.is("{\"erros\":{\"email\":\"E-mail deve ser um e-mail válido\"}}"));
	}
	
	
	
	@Test
	public void deveRealizarModificacoesCadastroNoValor() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("valor", 5000);
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.pathParam("cpf", "17822386034")
		.when()
		.put("/{cpf}")
		.then()
			.log().all()
			.statusCode(500)
			.body("valor", Matchers.is(5000));

	}
	
	@Test
	public void naoDeveRealizarModificacoesCadastroParcelasSomenteMenorQueDois() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parcelas", 1);
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.pathParam("cpf", "17822386034")
		.when()
		.put("/{cpf}")
		.then()
			.log().all()
			.statusCode(500);
	}
	
	@Test
	public void naoDeveRealizarModificacoesCadastroParcelasSomenteMaiorQue48() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parcelas", 49);
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.pathParam("cpf", "17822386034")
		.when()
		.put("/{cpf}")
		.then()
			.log().all()
			.statusCode(500);
	}
	
	
	@Test
	public void NaoDeveRealizarModificacoesCadastroSomenteEmailInvalido() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", "jose");
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.pathParam("cpf", "66414919004")
		.when()
		.put("/{cpf}")
		.then()
			.log().all()
			.statusCode(500);
	}
	
	@Test
	public void deveRealizarModificacoesCadastroSomenteEmailValido() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", "jose@gmail.com");	
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.pathParam("cpf", "66414919004")
		.when()
		.put("/{cpf}")
		.then()
			.log().all()
			.statusCode(500)
			.body("email", Matchers.is("jose@gmail.com"));
	}
	
	@Test
	public void deveRealizarModificacoesCadastroSomenteNome() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nome", "José");
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
			.pathParam("cpf", "17822386034")
		.when()
		.put("/{cpf}")
		.then()
			.log().all()
			.statusCode(500)
			.body("nome", Matchers.is("José"));

	}
}
