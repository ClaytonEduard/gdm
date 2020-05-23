package com.gdm.domain;

import java.util.Date;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")

@Entity
public class Pessoa extends GenericDomain {
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", nullable = false)
	private Date data_cadastro;

	@Column(length = 50)
	private String nome;
	@Column(length = 20, unique = true)
	private String cpf;
	@Column(length = 15)
	private String rg;

	@Column(length = 100)
	private String rua;
	@Column()
	private Short numero;
	@Column(length = 30)
	private String bairro;
	@Column(length = 10)
	private String cep;
	@Column(length = 50)
	private String complemento;
	@ManyToOne
	@JoinColumn
	private Cidade cidade;
	@Column(length = 13)
	private String telefone;
	@Column(length = 14)
	private String celular;
	@Column(length = 100)
	private String email;

	@ManyToOne
	@JoinColumn
	private FuncaoServico funcaoServico;
	private Boolean situacao;

	@Transient
	public String getSituacaoFormatado() {
		String situacaoFormatado = "Não";
		if (situacao) {
			situacaoFormatado = "Sim";
		}
		return situacaoFormatado;

	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public Short getNumero() {
		return numero;
	}

	public void setNumero(Short numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public FuncaoServico getFuncaoServico() {
		return funcaoServico;
	}

	public void setFuncaoServico(FuncaoServico funcaoServico) {
		this.funcaoServico = funcaoServico;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

}
