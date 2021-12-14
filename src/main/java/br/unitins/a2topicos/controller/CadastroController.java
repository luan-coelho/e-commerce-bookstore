package br.unitins.a2topicos.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.a2topicos.application.Util;
import br.unitins.a2topicos.dao.UsuarioDAO;
import br.unitins.a2topicos.model.Perfil;
import br.unitins.a2topicos.model.Sexo;
import br.unitins.a2topicos.model.Telefone;
import br.unitins.a2topicos.model.Usuario;

@Named
@ViewScoped
public class CadastroController implements Serializable {

	private static final long serialVersionUID = -6459229480405762593L;

	private Usuario usuario;
	private String senhaAuxiliar;
	
	public CadastroController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("usuario-crud");
		usuario = (Usuario) flash.get("usuario-crud");
	}
	
	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
	public Sexo[] getListaSexo() {
		return Sexo.values();
	}
	
	public String getSenhaAuxiliar() {
		return senhaAuxiliar;
	}

	public void setSenhaAuxiliar(String senhaAuxiliar) {
		this.senhaAuxiliar = senhaAuxiliar;
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setTelefone(new Telefone());		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void telaLogin() {
		Util.redirect("login.xhtml");
	}

	public void cadastrar() {
		UsuarioDAO dao = new UsuarioDAO();
		if (!emailJaExiste() && !cpfJaExiste()) {
			usuario.setPerfil(Perfil.CLIENTE);
			dao.cadastrar(usuario);
			Util.addInfoMessage("Parabéns "+Util.primeiroNome(usuario)+" seu cadastro foi realizado com sucesso.");
			usuario = null;
		}
	}

	public boolean emailJaExiste() {
		UsuarioDAO dao = new UsuarioDAO();
		if (dao.verificarEmailUsuario(usuario.getEmail())) {
			Util.addErrorMessage("Já existe um usuario cadastrado com esse email.");
			return true;
		}
		return false;
	}

	public boolean cpfJaExiste() {
		UsuarioDAO dao = new UsuarioDAO();
		if (usuario.getCpf().length() == 11) {
			if (dao.verificarCpfUsuario(usuario.getCpf())) {
				Util.addErrorMessage("Já existe um usuario cadastrado com esse CPF.");
				return true;
			}
		}else {
			Util.addErrorMessage("Digite 11 caracteres para o CPF");
		}
		return false;
	}

	public boolean verificarSenhaCadastro() {
		if (usuario.getSenha() != null) {
			if (usuario.getSenha().equals(senhaAuxiliar)) {
				return true;
			}
			Util.addErrorMessage("As senhas não coincidem.");
			getUsuario().setSenha(null);
			setSenhaAuxiliar(null);
		}
		return false;
	}
}
