package com.gdm.domain;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")

@Entity
public class Empresa extends GenericDomain {
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", nullable = false)
	private Date data_cadastro;
	@Column(length = 50)
	private String razaoSocial;
	@Column(length = 50)
	private String nomefantazia;

	@ManyToOne
	@JoinColumn
	private GrupoCNAE grupoCNAE;
	private ArrayList<String> plistClasseCnae;
	private String emailxml;

	@Column(length = 20, unique = true)
	private String cnpj;
	@Column(length = 15)
	private String inscricaoestadual;

	@Column(length = 100)
	private String rua;
	@Column()
	private int numero;
	@Column(length = 30)
	private String bairro;

	@ManyToOne
	@JoinColumn
	private Cidade cidade;
	@Column(length = 20)
	private String cep;
	@Column(length = 10)
	private String complemento;
	@Column(length = 13)
	private String telefone;
	@Column(length = 14)
	private String celular;
	@Column(length = 100)
	private String email;

	private String codigoIBGE;
	@Column(length = 20)
	private String longitude;
	@Column(length = 20)
	private String latitude;
	@Column(length = 20)
	private String pessoacontato;
	@ManyToOne
	@JoinColumn
	private FuncaoServico funcaoServico;

	@Column(length = 100)
	private String site;
	@Column(length = 10)
	private String regimeapuracao;
	@Column(length = 100)
	private String benificiosFiscais;
	
	private String tipoEmpresa;

	private Boolean situacao;
	@Column(length = 5)
	private String grau;

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getGrau() {
		return grau;
	}

	public void setGrau(String grau) {
		this.grau = grau;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomefantazia() {
		return nomefantazia;
	}

	public void setNomefantazia(String nomefantazia) {
		this.nomefantazia = nomefantazia;
	}

	public GrupoCNAE getGrupoCNAE() {
		return grupoCNAE;
	}

	public void setGrupoCNAE(GrupoCNAE grupoCNAE) {
		this.grupoCNAE = grupoCNAE;
	}

	public ArrayList<String> getPlistClasseCnae() {
		return plistClasseCnae;
	}

	public void setPlistClasseCnae(ArrayList<String> plistClasseCnae) {
		this.plistClasseCnae = plistClasseCnae;
	}

	public String getEmailxml() {
		return emailxml;
	}

	public void setEmailxml(String emailxml) {
		this.emailxml = emailxml;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoestadual() {
		return inscricaoestadual;
	}

	public void setInscricaoestadual(String inscricaoestadual) {
		this.inscricaoestadual = inscricaoestadual;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getPessoacontato() {
		return pessoacontato;
	}

	public void setPessoacontato(String pessoacontato) {
		this.pessoacontato = pessoacontato;
	}

	public FuncaoServico getFuncaoServico() {
		return funcaoServico;
	}

	public void setFuncaoServico(FuncaoServico funcaoServico) {
		this.funcaoServico = funcaoServico;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getRegimeapuracao() {
		return regimeapuracao;
	}

	public void setRegimeapuracao(String regimeapuracao) {
		this.regimeapuracao = regimeapuracao;
	}

	public String getBenificiosFiscais() {
		return benificiosFiscais;
	}

	public void setBenificiosFiscais(String benificiosFiscais) {
		this.benificiosFiscais = benificiosFiscais;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return nomefantazia + cnpj;
	}

}
