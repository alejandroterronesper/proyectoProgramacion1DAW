package modelo;


import java.time.LocalDate;




/**
 * Clase obra de arte
 * en ella creamos un objeto obra de arte
 * se almacena información, como el id del artista
 * , titulo, descripcion, el nombre de la imagen...
 * 
 * Tiene un metodo para calcular su valor en funcion de la tecnica y del año, partiendo de un
 * precio base para todos
 * 
 * Posee un metodo toString para ver la información detallada
 * 
 * 
 * 
 * @author Alejandro Terrones Pérez
 *
 */

public class obraArte {
	
	
	
	//Variables de instancia
	private int  id_artista;
	private String titulo, descripcion, imagen;
	private LocalDate fecha;
	private enumMetodo tecnicaObra;
	

	//Las obras siempre tiene el mismo valor inicial
	private final float PRECIOINICIAL = (float) 4879.45; //Los nombre de las constantes en mayuscula

	
	/**
	 * Constructor de la clase obra de Arte, le llegan los siguientes parámetros  
	 * @param id_artista
	 * @param titulo
	 * @param descripcion
	 * @param imagen
	 * @param fecha dd/mm/yyyy o  yyyy
	 * @param metodo
	 * @throws misExcepciones
	 */
	public obraArte (int id_artista, String titulo, String descripcion, String imagen, String fecha,
			enumMetodo metodo)
					throws misExcepciones{
		this.id_artista = id_artista;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.tecnicaObra = metodo;

		//-----------------------------FECHAS-----------------------//

		//Puede ocurrir que solamente nos llegue el año y no la fecha completa de la obra

		if (fecha.length() > 4){ // fecha completa
			if (libreriaFechas.isFechaCorrecta(fecha)){ //validamos fechas
				this.fecha = libreriaFechas.convierteStringToLocalDate(fecha);
			}
			else{ //lanzamos excepción fecha incorrecta
				throw new misExcepciones ("Introduzca una fecha correcta");
			}
		}
		else{ //solo año
			if ((Integer.parseInt(fecha)) <= 0){ //comprobamos que no sea un nº negativo
				throw new misExcepciones ("Introduzca una fecha correcta");
			}
			else{ //en caso de solo tener año la fecha se guardará asi -> 01/01/yyyy
				this.fecha = libreriaFechas.convierteStringToLocalDate("01/01/"+fecha);
			}
		}

		//-----------------------------IMAGEN-----------------------//
		this.imagen = imagen;
	}




	
	/**
	 * Método que calcula el valor de la 
	 * obra en función de su fecha de creación 
	 * y tecnica usada
	 * @return
	 */
	public float calculaValor (){

		float resultado = PRECIOINICIAL + (PRECIOINICIAL * valorTecnica(this.getTecnicaObra()) + 
				(PRECIOINICIAL *  valorYear(this.getFecha().getYear())));

		return  (float) (Math.round(resultado * 100.0) / 100.0); //redondeamos a dos decimales
	}
	
	
	
	/**
	 * Método que desglosa el valor en 
	 * euros y centimos
	 * @return
	 */
	public String stringValorObra(){
		float dinero = calculaValor();
		int euros = (int) dinero;
		int centimos =  (int )((dinero - euros) * 100) % 100;


		return  euros + " euros y " + centimos  + " céntimos.";	
	}
	

	/**
	 *Redefinición del método toString
	 */
	@Override
	public String toString() {
		return "Titulo: " + titulo + 
				"\nFecha: " + fecha.getYear() + 
				"\nTécnica: " + tecnicaObra.getTecnica() + 
				"\nDescripción: " + descripcion +
				"\nPrecio estimado: " + stringValorObra() +
				"\nRuta imagen: " + imagen; 
	}

	
	//-------------------METODOS PRIVADOS---------------------------------//
	
	/**
	 * Método privado que calcula el porcentaje a aplicar
	 * en función de la tecnica empleada
	 * @param tecnica
	 * @return
	 */
	private static float valorTecnica (enumMetodo tecnica) {
		
		if (tecnica == enumMetodo.OLEOLIENZO){
			return (float) 1.4;
		}
		else if (tecnica == enumMetodo.PUNTILLISMO){
			return (float) 0.5;
		}
		else if (tecnica == enumMetodo.GRABADO || tecnica == enumMetodo.TEMPLE){
			return (float) 0.7;
		}
		else if (tecnica == enumMetodo.ACUERELA){
			return (float) 0.55;
		}
		else if (tecnica == enumMetodo.PASTEL){
			return (float) 0.68;
		}
		else {//en caso de ser una pintura al fresco
			return (float) 0.36;
		}
	}
	
	
	/**
	 * Método privado que nos 
	 * devolvera un porcentaje 
	 * en función del año en el que se
	 * creó la obra, este método se usará
	 * para el método calculaValor()
	 * @return
	 */
	private static float valorYear (int anhio){
		
		if (anhio < 1500){
			return (float) 0.5;
		}
		else if (anhio >= 1500 && anhio < 1655){
			return (float) 0.68;
		}
		else if (anhio >=1655 && anhio < 1784){
			return (float) 0.74;
		}
		else if (anhio >=1784 && anhio < 1845){
			return (float) 0.88;
		}
		else if (anhio >=1845 && anhio < 1905){
			return (float) 0.41;
		}
		else if (anhio >=1905 && anhio < 2000){
			return (float) 0.67;
		}
		else {// del año 2000 a la actualidad
			return (float) 0.17;
		}
	}
	

	//Getters
	public int getId_artista() {return id_artista;}
	public String getTitulo() {return titulo;}
	public String getDescripcion() {return descripcion;}
	public String getImagen() {return imagen;}
	public LocalDate getFecha() {return fecha;}
	public enumMetodo getTecnicaObra() {return tecnicaObra;}



	//Setters
	public void setId_artista(byte id_artista) {this.id_artista = id_artista;}
	public void setTitulo(String titulo) {this.titulo = titulo;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
	public void setImagen(String imagen) {this.imagen = imagen;}
	public void setFecha(LocalDate fecha) {this.fecha = fecha;}
	public void setTecnicaObra(enumMetodo metodoObra) {this.tecnicaObra = metodoObra;}
	
	

}
