package com.gdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Cidade extends GenericDomain {
	@Override
	public String toString() {
		return  nome + " - "+ estado.getSigla();
	}

	@Column(length = 50, nullable = false)
	private String nome;

	private int CodMun;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado estado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getCodMun() {
		return CodMun;
	}

	public void setCodMun(int codMun) {
		CodMun = codMun;
	}
	
	
}
