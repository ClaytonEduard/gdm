package com.gdm.domain;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Tolerancia extends GenericDomain {

	private String descricao;
	private Double numero;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getNumero() {
		return numero;
	}

	public void setNumero(Double numero) {
		this.numero = numero;
	}

}
