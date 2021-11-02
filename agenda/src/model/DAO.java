package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	// -- M�dulo de Conex�o -- //
	/** The driver. */
	// Par�metro de Conex�o:
	private String driver = "com.mysql.cj.jdbc.Driver"; // S� funciona se importar o conector java para a pasta "lib"
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "@Esd121980";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// M�todo de Conex�o:
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver); // vai ler o drive do BD
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/*
	 * OBSERVA��O: apenas para teste de conex�o com o BD. //Teste de conex�o: public
	 * void testeConexao() { try { Connection con = conectar();
	 * System.out.println(con); con.close(); } catch (Exception e) {
	 * System.out.println(e); } }
	 */

	/**
	 *  CRUD - CREATE *.
	 *
	 * @param contato the contato
	 */
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome,fone,email) values(?,?,?)";
		try {
			// abrir a conex�o:
			Connection con = conectar();

			// preparar a query para execu��o no BD:
			PreparedStatement pst = con.prepareStatement(create);

			// subtituir os par�metros (?) pelo conte�do das vari�veis JavaBeans:
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			// executar a query:
			pst.executeUpdate();

			// Encerrar a conex�o com o BD:
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 *  CRUD - READ *.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> listarContatos() {
		// Criando um objeto para acessar a classe JavaBeans:
		ArrayList<JavaBeans> contatos = new ArrayList<>();

		String read = "select * from contatos order by nome";
		try {
			// abrir a conex�o:
			Connection con = conectar();

			// preparar a query para execu��o no BD:
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// o loop abaixo ser� executado enquanto houver contatos:
			while (rs.next()) {
				// Vari�veis de apoio que recebbem os dados do BD:
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// populando o ArrayList:
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			// Encerrar a conex�o com o BD:
			con.close();
			// Retorna o objeto contatos:
			return contatos;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 *  CRUD - UPDATE *.
	 *
	 * @param contato the contato
	 */
	// Selecionar o contatos:
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		try {
			// abrir a conex�o:
			Connection con = conectar();
			// preparar a query para execu��o no BD:
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				// Setar as vari�veis JavaBeans:
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			// Encerrar a conex�o com o BD:
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	// Editar contato:
	public void alterarContato(JavaBeans contato) {
		String update = "update contatos set nome=?, fone=?, email=? where idcon=?";
		try {
			// abrir a conex�o:
			Connection con = conectar();

			// preparar a query para execu��o no BD:
			PreparedStatement pst = con.prepareStatement(update);
			// Substituir as "?" pelas v�riaveis "JavaBeans":
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());

			// executar a query:
			pst.executeUpdate();
			/*
			 * Executa a instru��o SQL neste PreparedStatementobjeto, que deve ser uma
			 * instru��o SQL Data Manipulation Language (DML), como INSERT, UPDATEou DELETE;
			 * ou uma instru��o SQL que n�o retorna nada, como uma instru��o DDL.
			 */
			
			// fechar a conex�o:
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 *  CRUD DELETE *.
	 *
	 * @param contato the contato
	 */
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}