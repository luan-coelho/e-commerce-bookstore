package br.unitins.br.a2topicos.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.unitins.a2topicos.model.Usuario;
import br.unitins.a2topicos.model.Perfil;

@WebFilter(filterName = "SecurityFilter", urlPatterns = { "/pages/*" })
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;

        HttpSession session = servletRequest.getSession(false);
        Usuario usuarioLogado = null;
        if (session != null)
            usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            ((HttpServletResponse) response).sendRedirect("/A2Topicos/login.xhtml");
        } else {
            if (usuarioLogado.getPerfil().equals(Perfil.ADMINISTRADOR)) {
                // permitindo a execucao comleta do protocolo
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse)response).sendRedirect("/A2Topicos/sem-acesso.xhtml");
            }
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        Filter.super.init(filterConfig);
        System.out.println("Filtro SecurityFilter inicializado.");
    }
}