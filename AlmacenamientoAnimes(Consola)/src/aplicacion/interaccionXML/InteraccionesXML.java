package aplicacion.interaccionXML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import aplicacion.datosUsuario.CreacionConfigYArchivoXML;
import aplicacion.datosUsuario.LecturaDeDatosConsola;
import aplicacion.menu.FuncionamientoMenus;

public class InteraccionesXML {

	
	private static final String ID_ATT = "id";
	private static final String NOMBRE_ATT = "nombre";
	private static final String ESTADO_ATT = "estado";
	private static final String TEMP_V_ATT = "tempvistas";
	private static final String TEMP_T_ATT = "temptotales";
	private static final String CAP_V_ATT = "capvistos";
	private static final String CAP_T_ATT = "captotales";
	private static final String SINOPSIS = "sinopsis";
	private static final String TEMPORADA = "temporada";
	

	private static Document doc;
	private static Element elementoRaiz;
	private static Element elementoPadre;
	private static NodeList elementosHijos;


	/**
	 * Bloque estático que se inicia al cargar al clase y se encarga de obtener el
	 * acceso al documento xml.
	 */
	static {
		try {
			// Obtenemos el archivo xml.
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(CreacionConfigYArchivoXML.getArchivoXML());

			// Obtenemos el nodo raiz del archivo.
			elementoRaiz = (Element) doc.getFirstChild();

		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se encarga de obtener el nodo padre de todos los elementos que se guardaran
	 * posteriormente, sino existía ya, lo crea.
	 * 
	 * @param nombreNodoPadre Recibe el nombre del nodo padre.
	 * @param nombreNodosHijo Recibe el nombre que tendran los nodos hijos.
	 * @throws TransformerException
	 */
	public static void obtenerNodoPadre(String nombreNodoPadre , String nombreNodosHijo) {

		// Buscamos el nodo padre a partir del nombre recibido por parámetro.
		NodeList lista = elementoRaiz.getElementsByTagName(nombreNodoPadre);

		// Si no se encuentra ningun nodo hijo del nodo raiz con el nombre recibido, se
		// crea.
		if (lista.getLength() == 0) {
			try {
				// Creamos un elemento con el nombre recibido
				Element nuevoElemento = doc.createElement(nombreNodoPadre);
				// Lo añadimos al nodo padre.
				elementoRaiz.appendChild(nuevoElemento);
				// Y sobreescribimos el archivo existente con otro que contenga el nuevo
				// elemento.
				Transformer tf = TransformerFactory.newInstance().newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(CreacionConfigYArchivoXML.getArchivoXML()));
				tf.transform(source, result);
			} catch (TransformerException e) {
				e.printStackTrace();
			}

		}
		// Guardamos la direccón de este nodo que será el nodo padre de los elementos
		// que almacenaremos para utilizarlo una vez dentro del menú.
		elementoPadre = (Element) elementoRaiz.getElementsByTagName(nombreNodoPadre).item(0);
		elementosHijos = elementoPadre.getElementsByTagName(nombreNodosHijo);
	}

	/**
	 * Comprueba que el nodo padre con el nombre recibido no tiene hijos.
	 * 
	 * @param nombreNodo Recibe el nombre del nodo.
	 * @return
	 */
	public static boolean nodoSinHijos(String nombreNodo) {
		// Almacena si el nodo está o no vacío
		boolean nodoVacio = false;
		// Obtenemos la lista de elementos con el nombre dado.
		NodeList list = elementoRaiz.getElementsByTagName(nombreNodo).item(0).getChildNodes();
		// Si la lista está vacía cambia el valor a verdadero sobre la ausencia de
		// hijos.
		if (list.getLength() == 0) {
			nodoVacio = true;
		}
		return nodoVacio;
	}

	/**
	 * Metodo encargado de buscar el nodo por su nombre.
	 */
	public static void busquedaPorNombre() {
		//Variable encargada de salir del búcle.
		boolean salidaBusquedas = false;
		//Variable encargada de almacenar la cadena introducida por el usuario.
		String nombreIntroducido;
		
		Element elementoBuscado = null;
		
		//Bucle que se repetira si el nombre introducido no es válido o si el usuario quiere continuar haciendo busquedas por nombre.
		while (!salidaBusquedas) {
			
			System.out.println("\nIntroduce el nombre del elemento que quieres buscar o parte de este(mínimo tres letras).");
			nombreIntroducido = LecturaDeDatosConsola.lecturaTexto().toUpperCase();
			
			//Si el nombre introducido tiene 3 o más caracteres se procederá a buscar entre los elementos.
			if (nombreIntroducido.length() >= 3) {
				
				//Llamamos al método que busque el nodo a partir del nombre obtenido y si lo encuentra lo almacene en elementoBuscado.
				elementoBuscado = busquedaDeNodo(nombreIntroducido);
				
				/*
				 * Para finalizar si la variable elementoEncontrado tiene valor true y la variable que almacena
				 * el nodo que buscaba el usuario no esta vacía, imprime el nodo por consola, sino avisa al usuario
				 * que no se ha encontrado ningun elemento.
				 */
				if(elementoBuscado != null) {
					mostrarNodoCompleto(elementoBuscado);
					
				}else {
					System.out.println("Elemento no encontrado.");
				}
				
				//Creamos un bucle con una variable para salir de este cuando el usuario introduzca un caracter válido.
				boolean caracterValido = false;
				while(!caracterValido) {

					System.out.println("\n¿Quieres seguir buscando elementos?(S=Si N=NO)");
					char caracter = LecturaDeDatosConsola.lecturaTexto().toUpperCase().charAt(0);
					
					/*
					 * Si el valor introducido por el usuario es distinto a S o N, se le avisa que es un valor no válido
					 * y se le vuelve a pedir que introduzca otro valor. Si es N se saldra del bucle principal y del método,
					 * si es S volvera al incio para buscar otro elemento.
					 */
					if(caracter == 'N' || caracter == 'S') {
						salidaBusquedas = caracter == 'N'? true : false;
						caracterValido = true;
					}else if(caracter != 'S' || caracter != 'N') {
						System.out.println("Valor introducido invalido.");
					}
				}
				
				
			} else {
				System.out.println("Nombre demasiado corto.");
			}
			

		}
	}
	
	/**
	 * Método encargado de buscar un nodo a través del nobre recibido y devolver el elemento encontrado.
	 * @param nombreIntroducido Nombre del nodo que buscar.
	 * @return
	 */
	private static Element busquedaDeNodo(String nombreIntroducido) {
		//Creamos una lista donde almacenaremos todos los nodos que tengan coincidencias con el nombre introducido.
		List<Node> coincidenciasNodos = new ArrayList<Node>();
		//Indicara si el nombre ha sido encontrado.
		boolean elementoEncontrado = false;
		//Elemento el cual se buscaba.
		Element elementoBuscado = null;
		
		//Recorremos todos los hijos obtenidos.
		for (int i = 0; i < elementosHijos.getLength() && !elementoEncontrado; i++) {
			
			Element e = (Element) elementosHijos.item(i);
			
			/*
			 * Comprobamos si el valor del nodo es igual o contiene el valor introducido por el usuario.
			 * Si es igual, guardamos el nodo en una variable y cambiamos el valor a la variable elementoEncontrado.
			 * Si ningun elemento es igual pero si que contienen la cadena introducida se guarda en la lista
			 * creada anteriormente.
			 */
			if (e.getAttribute(NOMBRE_ATT).equals(nombreIntroducido)) {
				elementoBuscado = e;
				elementoEncontrado = true;
			} else if (e.getAttribute(NOMBRE_ATT).contains(nombreIntroducido)) {
				coincidenciasNodos.add(elementosHijos.item(i));
			}
			
			
		}
		
		//Comprobamos si la lista de coincidencias no está vacía y el elemento no ha sido encontrado
		if(coincidenciasNodos.size() != 0 && !elementoEncontrado) {
			//Creamos un array donde guardar todos los ids de las coincidencias.
			String[] ids = new String[coincidenciasNodos.size()];
			
			//Recorremos todas las coincidencias, las imprimimos y almacenamos sus ids en el array.
			for(int i = 0; i < coincidenciasNodos.size(); i++) {
				Element e = (Element) coincidenciasNodos.get(i);
				ids[i] = e.getAttribute(ID_ATT);
				System.out.println(mostrarNodoBase(e));
			}
			
			//Entramos en un búcle que se repetira hasta que el usuario no encuentre el elemento o salga.
			while(!elementoEncontrado) {
				//Pregunta el id del elemento que quiere obtener al usuario.
				System.out.println("\nIntroduce el id del elemento al que quieras acceder.(Introduce 0 para salir.)");
				int idIntroducida = LecturaDeDatosConsola.lecturaNumero(0);
				
				//Si el valor es 0 se sale sin obtener ningun elemento.
				if(idIntroducida == 0) {
					elementoEncontrado = true;
				}
				
				/*
				 * Si el valor es distinto a 0 busca la id introducida en el array de ids a ver si se encuentra.
				 * Si la encuentra se guarda el elemento en la variable y se cambia el valor de elementoEncontrado a true.
				 */
				for(int i = 0; i < ids.length && !elementoEncontrado; i++) {
					if(ids[i].equals(String.valueOf(idIntroducida))) {
						elementoEncontrado = true;
						elementoBuscado = (Element) coincidenciasNodos.get(i);
					}
				}
				
				//Si no se encontró el elemento notifica al usuario que la id introducia no corresponde con las coincidencias.
				if(!elementoEncontrado) {
					System.out.println("La id introducida no corresponde con las coincidencias.");
				}
			}
		}
		
		
		return elementoBuscado;
	}
	
	
	/**
	 * Metodo encargado de buscar todas las coincidencias a través del estado.
	 */
	public static void busquedaPorEstado() {
		//Variable encargada de parar el búcle una vez el usuario introduzca un valor válido.
		boolean estadoCorrecto = false;
		//Variable que guarda el valor introducido por el usuario.
		char estadoBuscado = ' ';
		
		
		while(!estadoCorrecto) {
			System.out.println("\nIntroduce por que estado quieres buscar(P=Pendiente, V=Visto, S=Siguiendo, D=Dropeado).");
			//Almacenamos el valor introducido por el usuario convirtiendolo en mayuscula.
			estadoBuscado = LecturaDeDatosConsola.lecturaTexto().toUpperCase().charAt(0);
			//Comprobamos que el valor introducido es un valor válido.
			estadoCorrecto = ComprobacionesInteracciones.comprobarEstadoChar(estadoBuscado);
		}
	
		//Creamos una lista donde almacenar todos los nodos coincidentes.
		ArrayList<Node> coincidenciasNodo = new ArrayList<Node>();
		//Recorremos la lista de todos los hijos
		for(int i = 0; i < elementosHijos.getLength(); i++) {
			Element e = (Element) elementosHijos.item(i);
			//Comprobamos si el valor de la primera letra del estado es igual que el valor introducido por el usuario.
			if(e.getAttribute(ESTADO_ATT).charAt(0) == estadoBuscado) {
				coincidenciasNodo.add(e);
			}
		}
		
		//Imprimimos cada coincidencia.
		for(Node nodo : coincidenciasNodo) {
			mostrarNodoBase((Element)nodo);
		}
		
	}
	
	/**
	 * Metodo principal para la busqueda de elemento por id.
	 */
	public static void busquedaPorId() {
		System.out.println("Introduce el id que quieres encontrar.");
		String id = LecturaDeDatosConsola.lecturaNumero(1) +"";
		busquedaId(id);
		
	}
	
	/**
	 * Metodo principal para la busqueda de elemento id aleatorio.
	 */
	public static void busquedaAleatoria() {
		
		String id =(int)Math.round(Math.random()*elementosHijos.getLength()) + "";
		busquedaId(id);
	}
	
	/**
	 * Metodo encargado de comparar los elementos con la id recibida y mostrar el que concuerde.
	 * @param id Recibe el id que debe buscar.
	 */
	private static void busquedaId(String id) {
		for(int i = 0; i < elementosHijos.getLength(); i++) {
			Element e = (Element) elementosHijos.item(i);
			if(e.getAttribute(ID_ATT).equals(id)) {
				mostrarNodoCompleto(e);
			}
		}
	}
	
	/**
	 * Metodo encargado de mostrar todos los elementos que contiene el nodo padre.
	 */
	public static void mostrarTodosElementos() {
		for(int i = 0; i < elementosHijos.getLength(); i++) {
			Element e = (Element)elementosHijos.item(i);
			System.out.println(mostrarNodoBase(e));
		}
	}
	
	/**
	 * Metodo encargado de introducir elementos hijos.
	 * @param tagElementoACrear Recibe el nombre de la etiqueta padre.
	 * @throws TransformerException
	 */
	public static void insertarElementos(String tagElementoACrear) throws TransformerException {
		//Creamos un nuevo elemento que será el que añadamos al padre.
		Element nuevoElemento = doc.createElement(tagElementoACrear);
		/*
		 * Creamos la variable que establecera el id del elemento, si no hay ningun elemento anterior se establecera
		 * el id con el valor de 1, en el caso que si haya elementos cogera la id del último elemento.
		 */
		String id = "1";
		//Comprobamos si hay algún elemento para obtener la id del último.
		if(elementosHijos.getLength() > 0) {
			//Obtenemos el último elemento hijo para obtener su id.
			Element e = (Element)elementosHijos.item(elementosHijos.getLength()-1);
			//Convertimos el id en entero, le sumamos 1 y lo volvemos a String.
			id = (Integer.parseInt(e.getAttribute(ID_ATT))+1) + "";
		}
		
		//Añadimos el atributo id junto a su valor al nuevo elemento.
		nuevoElemento.setAttribute(ID_ATT, id);
		
		
		//Pedimos el nombre para el nuevo elemento y lo añadimos como atributo junto a su valor.
		System.out.println("\nIntroduce el nombre del elemento");
		nuevoElemento.setAttribute(NOMBRE_ATT, LecturaDeDatosConsola.lecturaTexto().toUpperCase());
		
		/*
		 * Creamos una variable para guardar el estado introducido por el usuario y otra para detener el bucle
		 * cuando el valor sea válido.
		 */
		String estado = null;
		boolean campoValido = false;
		
		
		while(!campoValido) {
			System.out.println("Introduce el estado en el que se encuentra el documento. "
					+ "(Pendiente, Visto, Siguiendo, Dropeado)");
			//Obtenemos el estado y lo convertimos a mayusculas.
			estado = LecturaDeDatosConsola.lecturaTexto().toUpperCase();
			/*
			 * Llamamos al metodo para que compruebe que el valor de estado es válido. Si lo es cambiara el valor de
			 * campoValido y saldrá del bucle.
			 */
			campoValido = ComprobacionesInteracciones.comprobarEstado(estado);
		}
		//Añadimos el estado junto a su valor como atributo.
		nuevoElemento.setAttribute(ESTADO_ATT, estado);
		
		//Llamamos al método para crear Sinopsis.
		ModificacionesYCreaciones.CrearSinopsis(nuevoElemento);
		
		//Comprobamos si el elemento que estamos creando no es una película para no añadirle ni temporadas ni capítulos.
		if(!tagElementoACrear.contains("pelicula")) {
			
			//Obtenemos las temporadas totales.
			System.out.println("Introduce las temporadas totales");
			int tempTotales = LecturaDeDatosConsola.lecturaNumero(1);
			
			
			/*
			 * Comprobamos llamando a los métodos si el valor del estado pedido anteriormente es visto o pendiente.
			 * Si es visto, estableceremos las temporadas vistas al máximo.
			 * Si es pendiente, estableceremos las temporadas vistas a 0.
			 * En cualquier otro caso le preguntaremos al usuario y obtendremos la cantidad de temporadas vistas.
			 */
			int tempVistas = 0;
			if(ComprobacionesInteracciones.comprobarEstadoVisto(estado)) {
				tempVistas = tempTotales;
				
			}else if(ComprobacionesInteracciones.comprobarEstadoPendiente(estado)) {
				tempVistas = 0;
			}else {
				//Reseteamos el valor de la variable para un siguiente bucle.
				campoValido = false;
				System.out.println("\nIntroduce las temporadas vistas");
				while(!campoValido) {
					tempVistas = LecturaDeDatosConsola.lecturaNumero(0);
					campoValido = ComprobacionesInteracciones.comprobarTemporadas(tempTotales, tempVistas);
				}
			}
			
			//Añadimos las temporadas vistas junto a su valor como atributo.
			nuevoElemento.setAttribute(TEMP_V_ATT, tempVistas + "");
			//Añadimos las temporadas totales junto a su valor como atributo.
			nuevoElemento.setAttribute(TEMP_T_ATT, tempTotales + "");
			
			
			//Entramos en un bucle para crear etiquetas de las temporadas totales y pedirle la cantidad de capítulos como atributo.
			for(int i = 1; i <= tempTotales; i++) {
				ModificacionesYCreaciones.CrearTemporada(i, tempVistas, estado, nuevoElemento);
			}
		}
		
		//Añadimos el nuevo elemento.
		elementoPadre.appendChild(nuevoElemento);
		
		transmorfarXMLIdentado();
	}
	
	/**
	 * Metodo encargado de Transformar el DOM modificado a XML.
	 * @throws TransformerException
	 */
	public static void transmorfarXMLIdentado() throws TransformerException {
		//Transformamos los datos y sobreescribimos el archivo.
				Transformer tf = TransformerFactory.newInstance().newTransformer();
				//Habilita la identación en el documento XML.
				tf.setOutputProperty(OutputKeys.INDENT, "yes");
				//Establece la cantidad de espacios de la identacion, pues por defecto es 0.
				tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(CreacionConfigYArchivoXML.getArchivoXML()));
				tf.transform(source, result);
	}
	
	public static void transmorfarXML() throws TransformerException {
		//Transformamos los datos y sobreescribimos el archivo.
				Transformer tf = TransformerFactory.newInstance().newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(CreacionConfigYArchivoXML.getArchivoXML()));
				tf.transform(source, result);
	}
	
	

	/**
	 * Metodo encargado de modificar los elementos del Nodo.
	 * @throws TransformerException
	 */
	public static void modificarElemento() throws TransformerException {
		//Variable encargada de salir del búcle.
		boolean salidaModificaciones = false;
		//Variable encargada de almacenar la cadena introducida por el usuario.
		String nombreIntroducido;
				
		Element elementoBuscado = null;
		
		while(!salidaModificaciones) {
			
			System.out.println("Introduce el nombre del elemento que quieres modificar.(Mínimo 3 letras.)");
			nombreIntroducido = LecturaDeDatosConsola.lecturaTexto().toUpperCase();
			
			//Si el nombre introducido tiene 3 o más caracteres se procederá a buscar entre los elementos.
			if(nombreIntroducido.length() >= 3) {
				
				//Llamamos al método que busque el nodo a partir del nombre obtenido y si lo encuentra lo almacene en elementoBuscado.
				elementoBuscado = busquedaDeNodo(nombreIntroducido);
				
				if(elementoBuscado != null) {
					
					mostrarNodoCompleto(elementoBuscado);
					
					//Comprobamos si el elemento que estamos modificando tiene temporadas, lo que querra decier que es una serie.
					if(elementoBuscado.hasAttribute(TEMP_T_ATT)) {
						salidaModificaciones = FuncionamientoMenus.funcionMenuModificaciones(elementoBuscado);
					}else {
						salidaModificaciones = FuncionamientoMenus.funcionMenuModificacionesPeliculas(elementoBuscado);
					}
					
					
					mostrarNodoCompleto(elementoBuscado);
					
				}else {
					System.out.println("Elemento no encontrado.");
					//Creamos un bucle con una variable para salir de este cuando el usuario introduzca un caracter válido.
					boolean caracterValido = false;
					while(!caracterValido) {
	
						System.out.println("\n¿Quieres buscar el elemento de nuevo?(S=Si N=NO)");
						char caracter = LecturaDeDatosConsola.lecturaTexto().toUpperCase().charAt(0);
						
						/*
						 * Si el valor introducido por el usuario es distinto a S o N, se le avisa que es un valor no válido
						 * y se le vuelve a pedir que introduzca otro valor. Si es N se saldra del bucle principal y del método,
						 * si es S volvera al incio para buscar otro elemento.
						 */
						if(caracter == 'N' || caracter == 'S') {
							salidaModificaciones = caracter == 'N'? true : false;
							caracterValido = true;
						}else if(caracter != 'S' || caracter != 'N') {
							System.out.println("Valor introducido invalido.");
						}
					}	
				}
				
			}else {
				System.out.println("Nombre demasiado corto.");
			}
			
		}
	}

	
	public static void eliminarElemento() throws TransformerException {
		//Variable encargada de salir del búcle.
		boolean salidaEliminarElemento = false;
		//Variable encargada de almacenar la cadena introducida por el usuario.
		String nombreIntroducido;
						
		Element elementoBuscado = null;
				
				while(!salidaEliminarElemento) {
					
					System.out.println("Introduce el nombre del elemento que quieres modificar.(Mínimo 3 letras.)");
					nombreIntroducido = LecturaDeDatosConsola.lecturaTexto().toUpperCase();
					
					//Si el nombre introducido tiene 3 o más caracteres se procederá a buscar entre los elementos.
					if(nombreIntroducido.length() >= 3) {
						
						//Llamamos al método que busque el nodo a partir del nombre obtenido y si lo encuentra lo almacene en elementoBuscado.
						elementoBuscado = busquedaDeNodo(nombreIntroducido);
						
						if(elementoBuscado != null) {
							
							mostrarNodoCompleto(elementoBuscado);
							
							boolean caracterValido = false;
							while(!caracterValido) {
								
								//Le preguntamos al usuario si está seguro de eliminar el elemento.
								System.out.println("\n¿Estás seguro de eliminar este elemento?(S=Si N=NO)");
								char caracter = LecturaDeDatosConsola.lecturaTexto().toUpperCase().charAt(0);
								
								/*
								 * Si el valor introducido por el usuario es distinto a S o N, se le avisa que es un valor no válido
								 * y se le vuelve a pedir que introduzca otro valor. Si es N no se borrará el elemento.
								 */
								if(caracter == 'N' || caracter == 'S') {
									caracterValido = true;
									if(caracter == 'S'){
										//Eliminamos el elemento.
										elementoPadre.removeChild(elementoBuscado);
										transmorfarXML();
										System.out.println("\nEl elemento se ha eliminado con exito.");
									}else {
										System.out.println("\nNo se ha eliminado ningún elemento.");
									}
								}else if(caracter != 'S' || caracter != 'N') {
									System.out.println("Valor introducido invalido.");
								}
							}	
							
							
							
						}else {
							
							System.out.println("Elemento no encontrado.");
							
						}
						
						//Creamos un bucle con una variable para salir de este cuando el usuario introduzca un caracter válido.
						boolean caracterValido = false;
						while(!caracterValido) {
		
							System.out.println("\n¿Quieres buscar el elemento de nuevo?(S=Si N=NO)");
							char caracter = LecturaDeDatosConsola.lecturaTexto().toUpperCase().charAt(0);
							
							/*
							 * Si el valor introducido por el usuario es distinto a S o N, se le avisa que es un valor no válido
							 * y se le vuelve a pedir que introduzca otro valor. Si es N se saldra del bucle principal y del método,
							 * si es S volvera al incio para buscar otro elemento.
							 */
							if(caracter == 'N' || caracter == 'S') {
								salidaEliminarElemento = caracter == 'N'? true : false;
								caracterValido = true;
							}else if(caracter != 'S' || caracter != 'N') {
								System.out.println("Valor introducido invalido.");
							}
						}	
						
					}else {
						
					
						System.out.println("Nombre demasiado corto.");
					}
				}
	}
	
	
	/**
	 * Método encargado de imprimir la informacíon de los nodos.
	 * @param elemento Recibe el elemento a imprimir.
	 */
	private static void mostrarNodoCompleto(Element elemento) {
		//Creamos un StringBuilder para almacenar todos los datos que se imprimiran.
		StringBuilder sb = new StringBuilder();
		//Llamamos al metodo que muestra los datos base del nodo y lo añadimos al StringBuffer.
		sb.append(mostrarNodoBase(elemento));
		
		//Comprobamos si el elemento tiene el atributo temporoda para saber si el elemento es cualquier tipo de serie o si es una pelicula.
			//Obtenemos el elemento sinopsis y añadimos su contenido al StringBuffer.
			Element sinopsis = (Element)elemento.getElementsByTagName(SINOPSIS).item(0);
			sb.append("\nSinopsis:\n").append(sinopsis.getTextContent());
			if(elemento.hasAttribute(TEMP_T_ATT)) {
			//Obtenemos todos los elementos temporadas y los guardamos en el StringBuffer junto a sus atributos.
			NodeList lista = elemento.getElementsByTagName(TEMPORADA);
			for(int i = 0; i < lista.getLength(); i++) {
				Element e = (Element) lista.item(i);
				sb.append("\nTemporada ").append(i+1).append("-> ");
				sb.append("Capitulos: ").append(e.getAttribute(CAP_V_ATT));
				sb.append("/").append(e.getAttribute(CAP_T_ATT));
			}
		}
		
		
		//Imprimimos por pantalla todos los datos.
		System.out.println(sb);

	}
	
	/**
	 * Obtiene los atributos base de todos los elementos y los almacena y devuelve en un StringBuffer.
	 * @param elemento Recibe el elemento del que obtener los atributos.
	 * @return
	 */
	private static StringBuilder mostrarNodoBase(Element elemento) {
		StringBuilder sb = new StringBuilder();
		sb.append("Elemento encontrado:\n");
		sb.append("Id: ").append(elemento.getAttribute(ID_ATT));
		sb.append("| Nombre: ").append(elemento.getAttribute(NOMBRE_ATT));
		sb.append("| Estado: ").append(elemento.getAttribute(ESTADO_ATT));
		
		//Comprobamos si el elemento tiene el atributo temporoda para saber si el elemento es cualquier tipo de serie o si es una pelicula.
		if(elemento.hasAttribute(TEMP_T_ATT)) {
			sb.append("| Temporadas Vistas: ").append(elemento.getAttribute(TEMP_V_ATT));
			sb.append("| Temporadas Totales: ").append(elemento.getAttribute(TEMP_T_ATT));
		}
		return sb;
	}
	
	private InteraccionesXML() {}

	
	public static String getEstadoAtt() {
		return ESTADO_ATT;
	}

	public static String getTempVAtt() {
		return TEMP_V_ATT;
	}

	public static String getTempTAtt() {
		return TEMP_T_ATT;
	}

	public static String getCapVAtt() {
		return CAP_V_ATT;
	}

	public static String getCapTAtt() {
		return CAP_T_ATT;
	}

	public static String getSinopsis() {
		return SINOPSIS;
	}

	public static String getTemporada() {
		return TEMPORADA;
	}

	public static Document getDoc() {
		return doc;
	}

	public static String getIdAtt() {
		return ID_ATT;
	}

}
