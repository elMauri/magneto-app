package com.magneto.app.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.assertj.core.util.Lists;

import com.magneto.app.domain.Adn;
import com.magneto.app.repository.AdnRepository;

import static org.mockito.Mockito.when;

import java.util.List;

public class AdnServiceTest {
	
	@Mock
	private AdnRepository repository;
	@Mock
	private Adn and;

	private String[] chain;
	private List<Adn> adnList;
	private AdnService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new AdnService();
		adnList = Lists.newArrayList();
	}
	
	@Test
	public void testSaveToRepository() {
	/*	
		when((Boolean.valueOf(chain.length == 0)).thenReturn(Boolean.FALSE));
		repository.findById(chain).get();
	*/
	}
	
	@Test
	public void testIsMutantDna() {}
	
	@Test
	public void testIsHumanDna() {}
		
	@Test
	public void testLoadEmptyMatrix() {}
	
	@Test
	public void testAdnChainsSizeNoMatchException() {}
	
	@Test
	public void testAdnChainNoMatchException() {}
	
	@Test
	public void testMoreWordsThanMutantBase() {}
}
