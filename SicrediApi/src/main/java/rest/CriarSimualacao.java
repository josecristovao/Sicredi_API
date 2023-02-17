package rest;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CriarSimualacao {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost/api/v1";
		RestAssured.port = 8080;
		RestAssured.basePath = "simulacoes";
	}
	
	
	
	
	@Test
	public void deveCriarSimulacaoComSucesso() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126401");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(201)
			.body("cpf", Matchers.is("07161126401"))
			.body("nome", Matchers.is("José"))
			.body("email", Matchers.is("jose@gmail.com"))
			.body("valor", Matchers.is(1000))
			.body("parcelas", Matchers.is(2))
			.body("seguro", Matchers.is(true));
	}
	
	@Test
	public void naoDeveCriarSimulacaoComCPFInvalidoComMaisNumeros() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "0716112640111");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("cpf", Matchers.is("0716112640111"))
			.body("nome", Matchers.is("José"))
			.body("email", Matchers.is("jose@gmail.com"))
			.body("valor", Matchers.is(1000))
			.body("parcelas", Matchers.is(2))
			.body("seguro", Matchers.is(true));
	}
	
	@Test
	public void naoDeveCriarSimulacaoComCPFInvalidoComMenosNumeros() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "123");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("cpf", Matchers.is("123"))
			.body("nome", Matchers.is("José"))
			.body("email", Matchers.is("jose@gmail.com"))
			.body("valor", Matchers.is(1000))
			.body("parcelas", Matchers.is(2))
			.body("seguro", Matchers.is(true));
	}
	
	@Test
	public void naoDeveCriarSimulacaoComCPFJaCadastrado() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "66414919004");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(409)
			.body("mensagem", Matchers.is("CPF já existente"));
	}
	
	
	@Test
	public void naoDeveCriarSimulacaoComNomeInvalido() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126999");
		params.put("nome", "1José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(409);
			
	}
	
	@Test
	public void naoDeveCriarSimulacaoComEmailInvalido() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161125599");
		params.put("nome", "José");
		params.put("email", "jose");
		params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body(Matchers.is("{\"erros\":{\"email\":\"E-mail deve ser um e-mail válido\"}}"));
	}
	
	@Test
	public void naoDeveCriarSimulacaoComValorlInvalidoMenorQueOPermitido() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161125591");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 999);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400);
			}
	
	@Test
	public void naoDeveCriarSimulacaoComValorlInvalidoMaiorQueOPermitido() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07151525591");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 40001);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body(Matchers.is("{\"erros\":{\"valor\":\"Valor deve ser menor ou igual a R$ 40.000\"}}"));
	}
	
	@Test
	public void naoDeveCriarSimulacaoComParcelaslInvalidaMaiorQueOPermitido() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07153525591");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 5000);
		params.put("parcelas", 49);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400);
	}
	
	@Test
	public void naoDeveCriarSimulacaoComParcelaslInvalidaMenorQueOPermitido() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "17153525591");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 5000);
		params.put("parcelas", 1);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
		.body(Matchers.is("{\"erros\":{\"parcelas\":\"Parcelas deve ser igual ou maior que 2\"}}"));
	}
	
	
	
	@Test
	public void naoDeveCriarSimulacaoCampoObrigatorioCPFVazio() {
		Map<String, Object> params = new HashMap<String, Object>();
	//	params.put("cpf", "07161126401");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body(Matchers.is("{\"erros\":{\"cpf\":\"CPF não pode ser vazio\"}}"));

	}
	@Test
	public void NaodeveCriarSimulacaoCampoObrigatorioNomeVazio() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126405");
	//	params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body(Matchers.is("{\"erros\":{\"nome\":\"Nome não pode ser vazio\"}}"));

	}
	
	@Test
	public void naoDeveCriarSimulacaoCampoObrigatorioEmailVazio() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126405");
		params.put("nome", "José");
	//	params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body(Matchers.is("{\"erros\":{\"email\":\"E-mail não deve ser vazio\"}}"));
	}
	
	@Test
	public void naoDeveCriarSimulacaoCampoObrigatorioValorVazio() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126405");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
	//	params.put("valor", 1000);
		params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body(Matchers.is("{\"erros\":{\"valor\":\"Valor não pode ser vazio\"}}"));
	}
	
	@Test
	public void naoDeveCriarSimulacaoCampoObrigatorioParcelasVazio() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126405");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
	//	params.put("parcelas", 2);
		params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body(Matchers.is("{\"erros\":{\"parcelas\":\"Parcelas não pode ser vazio\"}}"));
	}
	
	@Test
	public void naoDeveCriarSimulacaoCampoObrigatorioSeguroVazio() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", "07161126405");
		params.put("nome", "José");
		params.put("email", "jose@gmail.com");
		params.put("valor", 1000);
		params.put("parcelas", 2);
	//	params.put("seguro", true);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
		.post()
		.then()
			.log().all()
			.statusCode(400)
			.body(Matchers.is("{\"erros\":{\"seguro\":\"Seguro não pode ser vazio\"}}"));
	}
}
