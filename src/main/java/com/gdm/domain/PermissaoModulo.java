package com.gdm.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.gdm.enumeracao.Modulos;

@SuppressWarnings("serial")
@Entity
public class PermissaoModulo extends GenericDomain {

	@Enumerated(EnumType.STRING)
	private Modulos modulodescricao;
	@ManyToOne
	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Modulos getModulodescricao() {
		return modulodescricao;
	}

	public void setModulodescricao(Modulos modulodescricao) {
		this.modulodescricao = modulodescricao;
	}



}
