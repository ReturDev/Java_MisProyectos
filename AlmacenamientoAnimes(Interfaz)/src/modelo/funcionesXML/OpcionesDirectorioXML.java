package modelo.funcionesXML;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import modelo.enums.TiposPiezasAudiovisuales;

public class OpcionesDirectorioXML {

	
	/**
	 * Direcciones pruebas eclipse.
	 * ./src/resources/opciones.config
	 * ./src/resources
	 */
	
	//Dirección del archivo configuraci�n(Siempre ser� la misma).
	private static final Path ARCHIVO_CONFIG = Paths.get("./opciones.config");
	private static final String NOMBRE_DOC_DATOS = "/AlmacenamientoDatos.xml";
	public static final Path UBICACION_DATOS_DEFECTO = Paths.get(".").normalize().toAbsolutePath();
	private static final String NOMBRE_RAIZ = TiposPiezasAudiovisuales.class.getSimpleName().toLowerCase();
	
	private static Path rutaArchivo;
	
	
	public static void creacionArchivos() throws FileNotFoundException {
		
		try {
			creaArchivoConfig();
			leerRutaArchivoConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		comprobacionArchivoXML();
		
	}
	
	
	/**
	 * M�todo encargado de crear el archivo de configuraci�n en caso de no existir de antemano.
	 * @throws IOException
	 */
	private static void creaArchivoConfig() throws IOException {

		if(Files.notExists(ARCHIVO_CONFIG)) {
			
				//Crea el archivo en la ruta del programa.
				Files.createFile(ARCHIVO_CONFIG);
				//Configura el archivo para que est� oculto.
				Files.setAttribute(ARCHIVO_CONFIG,"dos:hidden",Boolean.TRUE,LinkOption.NOFOLLOW_LINKS);
				//Escribe en el archivo config la direcci�n del documento almacenamiento por defecto.
				Files.writeString(ARCHIVO_CONFIG, UBICACION_DATOS_DEFECTO + NOMBRE_DOC_DATOS, StandardOpenOption.WRITE);
		}

	}
	
	private static void leerRutaArchivoConfig() throws IOException {
		
		rutaArchivo = Paths.get(Files.readString(ARCHIVO_CONFIG));
		
	}
	
	private static void comprobacionArchivoXML() throws FileNotFoundException {
		
		if(!Files.isDirectory(rutaArchivo.getParent())) {
			throw new FileNotFoundException("El directorio donde se guarda el documento no existe.\n" + rutaArchivo);
		}
		
		if(Files.notExists(rutaArchivo)) {
			
			try {
				creacionArchivoXML();
			} catch (ParserConfigurationException | TransformerException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * Crea el archivo XML con el elemento raiz y todas las etiquetas de los tipos de elementos que se almacenaran
	 * {@link TiposPiezasAudiovisuales}
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private static void creacionArchivoXML() throws ParserConfigurationException, TransformerException {
		
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element elementoPadre = doc.createElement(NOMBRE_RAIZ);
		TiposPiezasAudiovisuales[] tipos = TiposPiezasAudiovisuales.values();
		
		for(int i = 0; i < tipos.length; i++) {
			
			Element elemento =  doc.createElement(tipos[i].toString().toLowerCase());
			
			elementoPadre.appendChild(elemento);
			
		}
		
		doc.appendChild(elementoPadre);
		
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		//Habilita la identación en el documento XML.
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		//Establece la cantidad de espacios de la identacion, pues por defecto es 0.
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(rutaArchivo.toFile());
		
		tf.transform(source, result);

	}


	public static void modificarConfig(String nuevaDireccion) throws IOException {
		
		String dirArchivo = nuevaDireccion + NOMBRE_DOC_DATOS;
		Files.writeString(ARCHIVO_CONFIG, dirArchivo,StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		
	}
	
	public static Path getRutaArchivo() {
		return rutaArchivo;
	}
	




	
}
