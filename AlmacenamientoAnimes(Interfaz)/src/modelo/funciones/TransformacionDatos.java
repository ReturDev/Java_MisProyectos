package modelo.funciones;

import java.util.ArrayList;
import java.util.HashSet;

import org.w3c.dom.*;

import modelo.clases.*;
import modelo.enums.*;

public class TransformacionDatos {

	/**
	 * M�todo encargado de transformar una lista de nodos del xml a un ArrayList de PiezasAudiovisuales.
	 * @param lista Lista de nodo perteneciente al XML
	 * @param tipo Tipo de Pieza Audiovisual al que pertenecen los nodos.
	 * @return
	 */
	public static HashSet<PiezaAudiovisual> datosXMLADatos(NodeList lista, TiposPiezasAudiovisuales tipo){
		
		//Variable para almacenar las Piezas Audiovisuales.
		HashSet<PiezaAudiovisual> piezas = new HashSet<PiezaAudiovisual>();
			
			//Se recorre toda la lista de nodos.
			for(int i = 0; i < lista.getLength(); i++) {
				
				//Comprueba que el nodo recorrido sea un nodo elemento.
				if(lista.item(i).getNodeType() == Node.ELEMENT_NODE) {
					//Se almacena nodo transformandolo en elemento para trabajar con �l.
					Element elemento = (Element) lista.item(i);
					
					//Se obtiene la instancia del tipo exacto de Pieza Audiovisual.
					PiezaAudiovisual claseElemento = FuncionesApoyoControladores.crearClaseMedianteTipo(tipo);
					//Se obtiene el id del elemento
					claseElemento.setId(Integer.parseInt(elemento.getAttribute(TagsXML.ID.name().toLowerCase())));
					//Se obtiene el titulo del elemento.
					claseElemento.setTitulo(elemento.getAttribute(TagsXML.TITULO.name().toLowerCase()));
					//Se obtiene el estado del elemento, a trav�s del m�todo encargado de transformal el texto al valor del enum.
					claseElemento.setEstado(obtenerEstadoXML(elemento.getAttribute(TagsXML.ESTADO.name().toLowerCase())));
					//Se obtiene la sinopsis del elemento a trav�s del m�todo encargado.
					claseElemento.setSinopsis(obtenerSinopsisXML(elemento));
				
					//Se comprueba si es de tipo serializable
					if(claseElemento instanceof Serializable) {
						
						//Se castea a serializable para dar valor a los campos que faltan
						Serializable serializable = (Serializable) claseElemento;
						//Se le da valor a las temporadas totales.
						serializable.setTemporadasTotales(Integer.parseInt(elemento.getAttribute(TagsXML.TEMP_TOTALES.name().toLowerCase())));
						//Se le da valor a las temporadas vistas.
						serializable.setTemporadasVistas(Integer.parseInt(elemento.getAttribute(TagsXML.TEMP_VISTAS.name().toLowerCase())));
						//Se le asigna las temporadas a trav�s del m�todo encargado de obtenerlas.
						serializable.setTemporadas(obtenerTemporadas(elemento));
						
					}
					
					//Se a�ade el elemento a la lista.
					piezas.add(claseElemento);
					
				}
				
			}
			
		return piezas;
		
	}
	
	//Continuar comentando y desde aqui.
	
	/**
	 * M�todo encargado de obtener las temporadas del archivo XML.
	 * @param elemento Recibe el elemento al que le pertenecen las temporadas.
	 * @return
	 */
	private static ArrayList<Temporada> obtenerTemporadas(Element elemento) {
		//Se obtienen los elementos temporada.
		NodeList list =  elemento.getElementsByTagName(TagsXML.TEMPORADA.name().toLowerCase());
		//Variable para guardar los objetos temporadas.
		ArrayList<Temporada> temporadas = new ArrayList<Temporada>();
		//Se recorre la lista de nodos.
		for(int i = 0; i < list.getLength(); i++) {
			//Se trnsforma cada nodo en elemento para trabajar con el.
			Element element = (Element) list.item(i);
			
			//Se obtiene el id de la temporada.
			int id = Integer.parseInt(element.getAttribute(TagsXML.ID.name().toLowerCase()));
			//Se obtiene los capitulos totales de la temporada.
			int capTotales = Integer.parseInt(element.getAttribute(TagsXML.CAP_TOTALES.name().toLowerCase()));
			//Se obtiene los capitulos vistos de la temporada.
			int capVistos = Integer.parseInt(element.getAttribute(TagsXML.CAP_VISTOS.name().toLowerCase()));
			//Se a�ade la temporada que los almacena.
			temporadas.add(new Temporada(id, capTotales, capVistos));
		}
		
		return temporadas;
	}
	
	
	/**
	 * M�todo encargado de transformar el valor del atributo estado en el XML a un valor del enum Estados.
	 * @param stringEstado Recibe el valor del XML
	 * @return
	 */
	private static Estados obtenerEstadoXML(String stringEstado) {
		
		Estados estado = null;
		
		if(stringEstado.toUpperCase().equals(Estados.PENDIENTE.toString())){
			estado = Estados.PENDIENTE;
		}else if(stringEstado.toUpperCase().equals(Estados.VISTO.toString())){
			estado = Estados.VISTO;
		}else if(stringEstado.toUpperCase().equals(Estados.SIGUIENDO.toString())){
			estado = Estados.SIGUIENDO;
		}else {
			estado = Estados.ABANDONADO;
		}
		
		return estado;
	}
	
	private static String obtenerSinopsisXML(Element elemento) {
		
		Element element = (Element) elemento.getElementsByTagName(TagsXML.SINOPSIS.toString().toLowerCase()).item(0);
		
		String sinopsis = element.getTextContent();
		
		return sinopsis;
		
	}
	
	
	
}
