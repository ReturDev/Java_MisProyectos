package aplicacion.menu;

public class VisualizacionMenus {
	
	/**
	 * Imprime en consola la estructura del menú principal.
	 */
	protected static void menuPrincipal() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n****Menú Principal****\n");
		sb.append("1. Menú Animes.\n");
		sb.append("2. Menú Peliculas Animes.\n");
		sb.append("3. Menú Series.\n");
		sb.append("4. Menú Peliculas.\n");
		sb.append("5. Salir.\n");
		sb.append("\nIntroduce el índice del menu al que quieres entrar:");
		
		System.out.println(sb);
	}
	
	
	/**
	 * Imprime en consola la estuctura del último menú.
	 * @param categoriaMenu Establece el nombre del menú en el que nos encontramos.
	 */
	protected static void menuComun(String categoriaMenu) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n****Menú ").append(categoriaMenu).append("****\n");
		sb.append("1. Busqueda por nombre.\n");
		sb.append("2. Busqueda por estado.\n");
		sb.append("3. Busqueda aleatoria.\n");
		sb.append("4. Busqueda por id.\n");
		sb.append("5. Mostrar todos.\n");
		sb.append("6. Añadir nuevos elementos.\n");
		sb.append("7. Modificar elementos.\n");
		sb.append("8. Eliminar elemento.\n");
		sb.append("9. Menú anterior.\n");
		sb.append("10. Salir\n");
		sb.append("\nIntroduce el índice de la acción que quieres realizar:");
		
		System.out.println(sb);
		
	}
	
	/**
	 * Método encargado de imprimir el menú de modificaciones para todos los elementos que contengan temporadas.
	 */
	protected static void menuModificaciones() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n****Menú Modificaciones****\n");
		sb.append("1. Modificar Estado.\n");
		sb.append("2. Modificar Temporadas Vistas.\n");
		sb.append("3. Modificar Temporadas Totales.\n");
		sb.append("4. Modificar Sinopsis.\n");
		sb.append("5. Modificar Capitulos Vistos.\n");
		sb.append("6. Modificar Capitulos Totales.\n");
		sb.append("7. Salir del menu de modificaciones(Guardar cambios).\n");
		sb.append("\nIntroduce el índice de la acción que quieres realizar:");
		
		System.out.println(sb);
	}
	
	protected static void menuModificacionesPeliculas() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n****Menú Modificaciones****\n");
		sb.append("1. Modificar Estado.\n");
		sb.append("2. Modificar Sinopsis.\n");
		sb.append("3. Salir del menu de modificaciones(Guardar cambios).\n");
	}
	
	private VisualizacionMenus() {}
}
