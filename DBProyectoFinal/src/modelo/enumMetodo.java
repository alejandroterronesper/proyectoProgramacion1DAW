package modelo;

/**
 * Enumeración de las técnicas artísticas
 * utilizadas por las obras de Arte
 * @author Alejandro Terrones
 *
 */

public enum enumMetodo {
	
	OLEOLIENZO ("Óleo sobre lienzo"),
	PUNTILLISMO ("Puntillismo"),
	GRABADO ("Grabado"),
	ACUERELA ("Acuarela"),
	PASTEL ("Pintura al pastel"),
	FRESCO ("Pintura al fresco"),
	TEMPLE ("Pintura al temple");

	
	//Variable de instancia
	private String tecnica;
	
	
	/**
	 * Constructor de enumeracion
	 * @param string
	 */
	enumMetodo(String string) {
		this.tecnica = string;
	}


	
	
	/**
	 * Método para encontrar un objeto Enum a partir del String
	 * Se usará para devolver un objeto Enum tras haber sido seleccionado
	 * en un comboBox de tipo String
	 * @param cadena
	 * @return
	 */
	public static enumMetodo buscaMetodo (String cadena) {
		enumMetodo [] arrayMetodos = enumMetodo.values(); //lo necesitamos por al objeto le pasamos una enum
		//no un String
		boolean encuentra = true;
		enumMetodo resultado = null;
		int cont = 0;
		while (encuentra && cont < arrayMetodos.length){ //con el booleano y el contador salimos del bucle
			if (cadena.equalsIgnoreCase(arrayMetodos[cont].getTecnica())){
				resultado = arrayMetodos[cont];
				encuentra = false;
			}
			cont ++;
		}

		return resultado;
	}
	

	/**
	 * Método que devuelve un array de las diferentes tecnicas artisticas,
	 * lo usamos para cargar el comboBox
	 * @return
	 */
	public static String [] arrayTecnicas () {
		String [] array = new String [enumMetodo.values().length];
		for(int cont = 0; cont < array.length; cont ++){
			array[cont] = enumMetodo.values()[cont].getTecnica();
		}
		return array;
	}
	
	//Getter y setter
	public String getTecnica() {return tecnica;}
	public void setTecnica(String tecnica) {this.tecnica = tecnica;}
	
	
	
	
	
}
