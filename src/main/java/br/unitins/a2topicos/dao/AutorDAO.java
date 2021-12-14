package br.unitins.a2topicos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.a2topicos.model.Autor;

public class AutorDAO implements DAO<Autor> {

	@Override
	public boolean incluir(Autor obj) {

		return false;
	}

	@Override
	public boolean alterar(Autor obj) {

		return false;
	}

	@Override
	public boolean excluir(Integer id) {

		return false;
	}

	@Override
	public List<Autor> obterTodos() {
		Connection conn = DAO.getConnection();

		List<Autor> listaAutor = new ArrayList<Autor>();

		if (conn == null)
			return null;

		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  a.id, ");
			sql.append("  a.nome ");
			sql.append("FROM ");
			sql.append("  autor a ");
			sql.append("ORDER BY ");
			sql.append("  a.nome ");

			rs = conn.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Autor autor = new Autor();
				autor.setId(rs.getInt("id"));
				autor.setNome(rs.getString("nome"));

				listaAutor.add(autor);
			}

		} catch (SQLException e) {
			listaAutor = null;
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}

		return listaAutor;
	}

	public Autor buscarPorId(Integer id) {
		Connection conn = DAO.getConnection();

		if (conn == null)
			return null;

		PreparedStatement stat = null;
		ResultSet rs = null;
		Autor autor = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  a.id, ");
			sql.append("  a.nome ");
			sql.append("FROM ");
			sql.append("  autor a ");
			sql.append("WHERE ");
			sql.append(" a.id = ? ");

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);

			rs = stat.executeQuery();

			if (rs.next()) {
				autor = new Autor();
				autor.setId(rs.getInt("id"));
				autor.setNome(rs.getString("nome"));
			}

		} catch (SQLException e) {
			autor = null;
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
			}
			try {
				rs.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}

		return autor;
	}

}
