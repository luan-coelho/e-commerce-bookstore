package br.unitins.a2topicos.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.unitins.a2topicos.application.Session;
import br.unitins.a2topicos.application.Util;
import br.unitins.a2topicos.dao.UsuarioDAO;
import br.unitins.a2topicos.model.Usuario;

@Named
@SessionScoped
public class PerfilUsuarioController implements Serializable {
	private static final long serialVersionUID = 7674082106300910071L;

	private Usuario usuario;

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = (Usuario) Session.getInstance().get("usuarioLogado");
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void alterarInformacoes() {
		UsuarioDAO dao = new UsuarioDAO();

		if (dao.alterar(getUsuario())) {
			Util.addInfoMessage("Alteração realizada com sucesso!");
		} else {
			Util.addErrorMessage("Erro ao salvar no banco.");
		}
	}
}
