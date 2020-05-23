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
public class Multa extends GenericDomain {
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataLancamento; // ok
	// private int condicao;
	private int tipoLancamento; // ok
	private int tipoDocumento; // ok
	@ManyToOne
	@JoinColumn
	private Orgao orgaoEmissor; // ok
	private Date dataOcorencia; // ok
	private Double valorComdesconto; // ok
	private Double valorTotal; // ok
	private Date dataVencimentoMulta;
	private String placaCavalo; // ok
	private String placa1; // ok
	private String placa2; // ok
	private String placa3; // ok
	private Double pesoAferidoPbt; // ok
	private Double limiteRegulamentarPBT; // ok
	private Double excessoPbt; // ok
	@Column(unique = true)
	private String autoInfracao; // ok
	private String rodovia; // ok
	private String km; // ok
	private String observacao; // ok
	@ManyToOne
	@JoinColumn
	private Veiculo veiculo; // ok
	private Double G1Multa;
	private Double G1;
	private Double G1Diferenca;
	private String mensagemG1;
	private Double G2Multa;
	private Double G2;
	private Double G2Diferenca;
	private String mensagemG2;
	private Double G3Multa;
	private Double G3;
	private Double G3Diferenca;
	private String mensagemG3;
	private Double G4Multa;
	private Double G4;
	private Double G4Diferenca;
	private String mensagemG4;
	private Double G5Multa;
	private Double G5;
	private Double G5Diferenca;
	private String mensagemG5;
	private Double G6Multa;
	private Double G6;
	private Double G6Diferenca;
	private String mensagemG6;
	private Double G7Multa;
	private Double G7;
	private Double G7Diferenca;
	private String mensagemG7;
	private String docTransporte;
	private String frete;
	private String mensagemPBT;
	private Double excessoDiverso;
	private int statusMultaF;
	@ManyToOne
	@JoinColumn
	private Produto produto;
	
	private String cidade;
	@ManyToOne
	@JoinColumn
	private Empresa empresa;

	@Transient
	public String getTipoDocumentoFormatado() {
		String documentoFormatado = "";
		if (tipoDocumento == 1) {
			documentoFormatado = "Multa";
		} else {
			documentoFormatado = "Notificação";
		}

		return documentoFormatado;
	}

	public String getTipoLancamentoFormatado() {
		String tipoLacamento = "";
		if (tipoLancamento == 1) {
			tipoLacamento = "Balança";
		} else {
			tipoLacamento = "Nota Fiscal";
		}
		return tipoLacamento;
	}

	// verificar a questao do transportador da origem

	public Date getDataVencimentoMulta() {
		return dataVencimentoMulta;
	}

	public void setDataVencimentoMulta(Date dataVencimentoMulta) {
		this.dataVencimentoMulta = dataVencimentoMulta;
	}

	public Double getG1() {
		return G1;
	}

	public void setG1(Double g1) {
		G1 = g1;
	}

	public Double getG2() {
		return G2;
	}

	public void setG2(Double g2) {
		G2 = g2;
	}

	public Double getG3() {
		return G3;
	}

	public void setG3(Double g3) {
		G3 = g3;
	}

	public Double getG4() {
		return G4;
	}

	public void setG4(Double g4) {
		G4 = g4;
	}

	public Double getG5() {
		return G5;
	}

	public void setG5(Double g5) {
		G5 = g5;
	}

	public Double getG6() {
		return G6;
	}

	public void setG6(Double g6) {
		G6 = g6;
	}

	public Double getG7() {
		return G7;
	}

	public void setG7(Double g7) {
		G7 = g7;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public int getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(int tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public int getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getDataOcorencia() {
		return dataOcorencia;
	}

	public void setDataOcorencia(Date dataOcorencia) {
		this.dataOcorencia = dataOcorencia;
	}

	public Double getValorComdesconto() {
		return valorComdesconto;
	}

	public void setValorComdesconto(Double valorComdesconto) {
		this.valorComdesconto = valorComdesconto;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getPlacaCavalo() {
		return placaCavalo;
	}

	public void setPlacaCavalo(String placaCavalo) {
		this.placaCavalo = placaCavalo;
	}

	public String getPlaca1() {
		return placa1;
	}

	public void setPlaca1(String placa1) {
		this.placa1 = placa1;
	}

	public String getPlaca2() {
		return placa2;
	}

	public void setPlaca2(String placa2) {
		this.placa2 = placa2;
	}

	public String getPlaca3() {
		return placa3;
	}

	public void setPlaca3(String placa3) {
		this.placa3 = placa3;
	}

	public Double getPesoAferidoPbt() {
		return pesoAferidoPbt;
	}

	public void setPesoAferidoPbt(Double pesoAferidoPbt) {
		this.pesoAferidoPbt = pesoAferidoPbt;
	}

	public Double getLimiteRegulamentarPBT() {
		return limiteRegulamentarPBT;
	}

	public void setLimiteRegulamentarPBT(Double limiteRegulamentarPBT) {
		this.limiteRegulamentarPBT = limiteRegulamentarPBT;
	}

	public Double getExcessoPbt() {
		return excessoPbt;
	}

	public void setExcessoPbt(Double excessoPbt) {
		this.excessoPbt = excessoPbt;
	}

	public String getAutoInfracao() {
		return autoInfracao;
	}

	public void setAutoInfracao(String autoInfracao) {
		this.autoInfracao = autoInfracao;
	}

	public String getRodovia() {
		return rodovia;
	}

	public void setRodovia(String rodovia) {
		this.rodovia = rodovia;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}




	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Orgao getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(Orgao orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public Double getG1Multa() {
		return G1Multa;
	}

	public void setG1Multa(Double g1Multa) {
		G1Multa = g1Multa;
	}

	public Double getG1Diferenca() {
		return G1Diferenca;
	}

	public void setG1Diferenca(Double g1Diferenca) {
		G1Diferenca = g1Diferenca;
	}

	public Double getG2Multa() {
		return G2Multa;
	}

	public void setG2Multa(Double g2Multa) {
		G2Multa = g2Multa;
	}

	public Double getG2Diferenca() {
		return G2Diferenca;
	}

	public void setG2Diferenca(Double g2Diferenca) {
		G2Diferenca = g2Diferenca;
	}

	public Double getG3Multa() {
		return G3Multa;
	}

	public void setG3Multa(Double g3Multa) {
		G3Multa = g3Multa;
	}

	public Double getG3Diferenca() {
		return G3Diferenca;
	}

	public void setG3Diferenca(Double g3Diferenca) {
		G3Diferenca = g3Diferenca;
	}

	public Double getG4Multa() {
		return G4Multa;
	}

	public void setG4Multa(Double g4Multa) {
		G4Multa = g4Multa;
	}

	public Double getG4Diferenca() {
		return G4Diferenca;
	}

	public void setG4Diferenca(Double g4Diferenca) {
		G4Diferenca = g4Diferenca;
	}

	public Double getG5Multa() {
		return G5Multa;
	}

	public void setG5Multa(Double g5Multa) {
		G5Multa = g5Multa;
	}

	public Double getG5Diferenca() {
		return G5Diferenca;
	}

	public void setG5Diferenca(Double g5Diferenca) {
		G5Diferenca = g5Diferenca;
	}

	public Double getG6Multa() {
		return G6Multa;
	}

	public void setG6Multa(Double g6Multa) {
		G6Multa = g6Multa;
	}

	public Double getG6Diferenca() {
		return G6Diferenca;
	}

	public void setG6Diferenca(Double g6Diferenca) {
		G6Diferenca = g6Diferenca;
	}

	public Double getG7Multa() {
		return G7Multa;
	}

	public void setG7Multa(Double g7Multa) {
		G7Multa = g7Multa;
	}

	public Double getG7Diferenca() {
		return G7Diferenca;
	}

	public void setG7Diferenca(Double g7Diferenca) {
		G7Diferenca = g7Diferenca;
	}

	public String getDocTransporte() {
		return docTransporte;
	}

	public void setDocTransporte(String docTransporte) {
		this.docTransporte = docTransporte;
	}

	public String getFrete() {
		return frete;
	}

	public void setFrete(String frete) {
		this.frete = frete;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getMensagemG1() {
		return mensagemG1;
	}

	public void setMensagemG1(String mensagemG1) {
		this.mensagemG1 = mensagemG1;
	}

	public String getMensagemG2() {
		return mensagemG2;
	}

	public void setMensagemG2(String mensagemG2) {
		this.mensagemG2 = mensagemG2;
	}

	public String getMensagemG3() {
		return mensagemG3;
	}

	public void setMensagemG3(String mensagemG3) {
		this.mensagemG3 = mensagemG3;
	}

	public String getMensagemG4() {
		return mensagemG4;
	}

	public void setMensagemG4(String mensagemG4) {
		this.mensagemG4 = mensagemG4;
	}

	public String getMensagemG5() {
		return mensagemG5;
	}

	public void setMensagemG5(String mensagemG5) {
		this.mensagemG5 = mensagemG5;
	}

	public String getMensagemG6() {
		return mensagemG6;
	}

	public void setMensagemG6(String mensagemG6) {
		this.mensagemG6 = mensagemG6;
	}

	public String getMensagemG7() {
		return mensagemG7;
	}

	public void setMensagemG7(String mensagemG7) {
		this.mensagemG7 = mensagemG7;
	}

	public String getMensagemPBT() {
		return mensagemPBT;
	}

	public void setMensagemPBT(String mensagemPBT) {
		this.mensagemPBT = mensagemPBT;
	}

	@Override
	public String toString() {
		return "Multa [dataLancamento=" + dataLancamento + ", tipoLancamento=" + tipoLancamento + ", tipoDocumento="
				+ tipoDocumento + ", orgaoEmissor=" + orgaoEmissor + ", dataOcorencia=" + dataOcorencia
				+ ", valorComdesconto=" + valorComdesconto + ", valorTotal=" + valorTotal + ", dataVencimentoMulta="
				+ dataVencimentoMulta + ", placaCavalo=" + placaCavalo + ", placa1=" + placa1 + ", placa2=" + placa2
				+ ", placa3=" + placa3 + ", pesoAferidoPbt=" + pesoAferidoPbt + ", limiteRegulamentarPBT="
				+ limiteRegulamentarPBT + ", excessoPbt=" + excessoPbt + ", autoInfracao=" + autoInfracao + ", rodovia="
				+ rodovia + ", km=" + km + ", cidade=" + cidade + ", observacao=" + observacao + ", veiculo=" + veiculo
				+ ", G1Multa=" + G1Multa + ", G1=" + G1 + ", G1Diferenca=" + G1Diferenca + ", mensagemG1=" + mensagemG1
				+ ", G2Multa=" + G2Multa + ", G2=" + G2 + ", G2Diferenca=" + G2Diferenca + ", mensagemG2=" + mensagemG2
				+ ", G3Multa=" + G3Multa + ", G3=" + G3 + ", G3Diferenca=" + G3Diferenca + ", mensagemG3=" + mensagemG3
				+ ", G4Multa=" + G4Multa + ", G4=" + G4 + ", G4Diferenca=" + G4Diferenca + ", mensagemG4=" + mensagemG4
				+ ", G5Multa=" + G5Multa + ", G5=" + G5 + ", G5Diferenca=" + G5Diferenca + ", mensagemG5=" + mensagemG5
				+ ", G6Multa=" + G6Multa + ", G6=" + G6 + ", G6Diferenca=" + G6Diferenca + ", mensagemG6=" + mensagemG6
				+ ", G7Multa=" + G7Multa + ", G7=" + G7 + ", G7Diferenca=" + G7Diferenca + ", mensagemG7=" + mensagemG7
				+ ", docTransporte=" + docTransporte + ", frete=" + frete + ", mensagemPBT=" + mensagemPBT
				+ ", excessoDiverso=" + excessoDiverso + ", statusMultaF=" + statusMultaF + ", produto=" + produto
				+ ", empresa=" + empresa + "]";
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Double getExcessoDiverso() {
		return excessoDiverso;
	}

	public void setExcessoDiverso(Double excessoDiverso) {
		this.excessoDiverso = excessoDiverso;
	}

	public int getStatusMultaF() {
		return statusMultaF;
	}

	public void setStatusMultaF(int statusMultaF) {
		this.statusMultaF = statusMultaF;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
}
