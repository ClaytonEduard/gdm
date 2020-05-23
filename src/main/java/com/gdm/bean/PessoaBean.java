package com.gdm.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.dao.CidadeDAO;
import com.gdm.dao.FuncaoServicoDAO;
import com.gdm.dao.PessoaDAO;
import com.gdm.domain.Cidade;
import com.gdm.domain.FuncaoServico;
import com.gdm.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {

	private Pessoa pessoa;

	private List<Pessoa> pessoas;
	private List<Pessoa> pessoasPorGrupo;
	private List<Pessoa> auxg;
	private List<Cidade> cidades;
	private List<FuncaoServico> funcaoServicos;

	private long t;

	@PostConstruct
	public void listar() {
		try {
			PessoaDAO dao = new PessoaDAO();
			pessoas = dao.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar a classe pessoas.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			// listarTipPessoa();
			pessoa = new Pessoa();

			CidadeDAO dao1 = new CidadeDAO();
			cidades = dao1.listar();

			FuncaoServicoDAO dao3 = new FuncaoServicoDAO();
			funcaoServicos = dao3.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar uma nova cidade");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			PessoaDAO dao = new PessoaDAO();
			if (pessoa.getData_cadastro() == null) {
				pessoa.setData_cadastro(new Date());

				dao.merge(pessoa);

				CidadeDAO cidadeDAO = new CidadeDAO();
				cidades = cidadeDAO.listar();

				FuncaoServicoDAO dao2 = new FuncaoServicoDAO();
				funcaoServicos = dao2.listar();

				pessoa = new Pessoa();
				pessoas = dao.listar();

				Messages.addGlobalInfo("Salvo com sucesso!");
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar." + erro);
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {

			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");

			PessoaDAO dao = new PessoaDAO();
			dao.excluir(pessoa);

			pessoas = dao.listar();
			Messages.addGlobalInfo("Removida com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover!" + erro);
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar();

			FuncaoServicoDAO dao2 = new FuncaoServicoDAO();
			funcaoServicos = dao2.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar editar!" + erro);
			erro.printStackTrace();
		}

	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<FuncaoServico> getFuncaoServicos() {
		return funcaoServicos;
	}

	public void setFuncaoServicos(List<FuncaoServico> funcaoServicos) {
		this.funcaoServicos = funcaoServicos;
	}

	@Override
	public String toString() {
		return "PessoaBean [pessoa=" + pessoa + ", pessoas=" + pessoas + ", pessoasPorGrupo=" + pessoasPorGrupo
				+ ", auxg=" + auxg + ", cidades=" + cidades + ", funcaoServicos=" + funcaoServicos + ", t=" + t + "]";
	}

	public List<Pessoa> getPessoasPorGrupo() {
		return pessoasPorGrupo;
	}

	public void setPessoasPorGrupo(List<Pessoa> pessoasPorGrupo) {
		this.pessoasPorGrupo = pessoasPorGrupo;
	}

	public List<Pessoa> getAuxg() {
		return auxg;
	}

	public void setAuxg(List<Pessoa> auxg) {
		this.auxg = auxg;
	}

	public long getT() {
		return t;
	}

	public void setT(long t) {
		this.t = t;
	}

}
