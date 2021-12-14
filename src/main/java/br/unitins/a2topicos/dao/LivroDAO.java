package br.unitins.a2topicos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.a2topicos.model.Autor;
import br.unitins.a2topicos.model.Idioma;
import br.unitins.a2topicos.model.Livro;

public class LivroDAO implements DAO<Livro> {

	@Override
	public boolean incluir(Livro livro) {
		Connection conn = DAO.getConnection();

		if (conn == null)
			return false;

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO livro ( ");
		sql.append("  nome, ");
		sql.append("  lancamento, ");
		sql.append("  editora, ");
		sql.append("  estoque, ");
		sql.append("  valor, ");
		sql.append("  idioma, ");
		sql.append("  id_autor ");
		sql.append(") VALUES (");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?  ");
		sql.append(") ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, livro.getNome());
			stat.setDate(2, livro.getLancamento() == null ? null : Date.valueOf(livro.getLancamento()));
			stat.setString(3, livro.getEditora());
			stat.setInt(4, livro.getEstoque());
			stat.setDouble(5, livro.getValor());

			if (livro.getIdioma() == null) {
				stat.setObject(6, null);
			} else {
				stat.setInt(6, livro.getIdioma().getId());
			}

			stat.setInt(7, livro.getAutor().getId());

			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return resultado;

	}

	@Override
	public boolean alterar(Livro livro) {
		Connection conn = DAO.getConnection();
		if (conn == null)
			return false;

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE livro SET ");
		sql.append("  nome = ?, ");
		sql.append("  lancamento = ?, ");
		sql.append("  editora = ?, ");
		sql.append("  estoque = ?, ");
		sql.append("  valor = ?, ");
		sql.append("  idioma = ?, ");
		sql.append("  id_autor = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, livro.getNome());
			stat.setDate(2, livro.getLancamento() == null ? null : Date.valueOf(livro.getLancamento()));
			stat.setString(3, livro.getEditora());
			stat.setInt(4, livro.getEstoque());
			stat.setDouble(5, livro.getValor());

			if (livro.getIdioma() == null)
				stat.setObject(6, null);
			else
				stat.setInt(6, livro.getIdioma().getId());

			stat.setInt(7, livro.getAutor().getId());

			stat.setInt(8, livro.getId());

			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return resultado;
	}

	@Override
	public boolean excluir(Integer id) {
		Connection conn = DAO.getConnection();
		if (conn == null)
			return false;

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM livro_venda AS l WHERE l.id_livro = ? ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);

			stat.execute();

			stat.close();

			sql = new StringBuffer();
			sql.append("DELETE FROM autor WHERE id = ? ");
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);

			stat.execute();

			stat.close();

			sql = new StringBuffer();
			sql.append("DELETE FROM livro WHERE id = ? ");
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);

			stat.execute();

			stat.close();

		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return resultado;
	}

	@Override
	public List<Livro> obterTodos() {
		Connection conn = DAO.getConnection();

		List<Livro> listaLivro = new ArrayList<Livro>();

		if (conn == null)
			return null;

		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  l.id, ");
			sql.append("  l.nome, ");
			sql.append("  l.lancamento, ");
			sql.append("  l.editora, ");
			sql.append("  l.estoque, ");
			sql.append("  l.valor, ");
			sql.append("  l.idioma, ");
			sql.append("  a.id AS id_autor, ");
			sql.append("  a.nome AS nome_autor ");
			sql.append("FROM ");
			sql.append("  livro l INNER JOIN autor a ON l.id_autor = a.id ");
			sql.append("ORDER BY ");
			sql.append("  l.nome ");

			rs = conn.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Livro livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setNome(rs.getString("nome"));

				Date data = rs.getDate("lancamento");
				if (data == null)
					livro.setLancamento(null);
				else
					livro.setLancamento(data.toLocalDate());

				livro.setEditora(rs.getString("editora"));
				livro.setEstoque(rs.getInt("estoque"));
				livro.setValor(rs.getDouble("valor"));
				livro.setIdioma(Idioma.valueOf(rs.getInt("idioma")));

				livro.setAutor(new Autor());
				livro.getAutor().setId(rs.getInt("id_autor"));
				livro.getAutor().setNome(rs.getString("nome_autor"));

				listaLivro.add(livro);
			}

		} catch (SQLException e) {
			listaLivro = null;
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

		return listaLivro;
	}

	public Livro buscarPorId(Integer id) {
		Connection conn = DAO.getConnection();

		if (conn == null)
			return null;

		PreparedStatement stat = null;
		ResultSet rs = null;
		Livro livro = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  l.id, ");
			sql.append("  l.nome, ");
			sql.append("  l.lancamento, ");
			sql.append("  l.editora, ");
			sql.append("  l.estoque, ");
			sql.append("  l.valor, ");
			sql.append("  l.idioma, ");
			sql.append("  a.id AS id_autor, ");
			sql.append("  a.nome AS nome_autor ");
			sql.append("FROM ");
			sql.append("  livro l INNER JOIN autor a ON l.id_autor = a.id ");
			sql.append("WHERE ");
			sql.append(" l.id = ? ");

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);

			rs = stat.executeQuery();

			if (rs.next()) {
				livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setNome(rs.getString("nome"));

				Date data = rs.getDate("lancamento");
				if (data == null)
					livro.setLancamento(null);
				else
					livro.setLancamento(data.toLocalDate());

				livro.setEditora(rs.getString("editora"));
				livro.setEstoque(rs.getInt("estoque"));
				livro.setValor(rs.getDouble("valor"));
				livro.setIdioma(Idioma.valueOf(rs.getInt("idioma")));

				livro.setAutor(new Autor());
				livro.getAutor().setId(rs.getInt("id_autor"));
				livro.getAutor().setNome(rs.getString("nome_autor"));

			}

		} catch (SQLException e) {
			livro = null;
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

		return livro;

	}

	public List<Livro> buscarPorNome(String nome) {
		Connection conn = DAO.getConnection();

		List<Livro> listaLivro = new ArrayList<Livro>();

		if (conn == null)
			return null;

		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  l.id, ");
			sql.append("  l.nome, ");
			sql.append("  l.lancamento, ");
			sql.append("  l.editora, ");
			sql.append("  l.estoque, ");
			sql.append("  l.valor, ");
			sql.append("  l.idioma, ");
			sql.append("  a.id AS id_autor, ");
			sql.append("  a.nome AS nome_autor ");
			sql.append("FROM ");
			sql.append("  livro l INNER JOIN autor a ON l.id_autor = a.id ");
			sql.append("WHERE ");
			sql.append("  l.nome iLIKE ? ");
			sql.append("ORDER BY ");
			sql.append("  l.nome ");

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + nome + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				Livro livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setNome(rs.getString("nome"));

				Date data = rs.getDate("lancamento");
				if (data == null)
					livro.setLancamento(null);
				else
					livro.setLancamento(data.toLocalDate());

				livro.setEditora(rs.getString("editora"));
				livro.setEstoque(rs.getInt("estoque"));
				livro.setValor(rs.getDouble("valor"));
				livro.setIdioma(Idioma.valueOf(rs.getInt("idioma")));

				livro.setAutor(new Autor());
				livro.getAutor().setId(rs.getInt("id_autor"));
				livro.getAutor().setNome(rs.getString("nome_autor"));

				listaLivro.add(livro);
			}

		} catch (SQLException e) {
			listaLivro = null;
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

		return listaLivro;

	}

	public List<Livro> buscarPorAutor(String nome) {
		Connection conn = DAO.getConnection();

		List<Livro> listaLivro = new ArrayList<Livro>();

		if (conn == null)
			return null;

		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  l.id, ");
			sql.append("  l.nome, ");
			sql.append("  l.lancamento, ");
			sql.append("  l.editora, ");
			sql.append("  l.estoque, ");
			sql.append("  l.valor, ");
			sql.append("  l.idioma, ");
			sql.append("  a.id AS id_autor, ");
			sql.append("  a.nome AS nome_autor ");
			sql.append("FROM ");
			sql.append("  livro l INNER JOIN autor a ON l.id_autor = a.id ");
			sql.append("WHERE ");
			sql.append("  a.nome iLIKE ? ");
			sql.append("ORDER BY ");
			sql.append("  l.nome ");

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + nome + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				Livro livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setNome(rs.getString("nome"));

				Date data = rs.getDate("lancamento");
				if (data == null)
					livro.setLancamento(null);
				else
					livro.setLancamento(data.toLocalDate());

				livro.setEditora(rs.getString("editora"));
				livro.setEstoque(rs.getInt("estoque"));
				livro.setValor(rs.getDouble("valor"));
				livro.setIdioma(Idioma.valueOf(rs.getInt("idioma")));

				livro.setAutor(new Autor());
				livro.getAutor().setId(rs.getInt("id_autor"));
				livro.getAutor().setNome(rs.getString("nome_autor"));

				listaLivro.add(livro);
			}

		} catch (SQLException e) {
			listaLivro = null;
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

		return listaLivro;

	}

	public List<Livro> buscarPorEditora(String editora) {
		Connection conn = DAO.getConnection();

		List<Livro> listaLivro = new ArrayList<Livro>();

		if (conn == null)
			return null;

		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  l.id, ");
			sql.append("  l.nome, ");
			sql.append("  l.lancamento, ");
			sql.append("  l.editora, ");
			sql.append("  l.estoque, ");
			sql.append("  l.valor, ");
			sql.append("  l.idioma, ");
			sql.append("  a.id AS id_autor, ");
			sql.append("  a.nome AS nome_autor ");
			sql.append("FROM ");
			sql.append("  livro l INNER JOIN autor a ON l.id_autor = a.id ");
			sql.append("WHERE ");
			sql.append("  l.editora iLIKE ? ");
			sql.append("ORDER BY ");
			sql.append("  l.nome ");

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + editora + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				Livro livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setNome(rs.getString("nome"));

				Date data = rs.getDate("lancamento");
				if (data == null)
					livro.setLancamento(null);
				else
					livro.setLancamento(data.toLocalDate());

				livro.setEditora(rs.getString("editora"));
				livro.setEstoque(rs.getInt("estoque"));
				livro.setValor(rs.getDouble("valor"));
				livro.setIdioma(Idioma.valueOf(rs.getInt("idioma")));

				livro.setAutor(new Autor());
				livro.getAutor().setId(rs.getInt("id_autor"));
				livro.getAutor().setNome(rs.getString("nome_autor"));

				listaLivro.add(livro);
			}

		} catch (SQLException e) {
			listaLivro = null;
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

		return listaLivro;

	}

}
