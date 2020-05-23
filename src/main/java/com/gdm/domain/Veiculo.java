package com.gdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Veiculo extends GenericDomain {
	@Column(length = 200, nullable = false)
	private String nome;
	private String apelido;
	private Double capacidade;
	private Double capacidadePBT;
	private String classe;
	private int codigoDnit;
	private int qtdEixos;
	private Double g1;
	private Double g1PBT;
	private String tipoEixoG1;
	private Double g2;
	private Double g2PBT;
	private String tipoEixoG2;
	private Double g3;
	private Double g3PBT;
	private String tipoEixoG3;
	private Double g4;
	private Double g4PBT;
	private String tipoEixoG4;
	private Double g5;
	private Double g5PBT;
	private String tipoEixoG5;
	private Double g6;
	private Double g6PBT;
	private String tipoEixoG6;
	private Double g7;
	private Double g7PBT;
	private String tipoEixoG7;
	private Double toleranciaEixo;
	private Double toleranciaPbt;
	private Double comprimentoMinimo;
	private Double comprimentoMaximo;
	private Boolean certificadoAet;
	@Column(length = 8)
	private Boolean ativo;

	@Column(nullable = false, length = 10)
	private String codigoUnicoVeiculo;

	@ManyToOne
	@JoinColumn
	private CavaloCarroceria cavalo;

	@ManyToOne
	@JoinColumn
	private CavaloCarroceria carroceria1;

	@ManyToOne
	@JoinColumn
	private CavaloCarroceria carroceria2;

	@ManyToOne
	@JoinColumn
	private CavaloCarroceria carroceria3;

	// variavel temporaria
	@Transient
	private String caminho;
	@Transient
	private String caminhoCav;
	@Transient
	private String caminhoCarr1;

	@Transient
	private String caminhoCarr2;

	@Transient
	private String caminhoCarr3;
	
	@Transient
	public String getAtivoFormatado() {
		String ativoFormatado = "Inativo";
		if (ativo) {
			ativoFormatado = "Ativo";
		}
		return ativoFormatado;

	}
	

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public Double getToleranciaPbt() {
		return toleranciaPbt;
	}

	public void setToleranciaPbt(Double toleranciaPbt) {
		this.toleranciaPbt = toleranciaPbt;
	}

	public Double getToleranciaEixo() {
		return toleranciaEixo;
	}

	public void setToleranciaEixo(Double toleranciaEixo) {
		this.toleranciaEixo = toleranciaEixo;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Double capacidade) {
		this.capacidade = capacidade;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public int getCodigoDnit() {
		return codigoDnit;
	}

	public void setCodigoDnit(int codigoDnit) {
		this.codigoDnit = codigoDnit;
	}

	public int getQtdEixos() {
		return qtdEixos;
	}

	public void setQtdEixos(int qtdEixos) {
		this.qtdEixos = qtdEixos;
	}

	public Double getG1() {
		return g1;
	}

	public void setG1(Double g1) {
		this.g1 = g1;
	}

	public String getTipoEixoG1() {
		return tipoEixoG1;
	}

	public void setTipoEixoG1(String tipoEixoG1) {
		this.tipoEixoG1 = tipoEixoG1;
	}

	public Double getG2() {
		return g2;
	}

	public void setG2(Double g2) {
		this.g2 = g2;
	}

	public String getTipoEixoG2() {
		return tipoEixoG2;
	}

	public void setTipoEixoG2(String tipoEixoG2) {
		this.tipoEixoG2 = tipoEixoG2;
	}

	public Double getG3() {
		return g3;
	}

	public void setG3(Double g3) {
		this.g3 = g3;
	}

	public String getTipoEixoG3() {
		return tipoEixoG3;
	}

	public void setTipoEixoG3(String tipoEixoG3) {
		this.tipoEixoG3 = tipoEixoG3;
	}

	public Double getG4() {
		return g4;
	}

	public void setG4(Double g4) {
		this.g4 = g4;
	}

	public String getTipoEixoG4() {
		return tipoEixoG4;
	}

	public void setTipoEixoG4(String tipoEixoG4) {
		this.tipoEixoG4 = tipoEixoG4;
	}

	public Double getG5() {
		return g5;
	}

	public void setG5(Double g5) {
		this.g5 = g5;
	}

	public String getTipoEixoG5() {
		return tipoEixoG5;
	}

	public void setTipoEixoG5(String tipoEixoG5) {
		this.tipoEixoG5 = tipoEixoG5;
	}

	public Double getG6() {
		return g6;
	}

	public void setG6(Double g6) {
		this.g6 = g6;
	}

	public String getTipoEixoG6() {
		return tipoEixoG6;
	}

	public void setTipoEixoG6(String tipoEixoG6) {
		this.tipoEixoG6 = tipoEixoG6;
	}

	public Double getG7() {
		return g7;
	}

	public void setG7(Double g7) {
		this.g7 = g7;
	}

	public String getTipoEixoG7() {
		return tipoEixoG7;
	}

	public void setTipoEixoG7(String tipoEixoG7) {
		this.tipoEixoG7 = tipoEixoG7;
	}

	public Double getCapacidadePBT() {
		return capacidadePBT;
	}

	public void setCapacidadePBT(Double capacidadePBT) {
		this.capacidadePBT = capacidadePBT;
	}

	public Double getG1PBT() {
		return g1PBT;
	}

	public void setG1PBT(Double g1pbt) {
		g1PBT = g1pbt;
	}

	public Double getG2PBT() {
		return g2PBT;
	}

	public void setG2PBT(Double g2pbt) {
		g2PBT = g2pbt;
	}

	public Double getG3PBT() {
		return g3PBT;
	}

	public void setG3PBT(Double g3pbt) {
		g3PBT = g3pbt;
	}

	public Double getG4PBT() {
		return g4PBT;
	}

	public void setG4PBT(Double g4pbt) {
		g4PBT = g4pbt;
	}

	public Double getG5PBT() {
		return g5PBT;
	}

	public void setG5PBT(Double g5pbt) {
		g5PBT = g5pbt;
	}

	public Double getG6PBT() {
		return g6PBT;
	}

	public void setG6PBT(Double g6pbt) {
		g6PBT = g6pbt;
	}

	public Double getG7PBT() {
		return g7PBT;
	}

	public void setG7PBT(Double g7pbt) {
		g7PBT = g7pbt;
	}

	public Double getComprimentoMinimo() {
		return comprimentoMinimo;
	}

	public void setComprimentoMinimo(Double comprimentoMinimo) {
		this.comprimentoMinimo = comprimentoMinimo;
	}

	public Double getComprimentoMaximo() {
		return comprimentoMaximo;
	}

	public void setComprimentoMaximo(Double comprimentoMaximo) {
		this.comprimentoMaximo = comprimentoMaximo;
	}

	public Boolean getCertificadoAet() {
		return certificadoAet;
	}

	public void setCertificadoAet(Boolean certificadoAet) {
		this.certificadoAet = certificadoAet;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getCodigoUnicoVeiculo() {
		return codigoUnicoVeiculo;
	}

	public void setCodigoUnicoVeiculo(String codigoUnicoVeiculo) {
		this.codigoUnicoVeiculo = codigoUnicoVeiculo;
	}

	public CavaloCarroceria getCavalo() {
		return cavalo;
	}

	public void setCavalo(CavaloCarroceria cavalo) {
		this.cavalo = cavalo;
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

	@Override
	public String toString() {
		return "Veiculo [nome=" + nome + ", apelido=" + apelido + ", capacidade=" + capacidade + ", capacidadePBT="
				+ capacidadePBT + ", classe=" + classe + ", codigoDnit=" + codigoDnit + ", qtdEixos=" + qtdEixos
				+ ", g1=" + g1 + ", g1PBT=" + g1PBT + ", tipoEixoG1=" + tipoEixoG1 + ", g2=" + g2 + ", g2PBT=" + g2PBT
				+ ", tipoEixoG2=" + tipoEixoG2 + ", g3=" + g3 + ", g3PBT=" + g3PBT + ", tipoEixoG3=" + tipoEixoG3
				+ ", g4=" + g4 + ", g4PBT=" + g4PBT + ", tipoEixoG4=" + tipoEixoG4 + ", g5=" + g5 + ", g5PBT=" + g5PBT
				+ ", tipoEixoG5=" + tipoEixoG5 + ", g6=" + g6 + ", g6PBT=" + g6PBT + ", tipoEixoG6=" + tipoEixoG6
				+ ", g7=" + g7 + ", g7PBT=" + g7PBT + ", tipoEixoG7=" + tipoEixoG7 + ", toleranciaEixo="
				+ toleranciaEixo + ", toleranciaPbt=" + toleranciaPbt + ", comprimentoMinimo=" + comprimentoMinimo
				+ ", comprimentoMaximo=" + comprimentoMaximo + ", certificadoAet=" + certificadoAet + ", ativo=" + ativo
				+ ", codigoUnicoVeiculo=" + codigoUnicoVeiculo + ", cavalo=" + cavalo + ", carroceria1=" + carroceria1
				+ ", carroceria2=" + carroceria2 + ", carroceria3=" + carroceria3 + ", caminho=" + caminho + "]";
	}

	public String getCaminhoCav() {
		return caminhoCav;
	}

	public void setCaminhoCav(String caminhoCav) {
		this.caminhoCav = caminhoCav;
	}

	public String getCaminhoCarr1() {
		return caminhoCarr1;
	}

	public void setCaminhoCarr1(String caminhoCarr1) {
		this.caminhoCarr1 = caminhoCarr1;
	}

	public String getCaminhoCarr2() {
		return caminhoCarr2;
	}

	public void setCaminhoCarr2(String caminhoCarr2) {
		this.caminhoCarr2 = caminhoCarr2;
	}

	public String getCaminhoCarr3() {
		return caminhoCarr3;
	}

	public void setCaminhoCarr3(String caminhoCarr3) {
		this.caminhoCarr3 = caminhoCarr3;
	}

}
