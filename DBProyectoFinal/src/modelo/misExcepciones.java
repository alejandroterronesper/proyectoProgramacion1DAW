package modelo;

/**
 * Clase para manejar excepciones
 * de los diferentes métodos y clases
 * 
 * @author Alejandro Terrones
 *
 */

public class misExcepciones extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4740621910910274515L;
	
	/**
	* Constructor por defecto
	*/
	public misExcepciones() {super();}
	
	
	/**
	* Constructor con mensaje personalizado
	* @param msg
	*/
	public misExcepciones(String msg) {
		super(
				msg);
	}

}
