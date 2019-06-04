package com.magneto.app.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "ADN")
public class Adn implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private String[] adn;
	
	public Adn() {
		super();
	}
	
	public Adn(String[] adn) {
		super();
		this.adn=adn;
	}

	/**
     * @return the Adn
     */
    @Id
    @Column(name="ADN")
	public String[] getAdn() {
		return adn;
	}

	public void setAdn(String[] adn) {
		this.adn = adn;
	}

}
