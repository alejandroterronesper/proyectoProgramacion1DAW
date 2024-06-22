package modelo;

/**
 * 
 * Clase enumeración de periodos artístico
 * 
 * @author Alejandro Terrones Pérez
 *
 */

public enum enumPeriodoArte {
	
	ROMANTICISMO ("Romanticismo", "XIX"),
	GOTICO ("Arte gótico", "XII"),
	RENACIMIENTO ("Pintura renacentista", "XV"),
	MANIERISMO ("Pintura manierista", "XVI "),
	BARROCO ("Barroco", "XVII"),
	ROCOCO ("Rococó", "XIX"),
	NEOCLASICISMO ("Pintura neoclásica", "XVIII"),
	REALISMO ("Realismo pictórico", "XIX"),
	IMPRESIONISMO ("Impresionismo", "XIX"),
	SIMBOLISMO ("Simbolismo", "XIX"),
	MODERNISMO ("Modernismo", "XIX"),
	EXPRESIONISMO ("Expresionismo", "XIX");
	
	
	//Variable de instancia
	private String periodo;
	private String siglo;

	

	//Constructor
	enumPeriodoArte(String string, String string2) {
		this.periodo = string;
		this.siglo = string2;
	}
	
	
	//Getters y setters
	public String getPeriodo() {return periodo;}
	public String getSiglo() {return siglo;}
	public void setPeriodo(String periodo) {this.periodo = periodo;}
	public void setSiglo(String siglo) {this.siglo = siglo;}
	
	
	
	
	/**
	 * Método privado que devuelve una enumeracion
	 * en funcion del String
	 * Se utilizaría en caso de un mantenimiento de la tabla artistas
	 * @param periodo
	 * @return
	 */
	public static enumPeriodoArte buscaPeriodo (String periodo){
		enumPeriodoArte [] arrayPeriodo = enumPeriodoArte.values();
		enumPeriodoArte periodoE = null;
		boolean encontrado = true;
		int cont = 0;
		while  (encontrado && cont < arrayPeriodo.length){
			if (arrayPeriodo[cont].getPeriodo().equalsIgnoreCase(periodo)){
				periodoE = arrayPeriodo[cont]; 
				encontrado = false;
			}
			cont++;
		}
		return periodoE;
	}
	
	
}
