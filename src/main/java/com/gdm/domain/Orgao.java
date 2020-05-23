package com.gdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Orgao extends GenericDomain {

	@Column(length = 100, nullable = false)
	private String nome;
	@Column(length = 200, nullable = false)
	private String descricao;
	@Column(nullable = false)
	private Character atuacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setAtuacao(Character atuacao) {
		this.atuacao = atuacao;
	}

	public Character getAtuacao() {
		return atuacao;
	}

	@Transient
	public String getTipoFormatadoOrgao() {
		String atuacaoFormatado = null;

		if (atuacao == 'E') {
			atuacaoFormatado = "ESTADUAL";
		} else if (atuacao == 'N') {
			atuacaoFormatado = "NACIONAL";
		}

		return atuacaoFormatado;
	}

	@Override
	public String toString() {
		return "Orgao [nome=" + nome + ", descricao=" + descricao + ", atuacao=" + atuacao + "]";
	}

}
