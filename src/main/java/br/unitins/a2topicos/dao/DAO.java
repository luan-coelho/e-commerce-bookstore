package br.unitins.a2topicos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
	
	public boolean incluir(T obj);

	public boolean alterar(T obj);

	public boolean excluir(Integer id);

	public List<T> obterTodos();

	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("O Driver nao foi encontrado.");
			e.printStackTrace();
			return null;
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/livrodb", "topicos1", "123456");
		} catch (SQLException e) {
			System.out.println("Erro ao conectar no banco de dados.");
			e.printStackTrace();
		}

		return conn;
	}

}
