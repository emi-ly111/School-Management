package src.data;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBase {
	private String _user = "JA_FUI_FILHAO";
	private String _password = "VAI_FILHAO";
	private String _local = "jdbc:postgresql://localhost:5432/testdb";
	private Connection _conn;

	public DataBase(){
		_conn= null;
	}

	public void Open(){
		try {
			_conn = DriverManager.getConnection(_local,_user,_password);
		} catch (SQLException e){
			    System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
		}

	}
	public void Close(){
		if(_conn != null){
			try {
				_conn.close();
			} catch (SQLException e){
			    System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
			}
		}
	}
}
