package modelo;
import java.sql.*;
import java.util.Scanner;


import java.io.*;

/**
 * Clase carga tablas artistas
 * 
 * En esta clase contiene los metodos para crear la tabla artistas
 * y cargarla con datos leidos desde un fichero,
 * en el main ejecutamos los métodos
 * 
 * @author Alejandro Terrones Pérez
 *
 */

public class CreaCargaTablasArtista {

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		
		Connection conexion = null;
		try {
	

						
			// Establecer la conexión
			String url = "jdbc:mysql://localhost:3306/proyecto";
			String usuario = "alejandro";
			String clave   = "1234";
			conexion = DriverManager.getConnection(url, usuario, clave);
			
			System.out.println("Conexión establecida");
			
			// Método para crear tabla artista
			creaTablaArtista(conexion);
			
			// Cargar tabla artista con ficheros
			cargaTablaArtista(conexion);
		}
		catch (SQLException e) {
			printSQLException(e);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conexion.close();
				System.out.println("Conexión cerrada");
			} 
			catch (SQLException e) {printSQLException(e);}
		}
	}
	

	/**
	 * Crea la tabla ARTISTA
	 * contiene los siguientes campos
	 * id_artista, nombre, fecha_nac, muerte, periodo, nacionalidad, nombre imagen
	 * @param con: Conexión
	 */
	public static void creaTablaArtista(Connection con) throws SQLException
	{
		String creaTabla = "create table proyecto.artistas " +
				"(id_artista INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " + //el id va a ser autonumérico
				"nombre VARCHAR(50) NOT NULL, " +
				"fecha_nac DATE NOT NULL, " +
				"fecha_muerte DATE NOT NULL, " +
				"periodo VARCHAR (50) NOT NULL, " 
				+ "nacionalidad VARCHAR (30) NOT NULL, "
				+ "ruta_imagen VARCHAR (50) NOT NULL ) ";

		System.out.println("Se va a ejecutar: "+creaTabla);


		Statement stmt = null;
		stmt = con.createStatement();
		stmt.executeUpdate(creaTabla);
		System.out.println("Tabla ARTISTAS creada");
		stmt.close();
	}

		
	/**
	 *	Método para cargar con datos la tabla artista
	 *	Se leen los datos desde un fichero
	 * @param con
	 * @throws SQLException
	 */
	public static void cargaTablaArtista(Connection con) throws SQLException {

		try (Scanner scFichero = new Scanner(new File("./files/artistas.txt"),"UTF-8")){

			while (scFichero.hasNextLine()) {

				String [] arrayDatos = scFichero.nextLine().split("-");

				String sqlString = "INSERT INTO proyecto.artistas (nombre, fecha_nac, fecha_muerte, periodo, nacionalidad, ruta_imagen) VALUES (?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sqlString);

				ps.setString(1, arrayDatos[0]); // Nombre
				ps.setDate(2, Date.valueOf(libreriaFechas.convierteStringToLocalDate(arrayDatos[1]))); // Fecha nacimiento
				ps.setDate(3, Date.valueOf(libreriaFechas.convierteStringToLocalDate(arrayDatos[2]))); // Fecha muerte
				ps.setString(4,  arrayDatos[3]); // Periodo
				ps.setString(5, arrayDatos[4]); //Nacionalidad
				ps.setString(6, arrayDatos[5]); //ruta
				ps.executeUpdate();
				ps.close();
			}
			System.out.println("artistas volcados desde fichero correctamente");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que muestra una descripción completa de la excepción
	 * que se ha producido
	 * @param ex -- Excepción SQL generada
	 */
	public static void printSQLException(SQLException ex)
	{
		ex.printStackTrace(System.err);
		System.err.println("SQLState: "+ex.getSQLState());
		System.err.println("Error code: "+ex.getErrorCode());
		System.err.println("Message: "+ex.getMessage());
		Throwable t = ex.getCause();
		while (t!=null) {
			System.out.println("Cause: "+t);
			t = t.getCause();
		}
	}
}
