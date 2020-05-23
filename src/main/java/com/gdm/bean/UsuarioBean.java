package com.gdm.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import com.gdm.dao.EmpresaDAO;
import com.gdm.dao.PessoaDAO;
import com.gdm.dao.UsuarioDAO;
import com.gdm.domain.Empresa;
import com.gdm.domain.Pessoa;
import com.gdm.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private Usuario usuario;
	private List<Usuario> usuarios;
	private List<Pessoa> pessoas;
	private List<Empresa> empresas;

	@PostConstruct
	public void listar() {
		try {
			UsuarioDAO dao = new UsuarioDAO();
			usuarios = dao.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os usuários");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			usuario = new Usuario();

			PessoaDAO dao = new PessoaDAO();
			pessoas = dao.listar();

			EmpresaDAO dao2 = new EmpresaDAO();
			empresas = dao2.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo usuário");
			erro.printStackTrace();
		}

	}

	public void salvar() {
		try {
			// senha semcrip recebe usuario senha
			usuario.setSenhaSemCriptografia(usuario.getSenha());

			SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
			usuario.setSenha(hash.toHex());
			UsuarioDAO dao = new UsuarioDAO();
			dao.merge(usuario);

			usuario = new Usuario();
			usuarios = dao.listar();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();

			EmpresaDAO dao2 = new EmpresaDAO();
			empresas = dao2.listar();

			Messages.addGlobalInfo("Usuário salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salva o usuário");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {

			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			
			UsuarioDAO dao = new UsuarioDAO();
			dao.excluir(usuario);
			
			

			usuarios = dao.listar();

			Messages.addGlobalInfo("Usuário removido com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover o usuário");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		try {
			
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();

			EmpresaDAO dao2 = new EmpresaDAO();
			empresas = dao2.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar editar o usuário");
			erro.printStackTrace();
		}

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	@Override
	public String toString() {
		return "UsuarioBean [usuario=" + usuario + ", usuarios=" + usuarios + ", pessoas=" + pessoas + "]";
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

}
