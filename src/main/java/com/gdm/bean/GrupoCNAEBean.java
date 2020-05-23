package com.gdm.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.dao.GrupoCNAEDAO;
import com.gdm.domain.GrupoCNAE;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class GrupoCNAEBean implements Serializable {

	private GrupoCNAE grupoCNAE;
	private List<GrupoCNAE> grupoCnaes;

	@PostConstruct
	public void listar() {
		try {

			GrupoCNAEDAO cnaedao = new GrupoCNAEDAO();
			grupoCnaes = cnaedao.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Grupos CNAES!");
			erro.printStackTrace();
		}

	}

	public void novo() {
		grupoCNAE = new GrupoCNAE();
	}

	public void salvar() {
		try {

			GrupoCNAEDAO cnaedao = new GrupoCNAEDAO();
			cnaedao.merge(grupoCNAE);

			grupoCNAE = new GrupoCNAE();
			grupoCnaes = cnaedao.listar();
			Messages.addGlobalInfo("Grupo salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o grupo!" + erro);
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {

			grupoCNAE = (GrupoCNAE) evento.getComponent().getAttributes().get("grupoCnae");
			GrupoCNAEDAO cnaedao = new GrupoCNAEDAO();
			cnaedao.excluir(grupoCNAE);

			grupoCnaes = cnaedao.listar();
			Messages.addGlobalInfo("Grupo excluido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o grupo!" + erro);
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			grupoCNAE = (GrupoCNAE) evento.getComponent().getAttributes().get("grupoCnae");
			Messages.addGlobalInfo("Grupo alterado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar alterar o grupo!" + erro);
			erro.printStackTrace();
		}
	}

	public GrupoCNAE getGrupoCNAES() {
		return grupoCNAE;
	}

	public void setGrupoCNAES(GrupoCNAE grupoCNAE) {
		this.grupoCNAE = grupoCNAE;
	}

	public List<GrupoCNAE> getGrupoCnaes() {
		return grupoCnaes;
	}

	public void setGrupoCnaes(List<GrupoCNAE> grupoCnaes) {
		this.grupoCnaes = grupoCnaes;
	}

	public GrupoCNAE getGrupoCNAE() {
		return grupoCNAE;
	}

	public void setGrupoCNAE(GrupoCNAE grupoCNAE) {
		this.grupoCNAE = grupoCNAE;
	}

}
