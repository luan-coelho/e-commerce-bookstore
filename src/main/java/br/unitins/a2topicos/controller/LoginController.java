package br.unitins.a2topicos.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.a2topicos.application.Session;
import br.unitins.a2topicos.application.Util;
import br.unitins.a2topicos.dao.UsuarioDAO;
import br.unitins.a2topicos.model.Usuario;

@Named
@RequestScoped
public class LoginController {

	private Usuario usuario;

	public String entrar() {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usu = dao.verificarUsuario(usuario.getEmail(), Util.hash(usuario));

		if (usu != null) {
			Session.getInstance().set("usuarioLogado", usu);
			
			Util.redirect("venda.xhtml");
		}
		Util.addErrorMessage("Email ou senha invalido.");
		return null;
	}

	public void telaCadastro() {
		
		Util.redirect("cadastro-livro.xhtml");
		
	}

	public boolean emailJaExiste() {
		UsuarioDAO dao = new UsuarioDAO();
		if (!dao.verificarEmailUsuario(usuario.getEmail())) {
			Util.addErrorMessage("Não existe nenhum usuario cadastrado com esse email.");
			return true;
		}
		return false;
	}

	public void limpar() {
		usuario = null;
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
