package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * Clase artista
 * nos permite crear objetos de tipos de artista
 * guardamos su fecha de nacimineto, muerte, nombre, nacionadlidad
 * periodo Artistico y el nombre de una foto suya
 * 
 * 
 * @author Alejandro Terrones Pérez
 *
 */


public class Artista {
	
	
	//Variables de instancia
	private LocalDate fecha_nac, fecha_muerte;
	private enumPeriodoArte pArtistico;
	private String nombre,nacionalidad, imagen;
	
	
	/**
	 * Constructor del artista donde le llegan todos los parámetros
	 * @param id_artista
	 * @param nacimiento
	 * @param muerte
	 * @param periodo
	 * @param nacionalida
	 * @throws misExcepciones
	 */
	public Artista (String nombre, String nacimiento, String muerte,
			enumPeriodoArte periodo, String nacionalidad, String imagen) throws misExcepciones{
		
		
		this.nombre = nombre;
		//Validamos ambas fechas
		if (!libreriaFechas.isFechaCorrecta(nacimiento) ||
				!libreriaFechas.isFechaCorrecta(muerte)){

			if (!libreriaFechas.isFechaCorrecta(nacimiento)){
				throw new misExcepciones ("Fecha nacimiento incorrecta");
			}
			else{
				throw new misExcepciones ("Fecha fallecimiento incorrecta incorrecta");
			}
		}
		else{//si son correctas
			this.fecha_nac = libreriaFechas.convierteStringToLocalDate(nacimiento);
			this.fecha_muerte = libreriaFechas.convierteStringToLocalDate(muerte);

		}
	
		this.pArtistico = periodo;
		this.nacionalidad = nacionalidad;
		
		//-----------------------------IMAGEN-----------------------//
		String ruta = "./img/" +  imagen;
		this.imagen = ruta;

	}

	

	/**
	 * redefinición del método toString
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+"\nNombre: " + nombre + 
				"\nNacionalidad: " + nacionalidad + 
				"\nFecha nacimiento: "  + libreriaFechas.getFechaShort(fecha_nac) + 
				"\nFecha muerte:  "  + libreriaFechas.getFechaShort( fecha_muerte) + 
				"\nPeriodo artístico: " + pArtistico.getPeriodo() + " (s." + pArtistico.getSiglo() + ")"
				+ "\nRuta de la imagen: " + this.imagen;
	}






	//GETTERS
	public String getNombre() {return nombre;}
	public LocalDate getFecha_nac() {return fecha_nac;}
	public LocalDate getFecha_muerte() {return fecha_muerte;}
	public enumPeriodoArte getpArtistico() {return pArtistico;}
	public String getNacionalidad() {return nacionalidad;}
	public String getImagen() {return imagen;}

	
	//Setters
	public void setNombre(String nombre) {this.nombre = nombre;}
	public void setFecha_nac(LocalDate fecha_nac) {this.fecha_nac = fecha_nac;}
	public void setFecha_muerte(LocalDate fecha_muerte) {this.fecha_muerte = fecha_muerte;}
	public void setpArtistico(enumPeriodoArte pArtistico) {this.pArtistico = pArtistico;}
	public void setNacionalidad(String nacionalidad) {this.nacionalidad = nacionalidad;}
	public void setImagen(String imagen) {this.imagen = imagen;}






	
	
}
