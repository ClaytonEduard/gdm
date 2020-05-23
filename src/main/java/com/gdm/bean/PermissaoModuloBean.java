package com.gdm.bean;

import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.gdm.dao.EmpresaDAO;
import com.gdm.dao.PermissaoModuloDAO;
import com.gdm.domain.Empresa;
import com.gdm.domain.PermissaoModulo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PermissaoModuloBean implements Serializable {

	private PermissaoModulo permissaoModulo;

	private Empresa empresa;

	private List<Empresa> empresas;

	private List<PermissaoModulo> permissaoModulos;
	private List<PermissaoModulo> permisaoLogado;

	@PostConstruct
	private void listar() {
		try {
			PermissaoModuloDAO moduloDAO = new PermissaoModuloDAO();
			permissaoModulos = moduloDAO.listar();

			EmpresaDAO empresaDAO = new EmpresaDAO();
			empresas = empresaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			EmpresaDAO empresaDAO = new EmpresaDAO();
			empresas = empresaDAO.listar();
			PermissaoModuloDAO moduloDAO = new PermissaoModuloDAO();
			moduloDAO.merge(permissaoModulo);

			permissaoModulos = moduloDAO.listar();

			Messages.addGlobalInfo("Permissão salva com successo!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar!");
			erro.printStackTrace();
		}
	}

	AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");

	public boolean permissaoModulo(List<String> modulos) {
		boolean mod = false;
		// verificaEmpresa();

		PermissaoModuloDAO dao = new PermissaoModuloDAO();
		Long i = autenticacaoBean.getUsuarioLogado().getEmpresa().getCodigo();
		// permisaoLogado = dao.listarEmpresa(i);
		permisaoLogado = dao.listModul(i);
		System.out.println("Lista " + permisaoLogado);
		permissaoModulos = dao.listar();
		for (int j = 0; j < permisaoLogado.size(); i++) {
			for (String modulo : modulos) {
				if (permisaoLogado.get(j).getModulodescricao().toString().equals(modulo)) {
					return mod = true;
				}

			}
		}

		return mod;

	}

	public void novo() {
		permissaoModulo = new PermissaoModulo();
	}

	public void excluir(ActionEvent evento) {
		try {

			permissaoModulo = (PermissaoModulo) evento.getComponent().getAttributes()
					.get("permissaoModuloSelecionado");

			PermissaoModuloDAO moduloDAO = new PermissaoModuloDAO();
			moduloDAO.excluir(permissaoModulo);

			permissaoModulos = moduloDAO.listar();

			Messages.addGlobalInfo("Permissão removida com successo!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover à permissão!");
			erro.printStackTrace();
		}
	}
	
	
	public void editar(ActionEvent evento){
		try {

			permissaoModulo = (PermissaoModulo) evento.getComponent().getAttributes()
					.get("permissaoModuloSelecionado");

			Messages.addGlobalInfo("Permissão alterada com successo!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar alterar à permissão!");
			erro.printStackTrace();
		}
	}

	public PermissaoModulo getPermissaoModulo() {
		return permissaoModulo;
	}

	public void setPermissaoModulo(PermissaoModulo permissaoModulo) {
		this.permissaoModulo = permissaoModulo;
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

	public List<PermissaoModulo> getPermissaoModulos() {
		return permissaoModulos;
	}

	public void setPermissaoModulos(List<PermissaoModulo> permissaoModulos) {
		this.permissaoModulos = permissaoModulos;
	}

	
}
