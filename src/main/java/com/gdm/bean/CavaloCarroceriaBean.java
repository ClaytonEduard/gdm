package com.gdm.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.gdm.domain.CavaloCarroceria;
import com.gdm.domain.TipoConjunto;
import com.gdm.dao.CavaloCarroceriaDAO;
import com.gdm.dao.TipoConjuntoDAO;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CavaloCarroceriaBean implements Serializable {

	// ----------------

	private CavaloCarroceria cavaloCarroceria;
	private List<CavaloCarroceria> cavaloCarrocerias;
	private List<TipoConjunto> tipoConjuntos;
	private String caminho;

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public List<TipoConjunto> getTipoConjuntos() {
		return tipoConjuntos;
	}

	public void setTipoConjuntos(List<TipoConjunto> tipoConjuntos) {
		this.tipoConjuntos = tipoConjuntos;
	}

	public CavaloCarroceria getCavaloCarroceria() {
		return cavaloCarroceria;
	}

	public void setCavaloCarroceria(CavaloCarroceria cavaloCarroceria) {
		this.cavaloCarroceria = cavaloCarroceria;
	}

	public List<CavaloCarroceria> getCavaloCarrocerias() {
		return cavaloCarrocerias;
	}

	public void setCavaloCarrocerias(List<CavaloCarroceria> cavaloCarrocerias) {
		this.cavaloCarrocerias = cavaloCarrocerias;
	}

	// --------------------------------------
	// COODIGO DE LISTAGEM DOS CONJUNTOS
	@PostConstruct
	public void listar() {
		try {
			CavaloCarroceriaDAO cavaloCarroceriaDao = new CavaloCarroceriaDAO();
			cavaloCarrocerias = cavaloCarroceriaDao.listar();

		} catch (RuntimeException erro) {
			// EXCESSAO DE ERRO
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar os conjuntos.");
			erro.printStackTrace();
		}
	}
	// -------------AQUI FINALIZA A LISTAGEM-----------------

	// -------------CRIAR UM NOVO CONJUNTO-------------------
	public void novo() {

		try {
			cavaloCarroceria = new CavaloCarroceria();

			// Listar a chave estrangeira ligada a classe
			// Em nesse caso o tipo de combinação
			TipoConjuntoDAO tipoConjuntoDao = new TipoConjuntoDAO();
			tipoConjuntos = tipoConjuntoDao.listar();

		} catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addFlashGlobalError("Ocorreu erro ao listar Carrocerias e Tipos de conjuntos");
			erro.printStackTrace();
		}
	}

	// --------------COMANDO PARA SALVAR----------------------
	public void salvar() {
		// Opçao de Metodo de mensagem via java simples
		// String texto = "Programação Web Java";
		// FacesMessage messagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
		// texto,
		// texto);
		// FacesContext contexto = FacesContext.getCurrentInstance();
		// contexto.addMessage(null, messagem);
		try {
			// Comando para salvar usando o merge para otimizar o editar no
			// banco de dados,
			// usando o validate em hibernate.cfg.xml
			CavaloCarroceriaDAO cavaloCarroceriaDao = new CavaloCarroceriaDAO();
			CavaloCarroceria cavalocarroceriaRetorno = cavaloCarroceriaDao.merge(cavaloCarroceria);

			Path origem = Paths.get(caminho);
			// ALTERAR ESSE CAMINHO CONFORME O CAMINHO DO BANCO
			Path destino = Paths
					.get("C:/Multas 1.1/Uploads/carrocerias/" + cavalocarroceriaRetorno.getCodigo() + ".png");
			
//			Path destino = Paths
//					.get("webapp/resources/carrocerias/" + cavalocarroceriaRetorno.getCodigo() + ".png");
			// Path path =
			// Paths.get("http://localhost:8081/GDMultas/resouces/images/veiculos/caminhoes/branco.png");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			// Chamar a criacao de um novo conjunto
			cavaloCarroceria = new CavaloCarroceria();

			// Gerar lista de tipos de conjuntos para a classe estrangeira
			TipoConjuntoDAO tipoConjuntoDao = new TipoConjuntoDAO();

			// atualizar a tela com a lista de conjunto atualizada
			tipoConjuntos = tipoConjuntoDao.listar();
			cavaloCarrocerias = cavaloCarroceriaDao.listar();

			// mostrar mensagens usando o Ominifaces
			Messages.addGlobalInfo("Cadastro efetuado com sucesso!");

		} catch (RuntimeException | IOException erro) {
			// TODO: handle exception
			// Mensagem de erro
			Messages.addGlobalError("Ocorreu um erro ao salvar o conjunto");
			erro.printStackTrace();
		}

	}
	// ------------------------FIM DO COMANDO SALVAR--------------------------

	// ------------------------COMANDO PARA EXCLUIR CONJUNTO-------------
	// Criamos uma variavel para capiturar o evento do server.faces e excluir o
	// valor correspondente
	public void excluir(ActionEvent evento) {
		// Inserimos o TRY para tratar erros
		try {
			cavaloCarroceria = (CavaloCarroceria) evento.getComponent().getAttributes().get("conjuntoSelecionado");

			CavaloCarroceriaDAO cavaloCarroceriaDao = new CavaloCarroceriaDAO();
			cavaloCarroceriaDao.excluir(cavaloCarroceria);

			// Carregar lista atualizada na variavel estados e recarregar a tela
			// com novos
			// valores do banco

			cavaloCarrocerias = cavaloCarroceriaDao.listar();
			Messages.addGlobalInfo("Conjunto removido com sucesso!");

		} catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addFlashGlobalError("Ocorreu erro ao tentar salvar");
			erro.printStackTrace();
		}
	}
	// -------------------AQUI FINALIZA O COMANDO EXCLUIR---------------

	// -------------------AQUI INICIA O COMANDO PARA EDITAR-------------
	public void editar(ActionEvent evento) {

		try {
			cavaloCarroceria = (CavaloCarroceria) evento.getComponent().getAttributes().get("conjuntoSelecionado");

			// Listar a chave estrangeira
			TipoConjuntoDAO tipoConjuntoDao = new TipoConjuntoDAO();
			tipoConjuntos = tipoConjuntoDao.listar();

		} catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addGlobalError("Não foi possível alterar o conjunto");
			erro.printStackTrace();
		}

	}
	// -------------------------FIM DO COMANDO EDITAR---------------------

	// CARREGAR IMAGEM
	public void SubirImagem(FileUploadEvent evento) {

		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivotemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivotemp, StandardCopyOption.REPLACE_EXISTING);
			caminho = (arquivotemp.toString());
			Messages.addFlashGlobalInfo("A imagem foi carregada com sucesso!");
		} catch (IOException erro) {
			erro.printStackTrace();

		}

	}

}
