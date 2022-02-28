package aplicacion.interaccionXML;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import aplicacion.datosUsuario.LecturaDeDatosConsola;

public class ModificacionesYCreaciones {
	
	
	/**
	 * Método encargado de modificar el Estado de un nodo.
	 * @param elementoModificar Recibe el elemento que modificar.
	 */
	public static void modificarEstado(Element elementoModificar) {
		//Variable utilizada para salir del bucle una vez que el valor introducido por el usuario sea correcto.
		boolean estadoCorrecto = false;
		String estado = null;
		
		while (!estadoCorrecto) {
		
			System.out.println("\nIntroduce el nuevo valor del Estado(Pendiente, Visto, Siguiendo, Dropeado).");
			// Obtenemos el valor del usuario y lo transformamos a mayusculas.
			estado = LecturaDeDatosConsola.lecturaTexto().toUpperCase();

			/*
			 * Llamamos al metodo para que compruebe que el valor de estado es válido. Si lo
			 * es cambiara el valor de estadoCorrecto y saldrá del bucle.
			 */
			estadoCorrecto = ComprobacionesInteracciones.comprobarEstado(estado);
		}
		
		/*
		 * Comprobamos si el valor del estado introducido es Visto o Pendiente.
		 * Si es Visto, cambiaremos todas las temporadas vistas y todos los capítulos vistos al máximo.
		 * Si es Pendiente, cambiaremos todas las temporadas vistas y todos los capítulos vistos a 0.
		 */
		if (ComprobacionesInteracciones.comprobarEstadoVisto(estado)) {
			//Establecemos el valor de las Temporadas Vistas y le damos el valor de las Temporadas Totales. 
			elementoModificar.setAttribute(InteraccionesXML.getTempVAtt(),
					elementoModificar.getAttribute(InteraccionesXML.getTempTAtt()));
			
			//Obtenemos todas las temporadas pertenecientes al elemento.
			NodeList temporadas = elementoModificar.getElementsByTagName(InteraccionesXML.getTemporada());
			
			//Recorremos cada Temporada para cambiar el valor de los Capitulos Vistos.
			for (int i = 0; i < temporadas.getLength(); i++) {
				//Obtenemos cada temporada y la transformamos a elemento.
				Element temporada = (Element) temporadas.item(i);
				
				
				//Cambiamos el valor de los Capitulos Vistos y le damos el valor de los Capitulos Totales.
				temporada.setAttribute(InteraccionesXML.getCapVAtt(),
						temporada.getAttribute(InteraccionesXML.getCapTAtt()));
			}

		} else if (ComprobacionesInteracciones.comprobarEstadoPendiente(estado)) {
			//Establecemos el valor de las Temporadas Vistas a 0.
			elementoModificar.setAttribute(InteraccionesXML.getTempVAtt(), "0");
			//Obtenemos todas las temporadas pertenecientes al elemento.
			NodeList temporadas = elementoModificar.getElementsByTagName(InteraccionesXML.getTemporada());
			for (int i = 0; i < temporadas.getLength(); i++) {
				//Obtenemos cada temporada y la transformamos a elemento.
				Element temporada = (Element) temporadas.item(i);
				//Cambiamos el valor de los Capitulos Vistos y le damos el valor de los Capitulos Totales.
				temporada.setAttribute(InteraccionesXML.getCapVAtt(), "0");
			}
		}
		
		//Establecemos el valor introducido por el usuario al atributo estado.
		elementoModificar.setAttribute(InteraccionesXML.getEstadoAtt(), estado);

	}

	/**
	 * Método encargado de modificar las Temporadas Vista.
	 * @param elementoModificar Recibe el elemento que modificar.
	 */
	public static void modificarTempVistas(Element elementoModificar) {
		//Variable utilizada para salir del bucle una vez que el valor introducido por el usuario sea correcto.
		boolean temporadasCorrectas = false;
		//Creamos la variable para guardar el valor introducido por el usuario.
		int temporadasVistas = 0;
		//Obtenemos el valor de las Temporadas Totales.
		int temporadasTotales = Integer.parseInt(elementoModificar.getAttribute(InteraccionesXML.getTempTAtt()));;
		
		
		while (!temporadasCorrectas) {
			System.out.println("\nIntroduce el nuevo valor de las Temporadas Vistas.");
			temporadasVistas = LecturaDeDatosConsola.lecturaNumero(0);
			//Llamamos al método para comprobar si el valor de temporadas es válido.
			temporadasCorrectas = ComprobacionesInteracciones.comprobarTemporadas(temporadasTotales, temporadasVistas);
			
		}
		
		//Obtenemos todas las temporadas pertenecientes al elemento.
		NodeList temporadas = elementoModificar.getElementsByTagName(InteraccionesXML.getTemporada());
		
		//Comprobamos si el valor del Estado es Pendiente y el valor de las temporadas vistas introducido es 1 o más.
		if(ComprobacionesInteracciones.comprobarEstadoPendiente(elementoModificar.getAttribute(InteraccionesXML.getEstadoAtt())) &&
				temporadasVistas > 0) {
			//Si se cumple la condición cambiamos el valor del Estado a Siguiendo.
			elementoModificar.setAttribute(InteraccionesXML.getEstadoAtt(), ComprobacionesInteracciones.geteSiguiendo());
		}
		/*
		 * Recorremos las temporadas y cambiamos el valor de los Capitulos Vistos al máximo de la misma cantidad de temporadas que
		 * se han establecido como vistas. 
		 */
		for(int i = 0; i < temporadasVistas; i++) {
			//Obtenemos cada temporada y la transformamos a elemento.
			Element temporada = (Element) temporadas.item(i);
			//Cambiamos el valor de los Capitulos Vistos y le damos el valor de los Capitulos Totales.
			temporada.setAttribute(InteraccionesXML.getCapVAtt(),
					temporada.getAttribute(InteraccionesXML.getCapTAtt()));
		}
		
		//Si las Temporadas Vistas son iguales que las Temporadas Totales cambiamos el valor del Estado del nodo a Visto.
		if(temporadasVistas == temporadasTotales) {
			elementoModificar.setAttribute(InteraccionesXML.getEstadoAtt(),ComprobacionesInteracciones.geteVisto());
		}
		
		//Asignamos el nuevo valor de las Temporadas Vistas.
		elementoModificar.setAttribute(InteraccionesXML.getTempVAtt(), temporadasVistas + "");
		
	}
	
	/**
	 * Método encargado de modificar las Temporadas Totales.
	 * @param elementoModificar Recibe el elemento que modificar.
	 * @throws TransformerException 
	 */
	public static void modificarTempTotales(Element elementoModificar) throws TransformerException {
		
		//Pedimos y obtenemos el valor del usuario para las Temporadas Totales.
		System.out.println("\nIntroduce el nuevo valor de las Temporadas Totales.");
		int tempTotales = LecturaDeDatosConsola.lecturaNumero(1);
		
		//Obtenemos el valor de las Temporadas Totales antes de ser modificada por el usuario.
		int tempTotalesAnteriores = Integer.parseInt(elementoModificar.getAttribute(InteraccionesXML.getTempTAtt()));
		
		//Comprobamos si el nuevo valor de las Temporadas Totales es menor que el anterior.
		if (tempTotales < tempTotalesAnteriores) {
			
			//Obtenemos todas las temporadas.
			NodeList temporadas = elementoModificar.getElementsByTagName(InteraccionesXML.getTemporada());
			/*
			 * Recorremos desde el final la cantidad de temporadas de diferendia entre el nuevo valor y el anterios y borramos las temporadas
			 * que quedarian sobrantes.
			 */
			for (int i = 0; i < (tempTotalesAnteriores - tempTotales); i++) {
				//Obtenemos la temporada.
				Element temporada = (Element) temporadas.item(temporadas.getLength() - 1);
				//Eliminamos la temporada
				
				elementoModificar.removeChild(temporada);
			}
			
			int tempVistas = Integer.parseInt(elementoModificar.getAttribute(InteraccionesXML.getTempVAtt()));
			if(tempTotales < tempVistas) {
				elementoModificar.setAttribute(InteraccionesXML.getTempVAtt(), tempTotales + "");
			}
			
		//Comprobamos si el nuevo valor es mayor que el anterior.
		} else if (tempTotales > tempTotalesAnteriores) {
			/*
			 * Obtenemos el Estado del nodo para realizar la comprobación de si su valor era Visto, si es así se cambiara el valor del atributo
			 * y támbien de esta variable, porque la necesitaremos para pasarla por parámetro al método de creación de temporadas y si
			 * no contemplamos la opción de que estuviera en visto y se haya cambiado al insertar nuevas temporadas, al llamar al método, se 
			 * crearan las nuevas temporadas con los valores al máximo respetando el estado Visto. 
			 */
			String estado = elementoModificar.getAttribute(InteraccionesXML.getEstadoAtt());
			//Compribamos si el valor que tenía el Estado era Visto, si es así lo cambiamos a Siguiendo.
			if(ComprobacionesInteracciones.comprobarEstadoVisto(estado)) {
				estado = ComprobacionesInteracciones.geteSiguiendo();
				elementoModificar.setAttribute(InteraccionesXML.getEstadoAtt(), estado);
			}
			//Por cada unidad de diferencia del nuevo valor con el valor anterior creamos un nuevo elemento de temporada.
			for (int i = 1; i <= tempTotales - tempTotalesAnteriores; i++) {
				/*
				 * Llamamos al método para crear temporadas al que le pasamos el valor del Total de Temporadas Anteriors más el iterador
				 * para establecer las id de las temporadas, le pasamos el estado en el que se encontraría el nodo y el elemento que estamos 
				 * modificando.
				 */
				CrearTemporada(tempTotalesAnteriores + i, 0, estado, elementoModificar);
			}
		}
		
		//Asignamos el nuevo valor al atributo Temporadas Totales.
		elementoModificar.setAttribute(InteraccionesXML.getTempTAtt(), tempTotales + "");

	}
	
	/**
	 * Método encargado de modificar la Sinopsis.
	 * @param elementoModificar Recibe el elemento que modificar.
	 */
	public static void modificarSinopsis(Element elementoModificar) {
		//Obtenemos la etiqueta sinopsis.
		Element sinopsis = (Element) elementoModificar.getElementsByTagName(InteraccionesXML.getSinopsis()).item(0);
		//Modificamos el texto de la etiqueta.
		sinopsis.setTextContent(LecturaDeDatosConsola.lecturaSinopsis().toString());
	}
	
	
	public static void modificarCapVistos(Element elementoModificar) {
		boolean capitulosCorrectos = false;
		NodeList temporadas = elementoModificar.getElementsByTagName(InteraccionesXML.getTemporada());
		System.out.println("¿De que temporada quieres modificar los capitulos?(Escribe su número)");
		int numeroTemporada = LecturaDeDatosConsola.lecturaNumero(1,temporadas.getLength() + 1);
		int capVistos = 0;
		Element temporada = (Element) temporadas.item(numeroTemporada -1);
		int capTotales = Integer.parseInt(temporada.getAttribute(InteraccionesXML.getCapTAtt()));
		
		while(!capitulosCorrectos) {
			System.out.println("\nIntroduce el nuevo valor de los Capitulos Vistos.");
			capVistos = LecturaDeDatosConsola.lecturaNumero(0);
			
			capitulosCorrectos = ComprobacionesInteracciones.comprobarCapitulos(capTotales, capVistos);
		}
		
		if(capVistos == capTotales) {
			int tempVistas = Integer.parseInt(elementoModificar.getAttribute(InteraccionesXML.getTempVAtt())) + 1;
			elementoModificar.setAttribute(InteraccionesXML.getTempVAtt(), tempVistas + "");
			int tempTotales = Integer.parseInt(elementoModificar.getAttribute(InteraccionesXML.getTempTAtt()));
			if(tempVistas == tempTotales) {
				elementoModificar.setAttribute(InteraccionesXML.getEstadoAtt(), ComprobacionesInteracciones.geteVisto());
			}
		}
		
		temporada.setAttribute(InteraccionesXML.getCapVAtt(), capVistos + "");
		
			
	}
	
	public static void modificarCapTotales(Element elementoModificar) {
		
		NodeList temporadas = elementoModificar.getElementsByTagName(InteraccionesXML.getTemporada());
		System.out.println("¿De que temporada quieres modificar los capitulos?(Escribe su número)");
		int numeroTemporada = LecturaDeDatosConsola.lecturaNumero(1,temporadas.getLength() + 1);
		
		Element temporada = (Element) temporadas.item(numeroTemporada - 1);
		
		System.out.println("\nIntroduce el nuevo valor de los Capitulos Totales.");
		int capTotales = LecturaDeDatosConsola.lecturaNumero(1);
		
		int capVistos = Integer.parseInt(temporada.getAttribute(InteraccionesXML.getCapVAtt()));
		int capTotalesAnteriores = Integer.parseInt(temporada.getAttribute(InteraccionesXML.getCapTAtt()));
		
		if(capTotales < capVistos) {
			temporada.setAttribute(InteraccionesXML.getCapVAtt(), "0");
		}else if(capTotales > capTotalesAnteriores && capTotalesAnteriores == capVistos) {
			int tempVistas = Integer.parseInt(elementoModificar.getAttribute(InteraccionesXML.getTempVAtt())) - 1;
			elementoModificar.setAttribute(InteraccionesXML.getTempVAtt(), tempVistas + "");
			if(ComprobacionesInteracciones.comprobarEstadoVisto(elementoModificar.getAttribute(InteraccionesXML.getEstadoAtt()))) {
				elementoModificar.setAttribute(InteraccionesXML.getEstadoAtt(), ComprobacionesInteracciones.geteSiguiendo());
			}
				
		}
		
		temporada.setAttribute(InteraccionesXML.getCapTAtt(), capTotales + "");

		
		
	}
	
	protected static void CrearTemporada(int idTemporada, int tempVistas, String estado, Element elemento) throws TransformerException {
		// Creamos una etiqueta temporada.
		Element temporada = InteraccionesXML.getDoc().createElement(InteraccionesXML.getTemporada());
		// Le damos un atributo id con el número de temporada correspondiente utilizando
		// el iterador del bucle.
		temporada.setAttribute(InteraccionesXML.getIdAtt(), idTemporada + "");

		// Pedimos y almacenamos el valor de capitulos totales de la temporada.
		System.out.println("Introduce los capitulos totales de la temporada " + idTemporada);
		int capTotales = LecturaDeDatosConsola.lecturaNumero(1);

		/*
		 * Comprobamos llamando a los métodos si el valor del estado pedido
		 * anteriormente es visto o pendiente. Si es visto, estableceremos los capítulos
		 * vistos al máximo. Si es pendiente, estableceremos los capítulos vistos a 0.
		 * En cualquier otro caso le preguntaremos al usuario y obtendremos la cantidad
		 * de capítulos vistos.
		 */
		int capVistos = 0;
		if ((tempVistas > 0 && tempVistas >= idTemporada) || ComprobacionesInteracciones.comprobarEstadoVisto(estado)) {
			capVistos = capTotales;
		} else if (ComprobacionesInteracciones.comprobarEstadoPendiente(estado)) {
			capVistos = 0;
		} else {
			boolean campoValido = false;
			System.out.println("Introduce los capitulos vistos de la temporada " + idTemporada);
			while (!campoValido) {
				capVistos = LecturaDeDatosConsola.lecturaNumero(0);
				campoValido = ComprobacionesInteracciones.comprobarCapitulos(capTotales, capVistos);
			}
		}
		// Añadimos los capitulos totales y vistos como atributos de la etiqueta
		// temporada con sus respectivos valores.
		temporada.setAttribute(InteraccionesXML.getCapVAtt(), capVistos + "");
		temporada.setAttribute(InteraccionesXML.getCapTAtt(), capTotales + "");
		// Añadimos la etiqueta temporada como hija del nuevo elemento.
		elemento.appendChild(temporada);
		
		InteraccionesXML.transmorfarXMLIdentado();
	}

	protected static void CrearSinopsis(Element elemento) {
		/*
		 * Creamos un StringBuilder donde almacenaremos la sinopsis del elemento y llamamos al método que se encargará
		 * de ir pidiendo al usuario el contenido.
		 */
		StringBuilder sinopsis = LecturaDeDatosConsola.lecturaSinopsis();
		//Creamos la etiqueta.
		Element tagSinopsis = InteraccionesXML.getDoc().createElement(InteraccionesXML.getSinopsis());
		//Añadimos la sinopsis como contenido de texto a la etiqueta.
		tagSinopsis.setTextContent(sinopsis.toString());
		//Añadimos la etiqueta sinopsis como hijo del nuevo elemento.
		elemento.appendChild(tagSinopsis);
	}
}
