package modelo;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


/**
 * Clase que tiene los metodos para crear la tabla obras
 * y cargar tablas obras desde la lectura de un fichero
 * 
 * en el main se ejecutan los dos métodos
 * 
 * @author Alejandro Terrones Pérez
 *
 */
public class CreaCargaTablaObra {
	
	
	/**
	 * 
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
			creaTablaObra(conexion);

			// Cargar tabla artista con ficheros
			cargaTablaObra(conexion);
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
	 * Crea la tabla Obra en nuestra base de datos proyecto
	 * las columnas son
	 * id_obra --> autonumérico
	 * id_artista --> foreign key de la tabla artistas
	 * titulo
	 * descripcion
	 * año
	 * ruta imagen
	 * nombre de la técnica
	 * @param con
	 * @throws SQLException
	 */
	public static void creaTablaObra(Connection con) throws SQLException
	{
		String creaTabla = "create table proyecto.obras " +
				"(id_obra INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
				"id_artista INT NOT NULL, " + 
				"titulo VARCHAR(150) NOT NULL, " +
				"descripcion VARCHAR (150) NOT NULL, " +
				"año INT, " +
				"ruta_imagen VARCHAR (50) NOT NULL, " 
				+ "tecnica_artista VARCHAR (50) NOT NULL)";




		System.out.println("Se va a ejecutar: "+creaTabla);


		Statement stmt = null;
		stmt = con.createStatement();
		stmt.executeUpdate(creaTabla);
		System.out.println("Tabla OBRAS creada");


		String foreign_key = "ALTER TABLE proyecto.obras ADD FOREIGN KEY " +  //le metemos la clave foranea de la 
				" (id_artista) REFERENCES proyecto.artistas (id_artista) " 	//tabla artistas
				+ "ON DELETE NO ACTION ON UPDATE CASCADE;" ;
		
		System.out.println("Foreign key ID_ARTISTA: " + foreign_key);

		stmt.executeUpdate(foreign_key);
		
		//Creamos unique, no puede haber dos objetos con mismo titulo y mismo id
		String unique = "ALTER TABLE proyecto.obras"
				+ " ADD CONSTRAINT unique_obras_titulo_idartista UNIQUE (titulo, id_artista)";
		
		System.out.println("Restriccion unique id_artista y titulo: " + unique);
		
		stmt.executeUpdate(unique);
		
		stmt.close();
	}

		
	/**
	 * Carga la tabla obra a partir de los datos de un fichero
	 * @param con
	 * @throws SQLException
	 */
	public static void cargaTablaObra(Connection con) throws SQLException {

		try (Scanner scFichero = new Scanner(new File("./files/obras.txt"),"UTF-8")){

			while (scFichero.hasNextLine()) {

				String [] arrayDatos = scFichero.nextLine().split("--");

				String sqlString = "INSERT INTO proyecto.obras (id_artista, titulo, descripcion, año, ruta_imagen, tecnica_artista) VALUES (?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sqlString);

				ps.setInt(1, Integer.parseInt(arrayDatos[0])); // id_artista
				ps.setString(2, arrayDatos[1]); //titulo
				ps.setString(3, arrayDatos[2]); //descripcion
				ps.setInt(4, Integer.parseInt(arrayDatos[3])); //año
				ps.setString(5, arrayDatos[4]); //ruta
				ps.setString(6, arrayDatos[5]); //técnica

				ps.executeUpdate();
				ps.close();
			}
			System.out.println("obras volcados desde fichero correctamente");
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
