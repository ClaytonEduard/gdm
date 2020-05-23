package com.gdm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.omnifaces.util.Faces;

import com.gdm.bean.AutenticacaoBean;
import com.gdm.domain.Multa;
import com.gdm.domain.Usuario;
import com.gdm.util.HibernateUtil;

public class MultaDAO extends GenericDAO<Multa> {

	AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");

	@SuppressWarnings("unchecked")
	public List<Multa> listarPorEmpresa() {
		Usuario usuario = autenticacaoBean.getUsuarioLogado();

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Multa.class);
			consulta.createAlias("empresa", "e");
			consulta.add(Restrictions.eq("e.cnpj", usuario.getEmpresa().getCnpj()));
			@SuppressWarnings("rawtypes")
			List res = consulta.list(); // retorna uma lista da classe multa

			return res;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Multa> listaPlaca(String placa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Multa.class);
			consulta.add(Restrictions.gt("placacavalo", placa));
			@SuppressWarnings("rawtypes")
			List res = consulta.list(); // retorna uma lista da classe multa

			return res;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}


}
