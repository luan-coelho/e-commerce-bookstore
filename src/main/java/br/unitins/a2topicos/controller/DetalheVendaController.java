package br.unitins.a2topicos.controller;

import javax.inject.Named;

import br.unitins.a2topicos.model.Venda;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;

@Named
@ViewScoped
public class DetalheVendaController implements Serializable {

	private static final long serialVersionUID = -2529971787429397531L;
	private Venda venda;

	public DetalheVendaController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("vendaFlash");
		venda = (Venda) flash.get("vendaFlash");
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
