package br.unitins.a2topicos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.a2topicos.model.Venda;
import br.unitins.a2topicos.model.Autor;
import br.unitins.a2topicos.model.Idioma;
import br.unitins.a2topicos.model.Livro;
import br.unitins.a2topicos.model.LivroVenda;
import br.unitins.a2topicos.model.Usuario;

public class VendaDAO implements DAO<Venda> {

	@Override
	public boolean incluir(Venda venda) {
		Connection conn = DAO.getConnection();
		if (conn == null)
			return false;

		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO venda ( ");
		sql.append("  data, ");
		sql.append("  id_usuario, ");
		sql.append("  valor_total ");
		sql.append(") VALUES (");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?  ");
		sql.append(") ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setDate(1, Date.valueOf(venda.getData()));
			stat.setInt(2, venda.getUsuario().getId());
			stat.setDouble(3, venda.getTotalVenda());

			stat.execute();

			
			ResultSet rs = stat.getGeneratedKeys();
			if (rs.next()) {
				venda.setId(rs.getInt("id"));
			}

			salvarLivrosVenda(venda, conn);

			
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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

	private void salvarLivrosVenda(Venda venda, Connection conn) throws SQLException {

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO livro_venda ( ");
		sql.append("  valor, ");
		sql.append("  quantidade, ");
		sql.append("  id_venda, ");
		sql.append("  id_livro ");
		sql.append(") VALUES (");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?  ");
		sql.append(") ");

		PreparedStatement stat = null;
		for (LivroVenda livroVenda : venda.getListaLivroVenda()) {
			stat = conn.prepareStatement(sql.toString());
			stat.setDouble(1, livroVenda.getValor());
			stat.setInt(2, livroVenda.getQuantidade());
			stat.setInt(3, venda.getId());
			stat.setInt(4, livroVenda.getLivro().getId());

			stat.execute();
		}

	}

	@Override
	public boolean alterar(Venda obj) {
	
		return false;
	}

	@Override
	public boolean excluir(Integer id) {
		
		return false;
	}

	@Override
	public List<Venda> obterTodos() {
	
		return null;
	}

	public List<Venda> obterTodos(Usuario usuario) {
		Connection conn = DAO.getConnection();

		List<Venda> listaVenda = new ArrayList<Venda>();

		if (conn == null)
			return null;

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  v.id, ");
			sql.append("  v.data, ");
			sql.append("  v.valor_total ");
			sql.append("FROM ");
			sql.append("  venda v ");
			sql.append("WHERE  ");
			sql.append("  v.id_usuario = ? ");
			sql.append("ORDER BY ");
			sql.append("  v.data DESC ");

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, usuario.getId());

			rs = stat.executeQuery();
			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				
				Date data = rs.getDate("data");
				if (data == null)
					venda.setData(null);
				else
					venda.setData(data.toLocalDate());
				
				venda.setTotalVenda(rs.getDouble("valor_total"));
				
				venda.setUsuario(usuario);
				venda.setListaLivroVenda(obterLivrosVenda(venda.getId()));

				listaVenda.add(venda);
			}

		} catch (SQLException e) {
			listaVenda = null;
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

		return listaVenda;

	}

	
	
	private List<LivroVenda> obterLivrosVenda(Integer id) {
		
		Connection conn = DAO.getConnection();
		
		List<LivroVenda> lista = new ArrayList<LivroVenda>();
		
		if (conn == null) 
			return null;
			
		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT  ");
			sql.append("  i.id, ");
			sql.append("  i.valor, ");
			sql.append("  i.quantidade, ");
			sql.append("  p.id AS id_livro, ");
			sql.append("  p.nome AS nome_livro, ");
			sql.append("  p.lancamento AS lancamento_livro, ");
			sql.append("  p.editora AS editora_livro, ");
			sql.append("  p.estoque AS estoque_livro, ");
			sql.append("  p.valor AS valor_livro, ");
			sql.append("  p.idioma AS idioma_livro, ");
			sql.append("  a.id AS id_autor, ");
			sql.append("  a.nome AS nome_autor ");
			sql.append("FROM  ");
			sql.append("	livro_venda i, ");
			sql.append("	livro p, ");
			sql.append("	autor a ");
			sql.append("WHERE  ");
			sql.append("	i.id_livro = p.id AND ");
			sql.append("	p.id_autor = a.id AND ");
			sql.append("	i.id_venda = ? ");
			
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			while(rs.next()) {
				LivroVenda item = new LivroVenda();
				item.setId(rs.getInt("id"));
				item.setValor(rs.getDouble("valor"));
				item.setQuantidade(rs.getInt("quantidade"));

				item.setLivro(new Livro());
				item.getLivro().setId(rs.getInt("id_livro"));
				item.getLivro().setNome(rs.getString("nome_livro"));

				Date data = rs.getDate("lancamento_livro");
				if (data == null)
					item.getLivro().setLancamento(null);
				else
					item.getLivro().setLancamento(data.toLocalDate());

				item.getLivro().setEditora(rs.getString("editora_livro"));
				item.getLivro().setEstoque(rs.getInt("estoque_livro"));
				item.getLivro().setValor(rs.getDouble("valor_livro"));
				item.getLivro().setIdioma(Idioma.valueOf(rs.getInt("idioma_livro")));

				item.getLivro().setAutor(new Autor());
				item.getLivro().getAutor().setId(rs.getInt("id_autor"));
				item.getLivro().getAutor().setNome(rs.getString("nome_autor"));

				lista.add(item);
			}
			
		} catch (SQLException e) {
			lista = null;
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
		
		return lista;	
		
	}

}
