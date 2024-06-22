package modelo;

/**
 * Aqui vamos a probar distintas clases 
 * del modelo para comprobar su perfecto 
 * funcionamiento
 * @author Alejandro Terrones
 *
 */

public class testeo1 {

	public static void main(String[] args) {
		
		
		
		enumPeriodoArte m =  enumPeriodoArte.IMPRESIONISMO;
		enumMetodo a = enumMetodo.OLEOLIENZO;
		
//byte id_artista, String nombre, String nacimiento, String muerte,
		//enumPeriodoArte periodo, String nacionalidad
		try {
			Artista a1 = new Artista ("Munch", "01/02/1789", "05/02/1854",m , "Francés", "goya.jpg");
			System.out.println(a1.toString());
			
			obraArte o1 = new obraArte (1, "El grito", "el tio grita", "grito.png", "24/05/1245", a);
		
			System.out.println(o1.toString());
			System.out.println(o1.calculaValor());
			
		} catch (misExcepciones e) {
		System.out.println();
			e.printStackTrace();
		}
		
		


	}

	
}
