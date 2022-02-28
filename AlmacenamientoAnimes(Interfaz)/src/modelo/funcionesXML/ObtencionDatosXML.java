package modelo.funcionesXML;

import java.io.IOException;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import modelo.clases.*;
import modelo.enums.*;
import modelo.funciones.TransformacionDatos;

public class ObtencionDatosXML {
	
	private static Document DOC;
	private static Element RAIZ;
	
	static {
		try {

			DOC = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(OpcionesDirectorioXML.getRutaArchivo().toFile());
			RAIZ = (Element) DOC.getFirstChild();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método encargado de obtener los elementos de tipo de pieza audiovisual.
	 * @param tipo Tipo de Pieza Audiovisual de la que se obtendrá sus elementos hijos.
	 * @return Devuelve la lista de elementos.
	 */
	private static NodeList obtenerElementos(TiposPiezasAudiovisuales tipo) {
		
		NodeList list = null;
		//Obtenemos el nodo padre, que es el Tipo obtenido por parámetro en minuscula.
		Node nodoPadre = RAIZ.getElementsByTagName(tipo.toString().toLowerCase()).item(0);
		//Combrobamos que el nodoPadre exista en el documento.
		if(nodoPadre != null) {
			//Se almacena todos los elementos hijos.
			list = nodoPadre.getChildNodes();
		}
		
		return list;
	}
	
	/**
	 * Obtiene los elementos del tipo y devuelve una lista de PiezasAudiovisuales.
	 * @param tipo Recibe el tipo de las Piezas Audiovisuales.
	 * @return
	 */
	public static HashSet<PiezaAudiovisual> obtenerListaElementosTipo(TiposPiezasAudiovisuales tipo){
		
		NodeList lista = obtenerElementos(tipo);
		HashSet<PiezaAudiovisual> piezasAudiovisuales = null;
		if(lista != null) {
			
			piezasAudiovisuales = TransformacionDatos.datosXMLADatos(lista, tipo);
			
		}
		
		return piezasAudiovisuales;
		
	}

	public static Document getDOC() {
		return DOC;
	}

	public static Element getRAIZ() {
		return RAIZ;
	}
	
	
	

	
}
