package com.gdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class CavaloCarroceria extends GenericDomain {

	// ---------------OBTER NOVO VALOR AO ALTERAR A SELECAO--------

	@Column(length = 8, nullable = false)
	private double capacidade;

	public TipoConjunto getTipoConjunto() {
		return tipoConjunto;
	}

	public void setTipoConjunto(TipoConjunto tipoConjunto) {
		this.tipoConjunto = tipoConjunto;
	}

	@Column(length = 100, nullable = false)
	private String descricao;

	@Column(length = 5)
	private Long imagem;

	@Column(length = 1)
	private int quantidadeEixos;

	@ManyToOne
	@JoinColumn
	private TipoConjunto tipoConjunto;

	public Long getImagem() {
		return imagem;
	}

	public void setImagem(Long imagem) {
		this.imagem = imagem;
	}

	public double getCapacidade() {
		return capacidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getQuantidadeEixos() {
		return quantidadeEixos;
	}

	public void setCapacidade(double capacidade) {
		this.capacidade = capacidade;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setQuantidadeEixos(int quantidadeEixos) {
		this.quantidadeEixos = quantidadeEixos;
	}

}
