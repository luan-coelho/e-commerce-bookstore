package br.unitins.a2topicos.model;

import java.util.Objects;

public class LivroVenda {

	private Integer id;
	private Double valor;
	private Integer quantidade;
	private Livro livro;

	public LivroVenda() {
		// TODO Auto-generated constructor stub
	}
	
	public LivroVenda(Integer id, Double valor, Integer quantidade, Livro livro) {
		super();
		this.id = id;
		this.valor = valor;
		this.quantidade = quantidade;
		this.livro = livro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(livro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LivroVenda other = (LivroVenda) obj;
		return Objects.equals(livro, other.livro);
	}

}
