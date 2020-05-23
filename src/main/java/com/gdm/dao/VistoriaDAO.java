package com.gdm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.omnifaces.util.Faces;

import com.gdm.bean.AutenticacaoBean;
import com.gdm.domain.Usuario;
import com.gdm.domain.Vistoria;
import com.gdm.util.HibernateUtil;

public class VistoriaDAO extends GenericDAO<Vistoria> {
	public Vistoria buscarVeiculoPorPlaca(String codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Vistoria.class);
			consulta.add(Restrictions.eq("placaCavalo", codigo));
			Vistoria resultado = (Vistoria) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");

	@SuppressWarnings("unchecked")
	public List<Vistoria> listarPorEmpresa() {
		Usuario usuario = autenticacaoBean.getUsuarioLogado();

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Vistoria.class);
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
	
	
	public Vistoria buscarVeiculoVistoria(String placa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Vistoria.class);
			consulta.add(Restrictions.eq("placaCavalo", placa));
			consulta.setMaxResults(1);
			consulta.addOrder(Order.desc("codigo"));
			Vistoria resultado = (Vistoria) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	

}
