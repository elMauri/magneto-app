package com.magneto.app.service;

/**
* Stats class se utiliza para el retorno de las
* estadisticas de los Adns de humanos y mutantes 
* y su relacion entre ellos
*  
*
* @author  Mauricio Borelli
* @version 1.0
* @since   2019-06-01 
*/


public class StatsService {
	
	private Long count_mutant_dna;
	
	private Long count_human_dna;
	
	private Float ratio;
	
	
	public StatsService(Long count_mutant_dna,Long count_human_dna, Float ratio) {
		super();
		this.count_mutant_dna=count_mutant_dna;
		this.count_human_dna=count_human_dna;
		this.ratio=ratio;
	}
	
	public Long getCount_mutant_dna() {
		return count_mutant_dna;
	}

	public void setCount_mutant_dna(Long count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}

	public Long getCount_human_dna() {
		return count_human_dna;
	}

	public void setCount_human_dna(Long count_human_dna) {
		this.count_human_dna = count_human_dna;
	}

	public Float getRatio() {
		return ratio;
	}

	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}
	
}
