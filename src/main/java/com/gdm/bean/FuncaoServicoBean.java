package com.gdm.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.dao.FuncaoServicoDAO;
import com.gdm.domain.FuncaoServico;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncaoServicoBean implements Serializable {

	private FuncaoServico funcaoServico;
	private List<FuncaoServico> funcoesServicos;

	@PostConstruct
	public void listar() {
		try {
			FuncaoServicoDAO dao = new FuncaoServicoDAO();
			funcoesServicos = dao.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as funções!");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			funcaoServico = new FuncaoServico();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar gerar uma nova função!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			FuncaoServicoDAO dao = new FuncaoServicoDAO();
			dao.merge(funcaoServico);

			funcaoServico = new FuncaoServico();
			funcoesServicos = dao.listar();
			Messages.addGlobalInfo("Funcão salva com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salva a função!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			funcaoServico = (FuncaoServico) evento.getComponent().getAttributes().get("funcaoServicoSelecionada");
			FuncaoServicoDAO dao = new FuncaoServicoDAO();
			dao.excluir(funcaoServico);
			funcoesServicos = dao.listar();
			Messages.addGlobalInfo("Funcão removida com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a função!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		funcaoServico = (FuncaoServico) evento.getComponent().getAttributes().get("funcaoServicoSelecionada");
		Messages.addGlobalInfo("Funcão alterada com sucesso! " + funcaoServico.getDescricao());
	}

	public FuncaoServico getFuncaoServico() {
		return funcaoServico;
	}

	public void setFuncaoServico(FuncaoServico funcaoServico) {
		this.funcaoServico = funcaoServico;
	}

	public List<FuncaoServico> getFuncoesServicos() {
		return funcoesServicos;
	}

	public void setFuncoesServicos(List<FuncaoServico> funcoesServicos) {
		this.funcoesServicos = funcoesServicos;
	}

	@Override
	public String toString() {
		return "FuncaoServicoBean [funcaoServico=" + funcaoServico + ", funcoesServico=" + funcoesServicos + "]";
	}

}
