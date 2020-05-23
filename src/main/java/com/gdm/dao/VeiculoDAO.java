package com.gdm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.gdm.domain.Veiculo;
import com.gdm.util.HibernateUtil;

public class VeiculoDAO extends GenericDAO<Veiculo> {

	// public Veiculo buscarVeiculoGDM(String codUnico) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// private Class<Entidade> classe;
	//
	// @SuppressWarnings("unchecked")
	public Veiculo buscarVeiculoGDM(String codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Veiculo.class);
			consulta.add(Restrictions.eq("codigoUnicoVeiculo", codigo));
			Veiculo resultado = (Veiculo) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Veiculo> listar(Boolean Status) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Veiculo.class);
			consulta.add(Restrictions.eq("ativo", Status));
			consulta.addOrder(Order.asc("capacidadePBT"));
			List<Veiculo> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
