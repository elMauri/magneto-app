package com.magneto.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.magneto.app.domain.Adn;
import com.magneto.app.service.AdnService;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MagnetoControllerTest {
	
	private MagnetoController controller;
	
	@Mock
	private AdnService service;
	@Mock
	private List<String> adns;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new MagnetoController(service);
	}
	
	@Test
	public void testReadEmptyPayload403() {
		Map <String, List<String>> payload = new HashMap<>();
		payload.put("dna",adns);
		when(adns.isEmpty()).thenReturn(Boolean.TRUE);
		ResponseEntity<Object> response = controller.read(payload);
		Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void testReadPayloadOk() {}
	
	@Test
	public void testStatsOk() {}
	
	
}
