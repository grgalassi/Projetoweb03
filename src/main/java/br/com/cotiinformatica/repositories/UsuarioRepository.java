package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class UsuarioRepository {

	public void create(Usuario usuario) throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("insert into usuario(nome,email,senha) values(?,?,?)");
		statement.setString(1, usuario.getNome());
		statement.setString(2, usuario.getEmail());
		statement.setString(3, usuario.getSenha());
		statement.execute();

		connection.close();
	}

	public void updateSenha(Integer idUsuario, String novaSenha) throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement("update usuario set senha = ? where idusuario = ?");
		statement.setString(1, novaSenha);
		statement.setInt(2, idUsuario);
		statement.execute();

		connection.close();

	}

	public Usuario findByEmail(String email) throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from usuario where email = ?");
		statement.setString(1, email);
		ResultSet resultset = statement.executeQuery();

		Usuario usuario = null;

		if (resultset.next()) {

			usuario = new Usuario();

			usuario.setIdUsuario(resultset.getInt("idusuario"));
			usuario.setNome(resultset.getString("nome"));
			usuario.setEmail(resultset.getString("email"));
			usuario.setSenha(resultset.getString("senha"));

		}

		connection.close();
		return usuario;

	}

	public Usuario findByEmailAndSenha(String email, String senha) throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from usuario where email = ? and senha = ?");
		statement.setString(1, email);
		statement.setString(2, senha);
		ResultSet resultset = statement.executeQuery();

		Usuario usuario = null;

		if (resultset.next()) {

			usuario = new Usuario();

			usuario.setIdUsuario(resultset.getInt("idusuario"));
			usuario.setNome(resultset.getString("nome"));
			usuario.setEmail(resultset.getString("email"));
			usuario.setSenha(resultset.getString("senha"));

		}

		connection.close();
		return usuario;

	}

}
