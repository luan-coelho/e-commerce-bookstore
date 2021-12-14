package br.unitins.a2topicos.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.a2topicos.application.Session;
import br.unitins.a2topicos.application.Util;
import br.unitins.a2topicos.dao.VendaDAO;
import br.unitins.a2topicos.model.Usuario;
import br.unitins.a2topicos.model.Venda;


@Named
@ViewScoped
public class HistoricoController implements Serializable{

	private static final long serialVersionUID = 7093749895818932336L;
	private List<Venda> listaVenda;

	public List<Venda> getListaVenda() {
		if (listaVenda == null) {
			Usuario usuario = (Usuario) Session.getInstance().get("usuarioLogado");
			VendaDAO dao = new VendaDAO();
			listaVenda = dao.obterTodos(usuario);
		}
		return listaVenda;
	}
	
	public void detalhes(Venda venda) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("vendaFlash", venda);
		Util.redirect("detalhes-venda.xhtml");
	}

	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}

}
