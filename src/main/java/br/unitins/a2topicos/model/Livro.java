package br.unitins.a2topicos.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class Livro {
	private Integer id;
	@NotBlank(message = "Informe o nome.")
	private String nome;
	@NotNull(message = "Informe a data de lançamento.")
	@Past(message = "Informe uma data anterior ao dia de hoje.")
	private LocalDate lancamento;
	@NotBlank(message = "Informe a editora.")
	private String editora;
	@NotNull(message = "Informe a quantidade de estoque.")
	private Integer estoque;
	@NotNull(message = "Informe o valor.")
	private Double valor;
	@NotNull(message = "Informe o idioma.")
	private Idioma idioma;
	@NotNull(message = "Informe o Autor.")
	private Autor autor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getLancamento() {
		return lancamento;
	}

	public void setLancamento(LocalDate lancamento) {
		this.lancamento = lancamento;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		return Objects.equals(id, other.id);
	}

}
