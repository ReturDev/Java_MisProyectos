package aplicacion.menu;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;

import aplicacion.datosUsuario.LecturaDeDatosConsola;
import aplicacion.interaccionXML.InteraccionesXML;
import aplicacion.interaccionXML.ModificacionesYCreaciones;

public class FuncionamientoMenus {
	
	private static final String TAG_ANIMES = "Animes";
	private static final String TAG_PELICULASANIMES = "Peliculas Animes";
	private static final String TAG_SERIES = "Series";
	private static final String TAG_PELICULAS = "Peliculas";
	

	/**
	 * Se encarga de la funcionalidad del menu principal y ejecuta los métodos
	 * correspondientes dependiendo del campo introducido por el usuari0.
	 * @return
	 * @throws TransformerException 
	 */
	public static boolean funcionMenuPrincipal() throws TransformerException {
		// Booleano encargado de romper el bucle cuando se quiera salir del programa.
		boolean salidaPrograma = false;
		// Imprime por consola el menú.
		VisualizacionMenus.menuPrincipal();
		// Guarda el número introducido por el usuario.
		int indice = LecturaDeDatosConsola.lecturaIndiceMenu(1, 6);

		/*
		 * En cada opcion se iguala el metodo al booleano encargado de interrumpir el
		 * bucle por si en alguno de ellos se decide salir del programa estos puedan
		 * devolver true y asi pararlo.
		 */
		switch (indice) {
		case 1:
			// Entra al menú de Animes.
			salidaPrograma = funcionMenuComun(TAG_ANIMES);
			break;
		case 2:
			salidaPrograma = funcionMenuComun(TAG_PELICULASANIMES);
			break;
		case 3:
			// Entra al menú común adaptado a series.
			salidaPrograma = funcionMenuComun(TAG_SERIES);
			break;
		case 4:
			// Entra al menú común adaptado a peliculas.
			salidaPrograma = funcionMenuComun(TAG_PELICULAS);
			break;
		case 5:
			// Cambia el valor del booleano para interrumpir el programa.
			salidaPrograma = true;
			break;
		}

		return salidaPrograma;
	}

	/**
	 * Encargado del menú común y final del programa.
	 * @param categoriaMenu Recibe el nombre del menú al que se adaptará.
	 * @return
	 * @throws TransformerException
	 */
	private static boolean funcionMenuComun(String categoriaMenu) throws TransformerException {
		boolean salidaPrograma = false;
		boolean salidaMenuComun = false;
		// Formateamos el nombre de la categoría para que este en minúsculas y sin
		// espacios para las etiquetas.
		String nombreTagsPadres = categoriaMenu.replace(" ", "").toLowerCase();
		//Modificamos el nombre de la etiqueta del padre quitandole la "s" del final para las etiquetas hijas.
		String nombreTagsHijos = nombreTagsPadres.substring(0,nombreTagsPadres.length()-1);
		/*
		 * Llamamos al método al que pasaremos el nombre de la etiqueta del padre y del hijo para que obtenga
		 * el nodo padre y la lista de los nodos hijos.
		 */
		InteraccionesXML.obtenerNodoPadre(nombreTagsPadres,nombreTagsHijos);

		while (!salidaMenuComun) {
			//Imprimimos el menú.
			VisualizacionMenus.menuComun(categoriaMenu);
			
			/*
			 * Llama al método encargado de pedirle al usuario que introduzca un índice entre los números que le pasamos
			 * por parámetro.
			 */
			 
			int indice = LecturaDeDatosConsola.lecturaIndiceMenu(1, 11);
			
			//Dependiendo del valor obtenido realizará la función correspondiente.
			switch (indice) {
			case 1: {
				if (InteraccionesXML.nodoSinHijos(nombreTagsPadres)) {
					System.out.println("No hay elementos.");
				} else {
					InteraccionesXML.busquedaPorNombre();
				}
				break;
			}
			case 2: {
				if (InteraccionesXML.nodoSinHijos(nombreTagsPadres)) {
					System.out.println("No hay elementos.");
				} else {
					InteraccionesXML.busquedaPorEstado();
				}
				break;
			}
			case 3: {
				if (InteraccionesXML.nodoSinHijos(nombreTagsPadres)) {
					System.out.println("No hay elementos.");
				} else {
					InteraccionesXML.busquedaAleatoria();
				}
				break;
			}
			case 4: {
				if (InteraccionesXML.nodoSinHijos(nombreTagsPadres)) {
					System.out.println("No hay elementos.");
				} else {
					InteraccionesXML.busquedaPorId();
				}
				break;
			}
			case 5: {
				if (InteraccionesXML.nodoSinHijos(nombreTagsPadres)) {
					System.out.println("No hay elementos.");
				} else {
					InteraccionesXML.mostrarTodosElementos();
				}
				break;
			}
			case 6:{
				InteraccionesXML.insertarElementos(nombreTagsHijos);
				break;
			}
			case 7: {
				if (InteraccionesXML.nodoSinHijos(nombreTagsPadres)) {
					System.out.println("No hay elementos.");
				} else {
					InteraccionesXML.modificarElemento();
				}
				break;
			}
			case 8: {
				if (InteraccionesXML.nodoSinHijos(nombreTagsPadres)) {
					System.out.println("No hay elementos.");
				} else {
					InteraccionesXML.eliminarElemento();
				}
				break;
			}
			case 9:
				salidaMenuComun = true;
				break;
			case 10:
				salidaMenuComun = true;
				salidaPrograma = true;
				break;
			}
		}

		return salidaPrograma;

	}
	
	/**
	 * Menú encargado de gestionar las modificaciones que el usuario quiera realizar.
	 * @param elementoModificar
	 * @return
	 * @throws TransformerException 
	 */
	public static boolean funcionMenuModificaciones(Element elementoModificar) throws TransformerException {
		boolean salidaMenuModificaciones = false;
		
		while(!salidaMenuModificaciones) {
			// Imprime por consola el menú.
			VisualizacionMenus.menuModificaciones();
			
			// Guarda el número introducido por el usuario.
			int indice = LecturaDeDatosConsola.lecturaIndiceMenu(1, 8);
			
			switch (indice) {
			case 1: {
				ModificacionesYCreaciones.modificarEstado(elementoModificar);
				break;
			}
			case 2: {
				ModificacionesYCreaciones.modificarTempVistas(elementoModificar);
				break;
			}
			case 3: {
				ModificacionesYCreaciones.modificarTempTotales(elementoModificar);
				break;
			}
			case 4: {
				ModificacionesYCreaciones.modificarSinopsis(elementoModificar);
				break;
			}
			case 5: {
				ModificacionesYCreaciones.modificarCapVistos(elementoModificar);
				break;
			}
			case 6:{
				ModificacionesYCreaciones.modificarCapTotales(elementoModificar);
				break;
			}
			case 7:
				salidaMenuModificaciones = true;
				break;
			}
			
			InteraccionesXML.transmorfarXML();
			
		}
		
		
		return salidaMenuModificaciones;
	}
	
	/**
	 * Menú encargado de gestionar las modificaciones que el usuario quiera realizar sobre películas.
	 * @param elementoModificar
	 * @return
	 */
	public static boolean funcionMenuModificacionesPeliculas(Element elementoModificar) {
		boolean salidaMenuModificaciones = false;
		
		while(!salidaMenuModificaciones) {
			// Imprime por consola el menú.
			VisualizacionMenus.menuModificacionesPeliculas();
						
			// Guarda el número introducido por el usuario.
			int indice = LecturaDeDatosConsola.lecturaIndiceMenu(1, 4);
			switch (indice) {
				case 1: {
					ModificacionesYCreaciones.modificarEstado(elementoModificar);
					break;
				}
				case 2: {
					ModificacionesYCreaciones.modificarSinopsis(elementoModificar);
					break;
				}
				case 3: {
					salidaMenuModificaciones = true;
					break;
				}
			}
		}
		return salidaMenuModificaciones;
		
	}
	
	private FuncionamientoMenus() {}
}
