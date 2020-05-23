package com.gdm.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.gdm.dao.CategoriaProdutoDAO;
import com.gdm.dao.ProdutoDAO;
import com.gdm.domain.CategoriaProduto;
import com.gdm.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

	private Produto produto;
	private List<Produto> produtos;
	private List<CategoriaProduto> categoriaProdutos;

	@PostConstruct
	public void listar() {

		try {

			ProdutoDAO dao = new ProdutoDAO();
			produtos = dao.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar os produtos!");
			erro.printStackTrace();
		}

	}

	public void novo() {
		try {
			produto = new Produto();

			CategoriaProdutoDAO categoriaProdutoDAO = new CategoriaProdutoDAO();
			categoriaProdutos = categoriaProdutoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo produto!");
			erro.printStackTrace();
		}

	}

	public void salvar() {

		try {
			ProdutoDAO dao = new ProdutoDAO();
			dao.merge(produto);

			produto = new Produto();

			CategoriaProdutoDAO dao2 = new CategoriaProdutoDAO();
			categoriaProdutos = dao2.listar();

			produtos = dao.listar();
			Messages.addGlobalInfo("Produto salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar os produtos!");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {

			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO dao = new ProdutoDAO();
			dao.excluir(produto);

			produtos = dao.listar();

			Messages.addGlobalInfo("Produto excluido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao remover o produto!");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		try {

			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			CategoriaProdutoDAO dao2 = new CategoriaProdutoDAO();
			categoriaProdutos = dao2.listar();

//			Messages.addGlobalInfo("Produto alterado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar alterar o produto!");
			erro.printStackTrace();
		}
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<CategoriaProduto> getCategoriaProdutos() {
		return categoriaProdutos;
	}

	public void setCategoriaProdutos(List<CategoriaProduto> categoriaProdutos) {
		this.categoriaProdutos = categoriaProdutos;
	}

}
