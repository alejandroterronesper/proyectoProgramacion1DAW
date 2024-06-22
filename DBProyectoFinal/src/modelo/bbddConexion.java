package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** 
 * Esta clase implementa el patrón Singleton para tener conexión a la base de datos
 */

public class bbddConexion {
	
	// Ruta de la bases de datos
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/proyecto";
	
	// Objeto para tener conexión con la BBDD
	private static Connection instance = null;
	
	/**
	 * Constructor privado, para evitar que puedan crearse objeto de esta clase
	 */
	private bbddConexion() { }
	
	/**
	 * 
	 * M�todo para devolver una referencia a la conexión,
	 * si se llama por primera vez, se crea
	 * si se vuelve a llamar, la conexión ya existe y la devuelve

	 * 
	 * @return --> El objeto Connection
	 * @throws SQLException
	 */
	public static Connection estableceConexion() throws SQLException {
		if (instance == null) {
			Properties props = new Properties();
			props.put("user", "alejandro");
			props.put("password", "1234");
			instance = DriverManager.getConnection(JDBC_URL, props);
		}
		
		return instance;
	}
	
	

	
	
}
