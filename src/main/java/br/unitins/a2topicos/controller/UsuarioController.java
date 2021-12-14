package br.unitins.a2topicos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 4178070395778788024L;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	
	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}

	public Sexo[] getListaSexo() {
		return Sexo.values();
	}
	
	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setTelefone(new Telefone());
		}
		return usuario;
	}

	public void incluir() {
		UsuarioDAO dao = new UsuarioDAO();

		getUsuario().setSenha(Util.hash(getUsuario()));

		if (dao.incluir(getUsuario())) {
			limpar();
			Util.addInfoMessage("Usuario adicionado com sucesso!");
		} else {
			Util.addErrorMessage("Erro ao salvar no banco.");
		}

	}
	
	public void editar(Integer id) {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario = (Usuario) dao.buscarPorId(id);
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("usuario-crud", usuario);
		
		Util.redirect("cadastro-usuario.xhtml"); 
	}

	public void editar(Usuario user) {
		setUsuario(user.getClone());
		if (getUsuario().getTelefone() == null)
			getUsuario().setTelefone(new Telefone());
	}

	public void alterar() {
		UsuarioDAO dao = new UsuarioDAO();

		getUsuario().setSenha(Util.hash(getUsuario()));

		if (dao.alterar(getUsuario())) {
			limpar();
			Util.addInfoMessage("Usuario alterado com sucesso!");
		} else {
			Util.addErrorMessage("Erro ao salvar no banco.");

		}

	}

	public void excluir() {
		excluir(getUsuario());
		limpar();
	}

	public void excluir(Usuario user) {
		UsuarioDAO dao = new UsuarioDAO();

		if (dao.excluir(user.getId())) {
			limpar();
			Util.addInfoMessage("Usuario removido com sucesso!");
			listaUsuario = null;
		} else {
			Util.addErrorMessage("Erro ao salvar no banco.");
		}
	}

	public void limpar() {
		usuario = null;
		listaUsuario = null;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			UsuarioDAO dao = new UsuarioDAO();
			listaUsuario = dao.obterTodos();
		}

		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	
	public void telaGerenciarUsuarios() {
		if(usuario.getPerfil().equals(Perfil.ADMINISTRADOR)) {
			Util.redirect("/A2Topicos/pages/gerenciar-usuarios.xhtml");
		}
		Util.redirect("sem-acesso.xhtml");
	}

}
