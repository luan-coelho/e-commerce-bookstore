package br.unitins.a2topicos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.a2topicos.application.Session;
import br.unitins.a2topicos.application.Util;
import br.unitins.a2topicos.dao.LivroDAO;
import br.unitins.a2topicos.model.Livro;
import br.unitins.a2topicos.model.LivroVenda;

@Named
@ViewScoped
public class VendaController implements Serializable {

	private static final long serialVersionUID = -6600989092898177785L;

	private int tipoFiltro;
	private String filtro;
	private List<Livro> listaLivro;
	
	
	// Adiciona o produto no carrinho
	public void comprar(Livro livro) {

		@SuppressWarnings("unchecked")
		List<LivroVenda> carrinho = (List<LivroVenda>) Session.getInstance().get("carrinho");

		if (carrinho == null) {
			carrinho = new ArrayList<LivroVenda>();
		}

		LivroVenda item = new LivroVenda();
		item.setLivro(livro);
		item.setValor(livro.getValor());
		item.setQuantidade(1);

		if (carrinho.contains(item)) {
			int index = carrinho.indexOf(item);
			int quantidade = carrinho.get(index).getQuantidade() + 1;
			double precoAux = carrinho.get(index).getValor() + livro.getValor();
			carrinho.get(index).setValor(precoAux);
			carrinho.get(index).setQuantidade(quantidade);

		} else {

			carrinho.add(item);
		}

		Session.getInstance().set("carrinho", carrinho);

		Util.addInfoMessage("Livro adicionado no carrinho.");

	}

	public void pesquisar() {
		LivroDAO dao = new LivroDAO();
		
		if(tipoFiltro == 1)
		setListaLivro(dao.buscarPorNome(getFiltro()));
		
		if(tipoFiltro == 2)
			setListaLivro(dao.buscarPorEditora(getFiltro()));
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Livro> getListaLivro() {
		if(listaLivro == null) {
			LivroDAO dao = new LivroDAO();
			listaLivro = dao.obterTodos();
		}
			
		return listaLivro;
	}

	public void setListaLivro(List<Livro> listaLivro) {
		this.listaLivro = listaLivro;
	}

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}
}
