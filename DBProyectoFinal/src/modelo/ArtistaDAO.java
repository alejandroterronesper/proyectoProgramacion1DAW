package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 * Clase para implementar el patron DAO
 * con la tabla de ARTISTAS, se interactua con la base de datos
 * 
 * @author Alejandro Terrones Pérez
 *
 */


public class ArtistaDAO {

	//Variables de instancia
	private Connection conexion;
	private Statement sentencias;
	private ResultSet resultadoConsulta; 



	/**
	 * Constructor de la clase ArtistaDAO
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArtistaDAO() throws SQLException, Exception {


		this.estableceConexion();
		this.crearStatement();
		this.crearConsulta();
		

	}


	/**
	 * Método para establecer conexión a la base de datos
	 * @throws SQLException
	 * @throws Exception
	 */
	public void estableceConexion() throws SQLException, Exception {

		conexion = bbddConexion.estableceConexion();

		System.out.println("Conexión establecida");
	}



	/**
	 * Método para cerrar conexión de la base da datos
	 * @throws SQLException
	 */
	public void cierraConexion() throws SQLException{

		conexion.close();
		System.out.println("Conexión cerrada");

	}



	/**
	 * Crea objeto Statement
	 * @throws SQLException
	 */
	public void crearStatement() throws SQLException {
		sentencias = conexion.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,  //Cursor bidireccional
				ResultSet.CONCUR_UPDATABLE);      // Se actualizan los resultados

	}
	
	
	
	/**
	 * Método que cierra el objeto Statement
	 */
	public void cierraStatement() throws SQLException {
		sentencias.close();
	}
	
	/**
	 * Método que permite crear la consulta SQL
	 */
	public void crearConsulta() throws SQLException{
		String sqlString = "SELECT * FROM ARTISTAS";
		
		resultadoConsulta = sentencias.executeQuery(sqlString);
	}
	
	/**
	 * Método que devuelve el primer registro (tupla) de la consulta 
	 * @throws MiExcepcion 
	 */
	public Artista getPrimero() throws SQLException, misExcepciones {
		resultadoConsulta.first();
		return this.creaArtista();
	}

			
	/**
	 * Método que devuelve el ultimo registro (tupla) de la consulta 
	 * @throws MiExcepcion 
	 */
	public Artista getUltimo() throws SQLException, misExcepciones {
		resultadoConsulta.last();
		return this.creaArtista();
	}
	
	/**	 
	 * Método que devuelve el registro siguiente al acual
	 * @throws MiExcepcion 
	 */
	public Artista getSiguiente() throws SQLException, misExcepciones {
		resultadoConsulta.next();
		return this.creaArtista();
	}
		
	/**
	 * Método que devuelve el registro anterior al actual
	 * @throws MiExcepcion 
	 */
	public Artista getAnterior() throws SQLException, misExcepciones {
		resultadoConsulta.previous();
		return this.creaArtista();
	}
	
	/**
	 * Método que devuelve el objeto del artista actual,
	 * se usa para las consultas y evitar que al pulsar siguiente
	 *  o anterior se te posicione desde el principio
	 * @param autor
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public Artista getArtistaActual (String autor) throws SQLException, misExcepciones {
		resultadoConsulta.absolute(buscaIdArtista(autor));
		return this.creaArtista();
	}
	
	
	/**
	 * Método que devuelve el nombre de un artista a partir de su id
	 * se utiliza para la el constructor en la pestaña obras
	 * @param cod
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public String buscaArtista(int cod) throws SQLException, misExcepciones {

		String nombre = null;

		String sql = "SELECT nombre FROM artistas WHERE id_artista = ?";

		PreparedStatement ps = conexion.prepareStatement(sql);
		ps.setInt(1, cod);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			nombre = rs.getString("nombre");
		}

		rs.close();
		ps.close();

		return nombre;

	}
	
	
	/**
	 * Método privado que crea objetos de tipo obraArte 
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public Artista creaArtista() throws SQLException, misExcepciones {
		
		return new Artista(
				resultadoConsulta.getString("nombre"),
				libreriaFechas.transformaFecha(resultadoConsulta.getString("fecha_nac")), //ordenamos la fecha dd/mm/yyyy
				libreriaFechas.transformaFecha(resultadoConsulta.getString("fecha_muerte")),
				enumPeriodoArte.buscaPeriodo (resultadoConsulta.getString("periodo")), // sacar periodo
				resultadoConsulta.getString("nacionalidad"),
				resultadoConsulta.getString("ruta_imagen")
				);

	}
	

	
	/**
	 * Método que nos busca el periodo de un artista a partir de su id
	 * Se usará para el panel de cuadros, ya que el periodo no está en la clase
	 * obras, solo en artista
	 * @param cod
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public String buscaPeriodo(int cod) throws SQLException, misExcepciones {

		String periodo = null;

		String sql = "SELECT periodo FROM artistas WHERE id_artista = ?";

		PreparedStatement ps = conexion.prepareStatement(sql);
		ps.setInt(1, cod);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			periodo = rs.getString("periodo");
		}

		rs.close();
		ps.close();

		return periodo;

	}
	
	
	/**
	 * Método que que busca el id de un artista en función del nombre
	 * se utiliza en el comboBox de insertar y actualizar cuadros
	 * ya que a estos objetos les llego por parámetro el id
	 * @param nombre
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public int buscaIdArtista(String nombre) throws SQLException, misExcepciones {

		int id_artista = 0;

	    String sql = "SELECT id_artista FROM artistas WHERE nombre = ?";
	    
	    PreparedStatement ps = conexion.prepareStatement(sql);
	    ps.setString(1, nombre);
	    
	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {
	    	id_artista = rs.getInt("id_artista");
	    }
	    
	    rs.close();
	    ps.close();

	    return id_artista;

	}
	
	
	
	/**
	 * Método para saber el nº de obras de un artistas
	 * @param cod
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public int numObras(String nombreAutor ) throws SQLException, misExcepciones {

		int numObras = 0;

		String sql = "SELECT COUNT(id_obra) FROM OBRAS WHERE id_artista = (SELECT id_artista FROM artistas WHERE nombre = ?)";


		PreparedStatement ps = conexion.prepareStatement(sql);
		ps.setString(1, nombreAutor);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			numObras = rs.getInt(1); 
		}
		rs.close();
		ps.close();
		return numObras;

	}
	
	
	/**
	 * Método que nos devuelve en una matriz de consulta
	 * con los cuadros de un autor de la fila 
	 * de la tabla en la que nos encontramos, junto a su año, tecnica y periodo artístico
	 * ordenadas por año
	 * @param autor
	 * @return
	 * @throws SQLException
	 */
	public String [][]consultaCuadros(String autor) throws SQLException{


		//Creamos la consulta
		String consulta = "SELECT obras.titulo, obras.descripcion, obras.año, obras.tecnica_artista, artistas.periodo "
				+ " FROM `obras` INNER JOIN `artistas`   "
				+ "WHERE obras.id_artista = artistas.id_artista and artistas.nombre = '" + autor 
				+ "'  ORDER BY  obras.año ASC"; 
		;

		ResultSet rsConsulta = null;  
		rsConsulta = this.sentencias.executeQuery(consulta);


		rsConsulta.last();
		int numFilas = rsConsulta.getRow();
		rsConsulta.first();


		// Ir volcando cada fila en una fila de la matriz
		String [][]arrayDatos = new String[numFilas][5];

		for(int fila = 0; fila < numFilas; fila++){
			arrayDatos[fila][0] = rsConsulta.getString("titulo");
			arrayDatos[fila][1] = rsConsulta.getString("descripcion");
			arrayDatos[fila][2] = rsConsulta.getString("año");
			arrayDatos[fila][3] = rsConsulta.getString("tecnica_artista");
			arrayDatos[fila][4] = rsConsulta.getString("periodo");
			rsConsulta.next();
		}

		this.crearConsulta();

		return arrayDatos;
	}
	
	/**
	 * Método que devuelve una lista
	 * de todos los titulos de los cuadros disponibles
	 * lo usamos para cargar los comboBoxes
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public List <String> listaArtistas() throws SQLException, misExcepciones {
		
		resultadoConsulta.beforeFirst(); // Para posicionar la consulta al principio
		
		List<String> listaArtistas = new ArrayList<>();

		while (resultadoConsulta.next()) {
			listaArtistas.add(creaArtista().getNombre());
		}

		resultadoConsulta.first();
		
		return listaArtistas;
	}

	/**
	 * M�todo que muestra una descripción completa de la excepción
	 * que se ha producido
	 * @param ex -- Excepción SQL generada
	 */
	public void printSQLException(SQLException ex)
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
