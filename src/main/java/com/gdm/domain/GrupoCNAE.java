package com.gdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class GrupoCNAE extends GenericDomain {

	@Column(length = 6, nullable = false)
	private String grupo;
	@Column(length = 100, nullable = false)
	private String denominacao;
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getDenominacao() {
		return denominacao;
	}
	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}

	

}
