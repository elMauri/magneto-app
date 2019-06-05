package com.magneto.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.magneto.app.domain.Adn;
import com.magneto.app.service.AdnService;
import com.magneto.app.service.StatsService;

/**
* MangentoController class se encarga de la gestion de los
* endpoint expuestos como asi tambien su procesamiento, ademas
* de persistir la informacion y recabar estadisticas. 
*
* @author  Mauricio Borelli
* @version 1.0
* @since   2019-06-01 
*/

@RestController
@RequestMapping("/magnetoapi")
public class MagnetoController {
	
	@Autowired
	AdnService adnService;
	
	public MagnetoController(AdnService service) {
		this.adnService = service;
	}
	
	/* read: Metodo que lee el Json array recibido por payload via Post y retorna http code 200 en caso
	 * de existir algun adn mutante y DNAMUTANTBASE03 forbiden en caso de que no
	 * 
	 * @param dna es un Map donde la key es el string que representa al elemento "dna" del json array,
	 * mientras que el value es el Array de String con las cadenas de ADN a verificar
	*/
	@RequestMapping(method=RequestMethod.POST, value="/mutant" )
	public ResponseEntity<Object> read(@RequestBody Map<String,List<String>> dna) {
		List<String> dnaChains = dna.get("dna");
		String[] adnMatrix = adnService.load(dnaChains);
		Adn adn = new Adn(adnMatrix);
		adnService.saveAdn(adn);
		//adnService.printAdnMatrix(adnMatrix);
		return (adnService.isMutant(adnMatrix)) ? new ResponseEntity<>(HttpStatus.OK): new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
	}
	
	
	/*
	 * getStats: Metodo que retorna las estadisiticas acerca de la cantidad de ANDs
	 * humanos, mutantes y la relacion que hay entre ellos 
	 */
	@RequestMapping(method=RequestMethod.GET, produces=("application/json;charset=UTF-8"),value="/stats" )
	public ResponseEntity<Object> getStats() {
		Long countM = (long) 0;
		Long countH = (long) 0;
		Float ratio;
		for (Adn adn : adnService.getAllAdns()) {
			if (adnService.isMutant(adn.getAdn()))
				++countM;
			else
				++countH;
		}
		try {
			//WA para evitar que la jvm de gcloud retorn "Infinity"
			ratio= (countM == 0)? 0: (float) countM/(countM+countH);
			StatsService statsS = new StatsService(countM, countH, ratio);
			return new ResponseEntity<>(statsS, HttpStatus.OK);				
		}catch (ArithmeticException e){
			ratio = (float) 0;
			StatsService statsS = new StatsService(countM, countH, ratio);
			return new ResponseEntity<>(statsS, HttpStatus.OK);
		}
	}
}