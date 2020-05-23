package com.gdm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.gdm.domain.Cidade;
import com.gdm.util.HibernateUtil;

public class CidadeDAO extends GenericDAO<Cidade> {

	@SuppressWarnings("unchecked")
	public List<String> buscarCidade(String nome) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.addOrder(Order.asc("nome"));
			consulta.add(Restrictions.ilike("nome", nome, MatchMode.START));
			List res = consulta.list();
			return res;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

	}
	
	
	
	public List<Cidade> buscaCidades(Cidade nome){
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.addOrder(Order.asc("nome"));
			consulta.add(Restrictions.idEq(nome));
			List res = consulta.list();
			return res;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}


}
