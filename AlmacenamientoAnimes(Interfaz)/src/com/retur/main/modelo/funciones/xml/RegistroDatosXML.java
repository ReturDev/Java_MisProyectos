package com.retur.main.modelo.funciones.xml;

import java.util.ArrayList;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import com.retur.main.modelo.elementos.*;
import com.retur.main.modelo.enums.*;


public class RegistroDatosXML {

	public static void introducirDatosPieza(PiezaAudiovisual pieza, TiposPiezasAudiovisuales tipo) throws TransformerException {
		
		
		Document doc = ObtencionDatosXML.getDOC();
		Element raiz = ObtencionDatosXML.getRAIZ();
		Element elementoPadre = (Element) raiz.getElementsByTagName(tipo.toString().toLowerCase()).item(0);
		Element nuevoElemento = doc.createElement(pieza.getClass().getSimpleName().toLowerCase());
		boolean modificar = false;
		
		if(pieza.getId() != 0) {
			
			modificar = true;
			nuevoElemento.setAttribute(TagsXML.ID.name().toLowerCase(), pieza.getId() + "");
			
		}else {
			
			nuevoElemento.setAttribute(TagsXML.ID.name().toLowerCase(), obtenerID(elementoPadre, pieza.getClass().getSimpleName().toLowerCase()));
		
		}
		
			
		
		nuevoElemento.setAttribute(TagsXML.ESTADO.name().toLowerCase(), pieza.getEstado().name());
		
		nuevoElemento.setAttribute(TagsXML.TITULO.name().toLowerCase(), pieza.getTitulo());
		
		Element sinopsis = doc.createElement(TagsXML.SINOPSIS.name().toLowerCase());
		
		sinopsis.setTextContent(pieza.getSinopsis());
		
		nuevoElemento.appendChild(sinopsis);
		
		if(pieza instanceof Serializable) {
			
			Serializable serializable = (Serializable) pieza;
			
			nuevoElemento.setAttribute(TagsXML.TEMP_VISTAS.name().toLowerCase(), serializable.getTemporadasVistas() + "");
			
			nuevoElemento.setAttribute(TagsXML.TEMP_TOTALES.name().toLowerCase(), serializable.getTemporadasTotales() + "");
			
			crearTemporadas(serializable.getTemporadas(), nuevoElemento);
		}
		
	
			
		if(modificar) {
			
			Element eModificar= localizarEModificar(pieza.getId(),  elementoPadre.getElementsByTagName(nuevoElemento.getNodeName()));
				
			elementoPadre.replaceChild(nuevoElemento, eModificar);
				
			transmorfarXML(doc);
				
		}else {
				
			elementoPadre.appendChild(nuevoElemento);
				
			transmorfarXMLIdentado(doc);
				
		}
			
		
	}
	

	
	
	private static String obtenerID(Element elementoPadre, String nombreElemento) {
		
		NodeList nodos = elementoPadre.getElementsByTagName(nombreElemento);
		
		String id = "1";
		
		if (nodos.getLength() != 0) {
			
			Element ultimoElemento = (Element) nodos.item(nodos.getLength()-1);
			
			if(ultimoElemento != null) {
				
				int nuevoID = Integer.parseInt(ultimoElemento.getAttribute(TagsXML.ID.name().toLowerCase())) + 1;
				id = nuevoID + "";
			
			}
			
		}
		
		
		return id;
	}
	
	private static void crearTemporadas(ArrayList<Temporada> temporadas, Element elementoPadre) {
		
		for(int i = 0; i < temporadas.size(); i++) {
			Element elemento = ObtencionDatosXML.getDOC().createElement(TagsXML.TEMPORADA.name().toLowerCase());
			Temporada temporada = temporadas.get(i);
			
			elemento.setAttribute(TagsXML.ID.name().toLowerCase(),temporada.getId() + "");
			
			elemento.setAttribute(TagsXML.CAP_VISTOS.name().toLowerCase(), temporada.getCapitulosVistos() + "");
			
			elemento.setAttribute(TagsXML.CAP_TOTALES.name().toLowerCase(), temporada.getCapitulosTotales() + "");
			
			elementoPadre.appendChild(elemento);
		}
		
	}
	
	/**
	 * Metodo encargado de Transformar el DOM creado a XML.
	 * @throws TransformerException
	 */
	public static void transmorfarXMLIdentado(Document doc) throws TransformerException {
		//Transformamos los datos y sobreescribimos el archivo.
				Transformer tf = TransformerFactory.newInstance().newTransformer();
				//Habilita la identación en el documento XML.
				tf.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(ObtencionDatosXML.getDOC());
				StreamResult result = new StreamResult(OpcionesDirectorioXML.getRutaArchivo().toFile());
				tf.transform(source, result);
	}
	
	/**
	 * Metodo encargado de Transformar el DOM modificado a XML sin identaci�n.
	 * @throws TransformerException
	 */
	public static void transmorfarXML(Document doc) throws TransformerException {
		//Transformamos los datos y sobreescribimos el archivo.
		Transformer tf = TransformerFactory.newInstance().newTransformer();
				
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(OpcionesDirectorioXML.getRutaArchivo().toFile());
		tf.transform(source, result);
		
	}
	
	
	private static Element localizarEModificar(int idBusqueda,NodeList nodos) {
		
		Element elementoModificar = null;
		
		for(int i = 0; i < nodos.getLength() && elementoModificar == null; i++) {
			
			Element elemento = (Element) nodos.item(i);
			
			if(elemento.getAttribute(TagsXML.ID.name().toLowerCase()).equals(idBusqueda + "")) {
				
				elementoModificar = elemento;
				
			}
			
		}
		
		return elementoModificar;
		
	}
}
