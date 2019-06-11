package com.magneto.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.magneto.app.service.AdnService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MagnetoControllerTest {
	
	@Autowired
	private WebApplicationContext appContext;
	
	@Autowired
	private MagnetoController controller;

	private MockMvc mockMvc;
	
	@Autowired
	private AdnService service;
	
	@Mock
	private List<String> DNAs;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.appContext).build();
	}
	
	@Test
	public void contextLoads() {
	}
	
	/**
	 * Test - valido el comienzo con valores en 0 tanto Mutant/Human - Status
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01AtStatsBegin() throws Exception {
		mockMvc.perform(
				get("/magnetoapi/stats/")
					.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.count_mutant_dna").value(0))
				.andExpect(jsonPath("$.count_human_dna").value(0))
				.andExpect(jsonPath("$.ratio").value(0));
		ResponseEntity<Object> response = controller.getStats();
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	/**
	 * Test solamente cadena de DNA Humano
	 * 
	 * @throws Exception
	 */	
	@Test
	public void test02HumanDNA() throws Exception{
		String request = "{\"dna\": [\"ACCTAT\",\"CTCACT\",\"ACGCTA\",\"ACCTAC\",\"CAATTC\",\"CAATTC\"]}";
		mockMvc.perform(post("/magnetoapi/mutant/").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(request.getBytes()))
				.andExpect(status().isForbidden());
	}
	
	/**
	 * Test, Valida que se haya contabilizado una sola cadena de DNA Humana al enviar varias veces 
	 * la misma cadena de DNA
	 * 
	 * @throws Exception
	 */	
	@Test
	public void test03OneHumanDna() throws Exception{
		test02HumanDNA();
		test02HumanDNA();
		test02HumanDNA();
		mockMvc.perform(
				get("/magnetoapi/stats/")
					.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.count_mutant_dna").value(0))
				.andExpect(jsonPath("$.count_human_dna").value(1))
				.andExpect(jsonPath("$.ratio").value(0));
	}
	
	
	/**
	 * Test que se haya detectado una cadena de DNA Mutante
	 * 
	 * @throws Exception
	 */	
	@Test
	public void test04MutantDNA() throws Exception{
		String request = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTA\"]}"; 
		mockMvc.perform(post("/magnetoapi/mutant/").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(request.getBytes()))
				.andExpect(status().isOk());
	}
	
	/**
	 * Test que se haya detectado una cadena de DNA Mutante y una cadena de DNA Humano
	 * 
	 * @throws Exception
	 */	
	@Test
	public void test05OneHumanDnaAndOneMutantDNA() throws Exception{
		mockMvc.perform(
				get("/magnetoapi/stats/")
					.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.count_mutant_dna").value(1))
				.andExpect(jsonPath("$.count_human_dna").value(1))
				.andExpect(jsonPath("$.ratio").value(0.5));
	}
	
	
	/**
	 * Test - Test Buscando DNA Mutante hacia abajo 4 letras iguales y en diagonal
	 *  hacia abajo a la derecha 4 letras iguales
	 * 
	 * @throws Exception
	 */
	@Test
	public void test06FindMutantGoOverDownAndCrosswiseRightDown() throws Exception {
		String request = "{\n" + 
				"	\"dna\": [\"AACCCC\",\n" + 
				"	          \"ATATGC\",\n" + 
				"	          \"ACCATA\",\n" + 
				"	          \"ACTTAC\",\n" + 
				"	          \"CAGATC\",\n" + 
				"	          \"CATCGA\"]\n" + 
				"}\n" + 
				""; 
		mockMvc.perform(
				post("/magnetoapi/mutant/")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(request.getBytes()))
				.andExpect(status().isOk());
	}
	
	/**
	 * Test - Test Buscando DNA Mutante diagonal hacia abajo a la derecha  find in last position valid
	 * 
	 * @throws Exception
	 */
	@Test
	public void test07FindMutantGoOverDownAndRightSide() throws Exception {
		String request = "{\n" + 
				"	\"dna\": [\"AACCCC\",\n" + 
				"	          \"ATTTGC\",\n" + 
				"	          \"ACCATA\",\n" + 
				"	          \"ACTTAC\",\n" + 
				"	          \"CAAAAC\",\n" + 
				"	          \"CATCGA\"]\n" + 
				"}\n" + 
				""; 
		mockMvc.perform(
				post("/magnetoapi/mutant/")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(request.getBytes()))
				.andExpect(status().isOk());
	}
	
	
	/**
	 * Base Nitrogenada Invalida. El request no esta dentro de los caracteres validos que son: A,
	 * T, C e G.
	 * @http_code esperado es 406 No Acceptable
	 * 
	 * @throws Exception
	 */
	@Test
	public void invalidNitrogenousBaseRequest() throws Exception {
		String request = "{\n" + 
				"	\"dna\": [\"CTXXXXT\",\n" + 
				"	        \"CTCACTT\",\n" + 
				"	        \"ACGCTAT\",\n" + 
				"	        \"ACCTACC\",\n" + 
				"	        \"CAATTCC\",\n" + 
				"	        \"CACCAAC\",\n" + 
				"	        \"CAACAAT\"]\n" + 
				"}\n" + 
				""; 
		mockMvc.perform(
				post("/magnetoapi/mutant/")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(request.getBytes()))
				.andExpect(status().isNotAcceptable());
	}
	
	
	/**
	 * La cantidad de letras de una cadena es diferente a la cantida de 
	 * cadenas que componen el DNA, debiendo ser igual para poder armar una matriz
	 * de NxN
	 * 
	 * @http_code esperado es 406 No Acceptable
	 * 
	 * @throws Exception
	 */
	@Test
	public void invalidDNAChainLengthReferToCompleteChainLenght() throws Exception {
		String request = "{\n" + 
				"	\"dna\": [\"CTXXXXT\",\n" + 
				"	        \"CTCACTT\",\n" + 
				"	        \"ACGCTAT\",\n" + 
				"	        \"ACCTACC\",\n" + 
				"	        \"CAATTCC\",\n" + 
				"	        \"CACCAAC\",\n" + 
				"	        \"CAACAAT\"]\n" + 
				"}\n" + 
				""; 
		mockMvc.perform(
				post("/magnetoapi/mutant/")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(request.getBytes()))
				.andExpect(status().isNotAcceptable());
	}
	
}
