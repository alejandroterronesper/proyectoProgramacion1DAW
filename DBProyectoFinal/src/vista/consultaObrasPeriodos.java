package vista;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 * 
 * Clase que crea un JDialog con el JTable de
 * la consulta de los cuadros ordenados por sus periodos
 * 
 * 
 * @author Alejandro Terrones Pérez
 *
 */


public class consultaObrasPeriodos extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2677065016337360620L;


	/**
	 * Constructor
	 * @param cuadros
	 */
	public consultaObrasPeriodos (String [][] cuadros) {

		//Ponemos modal y gestor de esquemas
		this.setModal(true);
		this.setTitle("Consulta de obras ordenadas por año");
		this.setLayout(new FlowLayout ());

		
		String [] nombreColumnas = {"Título", "Año", "Periodo"}; //columnas

		JTable tableResultados = new JTable (cuadros, nombreColumnas ); //añadimos consulta y columnas al JTable...
		JScrollPane scrollPane = new JScrollPane (tableResultados);

		this.add(scrollPane);

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.pack();  
		this.setVisible(true);   // Para mostrar el dialogo
		this.setResizable(false);

	}

}
