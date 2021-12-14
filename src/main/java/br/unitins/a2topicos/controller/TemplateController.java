package br.unitins.a2topicos.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.a2topicos.application.Session;
import br.unitins.a2topicos.application.Util;
import br.unitins.a2topicos.model.Perfil;
import br.unitins.a2topicos.model.Usuario;

@Named
@ViewScoped
public class TemplateController implements Serializable {

	private static final long serialVersionUID = 6861250649125110993L;
	private Usuario usuarioLogado;
	private String primeiroNome = primeiroNome(getUsuarioLogado().getNome());
	private String filtro;

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null)
			usuarioLogado = (Usuario) Session.getInstance().get("usuarioLogado");
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public void encerrarSessao() {
		Session.getInstance().invalidateSession();
		Util.redirect("login.xhtml");
	}

	public String primeiroNome(String nome) {
		return Util.primeiroNome(usuarioLogado);
	}

	public void telaPerfilUsuario() {
		Util.redirect("perfil-usuario.xhtml");
	}

	public void pesquisar() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("pesquisa", getFiltro());
		if(usuarioLogado.getPerfil().equals(Perfil.ADMINISTRADOR))
			Util.redirect("/A2Topicos/pesquisar-livro-cliente.xhtml");	
		Util.redirect("pesquisar-livro-cliente.xhtml");
	}
	
	public void telaVenda() {
		if(usuarioLogado.getPerfil().equals(Perfil.ADMINISTRADOR)) {
			
			Util.redirect("/A2Topicos/venda.xhtml");
		}
		Util.redirect("venda.xhtml");
	}
	
	public void telaCarrinho() {
		if(usuarioLogado.getPerfil().equals(Perfil.ADMINISTRADOR)) {
			Util.redirect("/A2Topicos/carrinho.xhtml");
		}
		Util.redirect("carrinho.xhtml");
	}
	
	public void telaHistorico() {
		if(usuarioLogado.getPerfil().equals(Perfil.ADMINISTRADOR)) {
			Util.redirect("/A2Topicos/historico.xhtml");
		}
		Util.redirect("historico.xhtml");
	}
	public void telaGerenciarUsuario() {
		if(usuarioLogado.getPerfil().equals(Perfil.ADMINISTRADOR)) {
			Util.redirect("/A2Topicos/pages/gerenciar-usuarios.xhtml");
		}
		Util.redirect("sem-acesso.xhtml");
	}
	public void telaGerenciarLivro() {
		if(usuarioLogado.getPerfil().equals(Perfil.ADMINISTRADOR)) {
			Util.redirect("/A2Topicos/pages/pesquisar-livro.xhtml");
		}
		Util.redirect("sem-acesso.xhtml");
	}
	
	
}
