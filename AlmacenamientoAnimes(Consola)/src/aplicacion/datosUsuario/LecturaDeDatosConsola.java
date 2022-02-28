package aplicacion.datosUsuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class LecturaDeDatosConsola {
	
	private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
	
	private LecturaDeDatosConsola() {}
	
	/**
	 * Obtiene un número entero introducido por consola y lo devuelve.
	 * @param min Número mínimo(este incluido) válido introducido.
	 * @param max Número máximo(este no incluido) válido introducido.
	 * @return 
	 */
	public static int lecturaIndiceMenu(int min, int max) {
		int indice = 0;
		boolean datoCorrecto = false;
		while(!datoCorrecto) {
			try {
				indice = Integer.parseInt(READER.readLine());
				if(indice >= min && indice < max) {
					datoCorrecto = true;
				}else {
					System.out.println("No existe el menú al que quieres acceder.");
					System.out.print("\nIntroduce el índice de un menú válido:");
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println("Error: No has introducido un número.");
				System.out.print("\nIntroduce el índice de nuevo:");
			}
		}
		
		return indice;
	}
	
	/**
	 * Obtiene un número entero introducido por consola y lo devuelve.
	 * @param min Número mínimo(este incluido) válido introducido.
	 * @return
	 */
	public static int lecturaNumero(int min) {
		int indice = 0;
		boolean datoCorrecto = false;
		while(!datoCorrecto) {
			try {
				indice = Integer.parseInt(READER.readLine());
				if(indice >= min) {
					datoCorrecto = true;
				}else {
					System.out.println("Número no válido.");
					System.out.print("\nIntroduce un número válido:");
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println("Error: No has introducido un número.");
				System.out.print("\nIntroduce el número de nuevo:");
			}
		}
		
		return indice;
	}
	
	public static int lecturaNumero(int min,int max) {
		int indice = 0;
		boolean datoCorrecto = false;
		while(!datoCorrecto) {
			try {
				indice = Integer.parseInt(READER.readLine());
				if(indice >= min && indice < max) {
					datoCorrecto = true;
				}else {
					System.out.println("Número no válido.");
					System.out.print("\nIntroduce un número válido:");
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println("Error: No has introducido un número.");
				System.out.print("\nIntroduce el número de nuevo:");
			}
		}
		
		return indice;
	}
	
	/**
	 * Obtiene una cadena introducida por un usuario y la devuelve.
	 * @return
	 */
	public static String lecturaTexto() {
		String texto = null;
		boolean datoCorrecto = false;
		while(!datoCorrecto) {
			try {
				texto = READER.readLine();
				if(!texto.isEmpty()) {
					datoCorrecto = true;
				}else {
					System.out.println("No se ha introducido ningun valor.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return texto;
	}
	
	/**
	 * Metodo encargado de leer la sinopsis pudiendo introducir el usuario saltos de línea.
	 * @return
	 */
	public static StringBuilder lecturaSinopsis() {
		//Creamos un StringBuilder donde almacenar todas las líneas de texto introducidad por el usuario.
		StringBuilder sinopsis = new StringBuilder();
		//Variable para comprobar el valor del texto antes de añadirla al StringBuilder.
		String textoSinopsis = JOptionPane.showInputDialog("Introduce la Sinopsis"
					+ "(Intro para cada salto de linea y escribe fin para salir.)");
			
		/*
		 * Comprueba la longitud de la sinopsis y le da salto de línea al siguiente espacio que
		 * se encuentre pasados los 150 caracteres y lo añade al buffer.
		 */
		if(textoSinopsis.length() > 150) {
			int inicio = 0;
			int fin = 150;
			while((fin = textoSinopsis.indexOf(" ", fin)) != -1) {
				sinopsis.append(textoSinopsis.substring(inicio, fin)).append("\n");
				inicio = fin + 1;
				fin += 150;
			}
			sinopsis.append(textoSinopsis.substring(inicio));
		}else {
			sinopsis.append(textoSinopsis);
			sinopsis.append("\n");
		}
		
		return sinopsis;
	}
	
	public static void cerrarFlujo() {
		try {
			READER.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
