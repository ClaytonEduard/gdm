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
import com.gdm.dao.ClasseCNAEDAO;
import com.gdm.dao.EmpresaDAO;
import com.gdm.dao.FuncaoServicoDAO;
import com.gdm.dao.GrupoCNAEDAO;
import com.gdm.domain.Cidade;
import com.gdm.domain.ClasseCNAE;
import com.gdm.domain.Empresa;
import com.gdm.domain.FuncaoServico;
import com.gdm.domain.GrupoCNAE;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EmpresaBean implements Serializable {

	private Empresa empresa;

	private List<Empresa> empresas;
	private List<GrupoCNAE> grupocnaes;
	private List<ClasseCNAE> classecnaes;
	private List<Cidade> cidades;
	private List<FuncaoServico> funcaoServicos;

	private int index = 0;

	@PostConstruct
	public void listar() {
		try {
			EmpresaDAO dao = new EmpresaDAO();
			empresas = dao.listar();

			GrupoCNAEDAO grupoCNAEDAO = new GrupoCNAEDAO();
			grupocnaes = grupoCNAEDAO.listar();

			ClasseCNAEDAO cnaedao = new ClasseCNAEDAO();
			classecnaes = cnaedao.listar();

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar();

			FuncaoServicoDAO funcaoServicoDAO = new FuncaoServicoDAO();
			funcaoServicos = funcaoServicoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar Empresas.");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {

			empresa = new Empresa();

			GrupoCNAEDAO grupoCNAEDAO = new GrupoCNAEDAO();
			grupocnaes = grupoCNAEDAO.listar();

			ClasseCNAEDAO cnaedao = new ClasseCNAEDAO();
			classecnaes = cnaedao.listar();

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar();

			FuncaoServicoDAO funcaoServicoDAO = new FuncaoServicoDAO();
			funcaoServicos = funcaoServicoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao gerar nova Empresa!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			EmpresaDAO dao = new EmpresaDAO();

			empresa.setData_cadastro(new Date());
			dao.merge(empresa);

			empresa = new Empresa();

			GrupoCNAEDAO grupoCNAEDAO = new GrupoCNAEDAO();
			grupocnaes = grupoCNAEDAO.listar();

			ClasseCNAEDAO cnaedao = new ClasseCNAEDAO();
			classecnaes = cnaedao.listar();

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar();

			FuncaoServicoDAO funcaoServicoDAO = new FuncaoServicoDAO();
			funcaoServicos = funcaoServicoDAO.listar();

			empresas = dao.listar();
			Messages.addGlobalInfo("Salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao inserir Empresa." + erro);
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			empresa = (Empresa) evento.getComponent().getAttributes().get("empresaSelecionada");

			EmpresaDAO dao = new EmpresaDAO();
			dao.excluir(empresa);

			empresas = dao.listar();
			Messages.addGlobalInfo("Empresa removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a Empresa");
			erro.printStackTrace();

		}
	}

	public void editar(ActionEvent evento) {

		try {
			empresa = (Empresa) evento.getComponent().getAttributes().get("empresaSelecionada");

			EmpresaDAO dao = new EmpresaDAO();
			empresas = dao.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar editar a Empresa");
			erro.printStackTrace();

		}
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public List<GrupoCNAE> getGrupocnaes() {
		return grupocnaes;
	}

	public void setGrupocnaes(List<GrupoCNAE> grupocnaes) {
		this.grupocnaes = grupocnaes;
	}

	public List<ClasseCNAE> getClassecnaes() {
		return classecnaes;
	}

	public void setClassecnaes(List<ClasseCNAE> classecnaes) {
		this.classecnaes = classecnaes;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
