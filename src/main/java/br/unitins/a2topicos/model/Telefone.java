package br.unitins.a2topicos.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Telefone {

	private Integer id;
	@Size(min = 2, max = 2, message = "Informe ao menos 2 digitos para código de área.")
	@NotNull(message = "o código de área é obrigatório.")
	private String codigoArea;

	@Size(min = 9, max = 9, message = "O número deve conter 9 digitos.")
	private String numero;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoArea() {
		return codigoArea;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
