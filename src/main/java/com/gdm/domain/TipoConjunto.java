package com.gdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class TipoConjunto extends GenericDomain {
	@Column(nullable = false, length = 40)
	private String TipoConjunto;

	public String getTipoConjunto() {
		return TipoConjunto;
	}

	public void setTipoConjunto(String tipoConjunto) {
		TipoConjunto = tipoConjunto;
	}

}
