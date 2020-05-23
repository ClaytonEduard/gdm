package com.gdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Vistoria extends GenericDomain {

	@Column(nullable = false)
	private String dataLancamento;

	public String getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	@ManyToOne
	@JoinColumn
	private Transportadora transportadora;

	@ManyToOne
	@JoinColumn
	private CavaloCarroceria cavalo;

	@Column(length = 20)
	private String imagem1;

	@Column(length = 20)
	private double comprimento;

	@ManyToOne
	@JoinColumn
	private CavaloCarroceria carroceria1;

	@Column(length = 20)
	private String imagem2;

	@ManyToOne
	@JoinColumn
	private CavaloCarroceria carroceria2;

	@Column(length = 20)
	private String imagem3;

	@ManyToOne
	@JoinColumn
	private CavaloCarroceria carroceria3;

	@Column(length = 20)
	private String imagem4;

	@Column(length = 5)
	private boolean combinacaoCorreta;

	@Column(length = 10)
	private String placaCavalo;

	@Column(length = 10)
	private String placaCarroceria1;

	@Column(length = 10)
	private String placaCarroceria2;

	@Column(length = 10)
	private String placaCarroceria3;

	@Column(length = 10, nullable = false)
	private double tara;

	@Column(length = 10, nullable = false)
	private double lotacao;

	@Column(length = 10, nullable = false)
	private double pbt;

	@Column
	private boolean semPlaqueta;

	@Column
	private boolean possuiTanqueSuplementar;

	@Column(length = 180)
	private String observacoes;

	@ManyToOne
	@JoinColumn
	private Veiculo veiculoResultadoCombinacao;

	@Column(length = 20)
	private String imagemCombinacaoGdm;

	@Column(length = 50, nullable = false)
	private String resultadoPlaqueta;

	@Column(length = 50, nullable = false)
	private String resultadoVistoriaSistema;

	@Column(length = 20, nullable = false)
	private String statusVistoria;

	@Column(length = 20)
	private String tipoVeiculo;

	// private Long usuario;
	@ManyToOne
	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public String getImagem1() {
		return imagem1;
	}

	public void setImagem1(String imagem1) {
		this.imagem1 = imagem1;
	}

	public String getImagem2() {
		return imagem2;
	}

	public void setImagem2(String imagem2) {
		this.imagem2 = imagem2;
	}

	public String getImagem3() {
		return imagem3;
	}

	public void setImagem3(String imagem3) {
		this.imagem3 = imagem3;
	}

	public String getImagem4() {
		return imagem4;
	}

	public void setImagem4(String imagem4) {
		this.imagem4 = imagem4;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public CavaloCarroceria getCavalo() {
		return cavalo;
	}

	public void setCavalo(CavaloCarroceria cavalo) {
		this.cavalo = cavalo;
	}

	public double getComprimento() {
		return comprimento;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public CavaloCarroceria getCarroceria1() {
		return carroceria1;
	}

	public void setCarroceria1(CavaloCarroceria carroceria1) {
		this.carroceria1 = carroceria1;
	}

	public CavaloCarroceria getCarroceria2() {
		return carroceria2;
	}

	public void setCarroceria2(CavaloCarroceria carroceria2) {
		this.carroceria2 = carroceria2;
	}

	public CavaloCarroceria getCarroceria3() {
		return carroceria3;
	}

	public void setCarroceria3(CavaloCarroceria carroceria3) {
		this.carroceria3 = carroceria3;
	}

	public boolean isCombinacaoCorreta() {
		return combinacaoCorreta;
	}

	public void setCombinacaoCorreta(boolean combinacaoCorreta) {
		this.combinacaoCorreta = combinacaoCorreta;
	}

	public String getPlacaCavalo() {
		return placaCavalo;
	}

	public void setPlacaCavalo(String placaCavalo) {
		this.placaCavalo = placaCavalo;
	}

	public String getPlacaCarroceria1() {
		return placaCarroceria1;
	}

	public void setPlacaCarroceria1(String placaCarroceria1) {
		this.placaCarroceria1 = placaCarroceria1;
	}

	public String getPlacaCarroceria2() {
		return placaCarroceria2;
	}

	public void setPlacaCarroceria2(String placaCarroceria2) {
		this.placaCarroceria2 = placaCarroceria2;
	}

	public String getPlacaCarroceria3() {
		return placaCarroceria3;
	}

	public void setPlacaCarroceria3(String placaCarroceria3) {
		this.placaCarroceria3 = placaCarroceria3;
	}

	public double getTara() {
		return tara;
	}

	public void setTara(double tara) {
		this.tara = tara;
	}

	public double getLotacao() {
		return lotacao;
	}

	public void setLotacao(double lotacao) {
		this.lotacao = lotacao;
	}

	public double getPbt() {
		return pbt;
	}

	public void setPbt(double pbt) {
		this.pbt = pbt;
	}

	public boolean isSemPlaqueta() {
		return semPlaqueta;
	}

	public void setSemPlaqueta(boolean semPlaqueta) {
		this.semPlaqueta = semPlaqueta;
	}

	public boolean isPossuiTanqueSuplementar() {
		return possuiTanqueSuplementar;
	}

	public void setPossuiTanqueSuplementar(boolean possuiTanqueSuplementar) {
		this.possuiTanqueSuplementar = possuiTanqueSuplementar;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Veiculo getVeiculoResultadoCombinacao() {
		return veiculoResultadoCombinacao;
	}

	public void setVeiculoResultadoCombinacao(Veiculo veiculoResultadoCombinacao) {
		this.veiculoResultadoCombinacao = veiculoResultadoCombinacao;
	}

	public String getImagemCombinacaoGdm() {
		return imagemCombinacaoGdm;
	}

	public void setImagemCombinacaoGdm(String imagemCombinacaoGdm) {
		this.imagemCombinacaoGdm = imagemCombinacaoGdm;
	}

	public String getResultadoPlaqueta() {
		return resultadoPlaqueta;
	}

	public void setResultadoPlaqueta(String resultadoPlaqueta) {
		this.resultadoPlaqueta = resultadoPlaqueta;
	}

	public String getResultadoVistoriaSistema() {
		return resultadoVistoriaSistema;
	}

	public void setResultadoVistoriaSistema(String resultadoVistoriaSistema) {
		this.resultadoVistoriaSistema = resultadoVistoriaSistema;
	}

	public String getStatusVistoria() {
		return statusVistoria;
	}

	public void setStatusVistoria(String statusVistoria) {
		this.statusVistoria = statusVistoria;
	}

	public String getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(String tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

}
