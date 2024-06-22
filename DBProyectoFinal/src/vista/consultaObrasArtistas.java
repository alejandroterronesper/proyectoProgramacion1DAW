package vista;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 * Clase para crear un JTable que contiene 
 * la consulta de los cuadros de un artista
 * de la fila actual
 * 
 * 
 * @author Alejandro Terrones Pérez
 *
 */

public class consultaObrasArtistas extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1750675963810930754L;


	/**
	 * Constructor de la clase 
	 * @param cuadros (matriz de la consulta)
	 */
	public consultaObrasArtistas (String [][] cuadros){

		//Ponemos modal y gestor de esquemas
		this.setModal(true);
		this.setTitle("Consulta de obras");
		this.setLayout(new FlowLayout ());

		//Establecemos el nombre de cada columna
		String [] nombreColumnas = {"Título", "Descripción", "Año", "Tecnica artística", "Periodo"};

		//Le pasamos al JTable la matriz del resultado y el array de las columnas
		JTable tableResultados = new JTable (cuadros, nombreColumnas);
		JScrollPane scrollPane = new JScrollPane (tableResultados); //ponemos ScrollPane en caso de que la 
																	//consulta sea muy grande
																	//para poder movernos
		//lo añadimos
		this.add(scrollPane);

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //se elimina de la memoria al cerrarse
		this.pack();  
		this.setVisible(true);   // Para mostrar el dialogo
		this.setResizable(false); //quitamos el ajuste

	}
}
