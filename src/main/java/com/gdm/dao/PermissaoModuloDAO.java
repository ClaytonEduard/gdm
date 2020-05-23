package com.gdm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.gdm.domain.PermissaoModulo;
import com.gdm.util.HibernateUtil;

public class PermissaoModuloDAO extends GenericDAO<PermissaoModulo> {

	// @Test
	// public PermissaoModulo autenticar(Long codigo) {
	// Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
	//
	// try {
	//
	// Criteria consulta = sessao.createCriteria(PermissaoModulo.class);
	// consulta.createAlias("empresa", "p");
	// // consulta.setMaxResults(1);
	// consulta.add(Restrictions.eq("p.codigo", codigo));
	//// consulta.add(Restrictions.eq("empresa_codigo", codigo));
	//
	// PermissaoModulo resultado = (PermissaoModulo) consulta.uniqueResult();
	// System.out.println("res " + resultado);
	// return resultado;
	// } catch (RuntimeException e) {
	// throw e;
	//
	// } finally {
	// sessao.close();
	// }
	// }

//	public PermissaoModulo buscaListEmpresa(Long codigo) {
//
//		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
//		try {
//			Criteria consulta = sessao.createCriteria(PermissaoModulo.class);
//			consulta.setMaxResults(1);
//			consulta.createAlias("empresa", "emp");
//			consulta.add(Restrictions.eq("emp.codigo", codigo));
//			PermissaoModulo res = (PermissaoModulo) consulta.uniqueResult();
//			return res;
//		} catch (RuntimeException e) {
//			throw e;
//		} finally {
//			sessao.close();
//		}
//	}
//
//	public PermissaoModulo listarEmpresa(Long codigo) {
//
//		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
//		try {
//
//			Criteria consulta = sessao.createCriteria(PermissaoModulo.class);
//			consulta.createAlias("empresa", "emp");
//			consulta.add(Restrictions.eq("emp.codigo", codigo));
//			System.out.println("codigo :" + codigo);
//			// consulta.setMaxResults(1);
//			// PermissaoModulo resultado = (PermissaoModulo) consulta.list();
//			List resultado = consulta.list();
//
//			System.out.println("res " + resultado);
//
//			return (PermissaoModulo) resultado;
//		} catch (RuntimeException e) {
//			throw e;
//
//		} finally {
//			sessao.close();
//		}
//	}
//
//	// pesquisar com codigo e texto
//	public PermissaoModulo pesquisaEmpresaPorModulo(Long codigo, String modu) {
//		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
//
//		try {
//
//			Criteria consulta = sessao.createCriteria(PermissaoModulo.class);
//			consulta.createAlias("empresa", "emp");
//			consulta.add(Restrictions.eq("emp.codigo", codigo));
//			// consulta.add(Restrictions.eq("empresa_codigo", codigo));
//			consulta.setMaxResults(1);
//			System.out.println("codigo :" + codigo);
//			consulta.add(Restrictions.eq("modulodescricao", modu));
//			System.out.println("modulo :" + modu);
//			consulta.setMaxResults(1);
//
//			PermissaoModulo resultado = (PermissaoModulo) consulta.uniqueResult();
//			System.out.println("Permisassao " + resultado);
//			return resultado;
//		} catch (RuntimeException e) {
//			throw e;
//
//		} finally {
//			sessao.close();
//		}
//	}
//
//	// pesquisar so texto
//	public PermissaoModulo pesquisaEmpresasoModulo(String modu) {
//		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
//
//		try {
//
//			Criteria consulta = sessao.createCriteria(PermissaoModulo.class);
//			consulta.setMaxResults(1);
//			consulta.add(Restrictions.eq("modulodescricao", modu));
//			System.out.println("modulo :" + modu);
//			consulta.setMaxResults(1);
//
//			PermissaoModulo resultado = (PermissaoModulo) consulta.uniqueResult();
//			System.out.println("res " + resultado);
//			return resultado;
//		} catch (RuntimeException e) {
//			throw e;
//
//		} finally {
//			sessao.close();
//		}
//	}


	@SuppressWarnings("unchecked")
	public List<PermissaoModulo> listModul(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(PermissaoModulo.class);
			consulta.createAlias("empresa", "emp");
			consulta.add(Restrictions.eq("emp.codigo", codigo));
//			System.out.println("codigo :" + codigo);
			@SuppressWarnings("rawtypes")
			List resultado = consulta.list();

			return resultado;
		} catch (RuntimeException e) {
			throw e;

		} finally {
			sessao.close();
			sessao = null;
		}

	}

}