package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;





/**
 * Clase para implementar el patron DAO
 * con la tabla de OBRAS, se interactua con la base de datos
 * 
 * @author Alejandro Terrones Pérez 
 *
 */

public class ObraDAO {

	//Variables de instancia
	private Connection conexion;
	private Statement sentencias;
	private ResultSet resultadoConsulta; 



	/**
	 * Constructor
	 * @throws SQLException
	 * @throws Exception
	 */
	public ObraDAO() throws SQLException, Exception {
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

		// En realidad no necesitamos que los datos del ResultSet sean actualizables, ya
		// que estamos creando un nuevo objeto Statement (en concreto PreparedStatement)
		// para realizar las actualizaciones de forma parametrizada y así evitar
		// el SQL Injection
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
		String sqlString = "SELECT * FROM OBRAS";
		
		resultadoConsulta = sentencias.executeQuery(sqlString);
	}
	
	/**
	 * Método que devuelve el primer registro (tupla) de la consulta 
	 * @throws MiExcepcion 
	 */
	public obraArte getPrimero() throws SQLException, misExcepciones {
		resultadoConsulta.first();
		return this.creaObraArte();
	}

			
	/**
	 * Método que devuelve el ultimo registro (tupla) de la consulta 
	 * @throws MiExcepcion 
	 */
	public obraArte getUltimo() throws SQLException, misExcepciones {
		resultadoConsulta.last();
		return this.creaObraArte();
	}
	
	/**	 
	 * Método que devuelve el registro siguiente al acual
	 * @throws MiExcepcion 
	 */
	public obraArte getSiguiente() throws SQLException, misExcepciones {
		resultadoConsulta.next();
		return this.creaObraArte();
	}
		
	/**
	 * Método que devuelve el registro anterior al actual
	 * @throws MiExcepcion 
	 */
	public obraArte getAnterior() throws SQLException, misExcepciones {
		resultadoConsulta.previous();
		return this.creaObraArte();
	}
	
	
	/**
	 * Devuelve la posicion actual
	 * del objeto arte
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public obraArte getActual(String titulo) throws SQLException, misExcepciones {
		resultadoConsulta.absolute(obtenFila(listaCuadros(), titulo));
		return this.creaObraArte();
	}
	
	

	/**
	 * Método que crea objetos de tipo obraArte 
	 * a partir de los datos de la tabla obras
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public obraArte creaObraArte() throws SQLException, misExcepciones {
		return new obraArte(
				resultadoConsulta.getInt("id_artista"),
				resultadoConsulta.getString("titulo"),
				resultadoConsulta.getString("descripcion"),
				resultadoConsulta.getString("ruta_imagen"),
				String.valueOf(resultadoConsulta.getInt("año")),
			enumMetodo.buscaMetodo (resultadoConsulta.getString("tecnica_artista"))

				);
	}
	
	
	/**
	 * Método que crea una matriz de tipo String
	 * es una consulta que devuelve los cuadros y los artistas
	 * ordenados por el titulo del cuadro
	 * @return
	 * @throws SQLException
	 */
	public String[][]consultaCuadrosOrdenNombre () throws SQLException{


		//Creamos consulta
		String consulta = "SELECT obras.titulo, artistas.nombre "
				+ "FROM `obras` "
				+ "INNER JOIN `artistas` "
				+ "WHERE obras.id_artista = artistas.id_artista "
				+ "ORDER BY obras.titulo "; 

		ResultSet rsConsulta = null;
		rsConsulta = this.sentencias.executeQuery(consulta);


		//Averiguamos el nº de filas que va a tener la sentencia
		rsConsulta.last();
		int numF = rsConsulta.getRow();
		rsConsulta.first();

		//Guardamos ls resultados de las filas en una matriz
		String [][] cuadrosOrdenTitulo = new String [numF][2]; 


		//Iteramos y guardamos
		for (int fila = 0; fila < numF; fila ++){
			cuadrosOrdenTitulo[fila][0] = rsConsulta.getString("titulo");
			cuadrosOrdenTitulo[fila][1] = rsConsulta.getString("nombre");
			rsConsulta.next();
		}

		this.crearConsulta();

		return cuadrosOrdenTitulo;
	}

	
	/**
	 * Con este método obtener una matriz
	 * de la consulta de mostrar todas las obras 
	 * ordenadas por fecha con su periodo
	 * @param autor
	 * @return
	 * @throws SQLException
	 */
	public String [][]consultaCuadrosPeriodo() throws SQLException{


		//Creamos la consulta
		String consulta = "SELECT obras.titulo, obras.año, artistas.periodo "
				+ "FROM `obras` INNER JOIN `artistas` "
				+ "WHERE obras.id_artista = artistas.id_artista"
				+ " ORDER BY obras.año";



		ResultSet rsConsulta = null;
		rsConsulta = this.sentencias.executeQuery(consulta);



		//Averiguamos el nº de filas que va a tener la sentencia
		rsConsulta.last();
		int numF = rsConsulta.getRow();
		rsConsulta.first();

		//Guardamos ls resultados de las filas en una matriz
		String [][] cuadrosPeriodo = new String [numF][3]; 


		//Iteramos y guardamos
		for (int fila = 0; fila < numF; fila ++){
			cuadrosPeriodo[fila][0] = rsConsulta.getString("titulo");
			cuadrosPeriodo[fila][1] = rsConsulta.getString("año");
			cuadrosPeriodo[fila][2] = rsConsulta.getString("periodo");


			rsConsulta.next();
		}


		this.crearConsulta();

		return cuadrosPeriodo;
	}

	
	/**
	 * Método que devuelve una lista
	 * de todos los titulos de los cuadros disponibles
	 * lo usamos para cargar los comboBoxes
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public List <String> listaCuadros() throws SQLException, misExcepciones {
		
		resultadoConsulta.beforeFirst(); // Para posicionar la consulta al principio
		
		List <String> listaCuadros = new ArrayList<>();

		while (resultadoConsulta.next()) {
			listaCuadros.add(creaObraArte().getTitulo());
		}

		resultadoConsulta.first();
		
		return listaCuadros;
	}
	
	
	/**
	 * Método que devuelve una lista 
	 * de objeto de obraArte
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public List <obraArte> listaCuadrosObj() throws SQLException, misExcepciones {
		
		resultadoConsulta.beforeFirst(); // Para posicionar la consulta al principio
		
		List <obraArte> listaCuadros = new ArrayList<>();

		while (resultadoConsulta.next()) {
			listaCuadros.add(creaObraArte());
		}

		resultadoConsulta.first();
		
		return listaCuadros;
	}
	
	/**
	 * Método que devuelve el id_obra
	 * en funcion del titulo
	 * Lo usamos para el comboBox de los autores
	 * para obtener su id, y pasarlo como parametro
	 * al objeto obraArte
	 * @param titulo
	 * @return
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	public int buscaIdObra(String titulo) throws SQLException, misExcepciones {

		int id_obra = 0;
		
	    String sql = "SELECT id_obra FROM obras WHERE titulo = ?";
	    
	    PreparedStatement ps = conexion.prepareStatement(sql);
	    ps.setString(1, titulo);
	    
	    ResultSet rs = ps.executeQuery();
	    
	    if (rs.next()) {
	    	id_obra = rs.getInt("id_obra");
	    }
	    
	    rs.close();
	    ps.close();

	    return id_obra;

	}
	
	//------------------MANTENIMIENTO CUADRO------------------------------//
	
	
	/**
	 * Método para realizar inserts a la tabla obras
	 * @param ob
	 * @throws SQLException
	 */
	public void insertObras(obraArte ob) throws SQLException {

		PreparedStatement ps = 
				conexion.prepareStatement("INSERT INTO proyecto.obras (id_artista, titulo, descripcion, año, ruta_imagen, tecnica_artista) VALUES (?,?,?,?,?,?)");

		ps.setInt(1,ob.getId_artista());
		ps.setString(2, ob.getTitulo());
		ps.setString(3, ob.getDescripcion());
		ps.setInt(4, ob.getFecha().getYear()); 
		ps.setString(5, ob.getImagen());
		ps.setString(6,ob.getTecnicaObra().getTecnica());



		ps.executeUpdate();
		ps.close();

		this.crearConsulta();  
	}


	/**
	 * Método que permite hacer
	 * un update de un objetoObra
	 * para ello le pasamos el objeto obra, con los parametros a cambiar
	 * y el id de la obra que queremos actualizar
	 * @param ob
	 * @throws SQLException
	 */
	public void updateObra(obraArte ob, int id) throws SQLException {

		PreparedStatement ps = conexion.prepareStatement(
				"UPDATE obras SET id_artista = ?, titulo = ?, descripcion = ?, año = ?"
						+ ", ruta_imagen = ?, tecnica_artista = ? WHERE id_obra =" + id);

		ps.setInt(1, ob.getId_artista());
		ps.setString(2, ob.getTitulo());
		ps.setString(3, ob.getDescripcion());
		ps.setInt(4, ob.getFecha().getYear());
		ps.setString(5,ob.getImagen());
		ps.setString(6,ob.getTecnicaObra().getTecnica());

		ps.executeUpdate();
		ps.close();


		this.crearConsulta();		
	}

	/**
	 * Permite eliminar una obra
	 * a partir del título
	 * @param titulo
	 * @throws SQLException
	 */
	public void deleteObra(String titulo) throws SQLException{
		PreparedStatement ps = 
				conexion.prepareStatement("DELETE FROM obras WHERE titulo = ?");

		ps.setString(1, titulo);

		ps.executeUpdate();
		ps.close();


		this.crearConsulta();
	}
		
	
	//----------------------------METODOS PRIVADOS----------------------------//
	
	
	/**
	 * Método que nos permitirá saber la fila 
	 * en la que se encuentra un objeto
	 * lo vamos a usar para el metodo getActual
	 * @param cuadros
	 * @param titulo
	 * @return
	 */
	private int obtenFila (List <String> cuadros, String titulo){

		int fila = 1; //iniciamos a 1
		Iterator <String> ItObra = cuadros.iterator();
		boolean encuentra = true;
		while (ItObra.hasNext() && encuentra){
			if (ItObra.next().equalsIgnoreCase(titulo)){
				encuentra = false;
			}
			else{
				fila ++;
			}
		}
		return fila;
	}
	
	
	
	
	
	
}

