package br.unitins.a2topicos.application;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;

import br.unitins.a2topicos.model.Usuario;

public class Util {
	public static void redirect(String page) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			System.out.println("Não foi possível realizar o redirecionamento.");
			e.printStackTrace();
		}
	}

	public static String hash(Usuario usuario) {
		return hash(usuario.getEmail() + usuario.getSenha());
	}

	private static String hash(String valor) {
		return DigestUtils.sha256Hex(valor);
	}
	
	private static void addMessage(String msg, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
	}

	public static void addErrorMessage(String msg) {
		addMessage(msg, FacesMessage.SEVERITY_ERROR);
	}

	public static void addWarnMessage(String msg) {
		addMessage(msg, FacesMessage.SEVERITY_WARN);
	}

	public static void addInfoMessage(String msg) {
		addMessage(msg, FacesMessage.SEVERITY_INFO);
	}
	
	public static String primeiroNome(Usuario usuario) {
		if (usuario != null) {
			String pattern = "\\S+";

			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(usuario.getNome());
			if (m.find()) {
				return m.group(0);
			}
		}
		return null;
	}
}
