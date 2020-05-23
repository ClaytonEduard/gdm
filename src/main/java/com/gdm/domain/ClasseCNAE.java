package com.gdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class ClasseCNAE extends GenericDomain {

	@Column(length = 10, nullable = false)
	private String classe;
	@Column(length = 100, nullable = false)
	private String denominacao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private GrupoCNAE grupoCNAE;

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getDenominacao() {
		return denominacao;
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}

	public GrupoCNAE getGrupoCNAE() {
		return grupoCNAE;
	}

	public void setGrupoCNAE(GrupoCNAE grupoCNAE) {
		this.grupoCNAE = grupoCNAE;
	}

	@Override
	public String toString() {
		return classe + "-" + denominacao;
	}

}
