package br.unitins.a2topicos.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

public class Usuario implements Cloneable {

	private Integer id;
	@NotBlank(message = "Informe o seu nome")
	private String nome;
	@CPF(message = "Informe um CPF válido.")
	@Size(min = 11, max = 11, message = "Informe 11 dígitos para o CPF")
	private String cpf;
	@NotNull(message = "Informe sua data de nascimento.")
	@Past(message = "Informe uma data anterior ao dia de hoje.")
	private LocalDate dataNascimento;
	@NotBlank(message = "Informe seu email.")
	@Email(message = "Informe um email válido.")
	private String email;
	@NotBlank(message = "Informe sua senha.")
	private String senha;
	@NotNull
	private Telefone telefone;
	@NotNull
	private Perfil perfil;
	@NotNull(message = "Informe o sexo.")
	private Sexo sexo;

	public Usuario() {

	}

	public Usuario(Integer id, String nome, String cpf, LocalDate dataNascimento, String email, String senha,
			Telefone telefone, Perfil perfil, Sexo sexo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
		this.perfil = perfil;
		this.sexo = sexo;
	}

	public Usuario getClone() {
		try {
			return (Usuario) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento
				+ ", email=" + email + ", senha=" + senha + ", telefone=" + telefone + ", perfil=" + perfil + ", sexo="
				+ sexo + "]";
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
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

}
