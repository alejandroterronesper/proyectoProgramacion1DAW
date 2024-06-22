package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import controlador.*;



/**
 * Clase de la vista de nuestra interfaz
 * 
 * @author Alejandro Terrones Pérez 
 * 
 *
 */
public class Vista extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6805781207242958920L;


	//Variables de instancia


	//-----CUADRO-----//
	private JTextField titulo, autor, anhio, tecnica, precio, periodo;
	private JButton bPrimerCuadro, bUltimoCuadro, bSiguienteCuadro, bAnteriorCuadro, bStringCuadro, bDescripcionCuadro;
	private JTabbedPane pestanhasPanel;
	private JLabel etiCuadro;
	private static int anchoCuadro = 300 ,altoCuadro = 400;



	//-----ARTISTA-----//
	private JTextField jtNombre, jtNacimiento, jtMuerte,jtPeriodo, jtNacionalidad, jtObrasDisponibles , jtNumCuadros;
	private JButton bPrimerArtista, bUltimoArtista, bSiguienteArtista, bArtistaAnterior, bMuestraCuadros;
	private JLabel etiArtista;
	private static int anchoFoto = 300, altoFoto = 440;


	//-----MANTENIMIENTO-----//
	private JTextField jtITitulo, jtiFecha, jtiImagen,jtiYear,
	jtUTitulo, jtUFecha, jtUImagen;
	private JTextArea jaIDescripcion, jaUDescripcion;
	private JComboBox <String> cbIAutores, cbITecnica, cbUCuadros, cbUAutores,cbUTecnica, cbDCuadros  ;
	private DefaultComboBoxModel<String> dcbIAutores, dcbITecnica, dcbUCuadros, dcbUAutores,dcbUTecnica, dcbDCuadros;
	private JButton bIAceptar, bICancelar, bUAceptar, bUCancelar, bDAceptar, bDCancelar;
	private JList <String> listCuadros;


	//-----MENU-----//
	private JMenuBar barraMenu;
	private JMenu menuConsulta;
	private JMenuItem itemConsulta , itemConsultaOrdenTitulo;

	//Fuente
	private Font fuenteBotones;
	private Font fuenteTexto;
	private Font fuenteEntrada;
	private Font fuentePestanha;

	
	/**
	 * Constructor de la clase vista
	 */
	public Vista (){


		this.fuenteBotones = new Font("Arial", Font.BOLD, 15);
		this.fuenteTexto = new Font("Segoe UI", Font.BOLD, 15);
		this.fuenteEntrada = new Font (Font.MONOSPACED, Font.ITALIC, 12);
		this.fuentePestanha =  new Font("Agency FB", Font.BOLD, 16);


		//Creamos las pestañas
		pestanhasPanel = new JTabbedPane();
		pestanhasPanel.setBackground(Color.white);
		pestanhasPanel.setFont(fuentePestanha);

		//Pestaña cuadros
		pestanhasPanel.add("Cuadros", panelCuadro());
		pestanhasPanel.setToolTipTextAt(0, "Permite visualizar los cuadros");
		pestanhasPanel.setIconAt(0, new ImageIcon("./icons/pintura.png"));

		//Pestaña artistas
		pestanhasPanel.add("Artistas", panelArtista());
		pestanhasPanel.setToolTipTextAt(1, "Permite ver información de los diferentes artistas");
		pestanhasPanel.setIconAt(1, new ImageIcon("./icons/artista.png"));

		//Pestaña mantenimiento
		pestanhasPanel.add("Mantenimiento cuadros",  panelMantenimientoCuadros( pInsert(), pUpdate(), pDelete()));
		pestanhasPanel.setToolTipTextAt(2, "Podemos añadir, modificar o eliminar cuadros");
		pestanhasPanel.setIconAt(2, new ImageIcon("./icons/computadora.png"));

		this.add(pestanhasPanel);

		//Creamos barra de menus
		preparaMenu();

	}

	
	/**
	 * Método privado para preparar el menu de la vista
	 * contiene dos menus de consulta
	 */
	private void preparaMenu(){

		barraMenu = new JMenuBar ();
		menuConsulta = new JMenu ("Consultas - tablas");
		itemConsulta = new JMenuItem ("Mostrar cuadros ordenador por periodo");
		itemConsultaOrdenTitulo = new JMenuItem ("Mostrar los cuadros junto a su autor "
				+ "ordenados por título");

		barraMenu.add(menuConsulta);
		menuConsulta.add(itemConsulta);
		menuConsulta.add(itemConsultaOrdenTitulo);

	}
	
	
	
	
	/**
	 * Método privado que crea el panel
	 * para los cuadros
	 * @return
	 */
	private JPanel panelCuadro () {
		JPanel pCuadros = new JPanel();


		pCuadros.setLayout(new BoxLayout(pCuadros, BoxLayout.Y_AXIS)); //Mismo espacio en las columnas


		//Creamos el panel que contiene los botones y la imagen del cuadro
		JPanel pImagen = new JPanel ();
		pImagen.setLayout(new FlowLayout ()); //organizamos asi el panel


		//Creamos panel boton izquierda
		JPanel pBotonIzquierda = new JPanel ();
		
		//Creamos componentes
		bPrimerCuadro = new JButton ();
		ImageIcon ibPrimerCuadro = new ImageIcon ("./icons/primero.png"); 
		bPrimerCuadro.setIcon(ibPrimerCuadro);
		bPrimerCuadro.setToolTipText("Primer cuadro");

		bAnteriorCuadro = new JButton ();
		ImageIcon ibCuadroAnterior = new ImageIcon ("./icons/izquierda.png");
		bAnteriorCuadro.setIcon(ibCuadroAnterior);
		bAnteriorCuadro.setToolTipText("Cuadro anterior");
		pBotonIzquierda.add(bPrimerCuadro);
		pBotonIzquierda.add(bAnteriorCuadro);
		pImagen.add(pBotonIzquierda, FlowLayout.LEFT);


		//Panel central donde contenemos el cuadro
		JPanel pCuadrosImagen = new JPanel ();
		
		//Creamos componentes
		etiCuadro = new JLabel ();
		etiCuadro.setBorder(new CompoundBorder(
				new BevelBorder(BevelBorder.RAISED),
				new LineBorder(Color.LIGHT_GRAY,3)));


		//Editamos el tamaño de la etiqueta
		etiCuadro.setSize(new Dimension(Vista.anchoCuadro, Vista.altoCuadro));
		//Le ponemos una iamgen por defecto en caso de que no se cargue desde el controlador y la escalaos al tamaño de la etiqueta
		ImageIcon iconoEscalado = new ImageIcon(new ImageIcon("./icons/archivo.png").getImage().getScaledInstance(anchoCuadro, altoCuadro, Image.SCALE_DEFAULT));
		etiCuadro.setIcon(iconoEscalado);//./icons/archivo
		pCuadrosImagen.add(etiCuadro);		
		pImagen.add(pCuadrosImagen, FlowLayout.CENTER);


		//Panel de la derecha para botones
		JPanel pBotonDerecha = new JPanel ();
		ImageIcon ibUltimoCuadro = new ImageIcon ("./icons/ultimo.png");
		bUltimoCuadro = new JButton ();
		bUltimoCuadro.setIcon(ibUltimoCuadro);
		bUltimoCuadro.setToolTipText("Último cuadro");

		bSiguienteCuadro = new JButton ();
		ImageIcon ibSiguienteCuadro = new ImageIcon ("./icons/derecha.png");
		bSiguienteCuadro.setIcon(ibSiguienteCuadro);
		bSiguienteCuadro.setToolTipText("Cuadro siguiente");

		pBotonDerecha.add(bSiguienteCuadro);
		pBotonDerecha.add(bUltimoCuadro);
		pImagen.add(pBotonDerecha, FlowLayout.RIGHT);

		//Establecemos alias a los botones
		bPrimerCuadro.setActionCommand("Primer cuadro");
		bAnteriorCuadro.setActionCommand("Cuadro anterior");
		bSiguienteCuadro.setActionCommand("Siguiente cuadro");
		bUltimoCuadro.setActionCommand("Último cuadro");



		//Panel que contiene la información del cuadro
		JPanel pInformacion = new JPanel ();
		pInformacion.setLayout(new GridLayout (3,3));




		//Le damos memoria
		titulo = new JTextField ("Título: ");
		titulo.setEditable(false);
		titulo.setFont(fuenteTexto);
		autor = new JTextField ("Autor: ");
		autor.setEditable(false);
		autor.setFont(fuenteTexto);
		anhio = new JTextField ("Año: ");
		anhio.setEditable(false);
		anhio.setFont(fuenteTexto);
		tecnica = new JTextField ("Técnica artística: ");
		tecnica.setEditable(false);
		tecnica.setFont(fuenteTexto);
		precio = new JTextField ("Valor estimado: ");
		precio.setEditable(false);
		precio.setFont(fuenteTexto);
		periodo = new JTextField ("Periodo artístico: ");
		periodo.setEditable(false);
		periodo.setFont(fuenteTexto);

		//Lo añadimos
		pInformacion.add(titulo);
		pInformacion.add(autor);
		pInformacion.add(anhio);
		pInformacion.add(tecnica);
		pInformacion.add(precio);
		pInformacion.add(periodo);

		
		//Boton para guardar la información del cuadro en un fichero txt
		JPanel pBoton = new JPanel ();
		bDescripcionCuadro = new JButton ("Ver descripción del cuadro");
		bDescripcionCuadro.setFont(fuenteBotones);
		ImageIcon iDescripcion = new ImageIcon ("./icons/descripcion.png");
		bDescripcionCuadro.setIcon(iDescripcion);

		//Botón para mostrar la descripción del cuadro en el que estamos
		bStringCuadro = new JButton ("Guardar");
		bStringCuadro.setFont(fuenteBotones);
		bStringCuadro.setToolTipText("Guardar en fichero información del cuadro");
		ImageIcon iGuardar = new ImageIcon ("./icons/disco.png");
		bStringCuadro.setIcon(iGuardar);
		pBoton.add(bDescripcionCuadro);
		pBoton.add(bStringCuadro);


		//Añadimos paneles
		pCuadros.add(pImagen);
		pCuadros.add(pInformacion);
		pCuadros.add(pBoton);

		return pCuadros;
	}


	
	
	/**
	 * Método privado que crea
	 * el panel para los artistas
	 * @return
	 */
	private JPanel panelArtista(){
		
		//Panel principal
		JPanel pPrincipal = new JPanel ();
		pPrincipal.setLayout(new BoxLayout (pPrincipal, BoxLayout.Y_AXIS));
		
		
		//Preparamos el panel donde contemeos la imagen y etiquetas de información
		JPanel pArtista = new JPanel ();
		
		//Gestor de esquemas
		pArtista.setLayout(new GridLayout(1,2));
		
		
		//Preparamos el panel donde colocamos la imagen del artistas
		JPanel pFoto = new JPanel ();
		pFoto.setLayout(new BoxLayout (pFoto, BoxLayout.Y_AXIS));


		//Panel donde se contiene la foto del artista
		JPanel pCentrArtista = new JPanel();
		pCentrArtista.setLayout(new BorderLayout());
		etiArtista = new JLabel ();
		etiArtista.setPreferredSize(new Dimension (Vista.anchoFoto, Vista.altoCuadro));
		etiArtista.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon iconoEscalado = new ImageIcon(new ImageIcon("./icons/archivo.png").getImage().getScaledInstance(Vista.anchoFoto, Vista.altoCuadro , Image.SCALE_DEFAULT));
		etiArtista.setIcon(iconoEscalado);
		pCentrArtista.add(etiArtista, BorderLayout.CENTER);
		pFoto.add(pCentrArtista);
		
		ImageIcon ibMuestraCuadros = new ImageIcon ("./icons/mostrarCuadros.png");
		bMuestraCuadros = new JButton ("Mostrar cuadros");
		bMuestraCuadros.setIcon(ibMuestraCuadros);
		bMuestraCuadros.setFont(fuenteBotones);
		bMuestraCuadros.setAlignmentX(CENTER_ALIGNMENT); 
		pFoto.add(bMuestraCuadros);
		
		//Preparamos el panel de la información del artista
		JPanel pInformacion = new JPanel ();
		pInformacion.setLayout(new GridLayout (3,3));
		pInformacion.setBorder(new TitledBorder ("Ficha técnica del artista"));
		
		//Panel que contiene nombre
		JPanel pNombre = new JPanel (new FlowLayout(FlowLayout.LEFT));
		jtNombre = new JTextField (25);
		jtNombre.setText("Nombre: ");
		jtNombre.setFont(fuenteTexto);
		jtNombre.setEditable(false);
		pNombre.add(jtNombre);
		
		//Panel que contiene nacionalidad
		JPanel pNacionalidad = new JPanel (new FlowLayout(FlowLayout.LEFT));
		jtNacionalidad = new JTextField ("Nacionalidad: ");
		jtNacionalidad.setEditable(false);
		jtNacionalidad.setFont(fuenteTexto);
		pNacionalidad.add(jtNacionalidad);
		
		//Panel que contiene nacimiento
		JPanel pNacimiento = new JPanel (new FlowLayout(FlowLayout.LEFT));
		jtNacimiento = new JTextField ("Fecha de nacimiento: ");
		jtNacimiento.setEditable(false);
		jtNacimiento.setFont(fuenteTexto);
		pNacimiento.add(jtNacimiento);
		
		//Panel que contiene muerte
		JPanel pMuerte = new JPanel (new FlowLayout(FlowLayout.LEFT));
		jtMuerte = new JTextField ("Fecha de muerte: ");
		jtMuerte.setEditable(false);
		jtMuerte.setFont(fuenteTexto);
		pMuerte.add(jtMuerte);
		
		//Panel que contiene num cuadros
		JPanel pNumCuadros = new JPanel (new FlowLayout(FlowLayout.LEFT));
		jtNumCuadros = new JTextField ("Nº de cuadros: ");
		jtNumCuadros.setEditable(false);
		jtNumCuadros.setFont(fuenteTexto);
		pNumCuadros.add(jtNumCuadros);

		//Panel que contiene el periodo artistico
		JPanel pPeriodo = new JPanel (new FlowLayout(FlowLayout.LEFT));
		jtPeriodo = new JTextField ("Periodo artístco");
		jtPeriodo.setEditable(false);
		jtPeriodo.setFont(fuenteTexto);
		pPeriodo.add(jtPeriodo);

	
		//Añadimos los componentes al panel informacion
		pInformacion.add(pNombre);
		pInformacion.add(pNacionalidad);
		pInformacion.add(pNacimiento);
		pInformacion.add(pMuerte);
		pInformacion.add(pNumCuadros);
		pInformacion.add(pPeriodo);
		

		//Añadimos paneles al panel artista
		pArtista.add(pFoto);
		pArtista.add(pInformacion);
		
		
		//Preparamos panel de los botones
		JPanel pBMover = new JPanel ();
		pBMover.setBorder(new TitledBorder ("Opciones"));
		
		//primero
		bPrimerArtista = new JButton ();
		ImageIcon ibPrimerArtista = new ImageIcon ("./icons/primero.png");
		bPrimerArtista.setIcon(ibPrimerArtista);
		bPrimerArtista.setToolTipText("Primer artista");
		
		//ultimo
		bUltimoArtista = new JButton ();
		ImageIcon ibUltimoArtista = new ImageIcon ("./icons/ultimo.png");
		bUltimoArtista.setIcon(ibUltimoArtista);
		bUltimoArtista.setToolTipText("Último artista");
		
		//siguiente
		bSiguienteArtista = new JButton ();
		ImageIcon ibSiguienteArtista = new ImageIcon ("./icons/derecha.png");
		bSiguienteArtista.setIcon(ibSiguienteArtista);
		bSiguienteArtista.setToolTipText("Siguiente artista");
		
		//anterior
		bArtistaAnterior = new JButton ();
		ImageIcon ibAnteriorArtista = new ImageIcon ("./icons/izquierda.png");
		bArtistaAnterior.setIcon(ibAnteriorArtista);
		bArtistaAnterior.setToolTipText("Artista anterior");
		
		//Añadimos botones
		pBMover.add(bPrimerArtista);
		pBMover.add(bArtistaAnterior);
		pBMover.add(bSiguienteArtista);
		pBMover.add(bUltimoArtista);
		
		
		//Le añadimos un alias
		bPrimerArtista.setActionCommand("Primer artista");
		bArtistaAnterior.setActionCommand("Artista anterior");
		bSiguienteArtista.setActionCommand("Siguiente artista");
		bUltimoArtista.setActionCommand("Último artista");
		

		//Añadimos al panel principal
		pPrincipal.add(pArtista);
		pPrincipal.add(pBMover);
		
		
		return pPrincipal;
	}
	

	
	
	/**
	 * Método privado que contiene
	 * los paneles de manteinimiento de la tabla cuadros
	 * insert, update, delete
	 * @param pInsert
	 * @param pUpdate
	 * @param pDelete
	 * @return panelMantenimientoCuadros
	 */
	private JPanel panelMantenimientoCuadros(JPanel pInsert, JPanel pUpdate, JPanel pDelete){

		JPanel pMantenimiento = new JPanel ();

		//Gestor de esquemas
		pMantenimiento.setLayout(new GridLayout (1,3));


		//Añadimos los paneles que nos lleguen como parámetro
		pMantenimiento.add(pInsert);
		pMantenimiento.add(pUpdate);
		pMantenimiento.add(pDelete);


		return pMantenimiento;

	}
	
	/**
	 * Método privado que devuelve el panel insert
	 * @return
	 */
	private JPanel pInsert (){
		//Panel del Insert
		JPanel pInsert = new JPanel ();
		pInsert.setLayout(new BoxLayout(pInsert, BoxLayout.Y_AXIS));


		//Titulo
		JPanel p1 = new JPanel ();

		JLabel lITitulo = new JLabel ("Título");
		jtITitulo = new JTextField (30);
		jtITitulo.setFont(fuenteEntrada);
		p1.add(lITitulo);
		p1.add(jtITitulo);



		//Autor
		JPanel p2 = new JPanel ();
		JLabel lIAutor = new JLabel ("Autor");
		dcbIAutores = new DefaultComboBoxModel <String> ();
		cbIAutores = new JComboBox <String> (dcbIAutores);
		cbIAutores.addItem("--Escoge autor--");
		p2.add(lIAutor);
		p2.add(cbIAutores);


		//Tecnica
		JPanel p3 = new JPanel ();
		JLabel lTecnica = new JLabel ("Técnica");
		dcbITecnica = new DefaultComboBoxModel <String> ();
		cbITecnica = new JComboBox <String> (dcbITecnica);
		cbITecnica.addItem("--Escoge técnica--");
		p3.add(lTecnica);
		p3.add(cbITecnica);


		//Imagen
		JPanel p4 = new JPanel ();
		JLabel lImagen = new JLabel ("Imagen");
		jtiImagen = new JTextField (15);
		jtiImagen.setFont(fuenteEntrada);
		p4.add(lImagen);
		p4.add(jtiImagen);

		//Descripcion
		JPanel p5 = new JPanel ();
		JLabel liDescripcion = new JLabel ("Descripción");
		jaIDescripcion = new JTextArea (4, 30);
		jaIDescripcion.setFont(fuenteEntrada);
		JScrollPane spIDescripcion = new JScrollPane (jaIDescripcion);
		p5.add(liDescripcion);
		p5.add(spIDescripcion);

		//Fecha
		JPanel p6 = new JPanel ();
		JLabel liYear = new JLabel ("Año");
		jtiYear = new JTextField (10);
		jtiYear.setFont(fuenteEntrada);
		p6.add(liYear);
		p6.add(jtiYear);

		//Botones
		JPanel p7 = new JPanel ();
		bIAceptar = new JButton ("Aceptar");
		bIAceptar.setFont(fuenteBotones);
		bIAceptar.setToolTipText("Insertamos un nuevo cuadro a la BD");
		ImageIcon ibInserAceptar = new ImageIcon ("./icons/insertar.png");
		bIAceptar.setIcon(ibInserAceptar);
		bICancelar = new JButton ("Cancelar");
		ImageIcon ibInserCancelar = new ImageIcon ("./icons/cancelar.png");
		bICancelar.setFont(fuenteBotones);
		bICancelar.setIcon(ibInserCancelar);
		p7.add(bIAceptar);
		p7.add(bICancelar);

		pInsert.add(p1);
		pInsert.add(p2);
		pInsert.add(p3);
		pInsert.add(p4);
		pInsert.add(p5);
		pInsert.add(p6);
		pInsert.add(p7);
		pInsert.setBorder(new TitledBorder ("Añadir nuevo cuadro"));


		return pInsert;
	}
	
	
	/**
	 * Método privado que devuelve el panel Update
	 * @return
	 */
	private JPanel pUpdate (){
		//Parte del Update
		JPanel pUpdate = new JPanel ();
		pUpdate.setLayout(new BoxLayout(pUpdate, BoxLayout.Y_AXIS));


		//Selecciona cuadro
		JPanel pU1 = new JPanel ();
		JLabel lUCuadro = new JLabel ("Selecciona cuadro");
		dcbUCuadros = new DefaultComboBoxModel <String>(); 
		cbUCuadros = new JComboBox <String> (dcbUCuadros);
		cbUCuadros.addItem("--Selecciona cuadro--");
		pU1.add(lUCuadro);
		pU1.add(cbUCuadros);


		//Titulo
		JPanel pU2 = new JPanel ();
		JLabel lUTitulo = new JLabel ("Título");
		jtUTitulo = new JTextField (30);
		jtUTitulo.setFont(fuenteEntrada);
		pU2.add(lUTitulo);
		pU2.add(jtUTitulo);

		//Autor
		JPanel pU3 = new JPanel ();
		JLabel lUAutor = new JLabel ("Autor");
		dcbUAutores = new DefaultComboBoxModel <String> ();
		cbUAutores = new JComboBox <String> (dcbUAutores);
		cbUAutores.addItem("--Escoge autor--");
		pU3.add(lUAutor);
		pU3.add(cbUAutores);


		//Tecnica
		JPanel pU4 = new JPanel ();
		JLabel lUTecnica = new JLabel ("Técnica");
		dcbUTecnica  = new DefaultComboBoxModel <String> ();
		cbUTecnica = new JComboBox <String>(dcbUTecnica);
		cbUTecnica.addItem("--Escoge técnica--");
		pU4.add(lUTecnica);
		pU4.add(cbUTecnica);

		//Imagen
		JPanel pU5 = new JPanel ();
		JLabel pUImagen = new JLabel ("Imagen");
		jtUImagen = new JTextField (15);
		jtUImagen.setFont(fuenteEntrada);
		pU5.add(pUImagen);
		pU5.add(jtUImagen);


		//Descripcion
		JPanel pU6 = new JPanel ();
		JLabel jlUDescripcion = new JLabel ("Descripción");
		jaUDescripcion = new JTextArea (4, 30);
		jaUDescripcion.setFont(fuenteEntrada);
		JScrollPane spUDescripcion = new JScrollPane (jaUDescripcion);
		pU6.add(jlUDescripcion);
		pU6.add(spUDescripcion);


		//Año
		JPanel pU7 = new JPanel ();
		JLabel jlUYear = new JLabel ("Año");
		jtUFecha = new JTextField (10);
		jtUFecha.setFont(fuenteEntrada);
		pU7.add(jlUYear);
		pU7.add(jtUFecha);

		//Botones
		JPanel pU8 = new JPanel ();
		bUAceptar = new JButton ("Aceptar");
		bUAceptar.setFont(fuenteBotones);
		ImageIcon ibUpdate = new ImageIcon ("./icons/actualizar.png");
		bUAceptar.setIcon(ibUpdate);
		bUAceptar.setToolTipText("Modificar cuadro existente");
		bUCancelar = new JButton ("Cancelar");
		ImageIcon ibUCancelar = new ImageIcon ("./icons/cancelar.png");
		bUCancelar.setFont(fuenteBotones);
		bUCancelar.setIcon(ibUCancelar);
		pU8.add(bUAceptar);
		pU8.add(bUCancelar);


		//Añadimos
		pUpdate.add(pU1);
		pUpdate.add(pU2);
		pUpdate.add(pU3);
		pUpdate.add(pU4);
		pUpdate.add(pU5);
		pUpdate.add(pU6);
		pUpdate.add(pU7);
		pUpdate.add(pU8);
		pUpdate.setBorder(new TitledBorder ("Modificar cuadro existente"));


		return pUpdate;
	}
	
	
	
	/**
	 * Método privado que devuelve el panel delete
	 * @return
	 */
	private JPanel pDelete (){
		//Parte del delete
		JPanel pDelete = new JPanel ();
		pDelete.setLayout(new BoxLayout(pDelete, BoxLayout.Y_AXIS));

		//Etiqueta
		JLabel lDelete = new JLabel ("Selecciona los cuadros a eliminar");
		lDelete.setAlignmentX(CENTER_ALIGNMENT);
		pDelete.add(lDelete);



		//combo de cuadros
		JPanel pDCombo = new JPanel ();
		dcbDCuadros = new DefaultComboBoxModel <String> ();
		cbDCuadros = new JComboBox <String> (dcbDCuadros);
		cbDCuadros.addItem("--Selecciona cuadro--"); 
		pDCombo.add(cbDCuadros);
		pDelete.add(pDCombo);




		//Botones
		JPanel pD1 = new JPanel ();
		bDAceptar = new JButton ("Aceptar");
		bDAceptar.setFont(fuenteBotones);
		ImageIcon ibDelete = new ImageIcon ("./icons/borrar.png");
		bDAceptar.setIcon(ibDelete);
		bDAceptar.setToolTipText("Borrar cuadro existente");
		bDCancelar = new JButton ("Cancelar");
		ImageIcon ibDCancelar = new ImageIcon ("./icons/cancelar.png");
		bDCancelar.setFont(fuenteBotones);
		bDCancelar.setIcon(ibDCancelar);
		
		//Añadimos
		pD1.add(bDAceptar);
		pD1.add(bDCancelar);
		pDelete.add(pD1);

		pDelete.setBorder(new TitledBorder ("Elimina cuadros existente"));

		return pDelete;
	}
	
	
	
	
	/**
	 * Métodos para añadir los eventos de 
	 * los objetos de la vista al controlador
	 * @param ctr
	 */
	public void control(Controlador ctr){


		//Botones de mostrar informacion, editar, eliminar, añadir
		bDescripcionCuadro.addActionListener(ctr);
		bStringCuadro.addActionListener(ctr);
		bMuestraCuadros.addActionListener(ctr);

		//Botones primero, siguiente, anterior, ultimo...
		bPrimerCuadro.addMouseListener(ctr);
		bUltimoCuadro.addMouseListener(ctr);
		bSiguienteCuadro.addMouseListener(ctr);
		bAnteriorCuadro.addMouseListener(ctr);
		bPrimerArtista.addMouseListener(ctr);
		bArtistaAnterior.addMouseListener(ctr);
		bSiguienteArtista.addMouseListener(ctr);
		bUltimoArtista.addMouseListener(ctr);


		//Eventos para el mantenimiento de tablas
		this.bIAceptar.addActionListener(ctr);
		this.bICancelar.addActionListener(ctr);
		this.bUAceptar.addActionListener(ctr);
		this.bUCancelar.addActionListener(ctr);
		this.bDAceptar.addActionListener(ctr);
		this.bDCancelar.addActionListener(ctr);

		//Añadimos eventos a las opciones del menú
		itemConsulta.addActionListener(ctr);	
		itemConsultaOrdenTitulo.addActionListener(ctr);


		//Para el comboBox del Update
		this.cbUCuadros.addItemListener(ctr);

	}


	// Getters
	public JTextField getTitulo() {return titulo;}
	public JTextField getAutor() {return autor;}
	public JTextField getAnhio() {return anhio;}
	public JTextField getTecnica() {return tecnica;}
	public JTextField getPrecio() {return precio;}
	public JTextField getPeriodo() {return periodo;}
	public JButton getbPrimerCuadro() {return bPrimerCuadro;}
	public JButton getbUltimoCuadro() {return bUltimoCuadro;}
	public JButton getbSiguienteCuadro() {return bSiguienteCuadro;}
	public JButton getbAnteriorCuadro() {return bAnteriorCuadro;}
	public JButton getbStringCuadro() {return bStringCuadro; }
	public JButton getbDescripcionCuadro() {return bDescripcionCuadro;}
	public JTabbedPane getPestanhasPanel() {return pestanhasPanel;}
	public JLabel getEtiCuadro() {return etiCuadro;}
	public JTextField getJtNombre() {return jtNombre;}
	public JTextField getJtNacimiento() {return jtNacimiento;}
	public JTextField getJtMuerte() {return jtMuerte;}
	public JTextField getJtPeriodo() {return jtPeriodo;}
	public JTextField getJtNacionalidad() {return jtNacionalidad;}
	public JTextField getJtObrasDisponibles() {return jtObrasDisponibles;}
	public JTextField getJtNumCuadros() {return jtNumCuadros;}
	public JButton getbPrimerArtista() {return bPrimerArtista;}
	public JButton getbUltimoArtista() {return bUltimoArtista;}
	public JButton getbSiguienteArtista() {return bSiguienteArtista;}
	public JButton getbArtistaAnterior() {return bArtistaAnterior;}
	public JLabel getEtiArtista() {return etiArtista;}
	public JButton getbMuestraCuadros() {return bMuestraCuadros;}
	public JMenuBar getBarraMenu() {return barraMenu;}
	public JMenu getMenuConsulta() {return menuConsulta;}
	public JMenuItem getItemConsulta() {return itemConsulta;}
	public JMenuItem getItemConsultaOrdenTitulo() {return itemConsultaOrdenTitulo;}
	public JTextField getJtITitulo() {return jtITitulo;}
	public JTextField getJtiFecha() {return jtiFecha;}
	public JTextField getJtiImagen() {return jtiImagen;}
	public JComboBox<String> getCbIAutores() {return cbIAutores;}
	public JTextField getJtiYear() {return jtiYear;}
	public JTextField getJtUTitulo() {return jtUTitulo;}
	public JTextField getJtUFecha() {return jtUFecha;}
	public JTextField getJtUImagen() {return jtUImagen;}
	public JTextArea getJaIDescripcion() {return jaIDescripcion;}
	public JTextArea getJaUDescripcion() {return jaUDescripcion;}
	public JComboBox<String> getCbITecnica() {return cbITecnica;}
	public JComboBox<String> getCbUCuadros() {return cbUCuadros;}
	public JComboBox<String> getCbUAutores() {return cbUAutores;}
	public JComboBox<String> getCbUTecnica() {return cbUTecnica;}
	public JButton getbIAceptar() {return bIAceptar;}
	public JButton getbICancelar() {return bICancelar;}
	public JButton getbUAceptar() {return bUAceptar;}
	public JButton getbUCancelar() {return bUCancelar;}
	public JButton getbDAceptar() {return bDAceptar;}
	public JButton getbDCancelar() {return bDCancelar;}
	public JList<String> getListCuadros() {return listCuadros;}
	public DefaultComboBoxModel<String> getDcbIAutores() {return dcbIAutores;}
	public DefaultComboBoxModel<String> getDcbITecnica() {return dcbITecnica;}
	public DefaultComboBoxModel<String> getDcbUCuadros() {return dcbUCuadros;}
	public DefaultComboBoxModel<String> getDcbUAutores() {return dcbUAutores;}
	public DefaultComboBoxModel<String> getDcbUTecnica() {return dcbUTecnica;}
	public JComboBox<String> getCbDCuadros() {return cbDCuadros;}
	public DefaultComboBoxModel<String> getDcbDCuadros() {return dcbDCuadros;}
	public int getAnchoCuadro() {return anchoCuadro;}
	public int getAltoCuadro() {return altoCuadro;}
	public int getAnchoFoto() {return anchoFoto;}
	public int getAltoFoto() {return altoFoto;}
	

	// Setters
	public void setTitulo(JTextField titulo) {this.titulo = titulo;}
	public void setAutor(JTextField autor) {this.autor = autor;}
	public void setAnhio(JTextField anhio) {this.anhio = anhio;}
	public void setTecnica(JTextField tecnica) {this.tecnica = tecnica;}
	public void setPrecio(JTextField precio) {this.precio = precio;}
	public void setPeriodo(JTextField periodo) {this.periodo = periodo;}
	public void setbPrimerCuadro(JButton bPrimerCuadro) {this.bPrimerCuadro = bPrimerCuadro;}
	public void setbUltimoCuadro(JButton bUltimoCuadro) {this.bUltimoCuadro = bUltimoCuadro;}
	public void setbSiguienteCuadro(JButton bSiguienteCuadro) {this.bSiguienteCuadro = bSiguienteCuadro;}
	public void setbAnteriorCuadro(JButton bAnteriorCuadro) {this.bAnteriorCuadro = bAnteriorCuadro;}
	public void setbStringCuadro(JButton bStringCuadro) {this.bStringCuadro = bStringCuadro;}
	public void setbDescripcionCuadro(JButton bDescripcionCuadro) {this.bDescripcionCuadro = bDescripcionCuadro;}
	public void setPestanhasPanel(JTabbedPane pestanhasPanel) {this.pestanhasPanel = pestanhasPanel;}
	public void setEtiCuadro(JLabel etiCuadro) {this.etiCuadro = etiCuadro;}
	public void setJtNombre(JTextField jtNombre) {this.jtNombre = jtNombre;}
	public void setJtNacimiento(JTextField jtNacimiento) { this.jtNacimiento = jtNacimiento;}
	public void setJtMuerte(JTextField jtMuerte) {this.jtMuerte = jtMuerte; }
	public void setJtPeriodo(JTextField jtPeriodo) {this.jtPeriodo = jtPeriodo;}
	public void setJtNacionalidad(JTextField jtNacionalidad) {this.jtNacionalidad = jtNacionalidad;}
	public void setJtObrasDisponibles(JTextField jtObrasDisponibles) {this.jtObrasDisponibles = jtObrasDisponibles;}
	public void setJtNumCuadros(JTextField jtEdad) {this.jtNumCuadros = jtEdad;}
	public void setbPrimerArtista(JButton bPrimerArtista) {this.bPrimerArtista = bPrimerArtista;}
	public void setbUltimoArtista(JButton bUltimoArtista) {this.bUltimoArtista = bUltimoArtista;}
	public void setbSiguienteArtista(JButton bSiguienteArtista) {this.bSiguienteArtista = bSiguienteArtista;}
	public void setbArtistaAnterior(JButton bArtistaAnterior) {this.bArtistaAnterior = bArtistaAnterior;}
	public void setEtiArtista(JLabel etiArtista) {this.etiArtista = etiArtista;}
	public void setbMuestraCuadros(JButton bMuestraCuadros) {this.bMuestraCuadros = bMuestraCuadros;}
	public void setBarraMenu(JMenuBar barraMenu) {this.barraMenu = barraMenu;}
	public void setMenuConsulta(JMenu menuConsulta) {this.menuConsulta = menuConsulta;}
	public void setItemConsulta(JMenuItem itemConsulta) {this.itemConsulta = itemConsulta;}
	public void setItemConsultaOrdenTitulo(JMenuItem itemConsultaOrdenTitulo) {this.itemConsultaOrdenTitulo = itemConsultaOrdenTitulo;}
	public void setJtITitulo(JTextField jtITitulo) {this.jtITitulo = jtITitulo;}
	public void setJtiFecha(JTextField jtiFecha) {this.jtiFecha = jtiFecha;}
	public void setJtiImagen(JTextField jtiImagen) {this.jtiImagen = jtiImagen;}
	public void setCbIAutores(JComboBox<String> cbIAutores) {this.cbIAutores = cbIAutores;}
	public void setJtiYear(JTextField jtiYear) {this.jtiYear = jtiYear;}
	public void setJtUTitulo(JTextField jtUTitulo) {this.jtUTitulo = jtUTitulo;}
	public void setJtUFecha(JTextField jtUFecha) {this.jtUFecha = jtUFecha;}
	public void setJtUImagen(JTextField jtUImagen) {this.jtUImagen = jtUImagen;}
	public void setJaIDescripcion(JTextArea jaIDescripcion) {this.jaIDescripcion = jaIDescripcion;}
	public void setJaUDescripcion(JTextArea jaUDescripcion) {this.jaUDescripcion = jaUDescripcion;}
	public void setCbITecnica(JComboBox<String> cbITecnica) {this.cbITecnica = cbITecnica;}
	public void setCbUCuadros(JComboBox<String> cbUCuadros) {this.cbUCuadros = cbUCuadros;}
	public void setCbUAutores(JComboBox<String> cbUAutores) {this.cbUAutores = cbUAutores;}
	public void setCbUTecnica(JComboBox<String> cbUTecnica) {this.cbUTecnica = cbUTecnica;}
	public void setbIAceptar(JButton bIAceptar) {this.bIAceptar = bIAceptar;}
	public void setbICancelar(JButton bICancelar) {this.bICancelar = bICancelar;}
	public void setbUAceptar(JButton bUAceptar) {this.bUAceptar = bUAceptar;}
	public void setbUCancelar(JButton bUCancelar) {this.bUCancelar = bUCancelar;}
	public void setbDAceptar(JButton bDAceptar) {this.bDAceptar = bDAceptar;}
	public void setbDCancelar(JButton bDCancelar) {this.bDCancelar = bDCancelar;}
	public void setListCuadros(JList<String> listCuadros) {this.listCuadros = listCuadros;}
	public void setDcbIAutores(DefaultComboBoxModel<String> dcbIAutores) {this.dcbIAutores = dcbIAutores;}
	public void setDcbITecnica(DefaultComboBoxModel<String> dcbITecnica) {this.dcbITecnica = dcbITecnica;}
	public void setDcbUCuadros(DefaultComboBoxModel<String> dcbUCuadros) {this.dcbUCuadros = dcbUCuadros;}
	public void setDcbUAutores(DefaultComboBoxModel<String> dcbUAutores) {this.dcbUAutores = dcbUAutores;}
	public void setDcbUTecnica(DefaultComboBoxModel<String> dcbUTecnica) {this.dcbUTecnica = dcbUTecnica;}
	public void setCbDCuadros(JComboBox<String> cbDCuadros) {this.cbDCuadros = cbDCuadros;}
	public void setDcbDCuadros(DefaultComboBoxModel<String> dcbDCuadros) {this.dcbDCuadros = dcbDCuadros;}
	public void setAnchoCuadro(int anchoCuadro) {Vista.anchoCuadro = anchoCuadro;}
	public void setAltoCuadro(int altoCuadro) {Vista.altoCuadro = altoCuadro;}
	public void setAnchoFoto(int anchoFoto) {Vista.anchoFoto = anchoFoto;}
	public void setAltoFoto(int altoFoto) {Vista.altoFoto = altoFoto;}

}
