package br.unitins.a2topicos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.a2topicos.application.Util;
import br.unitins.a2topicos.dao.AutorDAO;
import br.unitins.a2topicos.dao.LivroDAO;
import br.unitins.a2topicos.model.Autor;
import br.unitins.a2topicos.model.Idioma;
import br.unitins.a2topicos.model.Livro;

@Named
@ViewScoped
public class LivroController implements Serializable {

	private static final long serialVersionUID = -1003852965963119366L;
	private Livro livro;
	private List<Autor> listaAutor;

	public LivroController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("livroFlash");
		livro = (Livro) flash.get("livroFlash");
	}
	
	public Idioma[] getListaIdioma() {
		return Idioma.values();
	}

	public void incluir() {
		LivroDAO dao = new LivroDAO();

		if (dao.incluir(getLivro())) {
			limpar();
			Util.addInfoMessage("Livro adicionado com sucesso!");
		} else {
			Util.addErrorMessage("Erro ao salvar no banco!");
		}
	}

	public void alterar() {
		LivroDAO dao = new LivroDAO();

		if (dao.alterar(getLivro())) {
			limpar();
			Util.addInfoMessage("Alteração realizada com sucesso!");
		} else
			Util.addErrorMessage("Erro ao alterar os dados no banco!");
	}

	public void editar(Integer id) {
		LivroDAO dao = new LivroDAO();

		livro = dao.buscarPorId(id);
	}

	public void excluir() {
		LivroDAO dao = new LivroDAO();

		if (dao.excluir(livro.getId())) {
			Util.addInfoMessage("Exclusão realizada com sucesso!");
		} else
			Util.addErrorMessage("Problema ao excluir!");
		limpar();
	}

	public void limpar() {
		livro = null;
		listaAutor = null;
	}

	public Livro getLivro() {
		if (livro == null) {
			livro = new Livro();
			livro.setAutor(new Autor());
		}
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Autor> getListaAutor() {

		if (listaAutor == null) {
			AutorDAO dao = new AutorDAO();
			listaAutor = dao.obterTodos();

			if (listaAutor == null)
				listaAutor = new ArrayList<Autor>();

		}
		return listaAutor;
	}

	public void setListaAutor(List<Autor> listaAutor) {
		this.listaAutor = listaAutor;
	}

}
