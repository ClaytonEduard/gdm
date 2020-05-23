package com.gdm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.gdm.domain.CavaloCarroceria;
import com.gdm.domain.TipoConjunto;
import com.gdm.util.HibernateUtil;

public class CavaloCarroceriaDAO extends GenericDAO<CavaloCarroceria> {

	public List<TipoConjunto> conjuntos;

	// TENTATIVA DE LISTAR CONJUNTOS SOMENTE TIPO CARROCERIAS ---------------
	@SuppressWarnings("unchecked")
	public List<CavaloCarroceria> listarCarrocerias() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CavaloCarroceria.class);
			consulta.add(Restrictions.like("descricao", "CARRETA%"));

			List<CavaloCarroceria> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	// -----------------------------

	// LISTAGEM DE CAVALO COM CRITERIO DE DESCRICAO
	@SuppressWarnings("unchecked")
	public List<CavaloCarroceria> listarCavalos() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CavaloCarroceria.class);
			consulta.add(Restrictions.like("descricao", "CAVALO%"));

			List<CavaloCarroceria> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	// LISTAGEM DE CAMINHOES COM CRITERIO DE DESCRICAO
	@SuppressWarnings("unchecked")
	public List<CavaloCarroceria> listarCaminhao() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CavaloCarroceria.class);
			consulta.add(Restrictions.like("descricao", "CAMINHAO%"));

			List<CavaloCarroceria> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	// LISTAGEM DE CAMINHOES COM CRITERIO DE DESCRICAO
	@SuppressWarnings("unchecked")
	public List<CavaloCarroceria> listarReboque() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CavaloCarroceria.class);
			consulta.add(Restrictions.like("descricao", "REBOQUE%"));

			List<CavaloCarroceria> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	// LISTAGEM DE CAMINHOES COM CRITERIO DE DESCRICAO
	@SuppressWarnings("unchecked")
	public List<CavaloCarroceria> listarSemiReboque() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CavaloCarroceria.class);
			consulta.add(Restrictions.like("descricao", "SEMI%"));

			List<CavaloCarroceria> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
