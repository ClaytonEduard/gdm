package com.gdm.bean;

import java.io.Serializable;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.dao.ToleranciaDAO;
import com.gdm.domain.Tolerancia;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ToleranciaBean implements Serializable {

	private Tolerancia tolerancia;
	private List<Tolerancia> tolerancias;

	public Tolerancia getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(Tolerancia tolerancia) {
		this.tolerancia = tolerancia;
	}

	public List<Tolerancia> getTolerancias() {
		return tolerancias;
	}

	public void setTolerancias(List<Tolerancia> tolerancias) {
		this.tolerancias = tolerancias;
	}

	@PostConstruct
	public void listar() {
		try {
			ToleranciaDAO dao = new ToleranciaDAO();
			tolerancias = dao.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as tolerâncias!");
			erro.printStackTrace();
		}
	}

	public void novo() {
		tolerancia = new Tolerancia();
	}

	public void salvar() {
		try {
			ToleranciaDAO dao = new ToleranciaDAO();
			dao.merge(tolerancia);

			tolerancia = new Tolerancia();
			tolerancias = dao.listar();
			Messages.addGlobalInfo("Tolerância salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a tolerância!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {

			tolerancia = (Tolerancia) evento.getComponent().getAttributes().get("toleranciaSelecionada");

			ToleranciaDAO dao = new ToleranciaDAO();
			dao.excluir(tolerancia);

			tolerancia = new Tolerancia();
			tolerancias = dao.listar();
			Messages.addGlobalInfo("Tolerância removida com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover a tolerância!");
			erro.printStackTrace();

		}
	}

	public void editar(ActionEvent evento) {
		tolerancia = (Tolerancia) evento.getComponent().getAttributes().get("toleranciaSelecionada");
	}
}
