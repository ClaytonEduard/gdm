package com.gdm.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import com.gdm.bean.AutenticacaoBean;
import com.gdm.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// pega a pagina atual
		String paginaAtual = Faces.getViewId();

		boolean ehPaginaDeAutenticacao = paginaAtual.contains("login.xhtml");
		// se nao for a pagina de autenticacao , entao e privada
		if (!ehPaginaDeAutenticacao) {
			// pegar o bean da autenticacao 
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			//se o bean da autenticacao for igual null
			if (autenticacaoBean == null) {
				Faces.navigate("/login.xhtml");
				return;
			}
			// se o bean for criado pego a autenticacao
			Usuario usuario = autenticacaoBean.getUsuarioLogado();
			if (usuario == null) {
				Faces.navigate("/login.xhtml");
				return;
			}
		}

	}

	@Override
	public void beforePhase(PhaseEvent arg0) {

	}

	@Override
	public PhaseId getPhaseId() {

		return PhaseId.RESTORE_VIEW;
	}

}
