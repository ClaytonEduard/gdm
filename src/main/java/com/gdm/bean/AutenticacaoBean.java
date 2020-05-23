package com.gdm.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Session;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.gdm.dao.PermissaoModuloDAO;
import com.gdm.dao.UsuarioDAO;
import com.gdm.domain.Empresa;
import com.gdm.domain.PermissaoModulo;
import com.gdm.domain.Pessoa;
import com.gdm.domain.Usuario;
import com.gdm.util.HibernateUtil;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable {

	private Usuario usuario;
	private Usuario usuarioLogado;

	private List<PermissaoModulo> permisaoLogado;

	@PostConstruct
	public void iniciar() {

		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
		usuario.setEmpresa(new Empresa());
	}

	public void autenticar() {
		try {
			UsuarioDAO dao = new UsuarioDAO();
			// usuarioLogado = dao.autenticar(usuario.getPessoa().getCpf(),
			// usuario.getSenha());
			usuarioLogado = dao.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());
			if (usuarioLogado == null) {
				Messages.addFlashGlobalError("CPF e/ou Senha incorretos");
				return;
			} else {
				Messages.addGlobalError("Erro na autenticação ");
			}
			Messages.addGlobalInfo("Usuario Logado" + usuarioLogado);
			Faces.redirect("./home.xhtml");
		} catch (IOException erro) {

		}
	}

	public boolean temPermissoes(List<String> permissoes) {
		boolean per = false;

		for (String permissao : permissoes) {
			if (usuarioLogado.getTipoUsuario().toString().equals(permissao)) {
				return per = true;
			}
		}
		// retorna para a pagina false, desabilitando a mesma.
		return per;

	}

	public boolean permissaoModulo(List<String> modulos) {
		boolean mod = false;

		PermissaoModuloDAO dao = new PermissaoModuloDAO();

		permisaoLogado = dao.listModul(usuarioLogado.getEmpresa().getCodigo());
		// System.out.println("Lista " + permisaoLogado);
		// permissaoModulos = dao.listar();
		for (int j = 0; j < permisaoLogado.size(); j++) {
			for (String modulo : modulos) {
				if (permisaoLogado.get(j).getModulodescricao().name().equals(modulo)) {
					return mod = true;
				}

			}
		}

		return mod;
	}

	public void sair() {
		System.out.println("Chama sessão");
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		if (usuarioLogado.equals(true)) {
			session.disconnect();
			 System.out.println("Fechou sessão: " + session + usuarioLogado.getPessoa().getNome());
		}
		
//		precisa colocar a funcao em string
//		FacesContext fc = FacesContext.getCurrentInstance();
//		HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
//		sessao.invalidate();
//		System.out.println("Fechou sessão: " + sessao + usuarioLogado.getPessoa().getNome());
	

	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "AutenticacaoBean [usuario=" + usuario + ", usuarioLogado=" + usuarioLogado + "]";
	}

}
