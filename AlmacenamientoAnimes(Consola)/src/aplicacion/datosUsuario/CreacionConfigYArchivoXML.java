package aplicacion.datosUsuario;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreacionConfigYArchivoXML {

	private static final Path DIRCONFIG = Paths.get("C:/Users",System.getenv("USERNAME"),"/AppData/Roaming/DatosSeries.config");
	private static final String NOMBREARCHIVO = "/DatosSeries.xml";
	private static String localizacionArchivo; 
	private static String archivoXML;
	
	
	
	/**
	 * Comprueba si existe el archivo config, si no existe lo crea.
	 * @throws IOException
	 */
	public static void creacionArchivoConfig() throws IOException {
		
		//Compueba si el archivo config existe
		if(Files.notExists(DIRCONFIG)) {
			//Crea el archivo de configuración.
			Files.createFile(DIRCONFIG);
			
			//Le añade un atributo al archivo configuración, en este caso lo hace oculto.
            Files.setAttribute(DIRCONFIG,"dos:hidden",Boolean.TRUE,LinkOption.NOFOLLOW_LINKS);            	
            
            
            //Pide al usuario que introduzca la dirección donde almacenar el archivo XML y la almacena.
			System.out.println("Escribe la dirección donde guardar los datos de tus series.");
			Files.writeString(DIRCONFIG, LecturaDeDatosConsola.lecturaTexto(), StandardOpenOption.WRITE);
		}
		
		/*
		 * Guarda la dirección del archivo uniendo la dirección obtenida del archivo configuración y el nombre del archivo
		 * predeterminado.
		 */
				
		//Obtiene la dirección situada dentro del archivo configuración.
		obtenerDirArchivoXML();
		
		archivoXML = localizacionArchivo + NOMBREARCHIVO;
		
		//Comprueba que el archivo xml no exista, si es así lo crea.
		if(Files.notExists(Paths.get(archivoXML))) {
			crearArchivoXML();
		}
		
		
	}
	
	
	/**
	 * Obtiene la localizacion del archivo al leer el archivo configuración.
	 * @throws IOException
	 */
	private static void obtenerDirArchivoXML() throws IOException {
		localizacionArchivo = Files.readString(DIRCONFIG);
	}
	
	/**
	 * Método encargado de crear un archivo XML a partir de una dirección obtenida anteriormente con el método
	 * {@link #obtenerDirArchivoXML()} 
	 */
	private static void crearArchivoXML() {
		File f = new File(localizacionArchivo + NOMBREARCHIVO);
		try {
			//Creamos un documento y un elemento raiz.
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element raiz = doc.createElement("raiz");
			doc.appendChild(raiz);
			
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			
			//Habilita la identación en el documento XML.
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			//Establece la cantidad de espacios de la identacion, pues por defecto es 0.
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(f);
			
			tf.transform(source, result);
			
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}

	public static String getArchivoXML() {
		return archivoXML;
	}
	
	private CreacionConfigYArchivoXML() {}
}
