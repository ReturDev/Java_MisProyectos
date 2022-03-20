package programa.principal;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import aplicacion.datosUsuario.CreacionConfigYArchivoXML;
import aplicacion.datosUsuario.LecturaDeDatosConsola;
import aplicacion.menu.FuncionamientoMenus;

public class Ejecucion {
	
	
	
	
	public static void main(String[] args) {
		boolean finDelPrograma = false;
		try {
			CreacionConfigYArchivoXML.creacionArchivoConfig();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while(!finDelPrograma) {
			try {
				finDelPrograma = FuncionamientoMenus.funcionMenuPrincipal();
			} catch (TransformerException e) {
		
				e.printStackTrace();
			}
		}
		
		LecturaDeDatosConsola.cerrarFlujo();
	}
}
