package com.magneto.app.service;

import java.util.List;

import com.magneto.app.domain.Adn;

public interface IAdnService {
	
	public List<Adn> getAllAdns();
	
	public Adn getAdnByChain(String[] chain);
	
	public void saveAdn(Adn adn);
	
	public Boolean isMutant(String[] dna);
	
	public String[] load(List<String> dnaChains);
	
}