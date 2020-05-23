package com.gdm.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.domain.TipoConjunto;
import com.gdm.dao.TipoConjuntoDAO;



@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TipoConjuntoBean implements Serializable{
	
	//----------------------------
	private TipoConjunto tipoConjunto;
	private List<TipoConjunto> tipoConjuntos;
	

	public TipoConjunto getTipoConjunto() {
		return tipoConjunto;
	}

	public void setTipoConjunto(TipoConjunto tipoConjunto) {
		this.tipoConjunto = tipoConjunto;
	}
	
	//-------------------------------
	//CODIGO DE LISTAGEM DOS TIPOS 
	
	@PostConstruct
	public void listar() {
		try {
			TipoConjuntoDAO tipoConjuntoDao = new TipoConjuntoDAO();
			tipoConjuntos = tipoConjuntoDao.listar();
			
			
		}catch(RuntimeException erro) {
			
			//Mensagem de erro
			Messages.addGlobalError("Ocorreu um erro ao listar os tipos de conjuntos");
		erro.printStackTrace();
		}
	}
	
	//------------------------------------
	
	public void novo() {
		tipoConjunto = new TipoConjunto();
		
	}

	public List<TipoConjunto> getTipoConjuntos() {
		return tipoConjuntos;
	}

	public void setTipoConjuntos(List<TipoConjunto> tipoConjuntos) {
		this.tipoConjuntos = tipoConjuntos;
	}
	
	//-------------------------------------
	
	public void salvar() {
		// Opçao de Metodo de mensagem via java simples
		// String texto = "Programação Web Java";
		// FacesMessage messagem = new FacesMessage(FacesMessage.SEVERITY_INFO, texto,
		// texto);
		// FacesContext contexto = FacesContext.getCurrentInstance();
		// contexto.addMessage(null, messagem);
		
		try {
			TipoConjuntoDAO tipoConjuntoDao = new TipoConjuntoDAO();
			tipoConjuntoDao.merge(tipoConjunto);
			
			//chamar criacao do conjunto
			tipoConjunto = new TipoConjunto();
			
			//carregar lista atualizada na tela 
			tipoConjuntos = tipoConjuntoDao.listar();
			
			//Mensagem
			Messages.addGlobalInfo("Tipo de conjunto salvo com sucesso!");
			
		}catch(RuntimeException erro) {
			
			//mensagem de erro 
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar p tipo de conjunto");
			erro.printStackTrace();
		}
	}
	//----------------------------------
	
	public void excluir(ActionEvent evento) {
		//Bloco de codigo para excluir o tipo de conjunto 
		try{
			tipoConjunto = (TipoConjunto) evento.getComponent().getAttributes().get("TipoConjuntoSelecionado");
			
			TipoConjuntoDAO tipoConjuntoDao = new TipoConjuntoDAO();
			tipoConjuntoDao.excluir(tipoConjunto);
			
			// Carregar lista atualizada na variavel estados e recarregar a tela com novos
			// valores do banco
			
			tipoConjuntos = tipoConjuntoDao.listar();
			Messages.addGlobalInfo("Tipo de conjunto excluido com sucesso!");
			
		}catch (RuntimeException erro) {
			// TODO: handle exception
			Messages.addGlobalError("O tipo de conjunto não pode ser excluido");
			erro.printStackTrace();
		}
		
	}
	
	//-----------------------------------
	public void editar(ActionEvent evento) {
		tipoConjunto = (TipoConjunto) evento.getComponent().getAttributes().get("TipoConjuntoSelecionado");
		
	}
}
