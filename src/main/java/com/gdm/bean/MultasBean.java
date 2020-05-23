package com.gdm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import com.gdm.dao.CidadeDAO;
import com.gdm.dao.MultaDAO;
import com.gdm.dao.OrgaoDAO;
import com.gdm.dao.VeiculoDAO;
import com.gdm.dao.VistoriaDAO;
import com.gdm.domain.Cidade;
import com.gdm.domain.Multa;
import com.gdm.domain.Orgao;
import com.gdm.domain.Produto;
import com.gdm.domain.Usuario;
import com.gdm.domain.Veiculo;
import com.gdm.domain.Vistoria;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MultasBean implements Serializable {
	private Multa multa;
	private List<String> cidades;
	private List<Cidade> cidadetest;
	private List<Cidade> cidadess;
	private List<Orgao> orgaos;
	private List<Veiculo> veiculos;
	private List<Multa> multas;
	private List<Produto> produtos;
	private List<Multa> placas;
	private Vistoria vistoriaVeiculoPlaca;
	private List<String> imagens = new ArrayList<>();
	private List<String> imagens2 = new ArrayList<>();
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataLancamento; // é usada

	private Double pesoAfer; // pega o peso informado na multa
	private Double exessoPBT; // pega o excessoPBT
	private Double g1 = null;
	private Double g1aux;
	private Double g1total;

	private Double g2 = null;
	private Double g2aux;
	private Double g2total;

	private Double g3 = null;
	private Double g3aux = null;
	private Double g3total = null;

	private Double g4 = null;
	private Double g4aux = null;
	private Double g4total = null;

	private Double g5 = null;
	private Double g5aux = null;
	private Double g5total = null;

	private Double g6 = null;
	private Double g6aux = null;
	private Double g6total = null;

	private Double g7 = null;
	private Double g7aux = null;
	private Double g7total = null;

	private Double limitepbt;

	private List<Double> porcentagem;
	private Double percentual = 5.0 / 100.0;

	private String mensagemG1;
	private String mensagemG2;
	private String mensagemG3;
	private String mensagemG4;
	private String mensagemG5;
	private String mensagemG6;
	private String mensagemG7;
	// exibir campos pesos
	private boolean exibir = true;
	// exibir campos de observacao
	private boolean exibir2 = false;
	private String observacao;
	private String mensagemOrgao;

	private String pesqPlaca;

	private boolean autodeInfr_requerido = true;
	private boolean orgao_requerido = true;
	private boolean dataOcorencia_requerido = true;
	private boolean placacavalo_requerido = true;
	private boolean pesoaferidopbt_requerido = true;
	private boolean limiteregulament_requerido = true;
	private boolean valortotal_requerido = true;
	private boolean datavencMulta_requerido = true;
	private boolean salvarDiferenca = false;

	private String buscaCidade;
	private Cidade cidadeSelecionada;

	public MultasBean() {
		cidadeSelecionada = new Cidade();
		cidades = new LinkedList<>();
		cidadetest = new LinkedList<>();
	}

	public void geraImagem() {
		imagens.clear();
		imagens2.clear();

		try {

			long i = 0;
			for (i = 0; i <= 31; i++) {
				imagens.add(i + ".png");
			}
			imagens2.add("0.png");
			if (multa.getVeiculo() == null) {
				imagens2.add("0.png");
				// limpaCampos();
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os valores");
			erro.printStackTrace();
		}
		listar();

	}

	public void calcular() {
		try {
			// limpa o vetor de imagens

			// 1 DNIT Por balanca
			// 2 DER-SP Por balanca
			// 3 ANTT Por balanca
			// 4 DER-PR por nota
			// 5 DER-MG por nota
			// 6 DPRF por nota
			// 7 SRPRF-MG Por balanca
			// 8 ANTT - Antigo Por nota

			// por balanças = 1
			if (vistoriaVeiculoPlaca != null) {
				imagens2 = new ArrayList<>();
				imagens2.add(vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getCodigo() + ".png");
				limitepbt = vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getCapacidadePBT();
				// peso do caminhao + 5 %
				multa.setLimiteRegulamentarPBT(limitepbt);
				if ((multa.getPesoAferidoPbt() - limitepbt) < 1) {
					multa.setExcessoPbt((double) 0);
					multa.setMensagemPBT("");
				} else if ((multa.getPesoAferidoPbt() - limitepbt) > 0) {
					multa.setExcessoPbt(multa.getPesoAferidoPbt() - limitepbt);
					// System.out.println("Excesso Peso Bruto Total");
					// Messages.addGlobalInfo("Excesso PBT");
					multa.setMensagemPBT("Excesso Peso Bruto Total");
				}
			} else {
				if (multa.getTipoLancamento() == 1) {
					if (multa.getVeiculo().getCodigo() != null) {
						limpaCamposCaminhao();
					}
					// exibir = true; // para ativar os campos de exibiC'C#o
					if (multa.getVeiculo().getCodigo() == null) {
						// limpaCampos();
					}
					for (Veiculo veiculo : veiculos) {
						if (veiculo.getCodigo() == multa.getVeiculo().getCodigo()) {
							imagens2 = new ArrayList<>();
							imagens2.add(veiculo.getCodigo() + ".png");

							multa.setG1(veiculo.getG1PBT());
							multa.setG2(veiculo.getG2PBT());
							multa.setG3(veiculo.getG3PBT());
							multa.setG4(veiculo.getG4PBT());
							multa.setG5(veiculo.getG5PBT());
							multa.setG6(veiculo.getG6PBT());
							multa.setG7(veiculo.getG7PBT());

							limitepbt = veiculo.getCapacidadePBT();
							// peso do caminhao + 5 %
							// System.out.println("peso aferido: " +
							// multa.getPesoAferidoPbt());

							multa.setLimiteRegulamentarPBT(limitepbt);
							if ((multa.getPesoAferidoPbt() - limitepbt) < 1) {
								multa.setExcessoPbt((double) 0);
								multa.setMensagemPBT("");
							} else if ((multa.getPesoAferidoPbt() - limitepbt) > 0) {
								multa.setExcessoPbt(multa.getPesoAferidoPbt() - limitepbt);
								// System.out.println("Excesso Peso Bruto
								// Total");
								// Messages.addGlobalInfo("Excesso PBT");
								multa.setMensagemPBT("Excesso Peso Bruto Total");
							}

							// limpa os campos valores da multa e mensagens

							break;
						}
						// limpaCampos();
						// }
					}

					// System.out.println("Auxiliar combo: " +
					// multa.getVeiculo().getCodigo());
					// System.out.println("LimitePbt: " + limitepbt);
					// System.out.println("excesso pbt: " + exessoPBT);
				} else {
					// exibir = false;
					// limpaCamposCaminhao(); // limpa os campos
					for (Veiculo veiculo : veiculos) {

						if (veiculo.getCodigo() == multa.getVeiculo().getCodigo()) {
							// vetor de imagens recebe o codigo do veiculo + a
							// string .png para mostrar a imagem no vetor
							imagens2 = new ArrayList<>();
							imagens2.add(veiculo.getCodigo() + ".png");
							limitepbt = veiculo.getCapacidade();
							// peso do caminhao - 5 %

							multa.setLimiteRegulamentarPBT(limitepbt);
							if ((multa.getPesoAferidoPbt() - limitepbt) < 1) {
								multa.setExcessoPbt((double) 0);
								multa.setMensagemPBT("");
							} else if ((multa.getPesoAferidoPbt() - limitepbt) > 0) {
								multa.setExcessoPbt(multa.getPesoAferidoPbt() - limitepbt);
								// System.out.println("Excesso Peso Bruto
								// Total");
								observacao = "Excesso Peso Bruto Total : " + multa.getExcessoPbt();
								// Messages.addGlobalInfo(observacao);
								multa.setMensagemPBT("Excesso Peso Bruto Total");

							}
							break;

						}
						// observacao = null;
					}
					// System.out.println("Auxiliar combo: " +
					// multa.getVeiculo().getCodigo());
					// System.out.println("LimitePbt: " + limitepbt);
					// System.out.println("Excesso PBT : " +
					// multa.getExcessoPbt());
				}

			}

		} catch (

		RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os valores", erro);
			erro.printStackTrace();
		}
	}

	public void verificaOrgao() {
		if (multa.getOrgaoEmissor().getCodigo() == 2) {
			mensagemOrgao = "Favor somar o excesso do eixo indicado no auto com o peso do eixo da coluna 'Limite + tolerância' indicado no sistema e inserir essa soma no eixo da coluna 'Medição realizada'";
			Messages.addGlobalInfo(mensagemOrgao);
			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage(mensagemOrgao));
		} else {
			mensagemOrgao = null;
		}
		// 4 DER-PR por nota
		// 5 DER-MG por nota
		// 6 DPRF por nota
		// 7 SRPRF-MG Por balanca statica
		// if (multa.getOrgaoEmissor().getCodigo() >= 4 &&
		// multa.getOrgaoEmissor().getCodigo() <= 8) {
		// exibir = false;
		// exibir2 = true;
		// } else {
		// exibir = true;
		// exibir2 = false;
		// }

	}

	public void layoutTipoDocumento() {

		if (multa.getTipoDocumento() == 2 || multa.getTipoDocumento() == 4) {
			exibir = false;
			exibir2 = true;
		} else {
			exibir = true;
			exibir2 = false;
		}
	}

	public void capturaPesosG1() {

		if (multa.getG1() != null) {
			multa.setG1Diferenca(0.0);
			multa.setG1Diferenca(multa.getG1Multa() - multa.getG1());
			// System.out.println("G1 aux " + multa.getG1Multa());
			// System.out.println("G1 " + multa.getG1());
			// System.out.println("G1 Total " + multa.getG1Diferenca());

			// Se tipolancamento e igual = 1 = "BalanC'a" e 2 = "Nota Fiscal" E
			if (multa.getTipoLancamento() == 1) {

				if (multa.getG1Diferenca() <= 0) {
					multa.setMensagemG1("");
					// Messages.addGlobalInfo(multa.getMensagemG1());
				}

				if (multa.getG1Diferenca() <= 50 && multa.getG1Diferenca() > 0) {
					multa.setMensagemG1("FREADA NA BALANÇA");
					// Messages.addGlobalInfo(multa.getMensagemG1());
				}

				if (multa.getG1Diferenca() > 50) {
					multa.setMensagemG1("TANQUE SUPLEMENTAR OU CARGA DIANTEIRA");
					// Messages.addGlobalInfo(multa.getMensagemG1());

					switch (limitepbt.toString()) {
					case "16800.0":
					case "24150.0":
					case "30450.0":
						// case "57225.0": esta no if de fora solicitado pelo
						// Adriano dia 08/2
						multa.setMensagemG1("CARGA DIANTEIRA");
						// Messages.addGlobalInfo(multa.getMensagemG1());
						break;

					}
				}
			}
			// fim do IF de Tipo Lancamento

		} else

		{
			multa.setMensagemG1("Nenhum peso correpondente");
			// Messages.addGlobalInfo(multa.getMensagemG1());
		}

	}

	public void capturaPesosG2() {
		if (multa.getG2() != null) {
			multa.setG2Diferenca(0.0);
			multa.setG2Diferenca(multa.getG2Multa() - multa.getG2());
			// System.out.println("G2 aux " + multa.getG2Multa());
			// System.out.println("G2 " + multa.getG2());
			// System.out.println("G2 Total " + multa.getG2Diferenca());

			// Se tipolancamento e igual = 1 = "BalanC'a" e 2 = "Nota Fiscal" E
			// limitePBT do veiculo igual =
			if (multa.getTipoLancamento() == 1) {

				if (multa.getG2Diferenca() <= 0) {
					multa.setMensagemG2("");
					// Messages.addGlobalInfo(multa.getMensagemG2());
				}

				if (multa.getG2Diferenca() > 0) {
					switch (limitepbt.toString()) {

					case "16800.0":
					case "24150.0":
					case "30450.0":

						multa.setMensagemG2("CARGA TRASEIRA");
						if (multa.getG1Diferenca() > 0) {
							multa.setMensagemG1("EXCESSO PBT");
							multa.setMensagemG2("EXCESSO PBT");
						}
						// Messages.addGlobalInfo(multa.getMensagemG2());
						break;

					case "43575.0":
						multa.setMensagemG2("CARGA DIANTEIRA OU CONJUNTO ERRADO");
						// Messages.addGlobalInfo(multa.getMensagemG2());
						break;

					case "48300.0":
					case "45150.0":
					case "42000.0":
						// Carreta Trucada 6 eixos
					case "50925.0":
					case "57225.0":
						multa.setMensagemG2("CARGA DIANTEIRA");
						if (multa.getG1Diferenca() > 50) {
							multa.setMensagemG1("CARGA DIANTEIRA");
							multa.setMensagemG2("CARGA DIANTEIRA");
						}
						// Messages.addGlobalInfo(multa.getMensagemG2());
						break;

					// case "57225.0":
					// multa.setMensagemG2("TANQUE SUPLEMENTAR OU CARGA
					// DIANTEIRA");
					//
					// if (multa.getG1Diferenca() > 50) {
					// multa.setMensagemG1("TANQUE SUPLEMENTAR OU CARGA
					// DIANTEIRA");
					// multa.setMensagemG2("TANQUE SUPLEMENTAR OU CARGA
					// DIANTEIRA");
					// }
					// break;

					case "55650.0":
					case "52500.0":
						multa.setMensagemG2("CARGA DIANTEIRA");
						// Messages.addGlobalInfo(multa.getMensagemG2());
						break;

					case "59850.0":

					case "84000.0":
					case "77700.0": // BITREM ADAPTADO e Dolly
					case "68775.0":

						multa.setMensagemG2("CARGA DIANTEIRA NO 1° VAGÃO");
						// Messages.addGlobalInfo(multa.getMensagemG2());
						break;
					}
				}

				if ((multa.getG1Diferenca() > 50 && (multa.getG2Diferenca() <= (-1700)))) { // verificar
					switch (limitepbt.toString()) {
					case "43575.0":
					case "48300.0":
					case "45150.0":
					case "42000.0":
					case "50925.0":
						// NO DIA 28/01 O ADRIANO MANDOU TIRAR e colocar
						// novamente dia 20/02
					case "57225.0":
					case "55650.0":
					case "52500.0":
					case "59850.0":

					case "84000.0":
					case "77700.0": // BITREM ADAPTADO OU DOLLY
					case "68775.0":
						multa.setMensagemG1("QUINTA RODA DIANTEIRA");
						multa.setMensagemG2("");
						break;
					}

				}

				if (multa.getG1Diferenca() <= -600 && multa.getG2Diferenca() >= 0) { // verificar

					switch (limitepbt.toString()) {
					case "43575.0":
					case "48300.0":
					case "45150.0":
					case "42000.0":
					case "50925.0":
					case "57225.0":
					case "55650.0":
					case "52500.0":
					case "59850.0":

					case "84000.0":

					case "77700.0": // BITREM ADAPTADO ou Dolly
					case "68775.0":
						multa.setMensagemG1("");
						multa.setMensagemG2("QUINTA RODA TRASEIRA");

						break;
					}

				}

			} // fim do If por Balanca

		}
	}

	public void capturaPesosG3() {
		if (multa.getG3() != null) {
			multa.setG3Diferenca(0.0);
			multa.setG3Diferenca(multa.getG3Multa() - multa.getG3());
			//
			// System.out.println("G3 aux " + multa.getG3Multa());
			// System.out.println("G3 " + multa.getG3());
			// System.out.println("G3 Total " + multa.getG3Diferenca());

			if (multa.getTipoLancamento() == 1) {

				if (multa.getG3Diferenca() <= 0) {
					multa.setMensagemG3("");
					// Messages.addGlobalInfo(multa.getMensagemG3());
				}

				if (multa.getG3Diferenca() > 0) {
					switch (limitepbt.toString()) {
					case "43575.0":
					case "42000.0":
					case "30450.0":
						multa.setMensagemG3("CARGA TRASEIRA");
						if (multa.getG2Diferenca() > 0) {
							multa.setMensagemG2("EXCESSO PBT");
							multa.setMensagemG3("EXCESSO PBT");
						}

						break;
					case "52500.0":
						if (multa.getG4() == 18700) {
							multa.setMensagemG3("ALTA PRESSÃO NA VALVULA DO G3");
							// Messages.addGlobalInfo(multa.getMensagemG3());
						} else {

							multa.setMensagemG3("CARGA TRASEIRA");
							if (multa.getG2Diferenca() > 0) {
								multa.setMensagemG2("EXCESSO PBT");
								multa.setMensagemG3("EXCESSO PBT");
							}
						}
						break;

					case "48300.0":
					case "45150.0":
					case "55650.0":
						multa.setMensagemG3("ALTA PRESSÃO NA VALVULA DO G3");
						// adriano pediu para colocar
						if (multa.getG2Diferenca() > 0) {
							multa.setMensagemG2("EXCESSO PBT");
							multa.setMensagemG3("EXCESSO PBT");
						}
						break;
					// carreta trucada 6 eixos
					case "50925.0":
					case "57225.0":
						multa.setMensagemG3("CARGA TRASEIRA OU CONJUNTO ERRADO");
						// Messages.addGlobalInfo(multa.getMensagemG3());
						if (multa.getG2Diferenca() > 0) {
							multa.setMensagemG2("EXCESSO PBT");
							multa.setMensagemG3("EXCESSO PBT");
						}
						break;
					// case "57225.0":
					// multa.setMensagemG3("CARGA DIANTEIRA");
					// // Messages.addGlobalInfo(multa.getMensagemG3());
					// if (multa.getG2Diferenca() > 0) {
					// multa.setMensagemG2("CARGA DIANTEIRA");
					// multa.setMensagemG3("CARGA DIANTEIRA");
					// }
					// if ((multa.getG2Diferenca() <= (-200))) {
					// multa.setMensagemG2("");
					// multa.setMensagemG3("QUINTA RODA TRASEIRA");
					// }
					// break;

					case "84000.0":
						if (multa.getG2Diferenca() > 0) {
							multa.setMensagemG2("CARGA MAIOR NO 1° VAGÃO");
							multa.setMensagemG3("CARGA MAIOR NO 1° VAGÃO");
						}

						break;

					case "68775.0":

						multa.setMensagemG3("CARGA TRASEIRA NO 1° VAGÃO");
						if (multa.getG2Diferenca() > 0) {
							multa.setMensagemG2("CARGA MAIOR NO 1° VAGÃO");
							multa.setMensagemG3("CARGA MAIOR NO 1° VAGÃO");
						}

						if (multa.getG3Diferenca() > 5000 && (multa.getG3() == 18700)) {

							multa.setMensagemG3("SELECIONAR BITREM 8 EIXOS INVERTIDO");
						}

						if ((multa.getG3() == 28050)) {
							if (multa.getG3Diferenca() <= -6500) {

								multa.setMensagemG3("SELECIONAR BITREM 8 EIXOS ");
							}
						}
						break;

					case "59850.0":

						// multa.setMensagemG3("CARGA TRASEIRA NO 1° VAGÃO OU
						// CARGA DIANTEIRA DO 2° VAGÃO");
						multa.setMensagemG3("CARGA TRASEIRA NO 1° VAGÃO");
						// Messages.addGlobalInfo(multa.getMensagemG3());

						if (multa.getG2Diferenca() > 0) {
							multa.setMensagemG2("CARGA MAIOR NO 1° VAGÃO");
							multa.setMensagemG3("CARGA MAIOR NO 1° VAGÃO");
						}

						break;
					case "77700.0": // BITREM ADAPTADO
						// verifica dados do Dolly
						if (multa.getG3() == 18700) {
							multa.setMensagemG3("CARGA TRASEIRA NO 1° VAGÃO");
							if (multa.getG2Diferenca() > 0) {
								multa.setMensagemG2("CARGA MAIOR NO 1° VAGÃO");
								multa.setMensagemG3("CARGA MAIOR NO 1° VAGÃO");
							}
						} else {
							// verifica dados do Bitrem Adaptado
							multa.setMensagemG3("CARGA TRASEIRA NO 1° VAGÃO");
							if (multa.getG2Diferenca() > 0) {
								multa.setMensagemG2("CARGA MAIOR NO 1° VAGÃO");
								multa.setMensagemG3("CARGA MAIOR NO 1° VAGÃO");
							}
						}
						if (multa.getG3Diferenca() >= 5000) {
							multa.setMensagemG3("SELECIONAR O BITREM ADAPTADO '77.700'");
						}

						break;

					}

				} else {
					switch (limitepbt.toString()) {
					// retirado dia 26/02
					// case "57225.0":
					// if ((multa.getG2Diferenca() > 0 &&
					// (multa.getG3Diferenca() <= (-600)))) {
					// multa.setMensagemG2("QUINTA RODA DIANTEIRA");
					// multa.setMensagemG3("");
					// }
					// break;

					case "77700.0": // DOLLY
						if (multa.getG3Diferenca() < -6500) {
							multa.setMensagemG3("SELECIONAR O RODOTREM DOLLY '77.700'");
						}

						break;
					case "68775.0":

						if (multa.getG3Diferenca() <= -6500 && (multa.getG3() == 28050)) {

							multa.setMensagemG3("SELECIONAR BITREM 8 EIXOS ");
						}
						if (multa.getG3Diferenca() <= -6500 && (multa.getG4() == 28050)) {
							multa.setMensagemG3("SELECIONAR BITREM 8 EIXOS INVERTIDO ");

						}

					}
				}
			}
		}
	} // Fim do IF Balanca

	public void capturaPesosG4() {
		if (multa.getG4() != null) {
			multa.setG4Diferenca(0.0);
			multa.setG4Diferenca(multa.getG4Multa() - multa.getG4());

			// System.out.println("G4 aux " + multa.getG4Multa());
			// System.out.println("G4 " + multa.getG4());
			// System.out.println("G4 Total " + multa.getG4Diferenca());

			if (multa.getTipoLancamento() == 1) {

				if (multa.getG4Diferenca() <= 0) {
					multa.setMensagemG4("");
					// Messages.addGlobalInfo(multa.getMensagemG4());
				}

				// toda diferenca é positiva
				if (multa.getG4Diferenca() > 0) {
					switch (limitepbt.toString()) {
					case "57225.0":
						multa.setMensagemG4("CARGA TRASEIRA OU CONJUNTO ERRADO");
						// solicitado pelo Adriano dia 08/02
						if (multa.getG3Diferenca() > 0) {
							multa.setMensagemG3("EXCESSO PBT");
							multa.setMensagemG4("EXCESSO PBT");
							if (multa.getG2Diferenca() > 0) {
								multa.setMensagemG2("EXCESSO PBT");
								multa.setMensagemG3("EXCESSO PBT");
								multa.setMensagemG4("EXCESSO PBT");
							}

						}
						if (multa.getG2Diferenca() > 0) {
							multa.setMensagemG2("EXCESSO PBT");
							// multa.setMensagemG3("EXCESSO PBT");
							multa.setMensagemG4("EXCESSO PBT");
						}

						break;

					case "45150.0":
					case "52500.0":
						// multa.setMensagemG4("ALTA PRESSÃO NA VALVULA DO G3");
						// adriano pediu para colocar
						if (multa.getG2Diferenca() > 0) {
							multa.setMensagemG2("EXCESSO PBT");
							multa.setMensagemG4("EXCESSO PBT");
						}

						if (multa.getG3Diferenca() > 0) {
							multa.setMensagemG3("EXCESSO PBT");
							multa.setMensagemG4("EXCESSO PBT");
						}

						if (multa.getG2Diferenca() > 0 && multa.getG3Diferenca() > 0) {
							multa.setMensagemG2("EXCESSO PBT");
							multa.setMensagemG3("EXCESSO PBT");
							multa.setMensagemG4("EXCESSO PBT");
						}

						break;

					case "59850.0":
						multa.setMensagemG4("CARGA TRASEIRA NO 2° VAGÃO");
						if (multa.getG3Diferenca() > 0) {
							multa.setMensagemG3("CARGA MAIOR NO 2° VAGÃO");
							multa.setMensagemG4("CARGA MAIOR NO 2° VAGÃO");
						}

						break;
					case "68775.0":
						multa.setMensagemG4("CARGA MAIOR NO 2° VAGÃO");
						// BITREM 8 EIXOS NORMAL
						if (multa.getG3Diferenca() > 5000 && (multa.getG3() == 18700)) {
							multa.setMensagemG3("SELECIONAR BITREM 8 EIXOS INVERTIDO");
							multa.setMensagemG4("SELECIONAR BITREM 8 EIXOS INVERTIDO");
						}
						if (multa.getG4Diferenca() > 5000 && (multa.getG3() == 28050)) {
							multa.setMensagemG3("SELECIONAR BITREM 8 EIXOS ");
							multa.setMensagemG4("SELECIONAR BITREM 8 EIXOS ");
						}

						break;

					case "77700.0": // DOLLY
						// verifica se o Dolly
						if (multa.getG4() == 18700) {
							multa.setMensagemG4("CARGA DIANTEIRA NO 2° VAGÃO");
						} else {
							// mensagem para diferença do Bitrem Adaptado
							multa.setMensagemG4("CARGA MAIOR NO 2° VAGÃO");
							if (multa.getG3Diferenca() > 0 && multa.getG2Diferenca() > 0) {
								multa.setMensagemG2("CARGA MAIOR NO 1° VAGÃO");
								multa.setMensagemG3("CARGA MAIOR NO 1° VAGÃO");
								multa.setMensagemG4("CARGA MAIOR NO 2° VAGÃO");

							}

						}
						if (multa.getG4Diferenca() >= 5000) {
							multa.setMensagemG4("SELECIONAR O BITREM ADAPTADO '77.700'");
						}

						break;
					}
				} else {
					switch (limitepbt.toString()) {
					case "77700.0": // DOLLY
						if (multa.getG4Diferenca() < -6500) {
							multa.setMensagemG4("SELECIONAR O RODOTREM DOLLY '77.700'");
						}
						break;
					case "68775.0":

						if (multa.getG3Diferenca() <= -6500 && (multa.getG3() == 28050)) {
							multa.setMensagemG3("SELECIONAR BITREM 8 EIXOS ");
							multa.setMensagemG4("SELECIONAR BITREM 8 EIXOS ");
						}
						if (multa.getG4Diferenca() <= -6500 && (multa.getG4() == 28050)) {
							multa.setMensagemG3("SELECIONAR BITREM 8 EIXOS INVERTIDO ");
							multa.setMensagemG4("SELECIONAR BITREM 8 EIXOS INVERTIDO ");
						}
						break;
					}
				}
			}

			if (multa.getG3Diferenca() > 0 && multa.getG4Diferenca() <= 0) {
				switch (limitepbt.toString()) {
				case "45150.0":
				case "52500.0":
					multa.setMensagemG3("ALTA PRESSÃO DA VALVULA DO G3");
					multa.setMensagemG4("");
					// Messages.addGlobalInfo(multa.getMensagemG3());
					// Messages.addGlobalInfo(multa.getMensagemG4());
					break;
				}
			}

			if (multa.getG3Diferenca() > 0 && multa.getG4Diferenca() > 0) {
				switch (limitepbt.toString()) {
				case "45150.0":
				case "52500.0":

					multa.setMensagemG3("CARGA TRASEIRA");
					multa.setMensagemG4("CARGA TRASEIRA");
					if (multa.getG2Diferenca() > 0) {
						multa.setMensagemG2("EXCESSO PBT");
						multa.setMensagemG3("EXCESSO PBT");
						multa.setMensagemG4("EXCESSO PBT");
					}

					break;

				case "48300.0":
					// multa.setMensagemG2("EXCESSO PBT");
					multa.setMensagemG3("EXCESSO PBT");
					multa.setMensagemG4("EXCESSO PBT");

					break;

				case "84000.0":
					multa.setMensagemG3("CARGA TRASEIRA NO 1° VAGÃO");
					multa.setMensagemG4("CARGA TRASEIRA NO 1° VAGÃO");

					if (multa.getG2Diferenca() > 0) {
						multa.setMensagemG2("CARGA MAIOR NO 1° VAGÃO");
						multa.setMensagemG3("CARGA MAIOR NO 1° VAGÃO");
						multa.setMensagemG4("CARGA MAIOR NO 1° VAGÃO");
					}

					break;

				}
			}
			if (multa.getG2Diferenca() > 0 && multa.getG4Diferenca() > 0) {
				switch (limitepbt.toString()) {

				case "84000.0":
					if (multa.getG3Diferenca() <= 0) {
						multa.setMensagemG2("CARGA MAIOR NO 1° VAGÃO");
						multa.setMensagemG4("CARGA MAIOR NO 1° VAGÃO");
					}
					break;

				}
			}

			if (multa.getG3Diferenca() <= 0 && multa.getG4Diferenca() > 0) {
				switch (limitepbt.toString()) {
				case "45150.0":
				case "52500.0":
					multa.setMensagemG3("");
					multa.setMensagemG4("BAIXA PRESSÃO NA VALVULA DO G3");
					// Messages.addGlobalInfo(multa.getMensagemG3());
					// Messages.addGlobalInfo(multa.getMensagemG4());
					if (multa.getG2Diferenca() > 0) {
						multa.setMensagemG2("EXCESSO PBT");
						multa.setMensagemG4("EXCESSO PBT");
					}

					break;
				}
			}

		}

	}

	public void capturaPesosG5() {
		if (multa.getG5() != null) {
			multa.setG5Diferenca(0.0);
			multa.setG5Diferenca(multa.getG5Multa() - multa.getG5());

			// System.out.println("G5 aux " + multa.getG5Multa());
			// System.out.println("G5 " + multa.getG5());
			// System.out.println("G5 Total " + multa.getG5Diferenca());

			if (multa.getTipoLancamento() == 1) {

				if (multa.getG5Diferenca() <= 0) {
					multa.setMensagemG5("");
					// Messages.addGlobalInfo(multa.getMensagemG5());
				}

				if (multa.getG5Diferenca() > 0) {
					switch (limitepbt.toString()) {
					case "77700.0": // DOLLY
						multa.setMensagemG5("CARGA TRASEIRA NO 2° VAGÃO");
						if (multa.getG4Diferenca() > 0) {

							multa.setMensagemG4("CARGA MAIOR NO 2° VAGÃO");
							multa.setMensagemG5("CARGA MAIOR NO 2° VAGÃO");

						}

						break;

					case "84000.0":
						multa.setMensagemG5("CARGA DIANTEIRA NO 2° VAGÃO");
						// Messages.addGlobalInfo(multa.getMensagemG5());
						break;
					case "48300.0":
					case "55650.0":
						if (multa.getG3Diferenca() > 0 && multa.getG4Diferenca() > 0) {
							multa.setMensagemG3("EXCESSO PBT");
							multa.setMensagemG4("EXCESSO PBT");
							multa.setMensagemG5("EXCESSO PBT");
							if (multa.getG2Diferenca() > 0) {
								multa.setMensagemG2("EXCESSO PBT");
								multa.setMensagemG3("EXCESSO PBT");
								multa.setMensagemG4("EXCESSO PBT");
								multa.setMensagemG5("EXCESSO PBT");
							}

						}
						if (multa.getG3Diferenca() > 0) {
							multa.setMensagemG3("EXCESSO PBT");
							multa.setMensagemG5("EXCESSO PBT");
						}

						if (multa.getG2Diferenca() > 0 && multa.getG4Diferenca() > 0) {
							multa.setMensagemG2("EXCESSO PBT");
							multa.setMensagemG4("EXCESSO PBT");
							multa.setMensagemG5("EXCESSO PBT");

						}

						break;

					}

					if (multa.getG3Diferenca() > 0 && multa.getG4Diferenca() <= 0 && multa.getG5Diferenca() <= 0) {
						switch (limitepbt.toString()) {
						case "48300.0":
							if (multa.getG2Diferenca() > 0) {
								multa.setMensagemG3("EXCESSO PBT");
								multa.setMensagemG2("EXCESSO PBT");
							}
							multa.setMensagemG3("ALTA PRESSÃO NA VALVULA DO G3");
							multa.setMensagemG4("");
							multa.setMensagemG5("");
							break;
						case "55650.0":
							multa.setMensagemG3("ALTA PRESSÃO NA VALVULA DO G3");
							multa.setMensagemG4("");
							multa.setMensagemG5("");

							break;
						}
					}

					if (multa.getG2Diferenca() <= 0 && multa.getG3Diferenca() <= 0 && multa.getG4Diferenca() > 0
							&& multa.getG5Diferenca() > 0) {
						switch (limitepbt.toString()) {
						case "48300.0":
						case "55650.0":
							multa.setMensagemG3("");
							multa.setMensagemG4("BAIXA PRESSÃO NA VALVULA DO G3");
							multa.setMensagemG5("BAIXA PRESSÃO NA VALVULA DO G3");

							break;
						}
					}

				}
			}
		}
	}

	public void capturaPesosG6() {
		if (multa.getG6() != null) {
			multa.setG6Diferenca(0.0);
			multa.setG6Diferenca(multa.getG6Multa() - multa.getG6());

			// System.out.println("G6 aux " + multa.getG6Multa());
			// System.out.println("G6 " + multa.getG6());
			// System.out.println("G6 Total " + multa.getG6Diferenca());

			if (multa.getTipoLancamento() == 1) {

				if (multa.getG6Diferenca() <= 0) {
					multa.setMensagemG6("");
					// Messages.addGlobalInfo(multa.getMensagemG6());
				}
				if (multa.getG6Diferenca() > 0) {
					switch (limitepbt.toString()) {
					case "84000.0":
						multa.setMensagemG6("CARGA TRASEIRA NO 2° VAGÃO");

						if (multa.getG5Diferenca() > 0) {
							multa.setMensagemG5("CARGA MAIOR NO 2° VAGÃO");
							multa.setMensagemG6("CARGA MAIOR NO 2° VAGÃO");

						}

						// Messages.addGlobalInfo(multa.getMensagemG6());
						break;
					}
				}

			}
		}
	}

	public void capturaPesosG7() {
		if (multa.getG7() != null) {
			multa.setG7Diferenca(multa.getG7Multa() - multa.getG7());

			// System.out.println("G7 aux " + multa.getG7Multa());
			// System.out.println("G7 " + multa.getG7());
			// System.out.println("G7 Total " + multa.getG7Diferenca());
			if (multa.getTipoLancamento() == 1) {

				if (multa.getG7Diferenca() <= 0) {
					multa.setMensagemG7("");
					// Messages.addGlobalInfo(multa.getMensagemG7());
				}

				if (multa.getG7Diferenca() > 0) {
					switch (limitepbt.toString()) {
					case "84000.0":
						multa.setMensagemG6("CARGA TRASEIRA NO 2° VAGÃO");
						multa.setMensagemG7("CARGA TRASEIRA NO 2° VAGÃO");
						if (multa.getG5Diferenca() > 0 && multa.getG6Diferenca() > 0) {
							multa.setMensagemG5("CARGA MAIOR NO 2° VAGÃO");
							multa.setMensagemG6("CARGA MAIOR NO 2° VAGÃO");
							multa.setMensagemG7("CARGA MAIOR NO 2° VAGÃO");
						}
						if (multa.getG5Diferenca() > 0 && multa.getG6Diferenca() <= 0) {
							multa.setMensagemG5("CARGA MAIOR NO 2° VAGÃO");
							multa.setMensagemG6("");
							multa.setMensagemG7("CARGA MAIOR NO 2° VAGÃO");
						}

						break;
					}
				}

			}
		}
	}

	public void camposObrigatorios() {
		// para multas com eixo
		if (multa.getTipoDocumento() == 1) {
			autodeInfr_requerido = true;
			orgao_requerido = true;
			dataOcorencia_requerido = true;
			placacavalo_requerido = true;
			pesoaferidopbt_requerido = true;
			limiteregulament_requerido = true;
			valortotal_requerido = true;
			datavencMulta_requerido = true;
		}
		// para notificacao com eixo
		if (multa.getTipoDocumento() == 3) {
			autodeInfr_requerido = true;
			orgao_requerido = true;
			dataOcorencia_requerido = true;
			placacavalo_requerido = true;
			pesoaferidopbt_requerido = true;
			valortotal_requerido = false;
			datavencMulta_requerido = false;
			limiteregulament_requerido = true;
		}
		// para multas sem eixo
		if (multa.getTipoDocumento() == 2) {
			autodeInfr_requerido = true;
			orgao_requerido = true;
			dataOcorencia_requerido = true;
			placacavalo_requerido = true;
			pesoaferidopbt_requerido = false;
			valortotal_requerido = true;
			datavencMulta_requerido = true;
			limiteregulament_requerido = false;
		}
		// para notificacao sem eixo
		if (multa.getTipoDocumento() == 4) {
			autodeInfr_requerido = true;
			orgao_requerido = true;
			dataOcorencia_requerido = true;
			placacavalo_requerido = true;
			pesoaferidopbt_requerido = false;
			valortotal_requerido = false;
			datavencMulta_requerido = false;
			limiteregulament_requerido = false;
		}
	}

	// LIMPA TODODS OS CAMPOS DO DIALOGO
	public void limpaCampos() {

		// estou pensando em colocar um evento para limpar os campos de acordo
		// com que modifica o tipo de lancamento 1 ou 2
		salvarDiferenca = false;

		multa.setG1(null);
		multa.setG2(null);
		multa.setG3(null);
		multa.setG4(null);
		multa.setG5(null);
		multa.setG6(null);
		multa.setG7(null);

		multa.setG1Multa(null);
		multa.setG2Multa(null);
		multa.setG3Multa(null);
		multa.setG4Multa(null);
		multa.setG5Multa(null);
		multa.setG6Multa(null);
		multa.setG7Multa(null);

		multa.setMensagemG1(null);
		multa.setMensagemG2(null);
		multa.setMensagemG3(null);
		multa.setMensagemG4(null);
		multa.setMensagemG5(null);
		multa.setMensagemG6(null);
		multa.setMensagemG7(null);

		multa.setG1Diferenca(null);
		multa.setG2Diferenca(null);
		multa.setG3Diferenca(null);
		multa.setG4Diferenca(null);
		multa.setG5Diferenca(null);
		multa.setG6Diferenca(null);
		multa.setG7Diferenca(null);

		multa.setObservacao(null);
		multa.setDataOcorencia(null);
		multa.setDataVencimentoMulta(null);
		multa.setAutoInfracao(null);
		multa.setRodovia(null);
		multa.setKm(null);
		multa.setValorTotal(null);
		multa.setValorComdesconto(null);
		multa.setPlacaCavalo(null);
		multa.setPlaca1(null);
		multa.setPlaca2(null);
		multa.setExcessoPbt(null);
		multa.setMensagemPBT(null);
		multa.setPesoAferidoPbt(null);

		g1 = null;
		g1aux = null;
		g1total = null;
		mensagemG1 = null;

		g2 = null;
		g2aux = null;
		g2total = null;
		mensagemG2 = null;

		g3 = null;
		g3aux = null;
		g3total = null;
		mensagemG3 = null;

		g4 = null;
		g4aux = null;
		g4total = null;
		mensagemG4 = null;

		g5 = null;
		g5aux = null;
		g5total = null;
		mensagemG5 = null;

		g6 = null;
		g6aux = null;
		g6total = null;
		mensagemG6 = null;

		g7 = null;
		g7aux = null;
		g7total = null;
		mensagemG7 = null;

	}

	public void limpaCamposCaminhao() {

		// estou pensando em colocar um evento para limpar os campos de acordo
		// com que modifica o tipo de lancamento 1 ou 2

		multa.setG1Multa(null);
		multa.setG2Multa(null);
		multa.setG3Multa(null);
		multa.setG4Multa(null);
		multa.setG5Multa(null);
		multa.setG6Multa(null);
		multa.setG7Multa(null);

		multa.setG1(null);
		multa.setG2(null);
		multa.setG3(null);
		multa.setG4(null);
		multa.setG5(null);
		multa.setG6(null);
		multa.setG7(null);

		multa.setMensagemG1(null);
		multa.setMensagemG2(null);
		multa.setMensagemG3(null);
		multa.setMensagemG4(null);
		multa.setMensagemG5(null);
		multa.setMensagemG6(null);
		multa.setMensagemG7(null);

		multa.setG1Diferenca(null);
		multa.setG2Diferenca(null);
		multa.setG3Diferenca(null);
		multa.setG4Diferenca(null);
		multa.setG5Diferenca(null);
		multa.setG6Diferenca(null);
		multa.setG7Diferenca(null);

		imagens2 = new ArrayList<>();
		imagens2.add("branco.png");

		// multa.setVeiculo(null);

		multa.setExcessoPbt(null);
		multa.setMensagemPBT(null);
	}

	@PostConstruct
	public void listar() {

		try {
			// criar lista para o combobox
			// CidadeDAO cidadeDAO = new CidadeDAO();
			// cidades = cidadeDAO.listar("nome");
			// criar lista para o combobox
			OrgaoDAO orgaoDAO = new OrgaoDAO();
			orgaos = orgaoDAO.listar();

			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculos = veiculoDAO.listar("ativo");

			MultaDAO multaDAO = new MultaDAO();
			multas = multaDAO.listarPorEmpresa();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar os Multas!\n " + erro);
			erro.printStackTrace();

		}

	}

	public void novo() {
		imagens2.clear(); // limpar o vetor de imagens
		exibir = true; /*
						 * / para toda vez que iniciar o dialog vai ser com o
						 * layout do Dnit
						 */

		salvarDiferenca = false;
		exibir2 = false;
		try {
			multa = new Multa(); // cria um objeto multa
			// criar lista para o combobox
			// CidadeDAO cidadeDAO = new CidadeDAO();
			// cidades = cidadeDAO.listar("nome");

			// criar lista para o combobox
			OrgaoDAO orgaoDAO = new OrgaoDAO();
			orgaos = orgaoDAO.listar();

			VeiculoDAO veiculoDAO = new VeiculoDAO();
			// lista todos os veiculos que estao ativos.
			veiculos = veiculoDAO.listar(true);
			// veiculos = veiculoDAO.listar("apelido");
			multa.setTipoLancamento(1);

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao criar uma nova multa!\n " + erro);
			erro.printStackTrace();
		}
	}

	public void salvar() {

		// 1 DNIT Por balanca
		// 2 DER-SP Por balanca
		// 3 ANTT Por balanca
		// 7 SRPRF-MG Por balanca

		// 4 DER-PR por nota
		// 5 DER-MG por nota
		// 6 DPRF por nota
		// 8 ANTT - Antigo Por nota

		// modo de puxar o usuario logado.
		AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		Usuario usuario = autenticacaoBean.getUsuarioLogado();
		// atraves do usuario logado eu pego qual a empresa que ira salva
		multa.setEmpresa(usuario.getEmpresa());

		try {
			/*
			 * 
			 * selecionados com o tipo de Documento Multa por eixo ==1 ou
			 * notificacao por eixo == 3
			 */
			if (multa.getTipoDocumento() == 1 || multa.getTipoDocumento() == 3) {
				double soma;
				/*
				 * verifica se o peso aferido dos eixo informado e menor que o
				 * pbt do veiculo verificacao se todos os pesos estao nulos,
				 * caso estejao recebem 0 para poder calcular o resultado
				 */
				double i = 0;
				if (multa.getG1Multa() == null) {
					multa.setG1Multa(i);
				}
				if (multa.getG2Multa() == null) {
					multa.setG2Multa(i);
				}
				if (multa.getG3Multa() == null) {
					multa.setG3Multa(i);
				}
				if (multa.getG4Multa() == null) {
					multa.setG4Multa(i);
				}
				if (multa.getG5Multa() == null) {
					multa.setG5Multa(i);
				}
				if (multa.getG6Multa() == null) {
					multa.setG6Multa(i);
				}
				if (multa.getG7Multa() == null) {
					multa.setG7Multa(i);
				}

				soma = (multa.getG1Multa() + multa.getG2Multa() + multa.getG3Multa() + multa.getG4Multa()
						+ multa.getG5Multa() + multa.getG6Multa() + multa.getG7Multa());
				if (multa.getPesoAferidoPbt() == soma) {

					// System.out.println("Soma" + soma);

					multa.setDataLancamento(new Date());
					MultaDAO multaDAO = new MultaDAO();
					multaDAO.merge(multa);
					Messages.addGlobalInfo("Lançada com sucesso!");
					multa = new Multa();

					// criar lista para o combobox
					// CidadeDAO cidadeDAO = new CidadeDAO();
					// cidades = cidadeDAO.listar();

					// criar lista para o combobox
					OrgaoDAO orgaoDAO = new OrgaoDAO();
					orgaos = orgaoDAO.listar();

					VeiculoDAO veiculoDAO = new VeiculoDAO();
					veiculos = veiculoDAO.listar("apelido");

					imagens2.clear();
					multas = multaDAO.listarPorEmpresa();

				} else {
					if (multa.getPesoAferidoPbt() != soma) {

						MultaDAO multaDAO = new MultaDAO();

						RequestContext.getCurrentInstance().execute("PF('confirmaSalvar').show()");
						if (salvarDiferenca == true) {

							multa.setDataLancamento(new Date());

							multaDAO.merge(multa);
							Messages.addGlobalInfo("Lançada com sucesso!");
							multa = new Multa();
							// }
							// criar lista para o combobox
							// CidadeDAO cidadeDAO = new CidadeDAO();
							// cidades = cidadeDAO.listar();

							// criar lista para o combobox
							OrgaoDAO orgaoDAO = new OrgaoDAO();
							orgaos = orgaoDAO.listar();

							VeiculoDAO veiculoDAO = new VeiculoDAO();
							veiculos = veiculoDAO.listar("apelido");

							imagens2.clear();
							multas = multaDAO.listarPorEmpresa();
							RequestContext.getCurrentInstance().execute("PF('confirmaSalvar').hide()");
						}

					}

				}
			}
			/*
			 * 
			 * selecionados com o tipo de Documento Multa sem eixo ==2 ou
			 * notificacao sem eixo == 4
			 */
			else if (multa.getTipoDocumento() == 2 || multa.getTipoDocumento() == 4) {

				multa.setDataLancamento(new Date());
				MultaDAO multaDAO = new MultaDAO();
				multaDAO.merge(multa);
				Messages.addGlobalInfo("Lançada com sucesso!");
				multa = new Multa();

				// criar lista para o combobox
				// CidadeDAO cidadeDAO = new CidadeDAO();
				// cidades = cidadeDAO.listar();

				// criar lista para o combobox
				OrgaoDAO orgaoDAO = new OrgaoDAO();
				orgaos = orgaoDAO.listar();

				VeiculoDAO veiculoDAO = new VeiculoDAO();
				veiculos = veiculoDAO.listar("apelido");

				imagens2.clear();
				multas = multaDAO.listarPorEmpresa();

			}

		} catch (RuntimeException erro) {

			Messages.addFlashGlobalError("Ocorreu um erro ao salvar uma nova  multa!\n ");
			erro.printStackTrace();
			// Messages.addGlobalError("Multa Já lançada! " + " - " +
			// multa.getAutoInfracao());

		}

	}

	public void salvarBolean() {

		salvarDiferenca = true;
		salvar();

	}

	public void excluir(ActionEvent evento) {
		try {
			multa = (Multa) evento.getComponent().getAttributes().get("multaSelecionada");
			MultaDAO multaDAO = new MultaDAO();
			multaDAO.excluir(multa);
			multas = multaDAO.listarPorEmpresa();
			Messages.addGlobalInfo("Removida com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a multa!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		multa.setDataLancamento(getDataLancamento());
		// verifica se o graficImage possui algum codigo em cache, ai limpa
		if (imagens2 != null) {
			imagens2.clear();
		}
		try {
			// pega o objeto salvo na tabela e edita todos de acordo com o
			// codigo primary key
			multa = (Multa) evento.getComponent().getAttributes().get("multaSelecionada");

			// add ao graficImag o codigo primary key do veiculo + .png para
			// mostrar a imagem.
			imagens2.add(multa.getVeiculo().getCodigo() + ".png");
			// for (Multa multa : multas) {
			// tipoDocumentoAux = multa.getTipoDocumento();
			// }

			MultaDAO multaDAO = new MultaDAO();
			multas = multaDAO.listarPorEmpresa();
			// verificar como vai ficar a questao da mensagem
			// Messages.addGlobalInfo("Alterada com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar alterar a multa!");
			erro.printStackTrace();
		}

	}

	public void ListarPlaca() {
		MultaDAO multaDAO = new MultaDAO();
		placas = multaDAO.listaPlaca(pesqPlaca);
	}

	public void buscarVeiculoVistoria() {
		VistoriaDAO dao = new VistoriaDAO();
		vistoriaVeiculoPlaca = dao.buscarVeiculoPorPlaca(multa.getPlacaCavalo());
		if (vistoriaVeiculoPlaca == null) {
			// CASO NÃO TENHA NENHUM VEÍCULO NO BANCO NÃO PRECISA RETORNAR
			// NENHUM VALOR NA TELA
			// System.out.println("NÃO EXISTE VEÍCULO PARA A PLACA INFORMADA NO
			// BANCO DE DADOS DE VISTORIA");
		}
		if (vistoriaVeiculoPlaca != null) {
			multa.setPlaca1(vistoriaVeiculoPlaca.getPlacaCarroceria1());
			multa.setPlaca2(vistoriaVeiculoPlaca.getPlacaCarroceria2());
			multa.setPlaca3(vistoriaVeiculoPlaca.getPlacaCarroceria3());

			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculos = veiculoDAO.listar("ativo");
			for (Veiculo veiculo : veiculos) {
				if (veiculo.getCodigo() == vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getCodigo()) {
					multa.setVeiculo(vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao());
					imagens2 = new ArrayList<>();
					imagens2.add(veiculo.getCodigo() + ".png");
					multa.setG1(vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getG1PBT());
					multa.setG2(vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getG2PBT());
					multa.setG3(vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getG3PBT());
					multa.setG4(vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getG4PBT());
					multa.setG5(vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getG5PBT());
					multa.setG6(vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getG6PBT());
					multa.setG7(vistoriaVeiculoPlaca.getVeiculoResultadoCombinacao().getG7PBT());

				}
			}

		}

	}

	public void checarAutoinfracao() {

		// System.out.println("Auto Multa: " + multa.getAutoInfracao());
		String test = null;
		test = multa.getAutoInfracao();

		for (Multa multa1 : multas) {
			if (multa1.getAutoInfracao().equals(test)) {
				Messages.addFlashGlobalWarn("Já houve lançamento com esse N° de Auto de Infração: "
						+ multa1.getAutoInfracao() + "\n" + "Favor informar o N° de Auto de Infração correto!");
				break;
			}
		}

	}

	public Cidade obterCidadeCodigo(Long cod) {
		Cidade cid = null;
		for (Cidade cidade : cidadess) {
			if (cidade.getCodigo() == cod) {
				cid = cidade;
				System.out.println("Cid :" + cid);
			}
		}
		return cid;
	}

	public List<Cidade> buscarCidade(String nome) {
		List<Cidade> lista_resultado = new ArrayList<Cidade>();

		for (Cidade c : cidadetest) {
			if (c.getNome().toLowerCase().contains(nome.toLowerCase())) {
				lista_resultado.add(c);
				System.out.println("Lista :" + lista_resultado);
			}
		}
		return lista_resultado;

	}

	public List<Cidade> buscarCidades(Cidade nome) {
		if (nome != null) {
			for (Cidade c : cidadess) {
				if (c.getNome().equalsIgnoreCase(nome.getNome())) {
					cidadess.add(c);
					System.out.println("Lista :" + cidadess);
				}
			}
		}
		return cidadess;

	}

	public void test() {
		System.out.println("Pesquisada :" + multa.getCidade());
		for (Cidade cidade : cidadess) {
			if (cidade.getNome().equals(multa.getCidade())) {
				System.out.println("Pesquisa :" + cidade.getNome());
				System.out.println("Pesquisada :" + multa.getCidade());
				// System.out.println("Codigo salvo: " +
				// multa.getCidade1().getNome());
			}
		}
	}

	public List<String> buscarCidadet(String nome) {
		if (nome != null) {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.buscarCidade(nome);
		}
		return cidades;

	}

	public void obtercidade() {
		System.out.println("Pesquisada :" + multa.getCidade());
		for (Cidade cid : cidadess) {
			if (cid.getNome().equals(multa.getCidade())) {

				System.out.println("Pesquisada :" + multa.getCidade());
				System.out.println("Codigo :" + cid.getCodigo());
			}
		}

	}

	// fim metodos

	public Double getLimitepbt() {
		return limitepbt;
	}

	public void setLimitepbt(Double limitepbt) {
		this.limitepbt = limitepbt;
	}

	public List<String> getImagens() {
		return imagens;
	}

	public void setImagens(List<String> imagens) {
		this.imagens = imagens;
	}

	public List<String> getCidades() {
		return cidades;
	}

	public void setCidades(List<String> cidades) {
		this.cidades = cidades;
	}

	public void setOrgaos(List<Orgao> orgaos) {
		this.orgaos = orgaos;
	}

	public List<Orgao> getOrgaos() {
		return orgaos;
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}

	public Multa getMulta() {
		return multa;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public Double getPercentual() {
		return percentual;
	}

	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}

	public Double getG1() {
		return g1;
	}

	public void setG1(Double g1) {
		this.g1 = g1;
	}

	public Double getG2() {
		return g2;
	}

	public void setG2(Double g2) {
		this.g2 = g2;
	}

	public List<String> getImagens2() {
		return imagens2;
	}

	public List<Double> getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(List<Double> porcentagem) {
		this.porcentagem = porcentagem;
	}

	public Double getG3() {
		return g3;
	}

	public void setG3(Double g3) {
		this.g3 = g3;
	}

	public Double getG4() {
		return g4;
	}

	public void setG4(Double g4) {
		this.g4 = g4;
	}

	public Double getG5() {
		return g5;
	}

	public void setG5(Double g5) {
		this.g5 = g5;
	}

	public Double getG6() {
		return g6;
	}

	public void setG6(Double g6) {
		this.g6 = g6;
	}

	public Double getG7() {
		return g7;
	}

	public void setG7(Double g7) {
		this.g7 = g7;
	}

	public Double getExessoPBT() {
		return exessoPBT;
	}

	public void setExessoPBT(Double exessoPBT) {
		this.exessoPBT = exessoPBT;
	}

	public Double getPesoAfer() {
		return pesoAfer;
	}

	public void setPesoAfer(Double pesoAfer) {
		this.pesoAfer = pesoAfer;
	}

	public Double getG1aux() {
		return g1aux;
	}

	public void setG1aux(Double g1aux) {
		this.g1aux = g1aux;
	}

	public Double getG1total() {
		return g1total;
	}

	public void setG1total(Double g1total) {
		this.g1total = g1total;
	}

	public Double getG2aux() {
		return g2aux;
	}

	public void setG2aux(Double g2aux) {
		this.g2aux = g2aux;
	}

	public Double getG2total() {
		return g2total;
	}

	public void setG2total(Double g2total) {
		this.g2total = g2total;
	}

	public Double getG3aux() {
		return g3aux;
	}

	public String getBuscaCidade() {
		return buscaCidade;
	}

	public void setBuscaCidade(String buscaCidade) {
		this.buscaCidade = buscaCidade;
	}

	public void setG3aux(Double g3aux) {
		this.g3aux = g3aux;
	}

	public Double getG3total() {
		return g3total;
	}

	public void setG3total(Double g3total) {
		this.g3total = g3total;
	}

	public Double getG4aux() {
		return g4aux;
	}

	public void setG4aux(Double g4aux) {
		this.g4aux = g4aux;
	}

	public Double getG4total() {
		return g4total;
	}

	public void setG4total(Double g4total) {
		this.g4total = g4total;
	}

	public Double getG5aux() {
		return g5aux;
	}

	public void setG5aux(Double g5aux) {
		this.g5aux = g5aux;
	}

	public Double getG5total() {
		return g5total;
	}

	public void setG5total(Double g5total) {
		this.g5total = g5total;
	}

	public Double getG6aux() {
		return g6aux;
	}

	public void setG6aux(Double g6aux) {
		this.g6aux = g6aux;
	}

	public Double getG6total() {
		return g6total;
	}

	public void setG6total(Double g6total) {
		this.g6total = g6total;
	}

	public Double getG7aux() {
		return g7aux;
	}

	public void setG7aux(Double g7aux) {
		this.g7aux = g7aux;
	}

	public Double getG7total() {
		return g7total;
	}

	public void setG7total(Double g7total) {
		this.g7total = g7total;
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Multa> getMultas() {
		return multas;
	}

	public void setMultas(List<Multa> multas) {
		this.multas = multas;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public boolean isExibir() {
		return exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public String getMensagemOrgao() {
		return mensagemOrgao;
	}

	public boolean isExibir2() {
		return exibir2;
	}

	public void setExibir2(boolean exibir2) {
		this.exibir2 = exibir2;
	}

	public void setMensagemOrgao(String mensagemOrgao) {
		this.mensagemOrgao = mensagemOrgao;
	}

	@Override
	public String toString() {
		return "MultasBean [multa=" + multa + ", cidades=" + cidades + ", orgaos=" + orgaos + ", veiculos=" + veiculos
				+ ", multas=" + multas + ", produtos=" + produtos + ", placas=" + placas + ", vistoriaVeiculoPlaca="
				+ vistoriaVeiculoPlaca + ", imagens=" + imagens + ", imagens2=" + imagens2 + ", dataLancamento="
				+ dataLancamento + ", pesoAfer=" + pesoAfer + ", exessoPBT=" + exessoPBT + ", g1=" + g1 + ", g1aux="
				+ g1aux + ", g1total=" + g1total + ", g2=" + g2 + ", g2aux=" + g2aux + ", g2total=" + g2total + ", g3="
				+ g3 + ", g3aux=" + g3aux + ", g3total=" + g3total + ", g4=" + g4 + ", g4aux=" + g4aux + ", g4total="
				+ g4total + ", g5=" + g5 + ", g5aux=" + g5aux + ", g5total=" + g5total + ", g6=" + g6 + ", g6aux="
				+ g6aux + ", g6total=" + g6total + ", g7=" + g7 + ", g7aux=" + g7aux + ", g7total=" + g7total
				+ ", limitepbt=" + limitepbt + ", porcentagem=" + porcentagem + ", percentual=" + percentual
				+ ", mensagemG1=" + mensagemG1 + ", mensagemG2=" + mensagemG2 + ", mensagemG3=" + mensagemG3
				+ ", mensagemG4=" + mensagemG4 + ", mensagemG5=" + mensagemG5 + ", mensagemG6=" + mensagemG6
				+ ", mensagemG7=" + mensagemG7 + ", exibir=" + exibir + ", exibir2=" + exibir2 + ", observacao="
				+ observacao + ", mensagemOrgao=" + mensagemOrgao + ", pesqPlaca=" + pesqPlaca
				+ ", autodeInfr_requerido=" + autodeInfr_requerido + ", orgao_requerido=" + orgao_requerido
				+ ", dataOcorencia_requerido=" + dataOcorencia_requerido + ", placacavalo_requerido="
				+ placacavalo_requerido + ", pesoaferidopbt_requerido=" + pesoaferidopbt_requerido
				+ ", limiteregulament_requerido=" + limiteregulament_requerido + ", valortotal_requerido="
				+ valortotal_requerido + ", datavencMulta_requerido=" + datavencMulta_requerido + ", salvarDiferenca="
				+ salvarDiferenca + "]";
	}

	public List<Multa> getPlacas() {
		return placas;
	}

	public void setPlacas(List<Multa> placas) {
		this.placas = placas;
	}

	public String getPesqPlaca() {
		return pesqPlaca;
	}

	public void setPesqPlaca(String pesqPlaca) {
		this.pesqPlaca = pesqPlaca;
	}

	public Vistoria getVistoriaVeiculoPlaca() {
		return vistoriaVeiculoPlaca;
	}

	public void setVistoriaVeiculoPlaca(Vistoria vistoriaVeiculoPlaca) {
		this.vistoriaVeiculoPlaca = vistoriaVeiculoPlaca;
	}

	public boolean isAutodeInfr_requerido() {
		return autodeInfr_requerido;
	}

	public void setAutodeInfr_requerido(boolean autodeInfr_requerido) {
		this.autodeInfr_requerido = autodeInfr_requerido;
	}

	public boolean isOrgao_requerido() {
		return orgao_requerido;
	}

	public void setOrgao_requerido(boolean orgao_requerido) {
		this.orgao_requerido = orgao_requerido;
	}

	public boolean isDataOcorencia_requerido() {
		return dataOcorencia_requerido;
	}

	public void setDataOcorencia_requerido(boolean dataOcorencia_requerido) {
		this.dataOcorencia_requerido = dataOcorencia_requerido;
	}

	public boolean isPlacacavalo_requerido() {
		return placacavalo_requerido;
	}

	public void setPlacacavalo_requerido(boolean placacavalo_requerido) {
		this.placacavalo_requerido = placacavalo_requerido;
	}

	public boolean isPesoaferidopbt_requerido() {
		return pesoaferidopbt_requerido;
	}

	public void setPesoaferidopbt_requerido(boolean pesoaferidopbt_requerido) {
		this.pesoaferidopbt_requerido = pesoaferidopbt_requerido;
	}

	public boolean isLimiteregulament_requerido() {
		return limiteregulament_requerido;
	}

	public void setLimiteregulament_requerido(boolean limiteregulament_requerido) {
		this.limiteregulament_requerido = limiteregulament_requerido;
	}

	public boolean isValortotal_requerido() {
		return valortotal_requerido;
	}

	public void setValortotal_requerido(boolean valortotal_requerido) {
		this.valortotal_requerido = valortotal_requerido;
	}

	public boolean isDatavencMulta_requerido() {
		return datavencMulta_requerido;
	}

	public void setDatavencMulta_requerido(boolean datavencMulta_requerido) {
		this.datavencMulta_requerido = datavencMulta_requerido;
	}

	public void setImagens2(List<String> imagens2) {
		this.imagens2 = imagens2;
	}

	public boolean isSalvarDiferenca() {
		return salvarDiferenca;
	}

	public Cidade getCidadeSelecionada() {
		return cidadeSelecionada;
	}

	public void setCidadeSelecionada(Cidade cidadeSelecionada) {
		this.cidadeSelecionada = cidadeSelecionada;
	}

	public void setSalvarDiferenca(boolean salvarDiferenca) {
		this.salvarDiferenca = salvarDiferenca;
	}

}
