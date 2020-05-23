package com.gdm.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.dao.CategoriaProdutoDAO;
import com.gdm.domain.CategoriaProduto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CategoriaProdutoBean implements Serializable {

	private CategoriaProduto categoriaProduto;
	private List<CategoriaProduto> categoriaProdutos;

	@PostConstruct
	public void listar() {

		try {

			CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
			categoriaProdutos = dao.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as categorias!");
			erro.printStackTrace();
		}
	}

	public void novo() {
		categoriaProduto = new CategoriaProduto();
	}

	public void salvar() {

		try {

			CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
			dao.merge(categoriaProduto);

			categoriaProduto = new CategoriaProduto();
			categoriaProdutos = dao.listar();
			Messages.addGlobalInfo("Categoria Produto salva com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as categorias!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {

			categoriaProduto = (CategoriaProduto) evento.getComponent().getAttributes()
					.get("categoriaProdutoSelecionada");
			CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
			dao.excluir(categoriaProduto);

			categoriaProdutos = dao.listar();

			Messages.addGlobalInfo("Categoria Produto removida com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a Categoria produto!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			categoriaProduto = (CategoriaProduto) evento.getComponent().getAttributes()
					.get("categoriaProdutoSelecionada");
			Messages.addGlobalInfo("Categoria Produto alterada com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar alterar a Categoria produto!" + erro);
			erro.printStackTrace();
		}
	}

	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public List<CategoriaProduto> getCategoriaProdutos() {
		return categoriaProdutos;
	}

	public void setCategoriaProdutos(List<CategoriaProduto> categoriaProdutos) {
		this.categoriaProdutos = categoriaProdutos;
	}

}
