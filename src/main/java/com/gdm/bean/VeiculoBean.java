package com.gdm.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.gdm.dao.CavaloCarroceriaDAO;
import com.gdm.dao.ToleranciaDAO;
import com.gdm.dao.VeiculoDAO;
import com.gdm.domain.CavaloCarroceria;
import com.gdm.domain.Tolerancia;
import com.gdm.domain.Veiculo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VeiculoBean implements Serializable {
	private List<String> classes = new ArrayList<>();
	private List<String> tipoEixos = new ArrayList<>();
	private Veiculo veiculo;
	private List<Tolerancia> tolerancias;

	List<CavaloCarroceria> semireboques;
	List<CavaloCarroceria> reboques;
	List<CavaloCarroceria> carrocerias;
	List<CavaloCarroceria> cavalos;
	List<CavaloCarroceria> caminhoes;

	// variaveis locais para selecao dos dados de tolerancia
	private Double toleranciaPBT;
	private Double toleranciaPorEixo;
	private Double toleranciaAux;
	private Double toleranciaTotal;

	// variaveis auxiliares para capturar e calcular o execesso de peso
	private Double g1aux;
	private Double g1Total;
	private Double g2aux;
	private Double g2Total;
	private Double g3aux;
	private Double g3Total;
	private Double g4aux;
	private Double g4Total;
	private Double g5aux;
	private Double g5Total;
	private Double g6aux;
	private Double g6Total;
	private Double g7aux;
	private Double g7Total;

	private String acao;
	// fim
	private List<Veiculo> veiculos;

	private List<String> imagens;
	private List<String> imagemCavalo;
	private List<String> imagemCarroceria1;
	private List<String> imagemCarroceria2;
	private List<String> imagemCarroceria3;

	public VeiculoBean() {
		classes.add("2C"); // OK
		classes.add("3C"); // OK
		classes.add("4C"); // OK
		classes.add("2C2");
		classes.add("2C3");
		classes.add("3C3"); // OK
		classes.add("3D3");
		classes.add("3D4"); // OK
		classes.add("3D6");
		classes.add("2S1");
		classes.add("3C2"); // OK
		classes.add("4CD"); // OK
		classes.add("2S2");
		classes.add("2I2");
		classes.add("3S1");
		classes.add("2S3"); // OK
		classes.add("3S2");// OK
		classes.add("4S3");// OK Alterado de 3s3 pra 4S3 dia 25-02-2019 
		classes.add("2I3"); // OK
		classes.add("3I2");
		classes.add("3I3");
		classes.add("2J3"); // OK
		classes.add("3J3"); // OK

		classes.add("3T4");
		classes.add("3Q4");
		classes.add("3T6"); // OK
		classes.add("3Q6");
		classes.add("5CD"); // OK
		

		tipoEixos.add("DS" + "-" + "Dianteiro Simples");
		tipoEixos.add("DD" + "-" + "Dianteiro Duplo");
		tipoEixos.add("TS" + "-" + "Tandem Simples");
		tipoEixos.add("TD" + "-" + "Tandem Duplo");
		tipoEixos.add("TT" + "-" + "Tandem Triplo");

	}

	@PostConstruct
	public void listar() {
		try {

			VeiculoDAO dao = new VeiculoDAO();
			veiculos = dao.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os veiculos");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			veiculo = new Veiculo();

			ToleranciaDAO dao = new ToleranciaDAO();
			tolerancias = dao.listar();

			// LISTAR AS CHAVES ESTRANGEIRAS
			CavaloCarroceriaDAO cavaloCarroceriaDAO = new CavaloCarroceriaDAO();

			// LISTAR DADOS
			semireboques = cavaloCarroceriaDAO.listarSemiReboque();
			reboques = cavaloCarroceriaDAO.listarReboque();
			carrocerias = cavaloCarroceriaDAO.listarCarrocerias();
			cavalos = cavaloCarroceriaDAO.listarCavalos();
			caminhoes = cavaloCarroceriaDAO.listarCaminhao();

			// COMPLEMENTAR AS LISTAS COM DEMAIS DADOS
			cavalos.addAll(cavalos.size(), caminhoes);
			carrocerias.addAll(carrocerias.size(), reboques);
			carrocerias.addAll(carrocerias.size(), semireboques);

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo veiculo");
			erro.printStackTrace();
		}
	}

	public void salvar() {

		try {
			if (veiculo.getCaminho() == null) {
				Messages.addGlobalError("O campo foto é obrigatório");
				return;
			}

			ToleranciaDAO toleranciaDAO = new ToleranciaDAO();
			tolerancias = toleranciaDAO.listar();
			atribuirCodigoUnico();

			VeiculoDAO dao = new VeiculoDAO();
			Veiculo veiculoRetorno = dao.merge(veiculo);

			// local onde ira coletar as imagens
			Path origem = Paths.get(veiculo.getCaminho());
			// local onde irar salvar as imagens
			// Path destino = Paths.get("C:/Multas 1.1/Uploads/caminhoes/" +
			// veiculoRetorno.getCodigo() + ".png");
			Path destino = Paths.get("webapp/GDM/resources/caminhoes/" + veiculoRetorno.getCodigo() + ".png");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			Messages.addGlobalInfo("Salvo com sucesso");

			veiculo = new Veiculo();

			veiculos = dao.listar();
		} catch (RuntimeException | IOException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o veiculo");
			erro.printStackTrace();
		}
	}

	public void atribuirCodigoUnico() {

		try {
			if (veiculo.getCavalo() != null && veiculo.getCarroceria1() == null && veiculo.getCarroceria2() == null
					&& veiculo.getCarroceria3() == null) {
				veiculo.setCodigoUnicoVeiculo(veiculo.getCavalo().getCodigo().toString() + "000");
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar atribuir CódigoUnico do veiculo");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {

			veiculo = (Veiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");

			VeiculoDAO dao = new VeiculoDAO();
			dao.excluir(veiculo);

			Path arquivo = Paths.get("webapp/GDM/resources/caminhoes/" + veiculo.getCodigo() + ".png");
//			Path arquivo = Paths.get("C:/Multas 1.1/Uploads/caminhoes/" + veiculo.getCodigo() + ".png");

			Messages.addGlobalInfo("Path: " + arquivo);

			Files.delete(arquivo);
			veiculos = dao.listar();
			Messages.addGlobalInfo("Veiculo removido com sucesso");
		} catch (RuntimeException | IOException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover o veiculo");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {

			// LISTAR AS CHAVES ESTRANGEIRAS
			CavaloCarroceriaDAO cavaloCarroceriaDAO = new CavaloCarroceriaDAO();

			// LISTAR DADOS
			semireboques = cavaloCarroceriaDAO.listarSemiReboque();
			reboques = cavaloCarroceriaDAO.listarReboque();
			carrocerias = cavaloCarroceriaDAO.listarCarrocerias();
			cavalos = cavaloCarroceriaDAO.listarCavalos();
			caminhoes = cavaloCarroceriaDAO.listarCaminhao();

			// COMPLEMENTAR AS LISTAS COM DEMAIS DADOS
			cavalos.addAll(cavalos.size(), caminhoes);
			carrocerias.addAll(carrocerias.size(), reboques);
			carrocerias.addAll(carrocerias.size(), semireboques);

			ToleranciaDAO toleranciaDAO = new ToleranciaDAO();
			tolerancias = toleranciaDAO.listar();

			veiculo = (Veiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");

			veiculo.setCaminho("webapp/GDM/resources/caminhoes/" + veiculo.getCodigo() + ".png");

			if (veiculo.getCavalo() == null) {
				imagemCavalo = new ArrayList<String>();
				imagemCavalo.add("0.png");
			} else {
				imagemCavalo = new ArrayList<String>();
				imagemCavalo.add(veiculo.getCavalo().getCodigo().toString() + ".png");
			}

			if (veiculo.getCarroceria1() == null) {
				imagemCarroceria1 = new ArrayList<String>();
				imagemCarroceria1.add("0.png");
			} else {
				imagemCarroceria1 = new ArrayList<String>();
				imagemCarroceria1.add(veiculo.getCarroceria1().getCodigo() + ".png");
			}

			if (veiculo.getCarroceria2() == null) {
				imagemCarroceria2 = new ArrayList<String>();
				imagemCarroceria2.add("0.png");
			} else {
				imagemCarroceria2 = new ArrayList<String>();
				imagemCarroceria2.add(veiculo.getCarroceria2().getCodigo() + ".png");
			}
			if (veiculo.getCarroceria3() == null) {
				imagemCarroceria3 = new ArrayList<String>();
				imagemCarroceria3.add("0.png");
			} else {
				imagemCarroceria3 = new ArrayList<String>();
				imagemCarroceria3.add(veiculo.getCarroceria3().getCodigo() + ".png");
			}

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar editar o veiculo");
			erro.printStackTrace();
		}

	}

	public void upload(FileUploadEvent evento) {

		try {
			// aquivo original.
			UploadedFile arquivoUpload = evento.getFile();
			// arquivo temporario dentro do sistema
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			veiculo.setCaminho(arquivoTemp.toString());
			Messages.addGlobalInfo("Upload realizado com sucesso");
		} catch (IOException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar realizar o upload de arquivo");
			erro.printStackTrace();
		}
	}

	// calcula a porcentagem em relacao a PBT
	public void calculaPBT() {

		if (veiculo.getToleranciaPbt() == null) {
			veiculo.setCapacidadePBT(veiculo.getCapacidade());
		} else {
			toleranciaTotal = veiculo.getCapacidade() + ((veiculo.getCapacidade() * veiculo.getToleranciaPbt()) / 100);
			veiculo.setCapacidadePBT(toleranciaTotal);

		}

	}

	public void calcularGs() {

		if (veiculo.getToleranciaEixo() != null) {

			veiculo.setG1PBT(veiculo.getG1());
			veiculo.setG2PBT(veiculo.getG2());
			veiculo.setG3PBT(veiculo.getG3());
			veiculo.setG4PBT(veiculo.getG4());
			veiculo.setG5PBT(veiculo.getG5());
			veiculo.setG6PBT(veiculo.getG6());
			veiculo.setG7PBT(veiculo.getG7());

			veiculo.setG1PBT(veiculo.getG1() + ((veiculo.getG1() * veiculo.getToleranciaEixo()) / 100));
			g2Total = veiculo.getG2() + ((veiculo.getG2() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG2PBT(g2Total);
			g3Total = veiculo.getG3() + ((veiculo.getG3() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG3PBT(g3Total);
			g4Total = veiculo.getG4() + ((veiculo.getG4() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG4PBT(g4Total);
			g5Total = veiculo.getG5() + ((veiculo.getG5() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG5PBT(g5Total);
			g6Total = veiculo.getG6() + ((veiculo.getG6() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG6PBT(g6Total);
			g7Total = veiculo.getG7() + ((veiculo.getG7() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG7PBT(g7Total);
		}

	}

	public void calculaG1() {

		if (veiculo.getToleranciaEixo() == null) {
			veiculo.setG1PBT(veiculo.getG1());
		} else {
			System.out.println("tolerancia por exixo :" + toleranciaPorEixo);
			g1Total = veiculo.getG1() + ((veiculo.getG1() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG1PBT(g1Total);
			System.out.println("G1  :" + veiculo.getG1());
			System.out.println("G1 total :" + g1Total);
		}

	}

	public void calculaG2() {

		if (veiculo.getToleranciaEixo() == null) {
			veiculo.setG2PBT(veiculo.getG2());
		} else {
			System.out.println("tolerancia por exixo :" + toleranciaPorEixo);
			g2Total = veiculo.getG2() + ((veiculo.getG2() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG2PBT(g2Total);
			System.out.println("G2  :" + veiculo.getG2());
			System.out.println("G2 total :" + g2Total);
		}

	}

	public void calculaG3() {

		if (veiculo.getToleranciaEixo() == null) {
			veiculo.setG3PBT(veiculo.getG3());
		} else {
			System.out.println("tolerancia por exixo :" + toleranciaPorEixo);
			g3Total = veiculo.getG3() + ((veiculo.getG3() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG3PBT(g3Total);
			System.out.println("G3  :" + veiculo.getG3());
			System.out.println("G3 total :" + g3Total);
		}
	}

	public void calculaG4() {

		if (veiculo.getToleranciaEixo() == null) {
			veiculo.setG4PBT(veiculo.getG4());
		} else {
			System.out.println("tolerancia por exixo :" + toleranciaPorEixo);
			g4Total = veiculo.getG4() + ((veiculo.getG4() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG4PBT(g4Total);
			System.out.println("G4  :" + veiculo.getG4());
			System.out.println("G4 total :" + g4Total);
		}
	}

	public void calculaG5() {

		if (veiculo.getToleranciaEixo() == null) {
			veiculo.setG5PBT(veiculo.getG5());
		} else {
			System.out.println("tolerancia por exixo :" + toleranciaPorEixo);
			g5Total = veiculo.getG5() + ((veiculo.getG5() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG5PBT(g5Total);
			System.out.println("G5  :" + veiculo.getG5());
			System.out.println("G5 total :" + g5Total);
		}
	}

	public void calculaG6() {

		if (veiculo.getToleranciaEixo() == null) {
			veiculo.setG6PBT(veiculo.getG6());
		} else {
			System.out.println("tolerancia por exixo :" + toleranciaPorEixo);
			g6Total = veiculo.getG6() + ((veiculo.getG6() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG6PBT(g6Total);
			System.out.println("G6  :" + veiculo.getG6());
			System.out.println("G6 total :" + g6Total);
		}
	}

	public void calculaG7() {

		if (veiculo.getToleranciaEixo() == null) {
			veiculo.setG7PBT(veiculo.getG7());
		} else {
			System.out.println("tolerancia por exixo :" + toleranciaPorEixo);
			g7Total = veiculo.getG7() + ((veiculo.getG7() * veiculo.getToleranciaEixo()) / 100);
			veiculo.setG7PBT(g7Total);
			System.out.println("G7  :" + veiculo.getG7());
			System.out.println("G7 total :" + g7Total);
		}
	}

	/// METODO CARROCERIA / CAVALOS PARA POPULAR
	public void carregarImagemCavalo() {

		imagemCavalo = new ArrayList<String>();
		for (CavaloCarroceria cavaloCarroceria : cavalos) {
			if (cavaloCarroceria.getCodigo() == veiculo.getCavalo().getCodigo()) {
				imagemCavalo.add(cavaloCarroceria.getCodigo() + ".png");
				// vistoria.setImagem1(cavaloCarroceria.getCodigo() + ".png");
				// System.out.println("IMAGEM = " + vistoria.getImagem1());
			}
		}

	}

	// ---------------------------------------------------------------------------
	/// METODO CARROCERIA / CAVALOS PARA POPULAR
	public void carregarImagemCarroceria1() {
		imagemCarroceria1 = new ArrayList<String>();
		for (CavaloCarroceria cavaloCarroceria : carrocerias) {
			if (cavaloCarroceria.getCodigo() == veiculo.getCarroceria1().getCodigo()) {
				imagemCarroceria1.add(cavaloCarroceria.getCodigo() + ".png");
				// vistoria.setImagem2(cavaloCarroceria.getCodigo() + ".png");
			}
		}
	}

	// ---------------------------------------------------------------------------
	/// METODO CARROCERIA / CAVALOS PARA POPULAR
	public void carregarImagemCarroceria2() {
		imagemCarroceria2 = new ArrayList<String>();
		for (CavaloCarroceria cavaloCarroceria : carrocerias) {
			if (cavaloCarroceria.getCodigo() == veiculo.getCarroceria2().getCodigo()) {
				imagemCarroceria2.add(cavaloCarroceria.getCodigo() + ".png");
				// vistoria.setImagem3(cavaloCarroceria.getCodigo() + ".png");
			}
		}

	}

	// ---------------------------------------------------------------------------
	/// METODO CARROCERIA / CAVALOS PARA POPULAR
	public void carregarImagemCarroceria3() {
		imagemCarroceria3 = new ArrayList<String>();
		for (CavaloCarroceria cavaloCarroceria : carrocerias) {
			if (cavaloCarroceria.getCodigo() == veiculo.getCarroceria3().getCodigo()) {
				imagemCarroceria3.add(cavaloCarroceria.getCodigo() + ".png");
				// vistoria.setImagem4(cavaloCarroceria.getCodigo() + ".png");
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

	public void identificarVeiculo() {

		if (veiculo.getCavalo() != null && veiculo.getCarroceria1() == null && veiculo.getCarroceria2() == null
				&& veiculo.getCarroceria3() == null) {

			veiculo.setCodigoUnicoVeiculo(String.valueOf(veiculo.getCavalo().getCodigo()) + "000");

			// System.out.println("Codigo do veiculo unico = " +
			// veiculo.getCodigoUnicoVeiculo());

		}
		if (veiculo.getCavalo() != null && veiculo.getCarroceria1() != null && veiculo.getCarroceria2() == null
				&& veiculo.getCarroceria3() == null) {

			veiculo.setCodigoUnicoVeiculo(String.valueOf(veiculo.getCavalo().getCodigo())
					+ String.valueOf(veiculo.getCarroceria1().getCodigo()) + "00");

			// System.out.println("Codigo do veiculo unico = " +
			// veiculo.getCodigoUnicoVeiculo());

		}
		if (veiculo.getCavalo() != null && veiculo.getCarroceria1() != null && veiculo.getCarroceria2() != null
				&& veiculo.getCarroceria3() == null) {
			veiculo.setCodigoUnicoVeiculo(String.valueOf(veiculo.getCavalo().getCodigo())
					+ String.valueOf(veiculo.getCarroceria1().getCodigo())
					+ String.valueOf(veiculo.getCarroceria2().getCodigo()) + "0");

			// System.out.println("Codigo do veiculo unico = " +
			// veiculo.getCodigoUnicoVeiculo());

		}

		if (veiculo.getCavalo() != null && veiculo.getCarroceria1() != null && veiculo.getCarroceria2() != null
				&& veiculo.getCarroceria3() != null) {
			veiculo.setCodigoUnicoVeiculo(String.valueOf(veiculo.getCavalo().getCodigo())
					+ String.valueOf(veiculo.getCarroceria1().getCodigo())
					+ String.valueOf(veiculo.getCarroceria2().getCodigo())
					+ String.valueOf(veiculo.getCarroceria3().getCodigo()));
			// System.out.println("Codigo do veiculo unico = " +
			// veiculo.getCodigoUnicoVeiculo());

		}
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<String> getTipoEixos() {
		return tipoEixos;
	}

	public void setTipoEixos(List<String> tipoEixos) {
		this.tipoEixos = tipoEixos;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	@Override
	public String toString() {
		return "VeiculoBean [classes=" + classes + ", tipoEixos=" + tipoEixos + ", veiculo=" + veiculo + ", veiculos="
				+ veiculos + "]";
	}

	public List<Tolerancia> getTolerancias() {
		return tolerancias;
	}

	public void setTolerancias(List<Tolerancia> tolerancias) {
		this.tolerancias = tolerancias;
	}

	public Double getToleranciaPBT() {
		return toleranciaPBT;
	}

	public void setToleranciaPBT(Double toleranciaPBT) {
		this.toleranciaPBT = toleranciaPBT;
	}

	public Double getToleranciaPorEixo() {
		return toleranciaPorEixo;
	}

	public void setToleranciaPorEixo(Double toleranciaPorEixo) {
		this.toleranciaPorEixo = toleranciaPorEixo;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Double getToleranciaAux() {
		return toleranciaAux;
	}

	public void setToleranciaAux(Double toleranciaAux) {
		this.toleranciaAux = toleranciaAux;
	}

	public Double getToleranciaTotal() {
		return toleranciaTotal;
	}

	public void setToleranciaTotal(Double toleranciaTotal) {
		this.toleranciaTotal = toleranciaTotal;
	}

	public Double getG1aux() {
		return g1aux;
	}

	public void setG1aux(Double g1aux) {
		this.g1aux = g1aux;
	}

	public Double getG2aux() {
		return g2aux;
	}

	public void setG2aux(Double g2aux) {
		this.g2aux = g2aux;
	}

	public Double getG3aux() {
		return g3aux;
	}

	public void setG3aux(Double g3aux) {
		this.g3aux = g3aux;
	}

	public Double getG4aux() {
		return g4aux;
	}

	public void setG4aux(Double g4aux) {
		this.g4aux = g4aux;
	}

	public Double getG5aux() {
		return g5aux;
	}

	public void setG5aux(Double g5aux) {
		this.g5aux = g5aux;
	}

	public Double getG6aux() {
		return g6aux;
	}

	public void setG6aux(Double g6aux) {
		this.g6aux = g6aux;
	}

	public Double getG7aux() {
		return g7aux;
	}

	public void setG7aux(Double g7aux) {
		this.g7aux = g7aux;
	}

	public Double getG1Total() {
		return g1Total;
	}

	public void setG1Total(Double g1Total) {
		this.g1Total = g1Total;
	}

	public Double getG2Total() {
		return g2Total;
	}

	public void setG2Total(Double g2Total) {
		this.g2Total = g2Total;
	}

	public Double getG3Total() {
		return g3Total;
	}

	public void setG3Total(Double g3Total) {
		this.g3Total = g3Total;
	}

	public Double getG4Total() {
		return g4Total;
	}

	public void setG4Total(Double g4Total) {
		this.g4Total = g4Total;
	}

	public Double getG5Total() {
		return g5Total;
	}

	public void setG5Total(Double g5Total) {
		this.g5Total = g5Total;
	}

	public Double getG6Total() {
		return g6Total;
	}

	public void setG6Total(Double g6Total) {
		this.g6Total = g6Total;
	}

	public Double getG7Total() {
		return g7Total;
	}

	public void setG7Total(Double g7Total) {
		this.g7Total = g7Total;
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

	public List<String> getImagemCavalo() {
		return imagemCavalo;
	}

	public void setImagemCavalo(List<String> imagemCavalo) {
		this.imagemCavalo = imagemCavalo;
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

	public List<String> getImagens() {
		return imagens;
	}

	public void setImagens(List<String> imagens) {
		this.imagens = imagens;
	}

}
