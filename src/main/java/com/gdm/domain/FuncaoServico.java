package com.gdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class FuncaoServico extends GenericDomain {

	@Column(length = 100, nullable = false)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "FuncaoServico [descricao=" + descricao + "]";
	}

}
