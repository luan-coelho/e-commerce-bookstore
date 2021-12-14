package br.unitins.a2topicos.model;

import java.time.LocalDate;
import java.util.List;

public class Venda {
	private Integer id;
	private LocalDate data;
	private Double totalVenda;
	private Usuario usuario;
	private List<LivroVenda> listaLivroVenda;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Double getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(Double totalVenda) {
		this.totalVenda = totalVenda;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<LivroVenda> getListaLivroVenda() {
		return listaLivroVenda;
	}

	public void setListaLivroVenda(List<LivroVenda> listaLivroVenda) {
		this.listaLivroVenda = listaLivroVenda;
	}
}
