package modelo;

import java.sql.SQLException;

public class testeoConexion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			System.out.println(bbddConexion.estableceConexion());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 try {
	            ObraDAO obraDAO = new ObraDAO();
	            
	            // Obtener el primer registro de la consulta
	            obraArte primeraObra = obraDAO.getPrimero();
	            System.out.println("Primer obra: " + primeraObra);
	            
	            // Obtener el siguiente registro de la consulta
	            obraArte siguienteObra = obraDAO.getSiguiente();
	            System.out.println("Siguiente obra: " + siguienteObra);
	            
	            // Obtener el último registro de la consulta
	            obraArte ultimaObra = obraDAO.getUltimo();
	            System.out.println("Última obra: " + ultimaObra);
	            
	            // Obtener el registro anterior al actual
	            obraArte anteriorObra = obraDAO.getAnterior();
	            System.out.println("Obra anterior: " + anteriorObra);
	            
	            // Cerrar la conexión
	            obraDAO.cierraConexion();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (misExcepciones e) {
	            e.printStackTrace();
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		

	}

}
