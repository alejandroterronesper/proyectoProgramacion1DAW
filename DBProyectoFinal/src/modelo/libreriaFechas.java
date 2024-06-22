package modelo;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


/**
 * Libreria para manejar las fechas
 * de los cuadros y los artistas
 * 
 * @author Alejandro Terrones
 *
 */
public class libreriaFechas {


	/**
	 * Este método convierte una fecha en formato String a LocalDate
	 * 
	 * @param fechaString
	 * @return La fecha como objeto LocalDate, o un valor null, si su formato
	 *         no era vï¿½lido
	 */
	public static LocalDate convierteStringToLocalDate(String fechaString) {


		try {
			String [] arrayFecha = fechaString.split("/"); // Extraigo el dï¿½a, mes y aï¿½o

			LocalDate fecha = LocalDate.of(
					Integer.parseInt(arrayFecha[2]),  // AÃ±o
					Integer.parseInt(arrayFecha[1]),  // Mes 
					Integer.parseInt(arrayFecha[0])); // DÃ­a 

			// Si la fecha no es vï¿½lida el mï¿½todo of, lanzarï¿½ una excepciï¿½n (DateTimeException)
			// Si la fecha no tenï¿½a el formato dd/mm/yyyy, saltarï¿½ la excepciï¿½n
			// de array desbordado

			return fecha;
		}
		catch (DateTimeException e) {
			//			System.out.println("Fecha incorrecta "+fechaString);
			return null;
			//return LocalDate.now();
		}
		catch (ArrayIndexOutOfBoundsException e) {
			//	System.out.println("Menos datos de los esperados "+fechaString);
			return null;
			//return LocalDate.now();
		}
		catch (NullPointerException e) {
			return null;
		}
		catch (NumberFormatException e) {
			return null;
		}
	}


	/**
	 * Mï¿½todo que comprueba si una fecha es correcta, consideraremos
	 * fechas correctas las que tenga el formato dd/mm/yyyy
	 * @param fecha
	 * @return
	 */
	public static boolean isFechaCorrecta(String fechaString) {

		try{
			// El mï¿½todo convierteStringToLocalDate, es un mï¿½todo propio, que devuelve
			// null, si la fecha no es vï¿½lida, o no tiene el formato adecuado
			if (convierteStringToLocalDate(fechaString)==null)
				return false;

			LocalDate.parse(fechaString, DateTimeFormatter.ofPattern("d/M/yyyy")); 
			// Si el formato no es el esperado, el mï¿½todo parse lanzarï¿½ una excepcion (DateTimeParseExcepcion)
			// Lleva solo una d y una M, para que admita tambiï¿½n 3/2/2022, sino esta fecha
			// no serï¿½a vï¿½lida y sï¿½lo admitirï¿½a 03/02/2022
		}

		catch (DateTimeParseException e) {  // Esta saltarï¿½ con fechas como "12/10/20" 
			//	System.out.println("Formato incorrecto "+fechaString);
			return false;
		}

		return true;
	}

	/*
	 * Esta versiï¿½n no funciona bien, ya que si le paso, por ejemplo 31/09/2020
	 * me da la fecha por vï¿½lida, ya que me cambia el 31 por 30 ï¿½?
	 * public static boolean isFechaCorrecta(String fechaString) {

		try {
			LocalDate fecha = LocalDate.parse(fechaString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			System.out.println(fecha);
		}
		catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
	 */

	/**
	 * Mï¿½todo que devuelve una fecha formateada en formato corto 
	 * Ejemplo: 15/09/2008
	 * 
	 * @param fecha --> Se supone que la fecha es correcta, se habrï¿½ 
	 *                  validado previamente, si llega a null, se devuelve 
	 *                  la cadena vacï¿½a
	 * @return
	 */
	public static String getFechaShort(LocalDate fecha) {

		if (fecha == null) return "";
		return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	}

	/**
	 * Mï¿½todo que devuelve una fecha formateada en formato largo 
	 * Ejemplo: 15/09/2008
	 * 
	 * @param fecha --> Se supone que la fecha es correcta, se habrï¿½ 
	 *                  validado previamente, si llega a null, se devuelve 
	 *                  la cadena vacï¿½a
	 * @return
	 */
	public static String getFechaFull(LocalDate fecha) {

		if (fecha == null) return "";
		return fecha.format(DateTimeFormatter.ofPattern("EEEE',' d 'de' MMMM 'de' yyyy"));

	}


	/**
	 * Mï¿½todo que calcula la edad de una persona
	 * @param fechaNacimiento
	 * @return
	 */
	public static int getEdad(LocalDate fechaNacimiento) {

		return (int) ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now());

	}

	/**
	 * Mï¿½todo que suma un nï¿½mero de dï¿½as a una fecha que llega como parï¿½metro
	 * @param fechaInicio
	 * @param dias
	 * @return
	 */
	public static LocalDate sumaDias(LocalDate fechaInicio, int dias) {
		return fechaInicio.plusDays(dias);
	}


	/**
	 * M�todo que transforma una fecha leida en formato aaaa-mm-dd a dd/mm/aaaa
	 * Se utiliza cuando recuperamos fechas de una BD, que nos la devuelve en ese formato
	 * 
	 * @param fecha -- > aaaa-mm-dd
	 * @return --> dd/mm/aaaa
	 */
	public static String transformaFecha(String fecha) {

		String [] arrayFecha = fecha.split("-"); 

		String nuevaFecha = arrayFecha[2] + "/" + arrayFecha[1] + "/" + arrayFecha[0];

		return nuevaFecha;
	}
}
