package controlador;

import modelo.*;
import vista.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;


/**
 * Clase controlador donde programamos
 * los diferentes eventos de la interfaz
 * de mouseListener solo usamos MouseClicked para los botones de movernos por la tablas
 * el action Listener para el resto de botones
 * y el itemListener para los combo
 * @author alex_
 *
 */
public class Controlador implements ActionListener, MouseListener, ItemListener{

	
	//Variables de instancia
	private Vista miVista;
	private ObraDAO modeloObra;
	private ArtistaDAO modeloArtista;
	
	
	/**
	 * Constructor del controlador, le llega como parametro
	 * un objeto de tipo vista
	 * @param v
	 */
	public Controlador (Vista v){
		
		this.miVista = v;

		
		try {
			//Creamos modelo
			this.modeloObra = new ObraDAO();
			this.modeloArtista = new ArtistaDAO();

			//Mostramos el primer cuadro y el primer artista
			this.muestraCuadro(modeloObra.getPrimero());
			this.muestraArtista(modeloArtista.getPrimero());


			//Cargamos los diferentes comboBoxes del panel de mantenimiento de cuadros
			miVista.getDcbUCuadros().addAll(modeloObra.listaCuadros());
			miVista.getDcbIAutores().addAll(modeloArtista.listaArtistas());
			miVista.getDcbUAutores().addAll(modeloArtista.listaArtistas());
			miVista.getDcbITecnica().addAll(Arrays.asList(enumMetodo.arrayTecnicas ()));
			miVista.getDcbUTecnica().addAll(Arrays.asList(enumMetodo.arrayTecnicas ()));
			miVista.getDcbDCuadros().addAll(modeloObra.listaCuadros());
			miVista.getCbUCuadros().setSelectedIndex(0);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(miVista, "Problema SQL: " + e.toString());
			e.printStackTrace();
			System.exit(0);
		} 
		catch (NullPointerException e) {
			JOptionPane.showMessageDialog(miVista, "Problema al cargar la BD");
			System.exit(0);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(miVista, "El programa debe reiniciarse");
			e.printStackTrace();
		}
	}



	/**
	 * Método actionPerformed de la intefaz ActionListener
	 * lo vamos a utilizar para los botones de guardar toString,
	 * ver descripcioón, añadir, eliminar cuadros o artistas
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			if (e.getSource() == miVista.getbDescripcionCuadro()){ //ver descripcion
				muestraDescripcion ();
			}
			else if (e.getSource() == miVista.getbStringCuadro()){ //guardar toString del cuadro
				System.out.println("Boton guardar");
				guardaStringCuadro ();
			}
			else if (e.getSource() == miVista.getbMuestraCuadros()){ //muestra cuadro artista actual
				System.out.println("En construcción");
				consultaCuadrosArtista();
			}
			else if (e.getSource() == miVista.getItemConsulta()){ //realizar consulta cuadros ordenados por periodo
				System.out.println("Funciona");
				consultaCuadrosPeriodo();
			}
			else if (e.getSource() == miVista.getItemConsultaOrdenTitulo()){ //consulta cuadros ordenados pro titulo
				System.out.println("Consulta2");
				consultaCuadrosOrdenTitulo();
			}
			else if (e.getSource() == miVista.getbIAceptar()){ //insert acepar
				System.out.println("insert ACEPTAR");
				insertObra();
			}
			else if (e.getSource() == miVista.getbICancelar()){ //insert cancelar
				System.out.println("INSERT CANDELAR");
				cancelarInsert();
			}
			else if (e.getSource() == miVista.getbUAceptar()){ //update acepar
				System.out.println("update ACEPTAR");
				updateObra();
			}
			else if (e.getSource() == miVista.getbUCancelar()){ //update cancelar
				System.out.println("update CANCELAR");
				cancelarUpdate();
			}
			else if (e.getSource() == miVista.getbDAceptar()){ //delete acepar
				System.out.println("delete ACEPTAR");
				deleteObra();
			}
			else if (e.getSource() == miVista.getbDCancelar()){ //delete cancelar
				System.out.println("delelte CANCELAR");
				cancelarDelete();
			}
		}
		catch (misExcepciones e1) {
			JOptionPane.showMessageDialog(miVista, e1.getMessage());
		}

	}

	

	/**
	 * Método de la interfaz MouseListener
	 * lo usamos para los botones de avanzar e ir hacia atras
	 * de las tablas cuadro y artista
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
		JButton botonPulsado = (JButton) e.getSource() ; 


		if (!botonPulsado.isEnabled()) {return ;}


		try {
			if (botonPulsado.getActionCommand().equals("Primer cuadro") || 
					botonPulsado.getActionCommand().equals("Primer artista") ){ //primer elemento
				System.out.println("PRIMERO");
				if (botonPulsado.getActionCommand().equals("Primer cuadro")){
					this.muestraCuadro(modeloObra.getPrimero());
				}
				else{
					this.muestraArtista(modeloArtista.getPrimero());
				}
			}
			else if (botonPulsado.getActionCommand().equals("Cuadro anterior") || 
					botonPulsado.getActionCommand().equals("Artista anterior")){ //elemento anterior
				System.out.println("ANTERIOR");

				if (botonPulsado.getActionCommand().equals("Cuadro anterior") ){
					this.muestraCuadro(modeloObra.getAnterior());
				}
				else {
					this.muestraArtista(modeloArtista.getAnterior());
				}
			}
			else if (botonPulsado.getActionCommand().equals("Siguiente cuadro") ||
					botonPulsado.getActionCommand().equals("Siguiente artista") ){ //siguiente elemento
				System.out.println("SIGUIENTE");
				if (botonPulsado.getActionCommand().equals("Siguiente cuadro")){
					this.muestraCuadro(modeloObra.getSiguiente());
				}
				else{
					this.muestraArtista(modeloArtista.getSiguiente());
				}

			}
			else if (botonPulsado.getActionCommand().equals("Último cuadro") || 
					botonPulsado.getActionCommand().equals("Último artista")){ //ultimo elemento
				System.out.println("ÚLTIMO");
				if (botonPulsado.getActionCommand().equals("Último cuadro")){
					this.muestraCuadro(modeloObra.getUltimo());
				}else{
					this.muestraArtista(modeloArtista.getUltimo());
				}
			}
		}
		catch (SQLException e1) {
			try {
				if (botonPulsado.getActionCommand().equals("Siguiente cuadro") || 
						botonPulsado.getActionCommand().equals("Siguiente artista")){
					if (botonPulsado.getActionCommand().equals("Siguiente cuadro") ){
						modeloObra.getAnterior();
					}
					else{
						modeloArtista.getAnterior();
					}
				}
				else if (botonPulsado.getActionCommand().equals("Cuadro anterior") ||
						botonPulsado.getActionCommand().equals("Artista anterior")){
					if (botonPulsado.getActionCommand().equals("Cuadro anterior")){
						modeloObra.getSiguiente();
					}
					else{
						modeloArtista.getSiguiente();
					}
				}
			}
			catch (misExcepciones err1) {
				JOptionPane.showMessageDialog(miVista, e1.getMessage());
			}
			catch (SQLException err2) {
				JOptionPane.showMessageDialog(miVista, "Problema SQL");
			}
		}
		catch (misExcepciones err){
			JOptionPane.showMessageDialog(miVista, err.getMessage());
		} 
	}


	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}


	/**
	 * Método de la interfaz ItemListener
	 * lo usaremos para el comboBox
	 * del panel del update
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getStateChange() == ItemEvent.SELECTED) { //comprobamos que se ha pinchado en el combo
			String cuadro = miVista.getCbUCuadros().getSelectedItem().toString(); //cogemos el cuadro seleccionado
			if (miVista.getCbUCuadros().getSelectedIndex() != 0){  //mientras no sea el primero
				rellenaPanelUpdate (getCuadro(cuadro)); //rellenamos panel update
			}
			else{ //si no, lo reiniciamos
				cancelarUpdate();
			}
		}
	}
	
	//--------METODOS MANTENIMIENTO--------//

	
	/**
	 * Método privado para realizar insert a la tabla Obras
	 * @throws misExcepciones 
	 */
	private void insertObra() throws misExcepciones{

		if (miVista.getJtITitulo().getText().isEmpty()){
			throw new misExcepciones ("Debe introducir un título");
		}
		else if (miVista.getCbIAutores().getSelectedIndex() == 0){
			throw new misExcepciones ("Seleccione autor");
		}
		else if (miVista.getCbITecnica().getSelectedIndex() == 0){
			throw new misExcepciones ("Seleccione técnica");
		}
		else if (miVista.getJaIDescripcion().getText().isEmpty()){
			throw new misExcepciones ("Introduzca descripción");
		}
		else if (miVista.getJtiImagen().getText().isEmpty()){
			throw new misExcepciones ("Introduzca una imagen");
		}
		else if (miVista.getJtiYear().getText().isEmpty()){
			throw new misExcepciones ("Introduzca año");
		}
		else {
			int respuesta = JOptionPane.showConfirmDialog(miVista, "¿Deseas añadir: " + miVista.getJtITitulo().getText().toString() + "?" );

			if (respuesta == JOptionPane.YES_OPTION){

				try {
					obraArte miObra = new obraArte (
							modeloArtista.buscaIdArtista(miVista.getCbIAutores().getSelectedItem().toString()),
							miVista.getJtITitulo().getText().toString(),
							miVista.getJaIDescripcion().getText().toString(),
							miVista.getJtiImagen().getText().toString(),
							miVista.getJtiYear().getText().toString(),
							enumMetodo.buscaMetodo(miVista.getCbITecnica().getSelectedItem().toString())
							);
					modeloObra.insertObras(miObra);
					actualizaCombo (); //por cada insert actualizar combos
					cancelarInsert(); //restauramos el panel
					JOptionPane.showMessageDialog(miVista, "Cuadro añadido");
				} 
				catch (NumberFormatException e) {
			        JOptionPane.showMessageDialog(miVista, "Introduce un valor numérico");
			    }
				catch (misExcepciones e) {
					JOptionPane.showMessageDialog(miVista, e.getMessage());
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(miVista, e.getMessage());
				}
				
			}
			else{
				JOptionPane.showMessageDialog(miVista, "Operación cancelada");
				this.cancelarInsert(); //en caso de cancelar, borramos datos de panel
			}
		}
	}
	
	
	
	/**
	 * Método privado para reiniciar el panel
	 * del insert
	 */
	private void cancelarInsert(){
		this.miVista.getJtITitulo().setText(null);
		this.miVista.getCbIAutores().setSelectedIndex(0);
		this.miVista.getCbITecnica().setSelectedIndex(0);
		this.miVista.getJtiImagen().setText(null);
		this.miVista.getJaIDescripcion().setText(null);
		this.miVista.getJtiYear().setText(null);
	}

	
	
	
	/**
	 * Método que nos permite modificar/update una obra
	 * @throws misExcepciones 
	 */
	private void updateObra() throws misExcepciones{
		int id = 0; //cogemos el id del cuadro para pasarselo al metodo Update de DAO

		
		//comprobamos que no llegue ningun jt vacio
		if (miVista.getCbUAutores().getSelectedIndex() == 0){
			throw new misExcepciones ("Elige un artista");
		}
		else if (miVista.getJtUTitulo().getText().toString().isEmpty()){
			throw new misExcepciones ("Introduce el título");
		}
		else if (miVista.getJaUDescripcion().getText().toString().isEmpty()){
			throw new misExcepciones ("Introduce una descripción");
		}
		else if (miVista.getJtUImagen().getText().toString().isEmpty()){
			throw new misExcepciones ("Introduce una imagen");
		}
		else if (miVista.getJtUFecha().getText().toString().isEmpty()){
			throw new misExcepciones ("Introduce una fecha");
		}
		else if (miVista.getCbUTecnica().getSelectedIndex() == 0){
			throw new misExcepciones ("Elige una técnica artística");
		}

		else{
			int respuesta = JOptionPane.showConfirmDialog(miVista, "¿Deseas modificar: " + miVista.getJtUTitulo().getText().toString() + "?" );

			if (respuesta == JOptionPane.YES_OPTION){
				//aqui tengo que poner la pregunta
				try {
					id = modeloObra.buscaIdObra((String)miVista.getCbUCuadros().getSelectedItem());
					obraArte miObra = new obraArte (
							modeloArtista.buscaIdArtista(miVista.getCbUAutores().getSelectedItem().toString()),
							miVista.getJtUTitulo().getText().toString(),
							miVista.getJaUDescripcion().getText().toString(),
							miVista.getJtUImagen().getText().toString(),
							miVista.getJtUFecha().getText().toString(),
							enumMetodo.buscaMetodo(miVista.getCbUTecnica().getSelectedItem().toString())
							);
					modeloObra.updateObra(miObra,id ); //le pasamos el objeto a actualizar y su id
					actualizaCombo ();
					 JOptionPane.showMessageDialog(miVista, "Cuadro actualizado");
				}
				catch (NumberFormatException e) {
			        JOptionPane.showMessageDialog(miVista, "Introduce un valor numérico");
			    }
				catch (SQLException e) {
					JOptionPane.showMessageDialog(miVista, e.getMessage());
					e.printStackTrace();
				}
				catch (misExcepciones e) {
					JOptionPane.showMessageDialog(miVista, e.getMessage());
				} 
			}
			else{
				JOptionPane.showMessageDialog(miVista, "Operación cancelada");
				this.cancelarUpdate(); //si cancelamos, se borra los datos del panel
			}
		}	
	}

	
	/**
	 * Método privado para reiniciar
	 * el panel del upadte
	 */
	private void cancelarUpdate(){
		this.miVista.getCbUAutores().setSelectedIndex(0);
		this.miVista.getJtUTitulo().setText(null);
		this.miVista.getCbUCuadros().setSelectedIndex(0);
		this.miVista.getJtUImagen().setText(null);
		this.miVista.getJaUDescripcion().setText(null);
		this.miVista.getJtUFecha().setText(null);
		this.miVista.getCbUTecnica().setSelectedIndex(0);
	}

	/**
	 * Método que rellena el panel update los datos en funcion del comboBox
	 * @param obj
	 */
	private void rellenaPanelUpdate (obraArte obj){
		miVista.getJtUTitulo().setText(obj.getTitulo());
		try {
			if  (modeloArtista.buscaArtista(obj.getId_artista()).isEmpty()){
				throw new misExcepciones ("Error en el id_artista");
			}
			else{
				miVista.getCbUAutores().setSelectedItem(modeloArtista.buscaArtista(obj.getId_artista()));
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		} catch (misExcepciones e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		}
		miVista.getCbUTecnica().setSelectedItem(obj.getTecnicaObra().getTecnica());
		miVista.getJtUImagen().setText(obj.getImagen());
		miVista.getJaUDescripcion().setText(obj.getDescripcion());
		miVista.getJtUFecha().setText(String.valueOf(obj.getFecha().getYear()));
	}
	
	
	/**
	 * Método privado para eliminar un disco seleccionado en el comboBox
	 */
	private void deleteObra(){

		if (miVista.getCbDCuadros().getSelectedIndex() == 0){ //en el caso de darle a acepat con index 0
			JOptionPane.showMessageDialog(miVista, "Elige un cuadro");
		}
		else{
			int respuesta = JOptionPane.showConfirmDialog(miVista, "¿Deseas eliminar el cuadro actual?"); //le preguntamos

			if (respuesta == JOptionPane.YES_OPTION){

				try {
					modeloObra.deleteObra(miVista.getCbDCuadros().getSelectedItem().toString());
					JOptionPane.showMessageDialog(miVista, "Cuadro borrado");
					actualizaCombo ();

				} catch (SQLException e) {
					JOptionPane.showMessageDialog(miVista, e.getMessage());
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(miVista, "Operación cancelada");
				//Poner comboBox index0
				miVista.getCbDCuadros().setSelectedIndex(0);
			}
		}
	}
	
	
	/**
	 * Método privado 
	 * para reiniciar el panel del delete
	 */
	private void cancelarDelete(){
		miVista.getCbDCuadros().setSelectedIndex(0);
	}
	
	
	
	//-----COMBOBOXES-----//
	
	/**
	 * Método privado que actualiza los combos
	 * cada vez que se introduce o actualiza un cuadro 
	 */
	private void actualizaCombo (){
		//Los vaciamos
		miVista.getDcbUCuadros().removeAllElements();
		miVista.getDcbDCuadros().removeAllElements();

		
		//Añadimos la primera opción en cada combo
		String cadena = "--Selecciona cuadro--";
		miVista.getDcbUCuadros().addElement(cadena); 
		miVista.getDcbDCuadros().addElement(cadena);
		try {
			//rellenamos comboboxes
			miVista.getDcbUCuadros().addAll(modeloObra.listaCuadros());
			miVista.getDcbDCuadros().addAll(modeloObra.listaCuadros());
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(miVista, e1.getMessage());
			e1.printStackTrace();
		}
		 catch (misExcepciones e) {
				JOptionPane.showMessageDialog(miVista, e.getMessage());
				e.printStackTrace();
			}
	}


	
	//-----Consultas-----//

	/**
	 * Método que nos va a mostrar una consulta
	 * de los cuadros que posee cada artista
	 */
	private void consultaCuadrosArtista(){
		try {
			String nombre = modeloArtista.creaArtista().getNombre(); //guardamos el nombre para no perderlo
			new consultaObrasArtistas (modeloArtista.consultaCuadros(nombre)); //creamos la consulta
			modeloArtista.getArtistaActual (nombre);//nos posicionamos donde estamos, porque el metodo anterior nos lleva al principio
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		} catch (misExcepciones e) {
			JOptionPane.showMessageDialog(miVista,"Error: " + e.getMessage());
			e.printStackTrace();
		} 
	}


	/**
	 * Método que muestra al consulta
	 * de los cuadros ordenados por periodo
	 */
	private void consultaCuadrosPeriodo(){
		try {
			String nombre = modeloObra.creaObraArte().getTitulo();//guardamos el nombre para no perderlo
			new consultaObrasPeriodos (modeloObra.consultaCuadrosPeriodo()); //consulta
			modeloObra.getActual(nombre);//nos posicionamos donde estamos, porque el metodo anterior nos lleva al principio
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(miVista, e1.getMessage());
			e1.printStackTrace();
		} 
		catch (misExcepciones e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * Método que crea una consulta
	 * de los cuadros ordenados por titulo
	 */
	private void consultaCuadrosOrdenTitulo(){
		try {
			String nombre = modeloObra.creaObraArte().getTitulo();
			new consultaObrasOrdenTitulo (modeloObra.consultaCuadrosOrdenNombre()); //consulta
			modeloObra.getActual(nombre);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		} 
		catch (misExcepciones e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		}
	}




	



	



	




	/**
	 * Método privado 
	 * para mostrar la descricioón del cuadro 
	 * en el que nos encontramos
	 */
	private void muestraDescripcion (){
		try {

			if (modeloObra.creaObraArte().getDescripcion().isEmpty()){
				throw new misExcepciones ("No hay descripción");
			}
			else{
				JOptionPane.showMessageDialog(miVista, modeloObra.creaObraArte().getDescripcion());
			}
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		} catch (misExcepciones e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * Método para guardar String de un objeto Cuadro
	 */
	private void guardaStringCuadro (){

		JFileChooser guardar = new JFileChooser ("./files"); //ruta por defecto
		guardar.showSaveDialog(miVista);
		File guardaFichero = guardar.getSelectedFile();

		guardar.setVisible(true);

		if (guardaFichero != null){
			try (BufferedWriter flujoFichero = new BufferedWriter(
					new FileWriter(guardaFichero, true))) {

				String cadena = modeloObra.creaObraArte().toString() + "\nArtista: " +
						modeloArtista.buscaArtista(modeloObra.creaObraArte().getId_artista()) + "\n";


				flujoFichero.write(cadena + "\n");
				JOptionPane.showMessageDialog(miVista, "Fichero creado");


			}catch (NullPointerException e){
				JOptionPane.showMessageDialog(miVista, "ERROR: " + e.getMessage());
				e.printStackTrace();
			} 
			catch (IOException e) {
				JOptionPane.showMessageDialog(miVista, "ERROR: " + e.getMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(miVista, "ERROR: " + e.getMessage());
				e.printStackTrace();
			} catch (misExcepciones e) {
				JOptionPane.showMessageDialog(miVista, "ERROR: " + e.getMessage());
				e.printStackTrace();
			}
		}

	}



	/**
	 * Método que devuelve un objeto cuadro
	 * @param obj
	 * @throws misExcepciones 
	 * @throws SQLException 
	 */
	private void muestraCuadro (obraArte obj) throws SQLException, misExcepciones{

		miVista.getTitulo().setText("Título: " + obj.getTitulo());
		miVista.getAnhio().setText("Año: " + obj.getFecha().getYear());
		miVista.getPrecio().setText("Valor estimado: " + obj.stringValorObra());
		try {
			if (modeloArtista.buscaArtista(obj.getId_artista()).isEmpty()){
				throw new misExcepciones ("Error artista");
			}
			miVista.getAutor().setText("Autor: " + modeloArtista.buscaArtista(obj.getId_artista()));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		} catch (misExcepciones e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		}
		miVista.getTecnica().setText("Técnica artística: " + obj.getTecnicaObra().getTecnica());

		miVista.getPeriodo().setText("Periodo artístico: " + modeloArtista.buscaPeriodo(obj.getId_artista()));
		//Cambiamos la imagen
		ImageIcon iconoEscalado = new ImageIcon(new ImageIcon("./img/"+obj.getImagen()).getImage().getScaledInstance(miVista.getAnchoCuadro(), miVista.getAltoCuadro(), Image.SCALE_DEFAULT));
		miVista.getEtiCuadro().setIcon(iconoEscalado);
	}



	/**
	 * Método que devuelve el objeto artista
	 * @param obj
	 * @throws SQLException
	 * @throws misExcepciones
	 */
	private void muestraArtista (Artista obj) throws SQLException, misExcepciones{

		miVista.getJtNombre().setText("Nombre: " + obj.getNombre());
		miVista.getJtNacimiento().setText("Fecha de nacimiento: " + libreriaFechas.transformaFecha(obj.getFecha_nac().toString()));
		miVista.getJtMuerte().setText("Fecha de muerte: " + libreriaFechas.transformaFecha(obj.getFecha_muerte().toString()));
		miVista.getJtNacionalidad().setText("Nacionalidad: " +obj.getNacionalidad());
		miVista.getJtPeriodo().setText("Periodo artístico: " + obj.getpArtistico().getPeriodo() + " (" + obj.getpArtistico().getSiglo() + ") ");
		System.out.println(obj.getpArtistico().getPeriodo());
		miVista.getJtNumCuadros().setText("Nº de cuadros: " + String.valueOf(modeloArtista.numObras(obj.getNombre())));

		//Cambiamos la imagen
		ImageIcon iconoEscalado = new ImageIcon(new ImageIcon(obj.getImagen()).getImage().getScaledInstance(miVista.getAnchoCuadro(), miVista.getAltoCuadro(), Image.SCALE_DEFAULT));
		miVista.getEtiArtista().setIcon(iconoEscalado);


	}



	



	/**
	 * Método privado
	 * que devuelve un objeto obra en función del título
	 * @param titulo
	 * @return
	 */
	private obraArte getCuadro (String titulo){
		obraArte itera = null;
		obraArte miObra = null;
		boolean salir = true;
		//Guardamos lista de cuadros
		try {
			List <obraArte> listaCuadros  = modeloObra.listaCuadrosObj();

			//La guardamos en un iterator
			Iterator <obraArte> iterar = listaCuadros.iterator();

			while (iterar.hasNext() && salir){
				itera = iterar.next();
				if (itera.getTitulo().equalsIgnoreCase(titulo)){
					miObra = itera;
					salir = false;
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		} catch (misExcepciones e) {
			JOptionPane.showMessageDialog(miVista, e.getMessage());
			e.printStackTrace();
		}
		return miObra;
	}






}
