package com.gdm.util;

import org.hibernate.Session;
import org.junit.Test;

import com.gdm.util.HibernateUtil;

public class HibernateUtiTest {
	@Test
	public void conectar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}

}
