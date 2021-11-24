package uniandes.dpoo.taller1.interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

import uniandes.dpoo.taller1.excepciones.CustomMissingBook;
import uniandes.dpoo.taller1.excepciones.CustomNullAuthors;
import uniandes.dpoo.taller1.excepciones.CustomNullException;
import uniandes.dpoo.taller1.excepciones.CustomRepeatedException;
import uniandes.dpoo.taller1.modelo.Categoria;
import uniandes.dpoo.taller1.modelo.Libreria;
import uniandes.dpoo.taller1.modelo.Libro;

/**
 * Esta clase representa a la ventana principal de la aplicación
 */
@SuppressWarnings("serial")
public class InterfazLibreria extends JFrame {

	// ************************************************************************
	// Atributos
	// ************************************************************************

	/**
	 * Esta es la librería que se muestra durante la ejecución de la aplicación
	 */
	private Libreria libreria;

	// ************************************************************************
	// Elementos de la interfaz
	// ************************************************************************

	/**
	 * Este componente corresponde al menú completo que se encuentra en la parte
	 * superior de la ventana
	 */
	private JMenuBar barraMenu;

	/**
	 * Este componente corresponde al menú archivo
	 */
	private JMenu menuArchivo;

	/**
	 * Este componente corresponde a la opción para cargar los archivos de una
	 * librería
	 */
	private JMenuItem menuAbrir;

	/**
	 * Este componente corresponde a la opción para salir de la aplicación
	 */
	private JMenuItem menuSalir;

	/**
	 * Este componente corresponde al panel donde se muestran las categorías
	 * disponibles en la aplicación
	 */
	private PanelCategorias panelCategorias;

	/**
	 * Este componente corresponde al panel donde se muestra una lista de libros
	 */
	private PanelLibros panelLibros;

	/**
	 * Este componente corresponde al panel donde se muestra la información de un
	 * libro
	 */
	private PanelLibro panelLibro;

	/**
	 * Este componente corresponde al panel con los botones de la parte inferior de
	 * la ventana
	 */
	private PanelBotones panelBotones;

	// ************************************************************************
	// Constructores
	// ************************************************************************

	/**
	 * Construye la ventana principal para la aplicación, pero no carga la
	 * información de ninguna librería.
	 */
	public InterfazLibreria() {
		barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);

		menuArchivo = new JMenu("Archivo");
		barraMenu.add(menuArchivo);

		// Setting the accelerator:
		menuAbrir = new JMenuItem("Abrir", KeyEvent.VK_A);
		menuAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menuAbrir.setActionCommand(ListenerMenu.ABRIR_LIBROS);
		menuAbrir.addActionListener(new ListenerMenu(this));
		menuArchivo.add(menuAbrir);

		menuSalir = new JMenuItem("Salir", KeyEvent.VK_Q);
		menuSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuArchivo.add(menuSalir);

		JPanel panelArriba = new JPanel(new GridLayout(1, 2));
		add(panelArriba, BorderLayout.CENTER);

		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelArriba.add(panelIzquierdo);

		panelCategorias = new PanelCategorias(this);
		panelIzquierdo.add(panelCategorias, BorderLayout.NORTH);

		panelLibros = new PanelLibros(this);
		panelIzquierdo.add(panelLibros, BorderLayout.CENTER);

		JPanel panelDerecha = new JPanel(new BorderLayout());
		panelArriba.add(panelDerecha);
		panelLibro = new PanelLibro();
		panelDerecha.add(panelLibro, BorderLayout.CENTER);

		JPanel panelAbajo = new JPanel(new BorderLayout());
		panelBotones = new PanelBotones(this);
		panelAbajo.add(panelBotones, BorderLayout.CENTER);
		add(panelAbajo, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Librería");
		setSize(1000, 700);
		setVisible(true);
	}

	// ************************************************************************
	// Métodos
	// ************************************************************************

	/**
	 * Carga la información de una librería a partir de los archivos datos,
	 * construye un objeto Librería con esa información y lo deja en el atributo
	 * llamado 'libreria'
	 * 
	 * @param archivo_categorias El archivo que tiene la información de las
	 *                           categorías que se usarán para los libros
	 * @param archivo_libros     El archivo que tiene la información de los libros
	 */
	public void cargarArchivos(File archivo_categorias, File archivo_libros) {

		try {
			libreria = new Libreria(archivo_categorias.getPath(), archivo_libros.getPath(), this);
			panelCategorias.actualizarCategorias(libreria.darCategorias());
		}

		catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Hubo un error leyendo los archivos", "Error de lectura",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	/**
	 * Cambia la categoría para la cual se deben mostrar los libros en el panel
	 * panelLibros
	 * 
	 * @param categoria La categoría para la que se deben mostrar los libros ahora
	 */
	public void cambiarCategoria(Categoria categoria) {
		ArrayList<Libro> libros = libreria.darLibros(categoria.darNombre());
		panelLibros.actualizarLibros(libros);
		mostrarLibro(libros.get(0));
	}

	/**
	 * Cambia el libro para el cual se debe mostrar la información en el panel
	 * panelLibro
	 * 
	 * @param libro El libro para el que se debe mostrar la información
	 */
	public void mostrarLibro(Libro libro) {
		panelLibro.actualizarLibro(libro);
	}

	/**
	 * Le pide al usuario el título de un libro y lo busca en la librería.
	 * 
	 * Si existe un libro, le muestra al usuario la información del libro en el
	 * panel 'panelLibro'.
	 */
	public void buscarLibro() {
		String titulo = JOptionPane.showInputDialog(this, "Escriba el título del libro que busca", "titulo");
		if (titulo != null) {
			Libro libro = libreria.buscarLibro(titulo);
			if (libro == null) {
				JOptionPane.showMessageDialog(this, "No se encontró un libro con ese título", "No hay libro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				mostrarLibro(libro);
			}
		}
	}

	/**
	 * Busca los libros de un autor a partir de una parte del nombre del autor que
	 * haya dado el usuario.
	 * 
	 * La lista de libros que correspondan al autor dado se muestra en el panel
	 * panelLibros.
	 * @throws CustomNullAuthors 
	 */
	public void buscarLibrosAutor() throws CustomNullAuthors {
		String autor = JOptionPane.showInputDialog(this, "Escriba al menos una parte del autor que busca", "autor");
		if (autor != null) {
			ArrayList<Libro> libros = libreria.buscarLibrosAutor(autor);
			if (libros.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay ningún autor con ese nombre", "No hay libro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				panelLibros.actualizarLibros(libros);
				mostrarLibro(libros.get(0));
			}
		}
	}

	/**
	 * Le pide al usuario el nombre de un autor y le informa en qué categorías hay
	 * libros de ese autor.
	 */
	public void buscarCategoriasAutor() {

		String autor = JOptionPane.showInputDialog(this, "Escriba el nombre del autor que está buscando", "autor");
		if (autor != null) {
			ArrayList<Categoria> categorias = libreria.buscarCategoriasAutor(autor);
			if (categorias.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay ningún autor con ese nombre", "No hay libro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				String mensaje = "Hay libros de ese autor en las siguientes categorías:\n";
				for (Categoria categoria : categorias) {
					mensaje += " " + categoria.darNombre() + "\n";
				}
				JOptionPane.showMessageDialog(this, mensaje, "Categorías", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Le informa al usuario la calificación promedio de los libros de la librería,
	 * con base en la información disponible en cada uno de los libros.
	 */
	public void calcularCalificacionPromedio() {
		double calificacion = libreria.calificacionPromedio();
		calificacion = (double) ((int) calificacion * 1000) / 1000;
		JOptionPane.showMessageDialog(this, "La calificación promedio de los libros es " + calificacion,
				"Calificación promedio", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario cuál es la categoría con más libros en la librería.
	 */
	public void categoriaConMasLibros() {
		Categoria cat = libreria.categoriaConMasLibros();
		int cantidad = cat.contarLibrosEnCategoria();
		String mensaje = "La categoría con más libros es " + cat.darNombre() + " y tiene " + cantidad + " libros";
		JOptionPane.showMessageDialog(this, mensaje, "Categoría con más libros", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario la cantidad de libros en la librería para los cuales no
	 * se tiene una portada.
	 */
	public void contarSinPortada() {
		int cantidad = libreria.contarLibrosSinPortada();
		String mensaje = "Hay " + cantidad + " libros sin portada";
		JOptionPane.showMessageDialog(this, mensaje, "Libros sin portada", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario cuál es la categoría cuyos libros están mejor
	 * calificados.
	 */
	public void categoriaMejorCalificacion() {
		Categoria cat = libreria.categoriaConMejoresLibros();
		double calificacion = cat.calificacionPromedio();
		calificacion = (double) ((int) calificacion * 1000) / 1000;
		String mensaje = "La categoría con la mejor calificación es " + cat.darNombre()
				+ ".\nLa calificación promedio de los libros es " + calificacion;
		JOptionPane.showMessageDialog(this, mensaje, "Categoría con mejor calificación promedio",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Le informa al usuario si hay un autor que tenga libros en más de una
	 * categoría.
	 */
	public void hayAutorEnVariasCategorias() {
		boolean hay = libreria.hayAutorEnVariasCategorias();
		String mensaje = "No hay ningún autor con al menos un libro en dos categorías diferentes.";
		if (hay) {
			mensaje = "Hay al menos un autor con al menos un libro en dos categorías diferentes.";
		}
		JOptionPane.showMessageDialog(this, mensaje, "Consulta", JOptionPane.INFORMATION_MESSAGE);
	}

	public void renombrarCategoria() {

		JLabel ingreseCat = new JLabel("Seleccione la categoría que desea renombrar: ");

		JComboBox<Categoria> cbbCategorias = new JComboBox<Categoria>();

		int categorias_size = panelCategorias.getComboBoxCategorias().getItemCount();

		for (int i = 0; i < categorias_size; i++) {
			Categoria actualCategoria = panelCategorias.getComboBoxCategorias().getItemAt(i);
			cbbCategorias.addItem(actualCategoria);
		}

		JLabel nuevaCatLabel = new JLabel("Ingrese el nombre con el que desea renombrar la categoria: ");
		JTextField nuevoNombreCat = new JTextField();

		Object[] mensaje = new Object[] { ingreseCat, cbbCategorias, nuevaCatLabel, nuevoNombreCat };

		int paneResponse = JOptionPane.showConfirmDialog(this, mensaje, "Renombrar categoría",
				JOptionPane.OK_CANCEL_OPTION);

		if (paneResponse == JOptionPane.OK_OPTION) {
			Categoria categoriaAModificar;
			try {
				categoriaAModificar = libreria.obtenerCategoria(
						((Categoria) cbbCategorias.getSelectedItem()).darNombre(), nuevoNombreCat.getText());
				categoriaAModificar.renombrar(nuevoNombreCat.getText());
				JOptionPane.showMessageDialog(this, "El nombre de la categoría se ha modificado exitosamente.");

			} catch (CustomRepeatedException e) {
				JOptionPane.showMessageDialog(this,
						"¡Se ha intentado renombrar una categoría con un nombre ya existente! Intentelo de nuevo.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	public void eliminarLibrosAutor() {
		
		ArrayList<String> autoresEncontrados = new ArrayList<String>();
		ArrayList<String> autoresNoEncontrados = new ArrayList<String>();
		ArrayList<Libro>  librosAEliminar = new ArrayList<Libro>();
		
		String mensaje_no_encontrados = "";
		String mensaje_encontrados = "";
		
		JLabel labelPreguntaAutores = new JLabel(
				"Ingrese el nombre de los autores cuyos libros desea eliminar separados por una coma (,): ");
		JTextField textFieldAutores = new JTextField();

		Object[] mensaje = new Object[] { labelPreguntaAutores, textFieldAutores };

		int paneResponse = JOptionPane.showConfirmDialog(this, mensaje, " Eliminar libros - Autor",
				JOptionPane.OK_CANCEL_OPTION);

		if (paneResponse == JOptionPane.OK_OPTION) {
			
			String listaAutoresStr = textFieldAutores.getText();
			String[] listaAutoresArray = listaAutoresStr.split(",");
			
			for(int i = 0; i < listaAutoresArray.length; i++) {
				
				String autorActual = listaAutoresArray[i].strip();
				
				try {
					ArrayList<Libro> listaLibrosAutor = libreria.buscarLibrosAutor(autorActual);
					autoresEncontrados.add(autorActual);
					mensaje_encontrados += autorActual + ",";
					librosAEliminar.addAll(listaLibrosAutor);
					
					
				} catch (CustomNullAuthors e) {
					autoresNoEncontrados.add(e.getAutorNulo());
					mensaje_no_encontrados += e.getAutorNulo() + ", ";
				}
				
			
			}
			
			//Se evalua que mensaje y accion tomar 
			if(autoresNoEncontrados.size() != 0) {
				
				String mensaje_error = "Los siguientes nombres corresponden a autores existentes: " + mensaje_encontrados;
				mensaje_error += "\n Los siguientes nombres corresponden a autores inexistentes: " + mensaje_no_encontrados;
				
				
				JOptionPane.showMessageDialog(this, mensaje_error, "Operación cancelada - Autor no encontrado",
						JOptionPane.ERROR_MESSAGE);
			}
			
			else {
				
				Iterator<Libro> iterLibrosEliminar = librosAEliminar.iterator();
				
				
				while(iterLibrosEliminar.hasNext()) {
					Libro libroElimActual = iterLibrosEliminar.next();
					
					//Se captura la excepción en dado caso que no se pueda eliminar el libro.
					try {
						libreria.eliminarLibro(libroElimActual);
					} catch (CustomMissingBook e) {
						
						//Se le notifica al usuario del error (No se pudo eliminar el libro)
						Libro libroErroneo = e.getMissingBook();
						String titulo = libroErroneo.darTitulo();
						String autor = libroErroneo.darAutor();
						String categoria = libroErroneo.darCategoria().darNombre();
					
						String mensaje_libro = "No ha sido posible eliminar el libro: " + titulo;
						mensaje_libro += "\nAutor: " + autor;
						mensaje_libro += "\nCategoria: " + categoria;
						
						JOptionPane.showMessageDialog(this, mensaje_libro, "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				
				String mensaje_exito = "Se han eliminado exitosamente " + librosAEliminar.size() + " libros.";
				JOptionPane.showMessageDialog(this, mensaje_exito, "Operación exitosa - Libros eliminados",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
	}

	public void errorHanlderInterfaz(Libreria lib, CustomNullException e) {
		if (e.getNullObject() == CustomNullException.NULL_CATEGORY) {
			String mensaje = "Se ha agregado la categoría: " + e.getCategoriaName() + "\nCon "
					+ lib.darLibros(e.getCategoriaName()).size() + " libros agregados.";
			JOptionPane.showMessageDialog(this, mensaje, "Categorías nuevas encontradas",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	// ************************************************************************
	// Main
	// ************************************************************************

	/**
	 * Método inicial de la aplicación
	 * 
	 * @param args Parámetros introducidos por el usuario en la línea de comandos
	 * @throws IOException
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		/*
		 * for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) { if
		 * ("Nimbus".equals(info.getName())) {
		 * UIManager.setLookAndFeel(info.getClassName()); break; } }
		 */
		FlatLightLaf.install();
		// UIManager.setLookAndFeel( new FlatDarculaLaf());

		new InterfazLibreria();
	}

}
