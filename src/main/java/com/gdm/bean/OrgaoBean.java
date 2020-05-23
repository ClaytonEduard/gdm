package com.gdm.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.dao.OrgaoDAO;
import com.gdm.domain.Orgao;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class OrgaoBean implements Serializable {
	private Orgao orgao;
	private List<Orgao> orgaos;

	public Orgao getOrgao() {
		return orgao;
	}

	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}

	public List<Orgao> getOrgaos() {
		return orgaos;
	}

	public void setOrgaos(List<Orgao> orgaos) {
		this.orgaos = orgaos;
	}

	@PostConstruct
	public void listar() {
		try {
			OrgaoDAO orgaoDAO = new OrgaoDAO();
			orgaos = orgaoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os orgãos emissores");
			erro.printStackTrace();
		}
	}

	public void novo() {
		orgao = new Orgao();
	}

	public void salvar() {
		try {

			OrgaoDAO orgaoDAO = new OrgaoDAO();
			orgaoDAO.merge(orgao);

			orgao = new Orgao();
			orgaos = orgaoDAO.listar();
			Messages.addGlobalInfo("Orgão salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o orgão emissor!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			orgao = (Orgao) evento.getComponent().getAttributes().get("orgaoSelecionado");

			OrgaoDAO dao = new OrgaoDAO();
			dao.excluir(orgao);

			orgaos = dao.listar();
			Messages.addGlobalInfo("Orgão removido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o orgão!");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		orgao = (Orgao) evento.getComponent().getAttributes().get("orgaoSelecionado");
		Messages.addGlobalInfo("Nome: " + orgao.getNome());
	}

	@Override
	public String toString() {
		return "OrgaoBean [orgao=" + orgao + ", orgaos=" + orgaos + "]";
	}

}
