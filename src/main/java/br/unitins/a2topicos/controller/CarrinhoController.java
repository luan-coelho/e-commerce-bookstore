package br.unitins.a2topicos.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.a2topicos.application.Session;
import br.unitins.a2topicos.application.Util;
import br.unitins.a2topicos.dao.VendaDAO;
import br.unitins.a2topicos.model.LivroVenda;
import br.unitins.a2topicos.model.Usuario;
import br.unitins.a2topicos.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = 8333703897323811831L;
	private List<LivroVenda> listaLivroVenda = null;
	private Double valorTotal;

	@SuppressWarnings("unchecked")
	public List<LivroVenda> getListaLivroVenda() {

		listaLivroVenda = (List<LivroVenda>) Session.getInstance().get("carrinho");

		return listaLivroVenda;
	}

	public void setListaLivroVenda(List<LivroVenda> listaLivroVenda) {
		this.listaLivroVenda = listaLivroVenda;
	}

	private Double valorTotalVenda(List<LivroVenda> lista) {
		Double aux = 0.0;
		if (lista != null) {
			for (LivroVenda itemVenda : lista) {
				aux = aux + itemVenda.getValor();
			}
			return aux;
		}
		return null;
	}

	public Double getValorTotal() {
		if (valorTotal == null)
			valorTotal = valorTotalVenda(listaLivroVenda);

		if (valorTotal == null)
			valorTotal = 0.0;

		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void remover(LivroVenda livro) {

		@SuppressWarnings("unchecked")
		List<LivroVenda> carrinho = (List<LivroVenda>) Session.getInstance().get("carrinho");

		carrinho.remove(livro);

	}

	public void finalizar() {
		// verificar se existe um usuario logado no sistema
		Usuario usuarioLogado = (Usuario) Session.getInstance().get("usuarioLogado");
		if (usuarioLogado == null) {
			Util.addErrorMessage("Faca o Login para concluir a venda.");
			return;
		}

		// verificar se existe algum produto no carrinho (sessao)
		@SuppressWarnings("unchecked")
		List<LivroVenda> carrinho = (List<LivroVenda>) Session.getInstance().get("carrinho");

		if (carrinho == null || carrinho.isEmpty()) {
			Util.addErrorMessage("Nao existem produtos no carrinho.");
			return;
		}

		Venda venda = new Venda();
		venda.setData(LocalDate.now());
		venda.setUsuario(usuarioLogado);
		venda.setListaLivroVenda(carrinho);
		venda.setTotalVenda(getValorTotal());

		VendaDAO dao = new VendaDAO();
		if (dao.incluir(venda)) {
			carrinho.clear();

			Util.addInfoMessage("Venda realizada com sucesso.");

		} else {
			Util.addErrorMessage("Problemas ao realizar a venda.");
		}
	}
}
