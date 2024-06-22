package vista;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controlador.Controlador;


/**
 * En esta clase testeamos la vista junto al controlador
 * @author Alejandro Terrones
 *
 */
public class testVista {

	public static void main(String[] args) {
		
		
		Vista v = new Vista (); //vista
		Controlador ctr = new Controlador(v); //controlador
		v.control(ctr); //añadimos el controlador a la vista
		
		JFrame ventana = new JFrame ("Museo"); //creamos el JFrame donde vamos a contener a la vista
		ventana.add(v); //lo añadimos
		ventana.setJMenuBar(v.getBarraMenu()); //añadimos el menu de la vista a nuestro jframe

		
		//Adaptamos resolucion ventana
		ventana.pack();
		ventana.setVisible(true);
		
		
		//Le ponemos un icono
		 ImageIcon icono = new ImageIcon("./icons/museo.png");
		 ventana.setIconImage(icono.getImage());
		
	}

}
