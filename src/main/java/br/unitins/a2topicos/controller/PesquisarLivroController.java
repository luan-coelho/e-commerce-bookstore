package br.unitins.a2topicos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.a2topicos.application.Util;
import br.unitins.a2topicos.dao.LivroDAO;
import br.unitins.a2topicos.model.Livro;

@Named
@ViewScoped
public class PesquisarLivroController implements Serializable {

	private static final long serialVersionUID = 6503296461088346202L;
	private Integer tipoFiltro;
	private String filtro;
	private List<Livro> listaLivro;

	public PesquisarLivroController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		filtro = (String) flash.get("pesquisa");
		
		if(filtro != null) {
			pesquisar(1);
		}
	}
	
	public String novoLivro() {
		return "livro.xhtml?faces-redirect=true";
	}

	public void pesquisar() {
		LivroDAO dao = new LivroDAO();
		
		if(tipoFiltro == 1)
			listaLivro = dao.buscarPorNome(getFiltro().trim().toLowerCase());
		
		if(tipoFiltro == 2) {
			listaLivro = dao.buscarPorAutor(getFiltro().trim().toLowerCase());
		}
		
		if(listaLivro.isEmpty()) {
			Util.addErrorMessage("Nenhum resultado encontrado.");
		}
	}
	
	public void pesquisar(int tipoFiltro) {
		LivroDAO dao = new LivroDAO();
		
		if(tipoFiltro == 1) {
			listaLivro = dao.buscarPorNome(getFiltro());
			if(listaLivro.isEmpty()) {
				listaLivro = dao.buscarPorAutor(getFiltro());
			}
		}
		
		if(listaLivro.isEmpty()) {
			Util.addErrorMessage("Nenhum resultado encontrado.");
		}
	}

	public void editar(Integer id) {
		LivroDAO dao = new LivroDAO();
		Livro livro = dao.buscarPorId(id);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("livroFlash", livro);
		
		Util.redirect("cadastro-livro.xhtml");
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Livro> getListaLivro() {
		if (listaLivro == null)
			listaLivro = new ArrayList<Livro>();
		return listaLivro;
	}
	
	public int tamanhoLista() {
		if(this.listaLivro == null)
			return 0;
		return this.listaLivro.size();
	}
	
	public void setListaLivro(List<Livro> listaLivro) {
		this.listaLivro = listaLivro;
	}

}
