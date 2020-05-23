package com.gdm.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.dao.ClasseCNAEDAO;
import com.gdm.dao.GrupoCNAEDAO;
import com.gdm.domain.ClasseCNAE;
import com.gdm.domain.GrupoCNAE;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClasseCNAEBean implements Serializable {

	private ClasseCNAE classeCNAE;
	private List<ClasseCNAE> classeCNAEs;
	private List<GrupoCNAE> grupoCNAEs;

	@PostConstruct
	public void listar() {

		try {

			ClasseCNAEDAO dao = new ClasseCNAEDAO();
			classeCNAEs = dao.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as classes Cnaes" + erro);
			erro.printStackTrace();
		}

	}

	public void novo() {

		try {

			classeCNAE = new ClasseCNAE();

			GrupoCNAEDAO grupoCNAEDAO = new GrupoCNAEDAO();
			grupoCNAEs = grupoCNAEDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar criar a classe Cnae!" + erro);
			erro.printStackTrace();
		}

	}

	public void salvar() {
		try {

			ClasseCNAEDAO classeCNAEDAO = new ClasseCNAEDAO();
			classeCNAEDAO.merge(classeCNAE);

			classeCNAE = new ClasseCNAE();

			GrupoCNAEDAO grupoCNAEDAO = new GrupoCNAEDAO();
			grupoCNAEs = grupoCNAEDAO.listar();

			classeCNAEs = classeCNAEDAO.listar();
			Messages.addGlobalInfo("Classe CNAE salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar a classe Cnae!" + erro);
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {

			classeCNAE = (ClasseCNAE) evento.getComponent().getAttributes().get("classeCNAE");
			ClasseCNAEDAO classeCNAEDAO = new ClasseCNAEDAO();
			classeCNAEDAO.excluir(classeCNAE);

			classeCNAEs = classeCNAEDAO.listar();
			Messages.addGlobalInfo("Classe CNAE removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a classe Cnae!" + erro);
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {

			classeCNAE = (ClasseCNAE) evento.getComponent().getAttributes().get("classeCNAE");
			GrupoCNAEDAO grupoCNAEDAO = new GrupoCNAEDAO();
			grupoCNAEs = grupoCNAEDAO.listar();

//			Messages.addGlobalInfo("Classe CNAE alterada com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar alterar a classe Cnae!" + erro);
			erro.printStackTrace();
		}
	}

	public ClasseCNAE getClasseCNAE() {
		return classeCNAE;
	}

	public void setClasseCNAE(ClasseCNAE classeCNAE) {
		this.classeCNAE = classeCNAE;
	}

	public List<ClasseCNAE> getClasseCNAEs() {
		return classeCNAEs;
	}

	public void setClasseCNAEs(List<ClasseCNAE> classeCNAEs) {
		this.classeCNAEs = classeCNAEs;
	}

	public List<GrupoCNAE> getGrupoCNAEs() {
		return grupoCNAEs;
	}

	public void setGrupoCNAEs(List<GrupoCNAE> grupoCNAEs) {
		this.grupoCNAEs = grupoCNAEs;
	}

	@Override
	public String toString() {
		return "ClasseCNAEBean [classeCNAE=" + classeCNAE + ", classeCNAEs=" + classeCNAEs + ", grupoCNAEs="
				+ grupoCNAEs + "]";
	}

}
