package com.magneto.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.magneto.app.domain.Adn;
import com.magneto.app.exception.AdnChainNoMatchException;
import com.magneto.app.exception.AdnChainsSizeNoMatchException;
import com.magneto.app.repository.AdnRepository;

@Component
public class AdnService implements IAdnService{
	
	/* DNAMUTANTBASE: cantidad de letras que conforman la cadena
	 * nitrogenada de un ADN Mutante 
	 */
	@Value( "${nitrogenous.base.mutant.size}" )
	private int DNAMUTANTBASE;
	
	/* ADNPATTERN: Patron correspondiente a la base nitrogenada 
	 * y que debe cumplirse. El valor se levanta del application.properties 
	 * de la key "nitrogenous.base.pattern"
	 */	
	@Value( "${nitrogenous.base.pattern}" )
	private String ADNPATTERN;
	
	@Autowired
	AdnRepository adnRepository;
	
	
	@Override
	public List<Adn> getAllAdns() {
		List<Adn> adns = new ArrayList<Adn>();
		adnRepository.findAll().forEach(adn -> adns.add(adn));
        return adns;
	}
	
	@Override
	public Adn getAdnByChain(String[] chain) {
		return adnRepository.findById(chain).get();
	}

	@Override
	public void saveAdn(Adn adn) {
		adnRepository.save(adn);	
	}

	@Override
	/* isMutant: Metodo que retorna "true" si un Adn es Mutante, "false" caso contrario
	 * 
	 * @param dna es la matriz de ADNs
	 */
	public Boolean isMutant(String[] dna) {
		int cantLetras;
		int cantSecAdn = 0;
		for (int row = 0; row < dna.length; row++ ) {
			for (int col = 0; col < dna[row].length(); col++ ) {
				cantLetras = 1;
				if ((dna[row].length() - (col + 1)) >= DNAMUTANTBASE )
					cantSecAdn = (goOverRight(dna, row, col, cantLetras) >= DNAMUTANTBASE )? ++cantSecAdn : cantSecAdn;
				if ((dna[row].length() - (col + 1)) >= DNAMUTANTBASE && (dna.length - (row + 1)) >= DNAMUTANTBASE)
					cantSecAdn = (goOverCrosswiseRight(dna, row, col, cantLetras) >= DNAMUTANTBASE )? ++cantSecAdn : cantSecAdn;
				if ((dna.length - (row + 1)) >= DNAMUTANTBASE )
					cantSecAdn =(goOverDown(dna, row, col, cantLetras) >= DNAMUTANTBASE )? ++cantSecAdn : cantSecAdn;
			}
		}
		return (cantSecAdn > 1);
	}
	
	
	//Metodo que recorre la matrix de ADNs hacia la DERECHA buscando DNAMUTANTBASE letras iguales 
	public int goOverRight(String[] adnMatrix, int row, int col, int cantLetras) {
		if (adnMatrix[row].charAt(col) == adnMatrix[row].charAt(col + 1) && cantLetras < DNAMUTANTBASE && ((DNAMUTANTBASE - cantLetras) <= (adnMatrix[row].length() - (col + 1))))
			return (goOverRight(adnMatrix, row, col+1, ++cantLetras));
		else return cantLetras;
	}
	
	
	//Metodo que recorre la matrix de ADNs hacia ABAJO buscando DNAMUTANTBASE letras iguales 
	public int goOverDown(String[] adnMatrix, int row, int col, int cantLetras) {
		if (adnMatrix[row].charAt(col) == adnMatrix[row + 1].charAt(col) && cantLetras < DNAMUTANTBASE && ((DNAMUTANTBASE - cantLetras) <= (adnMatrix.length - (row + 1)))) {
			return (goOverDown(adnMatrix, row + 1, col, ++cantLetras));
		}
		else return cantLetras;
	}
	
	
	//Metodo que recorre la matrix de ADNs en DIAGONAL, ABAJO y DERECHA buscando DNAMUTANTBASE letras iguales 
	public int goOverCrosswiseRight(String[] adnMatrix, int row, int col, int cantLetras) {
		if (adnMatrix[row].charAt(col) == adnMatrix[row + 1].charAt(col + 1) && cantLetras < DNAMUTANTBASE && ((DNAMUTANTBASE - cantLetras) <= (adnMatrix[row].length() - (col + 1))))
			return goOverCrosswiseRight(adnMatrix, row + 1, col + 1, ++cantLetras);
		else return cantLetras;
	}
	
	//Metodo que imprime la matriz de Adns por consola
	public void printAdnMatrix(String[] matrix) {
		for (String adn : matrix) {
			System.out.print("| ");
	    	System.out.print(""+adn+" ");
			System.out.print("|");
			System.out.println();
		}
	}
	
	/* load: Metodo que carga los ADNs a partir de un List<String> y comprueba 
	 * que las cadenas de caracteres recibidas como ADN cumplan con 
	*/
	public String[] load(List<String> dnaChains) {
		int i = 0;
		Pattern p = Pattern.compile(ADNPATTERN);
		Matcher m;
		int nitrogenousBaseSize = dnaChains.get(0).length();
		if (dnaChains.size() != nitrogenousBaseSize)
			throw new AdnChainsSizeNoMatchException("La cantidad de cadenas ["+dnaChains.size()+"] NO es igual a la cantidad de Letras de una cadena :"+nitrogenousBaseSize);
		String[] adnMatrix = new String[dnaChains.size()];
		for (String chain : dnaChains) {
			m = p.matcher(chain);
			if (!m.find()){
				throw new AdnChainNoMatchException("La cadena ["+chain+"] NO cumple con las letras de la base Nitrogenada :"+ADNPATTERN.substring(ADNPATTERN.indexOf("["), ADNPATTERN.indexOf("]")));
			}
			adnMatrix[i++] = chain;
		}
		return adnMatrix;
	}
	
}
