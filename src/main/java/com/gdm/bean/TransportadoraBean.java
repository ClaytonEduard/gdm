package com.gdm.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.domain.Transportadora;
import com.gdm.dao.TransportadoraDAO;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TransportadoraBean implements Serializable {

	private Transportadora transportadora;
	private List<Transportadora> transportadoras;
	
	
	public Transportadora getTransportadora() {
		return transportadora;
	}


	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}
	


	/*AQUI INICIA O CODIGO DE LISTAGEM DAS TRANSPORTADORAS SALVAS NO BANCO 
	 *--------------------------------------------------------- 
	 */
	//PostConstruct refere se ao metodo listar ao criar e carregar a pagina
	@PostConstruct
	public void listar() {
		try {
			
			TransportadoraDAO transportadoraDao = new TransportadoraDAO();
			transportadoras = transportadoraDao.listar();
			
		} catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as transportadoras");
			erro.printStackTrace();
			}
	}
	
	//-----------AQUI-FINALIZA-A-LISTAGEM----------------------------
	


	/*AQUI INICIA A CRIAÇÃO DE UMA NOVA TRANSPORTADORA
	 * ----------------------------------------------------------------
	 */
	public void novo() {
		transportadora = new Transportadora();
	}

	
	public List<Transportadora> getTransportadoras() {
		return transportadoras;
	}


	public void setTransportadoras(List<Transportadora> transportadoras) {
		this.transportadoras = transportadoras;
	}
	
	//-------------------------COMANDO SALVAR----------------------------------
	public void salvar() {
		// Opçao de Metodo de mensagem via java simples
		// String texto = "Programação Web Java";
		// FacesMessage messagem = new FacesMessage(FacesMessage.SEVERITY_INFO, texto,
		// texto);
		// FacesContext contexto = FacesContext.getCurrentInstance();
		// contexto.addMessage(null, messagem);
		try {
			//Comando para salvar usando o merge para otimizar o editar no banco de dados, 
			//usando o validate em hibernate.cfg.xml
			TransportadoraDAO transportadoraDao = new TransportadoraDAO();
			transportadoraDao.merge(transportadora);
			
			//Chamar a criacao de uma nova transportadora
			transportadora = new Transportadora();
			
			//atualizar a tela com a lista de transportadora atualizada
			transportadoras = transportadoraDao.listar();
			
			//mostrar mensagens usando o Ominifaces
			Messages.addGlobalInfo("Cadastro efetuado com sucesso!");
			
		}catch (RuntimeException erro) {
						// TODO: handle exception
			
			//Mensagem de erro
			Messages.addGlobalError("Ocorreu um erro ao salvar a transportadora");
			erro.printStackTrace();
		}
		
	}
	//--------------------COMANDO SALVAR FINALIZA AQUI------------------------------------
	
	
	//----------------------------COMANDO PARA EXCLUIR UMA TRANSPORTADORA-----------------
	// Criamos uma variavel para capiturar o evento do server.faces e excluir o
	// valor correspondente
	public void excluir(ActionEvent evento) {
		//Inserimos o TRY para tratar erros 
		try {
		
		transportadora = (Transportadora) evento.getComponent().getAttributes().get("transportadoraSelecionada");
		
		TransportadoraDAO transportadoraDao = new TransportadoraDAO();
		transportadoraDao.excluir(transportadora);
		
		// Carregar lista atualizada na variavel estados e recarregar a tela com novos
		// valores do banco
		
		transportadoras = transportadoraDao.listar();
		
		Messages.addGlobalInfo("Transportadora removida com sucesso");
		
		// Se ocorrer erro vamso executar a excessão mostrando a seguinte mensagem
				} catch(RuntimeException error) {
					Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a transportadora ");
					error.printStackTrace();
					
				}
		
	}
	//---------------------AQUI FINALIZA O COMANDO EXCLUIR--------------------------
	
	
	//---------------------AQUI INICIA O COMANDO PARA EDITAR-------------------------
	public void editar(ActionEvent evento) {
		transportadora = (Transportadora) evento.getComponent().getAttributes().get("transportadoraSelecionada");
	}
	//------------------------------FIM DA EDIÇÃO------------------------------------
	
}
