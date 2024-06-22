package vista;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 * JTable para ver las obras ordenadas 
 * alfabeticamente junto el nombre del 
 * autor
 * @author Alejandro Terrones 
 *
 */

public class consultaObrasOrdenTitulo extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5174211553641461653L;

	
	
	/**
	 * Constructor de la clase
	 */
	public consultaObrasOrdenTitulo (String [][] consulta) {
		
		
		//Ponemos modal y gestor de esquemas
		this.setModal(true);
		this.setTitle("Obras ordenadas alfabéticamente");
		this.setLayout(new FlowLayout ());
		
		String [] nombreColumnas = {"Título", "Autor"}; //nombre de las columnas
		
		JTable tableResultados = new JTable (consulta, nombreColumnas); //añadimos matriz de consultas y el nombre de columnas
		JScrollPane scrollPane = new JScrollPane (tableResultados);
		
		this.add(scrollPane);
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //al cerrar se elimina de la memoria
    	this.pack();  
		this.setVisible(true);   // Para mostrar el dialogo
		this.setResizable(false);
		
		
	}
	
	
	
}
