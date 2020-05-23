package com.gdm.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.SlideEndEvent;

import com.gdm.domain.CavaloCarroceria;
import com.gdm.domain.TipoConjunto;
import com.gdm.domain.Transportadora;
import com.gdm.domain.Usuario;
import com.gdm.domain.Veiculo;
import com.gdm.domain.Vistoria;
import com.gdm.dao.CavaloCarroceriaDAO;
import com.gdm.dao.TipoConjuntoDAO;
import com.gdm.dao.TransportadoraDAO;
import com.gdm.dao.VeiculoDAO;
import com.gdm.dao.VistoriaDAO;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VistoriaBean implements Serializable {

	/// SEQUENCIA DE VARIÁVEIS LOCAIS -----------------

	private Long indenticaVe = 0L;
	private List<Vistoria> vistorias;
	List<CavaloCarroceria> semireboques;
	List<CavaloCarroceria> reboques;
	List<CavaloCarroceria> carrocerias;
	List<CavaloCarroceria> cavalos;
	List<CavaloCarroceria> caminhoes;
	List<Veiculo> veiculos;
	List<TipoConjunto> tipoConjuntos;
	List<Transportadora> transportadoras;
	List<CavaloCarroceria> cavaloCarrocerias;
	private Vistoria vistoria;
	private List<String> imagens;
	private int comprimentoDigitado;
	private String mensagemComprimento2;
	private double TaraDigitada;
	private double LotacaoDigitada;
	private double PbtDigitado;
	private String mensagemPlaqueta;
	private Boolean tanqueSuplemSelecionado;
	private String mensagemTanqueSuplem;
	private double CapacidadeConjuntos;
	private String codUnico;
	private Veiculo veiculo;
	private String mensagemCombinacao;
	private Boolean possuiPlaqSelecionado;
	private int cavaloSelecionado;
	private int carroceria1Selecionada;
	private int carroceria2Selecionada;
	private int carroceria3Selecionada;
	private String mensagemTanqueSuplementarObs;
	private Veiculo resultadoVeiculoPlaca;
	private List<String> imagemCavalo;
	private List<String> imagemCarroceria1;
	private List<String> imagemCarroceria2;
	private List<String> imagemCarroceria3;
	private List<String> imagemVeiculoGdm;
	private int indiceTabView;

	public int getIndiceTabView() {
		return indiceTabView;
	}

	public void setIndiceTabView(int indiceTabView) {
		this.indiceTabView = indiceTabView;
	}

	// --------------------------------------------------------------------
	// LISTAR TODAS AS VISTORIAS
	@PostConstruct
	public void listar() {
		try {
			VistoriaDAO vistoriaDao = new VistoriaDAO();
			vistorias = vistoriaDao.listar();

		} catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addFlashGlobalError("Ocorreu erro ao tentar listar as vistorias");
			erro.printStackTrace();
		}
	}
	// --------------------------------------------------------------------

	// CRIAR NOVA VISTORIA
	public void novo() {
		setIndiceTabView(0);
		try {
			// CHAMAR MÉTODO DE LIMPAR OS CAMPOS
			limparCampos();

			vistoria = new Vistoria();

			// LISTAR AS CHAVES ESTRANGEIRAS
			CavaloCarroceriaDAO cavaloCarroceriaDAO = new CavaloCarroceriaDAO();
			TransportadoraDAO transportadoraDAO = new TransportadoraDAO();
			TipoConjuntoDAO tipoConjuntoDAO = new TipoConjuntoDAO();
			VeiculoDAO veiculoDAO = new VeiculoDAO();

			// LISTAR DADOS
			semireboques = cavaloCarroceriaDAO.listarSemiReboque();
			reboques = cavaloCarroceriaDAO.listarReboque();
			carrocerias = cavaloCarroceriaDAO.listarCarrocerias();
			cavalos = cavaloCarroceriaDAO.listarCavalos();
			caminhoes = cavaloCarroceriaDAO.listarCaminhao();
			veiculos = veiculoDAO.listar();
			tipoConjuntos = tipoConjuntoDAO.listar();
			transportadoras = transportadoraDAO.listar();

			// COMPLEMENTAR AS LISTAS COM DEMAIS DADOS
			cavalos.addAll(cavalos.size(), caminhoes);
			carrocerias.addAll(carrocerias.size(), reboques);
			carrocerias.addAll(carrocerias.size(), semireboques);
			vistoria.setPossuiTanqueSuplementar(false);
			vistoria.setSemPlaqueta(true);

			// ---------------------------------------------------------
			// FALTA A CLASSE USUARIO E EMPRESA ---> SALVAR USUARIO QUE REALIZOU
			// A VISTORIA
			// VINCULAR EMPRESA NA VISTORIA PARA SEPARAR
			// ------------------------------------------------------
			//

		} catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addFlashGlobalError("Ocorreu erro ao tentar criar nova vistoria");
			erro.printStackTrace();
		}
	}
	// ----------------------------------------------------------------------

	public void salvar() {
		Locale local = new Locale("pt", "BR");
		Locale.setDefault(local);
		try {

			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			Usuario usuario = autenticacaoBean.getUsuarioLogado();
			// atraves do usuario logado eu pego qual a empresa que ira salva
			vistoria.setEmpresa(usuario.getEmpresa());
			// CRIAR DATA ATUAL
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime agora = LocalDateTime.now();
			formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String agoraFormatado = agora.format(formatter);
			vistoria.setDataLancamento(agoraFormatado);
			// ----------------------------
			VistoriaDAO vistoriaDao = new VistoriaDAO();
			vistoriaDao.merge(vistoria);

			// ----GERAR CHAVES ESTRANGEIRAS DA CLASSE
			// LISTAR AS CHAVES ESTRANGEIRAS
			CavaloCarroceriaDAO cavaloCarroceriaDAO = new CavaloCarroceriaDAO();
			TransportadoraDAO transportadoraDAO = new TransportadoraDAO();
			TipoConjuntoDAO tipoConjuntoDao = new TipoConjuntoDAO();
			VeiculoDAO veiculoDAO = new VeiculoDAO();

			// LISTAR DADOS
			semireboques = cavaloCarroceriaDAO.listarSemiReboque();
			reboques = cavaloCarroceriaDAO.listarReboque();
			carrocerias = cavaloCarroceriaDAO.listarCarrocerias();
			cavalos = cavaloCarroceriaDAO.listarCavalos();
			caminhoes = cavaloCarroceriaDAO.listarCaminhao();
			veiculos = veiculoDAO.listar();
			tipoConjuntos = tipoConjuntoDao.listar();
			transportadoras = transportadoraDAO.listar();

			// COMPLEMENTAR AS LISTAS COM DEMAIS DADOS
			cavalos.addAll(cavalos.size(), caminhoes);
			carrocerias.addAll(carrocerias.size(), reboques);
			carrocerias.addAll(carrocerias.size(), semireboques);

			// ----CRIAR UMA NOVA VISTORIA
			vistoria = new Vistoria();

			// LISTAR VISTORIAS PARA ATUALIZAR A PAGINA
			vistorias = vistoriaDao.listarPorEmpresa();

			// MOSTRAR MENSAGEM DE SUCESSO
			Messages.addGlobalInfo("Vistoria salva com sucesso!");

			// CHAMAR MÉTODO DE LIMPAR OS CAMPOS
			limparCampos();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu erro ao tentar salvar a vistoria");
			erro.printStackTrace();
		}
	}
	// ------------------------------------------------------------------------

	public void excluir(ActionEvent evento) {
		try {
			vistoria = (Vistoria) evento.getComponent().getAttributes().get("vistoriaSelecionada");
			VistoriaDAO vistoriaDAO = new VistoriaDAO();
			vistoriaDAO.excluir(vistoria);

			// ATUALIZAR LISTA DE VISTORIAS
			vistorias = vistoriaDAO.listarPorEmpresa();

			// ADICIONAR MENSAGEM GLOBAL DE ITEM EXCLUIDO COM SUCESSO
			Messages.addFlashGlobalInfo("Vistoria excluida com sucesso!");

		} catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addFlashGlobalError("Ocorreu erro ao tentar excluir a vistoria");
			erro.printStackTrace();
		}
	}
	// ------------------------------------------------------------------------

	// COMANDO PARA EDITAR A VISTORIA
	public void editar(ActionEvent evento) {
		try {
			// CAPTURAR O EVENTO VISTORIA NA TELA
			vistoria = (Vistoria) evento.getComponent().getAttributes().get("vistoriaSelecionada");

			// LISTAR AS CHAVES ESTRANGEIRAS
			CavaloCarroceriaDAO cavaloCarroceriaDAO = new CavaloCarroceriaDAO();
			TransportadoraDAO transportadoraDAO = new TransportadoraDAO();
			TipoConjuntoDAO tipoConjuntoDao = new TipoConjuntoDAO();
			VeiculoDAO veiculoDAO = new VeiculoDAO();

			// LISTAR DADOS
			semireboques = cavaloCarroceriaDAO.listarSemiReboque();
			reboques = cavaloCarroceriaDAO.listarReboque();
			carrocerias = cavaloCarroceriaDAO.listarCarrocerias();
			cavalos = cavaloCarroceriaDAO.listarCavalos();
			caminhoes = cavaloCarroceriaDAO.listarCaminhao();
			veiculos = veiculoDAO.listar();
			tipoConjuntos = tipoConjuntoDao.listar();
			transportadoras = transportadoraDAO.listar();

			// COMPLEMENTAR AS LISTAS COM DEMAIS DADOS
			cavalos.addAll(cavalos.size(), caminhoes);
			carrocerias.addAll(carrocerias.size(), reboques);
			carrocerias.addAll(carrocerias.size(), semireboques);

			// CHAMAR O PROCEDIMENTO DE ATRIBUIÇÃO DE IMAGENS
			atribuirImagensEditar();

		} catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addFlashGlobalError("Não foi possível alterar a vistoria");
			erro.printStackTrace();
		}
	}

	// ---------------------------------------------------------------------------
	/// METODO CARROCERIA / CAVALOS PARA POPULAR
	public void carregarImagemCavalo() {

		imagemCavalo = new ArrayList<String>();
		for (CavaloCarroceria cavaloCarroceria : cavalos) {
			if (cavaloCarroceria.getCodigo() == vistoria.getCavalo().getCodigo()) {
				imagemCavalo.add(cavaloCarroceria.getCodigo() + ".png");
				vistoria.setImagem1(cavaloCarroceria.getCodigo() + ".png");
				System.out.println("IMAGEM = " + vistoria.getImagem1());
			}
		}

	}

	// ---------------------------------------------------------------------------
	/// METODO CARROCERIA / CAVALOS PARA POPULAR
	public void carregarImagemCarroceria1() {
		imagemCarroceria1 = new ArrayList<String>();
		for (CavaloCarroceria cavaloCarroceria : carrocerias) {
			if (cavaloCarroceria.getCodigo() == vistoria.getCarroceria1().getCodigo()) {
				imagemCarroceria1.add(cavaloCarroceria.getCodigo() + ".png");
				vistoria.setImagem2(cavaloCarroceria.getCodigo() + ".png");
			}
		}
	}

	// ---------------------------------------------------------------------------
	/// METODO CARROCERIA / CAVALOS PARA POPULAR
	public void carregarImagemCarroceria2() {
		imagemCarroceria2 = new ArrayList<String>();
		for (CavaloCarroceria cavaloCarroceria : carrocerias) {
			if (cavaloCarroceria.getCodigo() == vistoria.getCarroceria2().getCodigo()) {
				imagemCarroceria2.add(cavaloCarroceria.getCodigo() + ".png");
				vistoria.setImagem3(cavaloCarroceria.getCodigo() + ".png");
			}
		}

	}

	// ---------------------------------------------------------------------------
	/// METODO CARROCERIA / CAVALOS PARA POPULAR
	public void carregarImagemCarroceria3() {
		imagemCarroceria3 = new ArrayList<String>();
		for (CavaloCarroceria cavaloCarroceria : carrocerias) {
			if (cavaloCarroceria.getCodigo() == vistoria.getCarroceria3().getCodigo()) {
				imagemCarroceria3.add(cavaloCarroceria.getCodigo() + ".png");
				vistoria.setImagem4(cavaloCarroceria.getCodigo() + ".png");
			}
		}

	}

	// --------------------------------------------------------------------------
	/// METODO CARROCERIA / CAVALOS PARA POPULAR
	public void carregarImagemVeiculoGdm() {

		imagemVeiculoGdm = new ArrayList<String>();
		if (veiculo.getCodigo() != vistoria.getVeiculoResultadoCombinacao().getCodigo()) {
			imagemVeiculoGdm = new ArrayList<String>();
			imagemVeiculoGdm.add("0.png");
			vistoria.setImagemCombinacaoGdm("0.png");
		}
		for (Veiculo veiculo : veiculos) {
			//
			// if (veiculo.getCodigo() !=
			// vistoria.getVeiculoResultadoCombinacao().getCodigo()) {
			// imagemVeiculoGdm = new ArrayList<String>();
			// imagemVeiculoGdm.add("0.png");
			// vistoria.setImagemCombinacaoGdm("0.png");
			// }
			if (veiculo.getCodigo() == vistoria.getVeiculoResultadoCombinacao().getCodigo()) {
				imagemVeiculoGdm = new ArrayList<String>();
				imagemVeiculoGdm.add(veiculo.getCodigo() + ".png");
				System.out.println("RESULTADO: " + vistoria.getVeiculoResultadoCombinacao().getCodigo());
				System.out.println("TAMANO DA LISTA " + veiculos.size());
				vistoria.setImagemCombinacaoGdm(veiculo.getCodigo() + ".png");
			}
		}



	}

	// IDENTIFICAR VEÍCULO GDM
	public void identificarVeiculoGdm() {
		VeiculoDAO veiculoDAO = new VeiculoDAO();

		// Verificar quais conjuntos foram informados
		if (vistoria.getCavalo() != null && vistoria.getCarroceria1() == null && vistoria.getCarroceria2() == null
				&& vistoria.getCarroceria3() == null) {
			setCapacidadeConjuntos(vistoria.getCavalo().getCapacidade());
			System.out.println("Carroceria 1 é nulo, mas cavalo = " + getCapacidadeConjuntos());
			// atribuir o codigo unico a variavel com base na junção dos codigos
			// de cada
			// conjunto
			//
			// nesse caso apenas o cavalo foi informado, capturar o codigo do
			// cavalo e
			// definir zero para as carrocerias
			codUnico = String.valueOf(vistoria.getCavalo().getCodigo()) + "000";
			//
			System.out.println("Codigo do veiculo unico = " + getCodUnico());
			// usar try para tratar erro caso nao existe a combinacao no banco
			try {
				//
				// buscar no banco um veiculo com o codigo configurado
				// CODCAVALO+000
				veiculo = veiculoDAO.buscarVeiculoGDM(codUnico);
				//
				indenticaVe = veiculo.getCodigo();
				// Retornar o resultado na variavel
				vistoria.setVeiculoResultadoCombinacao(veiculo);
				// criar mensagem de exibição concatenando as informações do
				// veiculo
				mensagemCombinacao = (veiculo.getApelido() + " - PBT: " + veiculo.getCapacidadePBT() + " - "
						+ veiculo.getQtdEixos() + " EIXOS");
				vistoria.setCombinacaoCorreta(true);
				vistoria.setResultadoVistoriaSistema("APROVADO");
				//
				// caso ocorra erro no comando anterior tratar com catch
			} catch (RuntimeException erro) {
				// Configurar mensagem de erro (veiculo nao encontrato no banco)
				mensagemCombinacao = ("O CONJUNTO INFORMADO ESTÁ INCORRETO OU NÃO É HOMOLOGADO");
				erro.printStackTrace();
				System.out.println("O conjunto selecionado está incorreto");
				vistoria.setCombinacaoCorreta(false);
				vistoria.setResultadoVistoriaSistema("REPROVADO");
			}
		}
		if (vistoria.getCavalo() != null && vistoria.getCarroceria1() != null && vistoria.getCarroceria2() == null
				&& vistoria.getCarroceria3() == null) {
			setCapacidadeConjuntos(vistoria.getCavalo().getCapacidade() + vistoria.getCarroceria1().getCapacidade());
//			System.out.println("Carroceria 2 é nulo, mas cavalo e carroceria 1 = " + getCapacidadeConjuntos());
	
			codUnico = String.valueOf(vistoria.getCavalo().getCodigo())
					+ String.valueOf(vistoria.getCarroceria1().getCodigo()) + "00";
	
			System.out.println("Codigo do veiculo unico = " + getCodUnico());
			try {
				veiculo = veiculoDAO.buscarVeiculoGDM(codUnico);
				indenticaVe = veiculo.getCodigo();
				vistoria.setVeiculoResultadoCombinacao(veiculo);
				mensagemCombinacao = (veiculo.getApelido() + " - PBT: " + veiculo.getCapacidadePBT() + " - "
						+ veiculo.getQtdEixos() + " EIXOS");
				vistoria.setCombinacaoCorreta(true);
				vistoria.setResultadoVistoriaSistema("APROVADO");
			} catch (RuntimeException erro) {
				mensagemCombinacao = ("O CONJUNTO INFORMADO ESTÁ INCORRETO OU NÃO É HOMOLOGADO");
				erro.printStackTrace();
				System.out.println("O conjunto selecionado está incorreto");
				vistoria.setCombinacaoCorreta(false);
				vistoria.setResultadoVistoriaSistema("REPROVADO");
			}
		}
		if (vistoria.getCavalo() != null && vistoria.getCarroceria1() != null && vistoria.getCarroceria2() != null
				&& vistoria.getCarroceria3() == null) {
			setCapacidadeConjuntos(vistoria.getCavalo().getCapacidade() + vistoria.getCarroceria1().getCapacidade()
					+ vistoria.getCarroceria2().getCapacidade());
			System.out.println(
					"Carroceria 2 é nulo, mas cavalo + carroceria 1 + carroceria 2 = " + getCapacidadeConjuntos());
			//
			codUnico = String.valueOf(vistoria.getCavalo().getCodigo())
					+ String.valueOf(vistoria.getCarroceria1().getCodigo())
					+ String.valueOf(vistoria.getCarroceria2().getCodigo()) + "0";
			//
			System.out.println("Codigo do veiculo unico = " + getCodUnico());
			try {
				veiculo = veiculoDAO.buscarVeiculoGDM(codUnico);
				indenticaVe = veiculo.getCodigo();
				vistoria.setVeiculoResultadoCombinacao(veiculo);
				mensagemCombinacao = (veiculo.getApelido() + " - PBT: " + veiculo.getCapacidadePBT() + " - "
						+ veiculo.getQtdEixos() + " EIXOS");
				vistoria.setCombinacaoCorreta(true);
				vistoria.setResultadoVistoriaSistema("APROVADO");
			} catch (RuntimeException erro) {
				mensagemCombinacao = ("O CONJUNTO INFORMADO ESTÁ INCORRETO OU NÃO É HOMOLOGADO");
				erro.printStackTrace();
				System.out.println("O conjunto selecionado está incorreto");
				vistoria.setCombinacaoCorreta(false);
				vistoria.setResultadoVistoriaSistema("REPROVADO");
			}
		}
		//
		if (vistoria.getCavalo() != null && vistoria.getCarroceria1() != null && vistoria.getCarroceria2() != null
				&& vistoria.getCarroceria3() != null) {
			setCapacidadeConjuntos(vistoria.getCavalo().getCapacidade() + vistoria.getCarroceria1().getCapacidade()
					+ vistoria.getCarroceria2().getCapacidade() + vistoria.getCarroceria3().getCapacidade());
			System.out.println("Nenhum conjunto é nulo. Total = " + getCapacidadeConjuntos());
			//
			codUnico = String.valueOf(vistoria.getCavalo().getCodigo())
					+ String.valueOf(vistoria.getCarroceria1().getCodigo())
					+ String.valueOf(vistoria.getCarroceria2().getCodigo())
					+ String.valueOf(vistoria.getCarroceria3().getCodigo());
			try {
				veiculo = veiculoDAO.buscarVeiculoGDM(codUnico);
				indenticaVe = veiculo.getCodigo();
				vistoria.setVeiculoResultadoCombinacao(veiculo);
				mensagemCombinacao = (veiculo.getApelido() + " - PBT: " + veiculo.getCapacidadePBT() + " - "
						+ veiculo.getQtdEixos() + " EIXOS");
				vistoria.setCombinacaoCorreta(true);
				vistoria.setResultadoVistoriaSistema("APROVADO");
			} catch (RuntimeException erro) {
				mensagemCombinacao = ("O CONJUNTO INFORMADO ESTÁ INCORRETO OU NÃO É HOMOLOGADO");
				erro.printStackTrace();
				vistoria.setCombinacaoCorreta(false);
				vistoria.setResultadoVistoriaSistema("REPROVADO");
			}
		}

	}



	// -------CLASSE DE IMAGENS PARA OS CONJUNTOS
	public void imagensConjuntos() {

		imagens = new ArrayList<String>();
		try {
			for (int i = 0; i < 40; i++) {

				imagens.add(i + ".png");
			}
		} catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addFlashGlobalError("Não foi possível listar as imagens");
			erro.printStackTrace();
		}

	}

	// --------------------------------------------------------------------------
	// ----MENSAGEM DE REDUÇÃO DE CAPACIDADE DO VEÍCULO DEVIDO O COMPRIMENTO
	public void MensagemComprimento(SlideEndEvent evento) {
		//

		setComprimentoDigitado(evento.getValue());
		System.out.println("Variavel numerica evento " + evento.getValue());
		System.out.println("Comprimento digitado capturado " + getComprimentoDigitado());
		vistoria.setComprimento(evento.getValue());

		vistoria.setTipoVeiculo("TAM " + evento.getValue() + "M - " + "SEM LIMITE");

		if (evento.getValue() < 16) {
			FacesContext.getCurrentInstance().addMessage("Msgcomp", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
					"A capacidade desse veículo deve ser reduzida a 45t"));
			mensagemComprimento2 = "VEICULO MENOR QUE 16M - CAPACIDADE PBT LIMITADA A 45t";
			System.out.println("Comprimento capturado: " + mensagemComprimento2);
			vistoria.setTipoVeiculo("TAM " + evento.getValue() + "M - " + "LIMITE 45t");
			String Statsis = "APROVADO";
			if (vistoria.getResultadoVistoriaSistema() == Statsis) {
				vistoria.setResultadoVistoriaSistema("AP.RESTRICAO");
			}

		}
		if (evento.getValue() >= 30.0) {
			FacesContext.getCurrentInstance().addMessage("Msgcomp",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Este veículo precisa de escolta"));
			mensagemComprimento2 = "VEICULO MAIOR QUE 30M - PRECISA DE ESCOLTA";
			System.out.println("Comprimento capturado: " + mensagemComprimento2);
			vistoria.setTipoVeiculo("TAM " + evento.getValue() + "M - " + "PBT");
		}
	}

	// -----------------------------------------------------------------------------------------
	/// VERIFICAR DADOS DA PLAQUETA

	/// TARA
	/// -----------------------------------------------------------------------------------
	public void tara() {

		try {

			setTaraDigitada(vistoria.getTara());
			vistoria.setResultadoPlaqueta("CORRETA");
			PbtDigitado = vistoria.getPbt();

			// REGRAS PLAQUETA TARA
			if (vistoria.getPbt() != 0 && vistoria.getLotacao() != 0) {
				if (vistoria.getCavalo() != null) {
					double resultadoPbt = getLotacaoDigitada() + getTaraDigitada();
					if (resultadoPbt != PbtDigitado) {
						FacesContext.getCurrentInstance().addMessage("Msgplaqueta", new FacesMessage(
								FacesMessage.SEVERITY_INFO, "Info", "Plaqueta com valores incorretos"));
						setMensagemPlaqueta("PLAQUETA COM VALORES INCORRETOS");
						vistoria.setResultadoPlaqueta("INCORRETA");
						vistoria.setResultadoVistoriaSistema("REPROVADO");
					}
					if (vistoria.getVeiculoResultadoCombinacao().getCapacidadePBT() != PbtDigitado) {
						FacesContext.getCurrentInstance().addMessage("Msgplaqueta", new FacesMessage(
								FacesMessage.SEVERITY_INFO, "Info", "Pbt diferente da capacidade do veículo"));
						setMensagemPlaqueta("PBT PLAQUETA DIFERENTE DA CAPACIDADE DO VEÍCULO");
						vistoria.setResultadoPlaqueta("INCORRETA");
						vistoria.setResultadoVistoriaSistema("REPROVADO");
					}
					if (vistoria.getVeiculoResultadoCombinacao().getCapacidadePBT() == vistoria.getPbt()) {

						setMensagemPlaqueta("");
						vistoria.setResultadoPlaqueta("CORRETA");
						vistoria.setResultadoVistoriaSistema("APROVADO");
						if (resultadoPbt != vistoria.getPbt()) {
							setMensagemPlaqueta("DADOS: TARA E LOTAÇÃO INCORRETOS");
							vistoria.setResultadoPlaqueta("INCORRETA");
							vistoria.setResultadoVistoriaSistema("REPROVADO");
						}

					}

				} else {
					FacesContext.getCurrentInstance().addMessage("Msgplaqueta",
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Veículo não selecionado"));
					vistoria.setResultadoPlaqueta("");
					vistoria.setResultadoVistoriaSistema("");
					mensagemPlaqueta = "SELECIONE O VEÍCULO";
				}
			} else {

				setMensagemPlaqueta("");
				vistoria.setResultadoPlaqueta("CORRETA");
			}
		} catch (RuntimeException e) {

			// TODO: handle exception
			setMensagemPlaqueta("VEICULO NÃO HOMOLOGADO OU CONJUNTO ERRADO");
			vistoria.setResultadoPlaqueta("INCORRETA");
			vistoria.setResultadoVistoriaSistema("REPROVADO");
		}
	}

	/// LOTACAO
	/// ---------------------------------------------------------------------------------
	public void lotacao() {

		try {

			setLotacaoDigitada(vistoria.getLotacao());
			vistoria.setResultadoPlaqueta("CORRETA");
			PbtDigitado = vistoria.getPbt();
			setLotacaoDigitada(vistoria.getLotacao());

			// REGRAS PLAQUETA LOTACAO
			if (vistoria.getPbt() != 0 && vistoria.getTara() != 0) {
				if (vistoria.getCavalo() != null) {
					double resultadoPbt = getLotacaoDigitada() + getTaraDigitada();
					if (resultadoPbt != PbtDigitado) {
						FacesContext.getCurrentInstance().addMessage("Msgplaqueta", new FacesMessage(
								FacesMessage.SEVERITY_INFO, "Info", "Plaqueta com valores incorretos"));
						setMensagemPlaqueta("PLAQUETA COM VALORES INCORRETOS");
						vistoria.setResultadoPlaqueta("INCORRETA");
						vistoria.setResultadoVistoriaSistema("REPROVADO");

					}
					if (vistoria.getVeiculoResultadoCombinacao().getCapacidadePBT() != PbtDigitado) {
						FacesContext.getCurrentInstance().addMessage("Msgplaqueta", new FacesMessage(
								FacesMessage.SEVERITY_INFO, "Info", "Pbt diferente da capacidade do veículo"));
						setMensagemPlaqueta("PBT PLAQUETA DIFERENTE DA CAPACIDADE DO VEÍCULO");
						vistoria.setResultadoPlaqueta("INCORRETA");
						vistoria.setResultadoVistoriaSistema("REPROVADO");
					}
					if (vistoria.getVeiculoResultadoCombinacao().getCapacidadePBT() == vistoria.getPbt()) {

						setMensagemPlaqueta("");
						vistoria.setResultadoPlaqueta("CORRETA");
						vistoria.setResultadoVistoriaSistema("APROVADO");
						if (resultadoPbt != vistoria.getPbt()) {
							setMensagemPlaqueta("DADOS: TARA E LOTAÇÃO INCORRETOS");
							vistoria.setResultadoPlaqueta("INCORRETA");
							vistoria.setResultadoVistoriaSistema("REPROVADO");
						}
					}

				} else {
					FacesContext.getCurrentInstance().addMessage("Msgplaqueta",
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Veículo não selecionado"));
					vistoria.setResultadoPlaqueta("");
					vistoria.setResultadoVistoriaSistema("");
					mensagemPlaqueta = "SELECIONE O VEÍCULO";
				}
			} else {

				setMensagemPlaqueta("");
				vistoria.setResultadoPlaqueta("");
			}
		} catch (RuntimeException e) {
			setMensagemPlaqueta("VEICULO NÃO HOMOLOGADO OU CONJUNTO ERRADO");
			vistoria.setResultadoPlaqueta("INCORRETA");
			vistoria.setResultadoVistoriaSistema("REPROVADO");
			// TODO: handle exception
		}
	}

	/// PBT
	/// -------------------------------------------------------------------------------------
	public void pbt() {

		try {

			setPbtDigitado(vistoria.getPbt());
			vistoria.setResultadoPlaqueta("CORRETA");
			PbtDigitado = vistoria.getPbt();
			setLotacaoDigitada(vistoria.getLotacao());

			// ------------REGRAS DE PLAQUETA
			if (vistoria.getTara() != 0 && vistoria.getLotacao() != 0) {
				if (vistoria.getCavalo() != null) {
					double resultadoPbt = getLotacaoDigitada() + getTaraDigitada();
					if (resultadoPbt != PbtDigitado) {
						FacesContext.getCurrentInstance().addMessage("Msgplaqueta", new FacesMessage(
								FacesMessage.SEVERITY_INFO, "Info", "Plaqueta com valores incorretos"));
						setMensagemPlaqueta("PLAQUETA COM VALORES INCORRETOS");
						vistoria.setResultadoPlaqueta("INCORRETA");
						vistoria.setResultadoVistoriaSistema("REPROVADO");
					}
					if (vistoria.getVeiculoResultadoCombinacao().getCapacidadePBT() != vistoria.getPbt()) {
						FacesContext.getCurrentInstance().addMessage("Msgplaqueta", new FacesMessage(
								FacesMessage.SEVERITY_INFO, "Info", "Pbt diferente da capacidade do veículo"));
						setMensagemPlaqueta("PBT PLAQUETA DIFERENTE DA CAPACIDADE DO VEÍCULO");
						vistoria.setResultadoPlaqueta("INCORRETA");
						vistoria.setResultadoVistoriaSistema("REPROVADO");
					}
					if (vistoria.getVeiculoResultadoCombinacao().getCapacidadePBT() == vistoria.getPbt()) {

						setMensagemPlaqueta("");
						vistoria.setResultadoPlaqueta("CORRETA");
						vistoria.setResultadoVistoriaSistema("APROVADO");
						if (resultadoPbt != vistoria.getPbt()) {
							setMensagemPlaqueta("DADOS: TARA E LOTAÇÃO INCORRETOS");
							vistoria.setResultadoPlaqueta("INCORRETA");
							vistoria.setResultadoVistoriaSistema("REPROVADO");
						}

					}

				} else {
					FacesContext.getCurrentInstance().addMessage("Msgplaqueta",
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Veículo não selecionado"));
					vistoria.setResultadoPlaqueta("");
					vistoria.setResultadoVistoriaSistema("");
					mensagemPlaqueta = "SELECIONE O VEÍCULO";
				}
			} else {

				setMensagemPlaqueta("");
				vistoria.setResultadoPlaqueta("");
			}
		} catch (RuntimeException erro) {

			setMensagemPlaqueta("VEICULO NÃO HOMOLOGADO OU CONJUNTO ERRADO");
			vistoria.setResultadoPlaqueta("INCORRETA");
			vistoria.setResultadoVistoriaSistema("REPROVADO");
		}
	}

	// ------------------------------------------------------------------------------------------
	/// VERIFICAR TANQUE SUPLEMENTAR
	public void tanqueSupRes() {
		if (tanqueSuplemSelecionado == true) {

			vistoria.setPossuiTanqueSuplementar(getTanqueSuplemSelecionado());
			mensagemTanqueSuplem = "ACONSELHÁVEL O TANQUE SUPLEMENTAR ESTAR SEMPRE CHEIO NA PESAGEM DA ORIGEM E NAS RODOVIAS";
			mensagemTanqueSuplementarObs = "ACONSELHÁVEL O TANQUE SUPLEMENTAR ESTAR SEMPRE CHEIO NA PESAGEM DA ORIGEM E NAS RODOVIAS";
			String Statsis = "APROVADO";
			if (vistoria.getResultadoVistoriaSistema() == Statsis) {
				vistoria.setResultadoVistoriaSistema("AP.RESTRICAO");
			}

			//
		} else {
			vistoria.setPossuiTanqueSuplementar(getTanqueSuplemSelecionado());
			mensagemTanqueSuplem = "";
			mensagemTanqueSuplementarObs = "";
			if (vistoria.getPbt() == vistoria.getVeiculoResultadoCombinacao().getCapacidadePBT()) {
				vistoria.setResultadoVistoriaSistema("APROVADO");
			}

		}
	}

	// ------------------------------------------------------------------------------------------

	/// LIMPAR VALORES DE VARIÁVEIS LOCAIS
	public void limparCampos() {
		imagemVeiculoGdm = new ArrayList<String>();
		comprimentoDigitado = 0;
		mensagemComprimento2 = "";
		TaraDigitada = 0;
		LotacaoDigitada = 0;
		PbtDigitado = 0;
		mensagemPlaqueta = "";
		tanqueSuplemSelecionado = false;
		mensagemTanqueSuplem = "";
		CapacidadeConjuntos = 0;
		codUnico = "0000";
		mensagemCombinacao = "";
		possuiPlaqSelecionado = false;
		cavaloSelecionado = 0;
		carroceria1Selecionada = 0;
		carroceria2Selecionada = 0;
		carroceria3Selecionada = 0;
		mensagemTanqueSuplementarObs = "";

		try {

			if (imagemCavalo.size() == 1) {
				imagemCavalo.clear();
			}
			if (imagemCarroceria1.size() == 1) {
				imagemCarroceria1.clear();
			}
			if (imagemCarroceria2.size() == 1) {
				imagemCarroceria2.clear();
			}
			if (imagemCarroceria3.size() == 1) {
				imagemCarroceria3.clear();
			}
			imagemVeiculoGdm.add("0.png");

		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println("tamanho da lista de imagens" + imagemVeiculoGdm.size());
		}
	}

	// -------------------------------------------------------------------------------------------
	/// EDITAR A VISTORIA - ATRIBUIR AS IMAGENS AOS QUADRANTES
	public void atribuirImagensEditar() {
		try {
			imagemVeiculoGdm = new ArrayList<String>();
			imagemVeiculoGdm.add(vistoria.getImagemCombinacaoGdm());

			imagemCavalo = new ArrayList<String>();
			imagemCavalo.add(vistoria.getImagem1());

			imagemCarroceria1 = new ArrayList<String>();
			imagemCarroceria1.add(vistoria.getImagem2());

			imagemCarroceria2 = new ArrayList<String>();
			imagemCarroceria2.add(vistoria.getImagem3());

			imagemCarroceria3 = new ArrayList<String>();
			imagemCarroceria3.add(vistoria.getImagem4());

			// ATRIBUIR A DESCRIÇÃO DO VEÍCULO GDM
			if (vistoria.getVeiculoResultadoCombinacao() != null) {
				Veiculo veiculoAux = new Veiculo();
				VeiculoDAO veiculoDAO = new VeiculoDAO();
				veiculoAux = veiculoDAO.Buscar(vistoria.getVeiculoResultadoCombinacao().getCodigo());

				mensagemCombinacao = (veiculoAux.getApelido() + " - PBT: " + veiculoAux.getCapacidadePBT() + " - "
						+ veiculoAux.getQtdEixos() + " EIXOS");
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	// ------------------------------------------------------------------------------------------
	

	public List<Vistoria> getVistorias() {
		return vistorias;
	}

	public List<String> getImagemVeiculoGdm() {
		return imagemVeiculoGdm;
	}

	public void setImagemVeiculoGdm(List<String> imagemVeiculoGdm) {
		this.imagemVeiculoGdm = imagemVeiculoGdm;
	}

	public List<String> getImagemCarroceria1() {
		return imagemCarroceria1;
	}

	public void setImagemCarroceria1(List<String> imagemCarroceria1) {
		this.imagemCarroceria1 = imagemCarroceria1;
	}

	public List<String> getImagemCarroceria2() {
		return imagemCarroceria2;
	}

	public void setImagemCarroceria2(List<String> imagemCarroceria2) {
		this.imagemCarroceria2 = imagemCarroceria2;
	}

	public List<String> getImagemCarroceria3() {
		return imagemCarroceria3;
	}

	public void setImagemCarroceria3(List<String> imagemCarroceria3) {
		this.imagemCarroceria3 = imagemCarroceria3;
	}

	public List<String> getImagemCavalo() {
		return imagemCavalo;
	}

	public void setImagemCavalo(List<String> imagemCavalo) {
		this.imagemCavalo = imagemCavalo;
	}

	public Veiculo getResultadoVeiculoPlaca() {
		return resultadoVeiculoPlaca;
	}

	public void setResultadoVeiculoPlaca(Veiculo resultadoVeiculoPlaca) {
		this.resultadoVeiculoPlaca = resultadoVeiculoPlaca;
	}

	public String getMensagemTanqueSuplementarObs() {
		return mensagemTanqueSuplementarObs;
	}

	public void setMensagemTanqueSuplementarObs(String mensagemTanqueSuplementarObs) {
		this.mensagemTanqueSuplementarObs = mensagemTanqueSuplementarObs;
	}

	public List<CavaloCarroceria> getCavaloCarrocerias() {
		return cavaloCarrocerias;
	}

	public void setCavaloCarrocerias(List<CavaloCarroceria> cavaloCarrocerias) {
		this.cavaloCarrocerias = cavaloCarrocerias;
	}

	public Boolean getPossuiPlaqSelecionado() {
		return possuiPlaqSelecionado;
	}

	public void setPossuiPlaqSelecionado(Boolean possuiPlaqSelecionado) {
		this.possuiPlaqSelecionado = possuiPlaqSelecionado;
	}

	public int getCavaloSelecionado() {
		return cavaloSelecionado;
	}

	public void setCavaloSelecionado(int cavaloSelecionado) {
		this.cavaloSelecionado = cavaloSelecionado;
	}

	public int getCarroceria1Selecionada() {
		return carroceria1Selecionada;
	}

	public void setCarroceria1Selecionada(int carroceria1Selecionada) {
		this.carroceria1Selecionada = carroceria1Selecionada;
	}

	public int getCarroceria2Selecionada() {
		return carroceria2Selecionada;
	}

	public void setCarroceria2Selecionada(int carroceria2Selecionada) {
		this.carroceria2Selecionada = carroceria2Selecionada;
	}

	public int getCarroceria3Selecionada() {
		return carroceria3Selecionada;
	}

	public void setCarroceria3Selecionada(int carroceria3Selecionada) {
		this.carroceria3Selecionada = carroceria3Selecionada;
	}

	public String getMensagemCombinacao() {
		return mensagemCombinacao;
	}

	public void setMensagemCombinacao(String mensagemCombinacao) {
		this.mensagemCombinacao = mensagemCombinacao;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public String getCodUnico() {
		return codUnico;
	}

	public void setCodUnico(String codUnico) {
		this.codUnico = codUnico;
	}

	public double getCapacidadeConjuntos() {
		return CapacidadeConjuntos;
	}

	public void setCapacidadeConjuntos(double capacidadeConjuntos) {
		CapacidadeConjuntos = capacidadeConjuntos;
	}

	public Boolean getTanqueSuplemSelecionado() {
		return tanqueSuplemSelecionado;
	}

	public void setTanqueSuplemSelecionado(Boolean tanqueSuplemSelecionado) {
		this.tanqueSuplemSelecionado = tanqueSuplemSelecionado;
	}

	public String getMensagemTanqueSuplem() {
		return mensagemTanqueSuplem;
	}

	public void setMensagemTanqueSuplem(String mensagemTanqueSuplem) {
		this.mensagemTanqueSuplem = mensagemTanqueSuplem;
	}

	public String getMensagemPlaqueta() {
		return mensagemPlaqueta;
	}

	public void setMensagemPlaqueta(String mensagemPlaqueta) {
		this.mensagemPlaqueta = mensagemPlaqueta;
	}

	public double getTaraDigitada() {
		return TaraDigitada;
	}

	public void setTaraDigitada(double taraDigitada) {
		TaraDigitada = taraDigitada;
	}

	public double getLotacaoDigitada() {
		return LotacaoDigitada;
	}

	public void setLotacaoDigitada(double lotacaoDigitada) {
		LotacaoDigitada = lotacaoDigitada;
	}

	public double getPbtDigitado() {
		return PbtDigitado;
	}

	public void setPbtDigitado(double pbtDigitado) {
		PbtDigitado = pbtDigitado;
	}

	public int getComprimentoDigitado() {
		return comprimentoDigitado;
	}

	public void setComprimentoDigitado(int comprimentoDigitado) {
		this.comprimentoDigitado = comprimentoDigitado;
	}

	public String getMensagemComprimento2() {
		return mensagemComprimento2;
	}

	public void setMensagemComprimento2(String mensagemComprimento2) {
		this.mensagemComprimento2 = mensagemComprimento2;
	}

	public List<String> getImagens() {
		return imagens;
	}

	public void setImagens(List<String> imagens) {
		this.imagens = imagens;
	}

	public void setVistorias(List<Vistoria> vistorias) {
		this.vistorias = vistorias;
	}

	public List<CavaloCarroceria> getSemireboques() {
		return semireboques;
	}

	public void setSemireboques(List<CavaloCarroceria> semireboques) {
		this.semireboques = semireboques;
	}

	public List<CavaloCarroceria> getReboques() {
		return reboques;
	}

	public void setReboques(List<CavaloCarroceria> reboques) {
		this.reboques = reboques;
	}

	public List<CavaloCarroceria> getCarrocerias() {
		return carrocerias;
	}

	public void setCarrocerias(List<CavaloCarroceria> carrocerias) {
		this.carrocerias = carrocerias;
	}

	public List<CavaloCarroceria> getCavalos() {
		return cavalos;
	}

	public void setCavalos(List<CavaloCarroceria> cavalos) {
		this.cavalos = cavalos;
	}

	public List<CavaloCarroceria> getCaminhoes() {
		return caminhoes;
	}

	public void setCaminhoes(List<CavaloCarroceria> caminhoes) {
		this.caminhoes = caminhoes;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public List<TipoConjunto> getTipoConjuntos() {
		return tipoConjuntos;
	}

	public void setTipoConjuntos(List<TipoConjunto> tipoConjuntos) {
		this.tipoConjuntos = tipoConjuntos;
	}

	public List<Transportadora> getTransportadoras() {
		return transportadoras;
	}

	public void setTransportadoras(List<Transportadora> transportadoras) {
		this.transportadoras = transportadoras;
	}

	public Vistoria getVistoria() {
		return vistoria;
	}

	public void setVistoria(Vistoria vistoria) {
		this.vistoria = vistoria;
	}

	public Long getIndenticaVe() {
		return indenticaVe;
	}

	public void setIndenticaVe(Long indenticaVe) {
		this.indenticaVe = indenticaVe;
	}

}
